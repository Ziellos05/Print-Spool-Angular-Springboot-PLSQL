-- Creando body del paquete

CREATE OR REPLACE
PACKAGE
	BODY app_datos_impresion.print_spool AS
-- Función que calcula un rate básico de entrega de las facturas

/* Esta función calcula un número entre 1001 y 999999 para cada dirección, generando el mismo número para cada factura
 * ubicada entre una calle con una avenida dada, además el aumento de los números generados se da siguiendo una secuencia,
 * de tal manera que el delivery rate genera un órden de entrega contínuo. Existen dos restricciones para este algoritmo:
 * 1. Solo sirve para direcciones dadas por St XXX # XXX XX o Av XXX # XXX XX.
 * 2. Solo sirve para calles y avenidas con máximo 3 cifras, por lo que aplica para cualquier ciudad colombiana. */
	FUNCTION del_rate(address IN VARCHAR2)  
RETURN NUMBER 
IS 	
	st NUMBER;

av NUMBER;

avV VARCHAR2(100);

rate NUMBER;
BEGIN
	IF
		REGEXP_SUBSTR(address,
	'[^ ]+',
	1,
	1) = 'St' THEN
		st := REGEXP_SUBSTR(address,
	'[^ ]+',
	1,
	2);

av := REGEXP_SUBSTR(address,
'[^ ]+',
1,
4);
ELSE
		av := REGEXP_SUBSTR(address,
'[^ ]+',
1,
2);

st := REGEXP_SUBSTR(address,
'[^ ]+',
1,
4);
END
IF;

IF
	MOD(st,
	2) = 0 THEN
		av := ABS(av-999);
END
IF;

avV := TO_CHAR(av);

IF
	LENGTH(avV) = 1 THEN
		st := st * 100;

ELSIF LENGTH(avV) = 2 THEN
		st := st * 10;
END
IF;

avV := CONCAT(TO_CHAR(st),
avV);

rate := TO_NUMBER(avV);

RETURN rate;

EXCEPTION
WHEN value_error THEN
      dbms_output.put_line('Bad input value');
WHEN program_error THEN
      dbms_output.put_line('Internal problem in function execution');
END del_rate;
-- Función que retorna el costo para cada factura

/* Esta función recupera el costo por centímetro cúbico de cada estrato y los metros cúbicos de cada consumo y entrega
 * el resultado de la multiplicación entre estos 2 para agregarlo a la factura generada */

FUNCTION cal_amount(cost_per_cm IN NUMBER,
cubic_meters NUMBER)  
RETURN NUMBER 
IS 
    amount NUMBER;
BEGIN 
	amount := cost_per_cm * cubic_meters;

RETURN amount;

EXCEPTION
WHEN invalid_number THEN
      dbms_output.put_line('Invalid input, insert NUMBER');
WHEN value_error THEN
      dbms_output.put_line('Bad input value');
WHEN program_error THEN
      dbms_output.put_line('Internal problem in function execution');
END cal_amount;
-- Generación de la fecha de pago

/* Esta función obtiene la fecha de facturación del consumo y le suma el tiempo para realizar el pago para el cliente 
 * de acuerdo a su estrato, agregando este dato a la factura generada */

FUNCTION cal_due(begining IN VARCHAR2,
time_lapse NUMBER)  
RETURN DATE 
IS 
    ending DATE;
BEGIN 
	ending := TO_DATE(begining,
	'MM/YYYY')+ time_lapse +(17 / 24);

RETURN ending;

EXCEPTION
WHEN invalid_number THEN
      dbms_output.put_line('Invalid inputs, one is NUMBER, another is VARCHAR2');
WHEN value_error THEN
      dbms_output.put_line('Bad input values');
WHEN program_error THEN
      dbms_output.put_line('Internal problem in function execution');
END cal_due;
-- Procedimiento que inserta los elementos en la tabla BILLS para generar el spool mensual

/* Este procedimiento genera la facturación para un mes dado, se utiliza desde el Backend para la generación
 * periódica de las facturas y para la generación de la facturación hasta el día actual del mes en el que se solicite */

PROCEDURE spool_gen (period IN VARCHAR2) AS
BEGIN
	  INSERT
	/*+ append */
	INTO
	APP_DATOS_IMPRESION.BILLS b (PAYMENT_DUE,
	AMOUNT,
	DELIVERY_RATE,
	CONSUMPTION_ID) 
	SELECT
	/*+ INDEX PERIOD_MONTH_YEAR_IDX ON APP_DATOS_IMPRESION.PERIODS (MONTH_YEAR) */ 
	print_spool.cal_due(p.MONTH_YEAR,
	s.BUSINESS_DAYS), 
		print_spool.cal_amount(s.COST_PER_CM,
	c2.CUBIC_METERS),
		print_spool.del_rate(c.ADDRESS),
		c2.ID
FROM
	APP_DATOS_IMPRESION.PERIODS p
INNER JOIN APP_DATOS_IMPRESION.CONSUMPTIONS c2
	ON
	c2.PERIOD_ID = p.ID
INNER JOIN APP_DATOS_IMPRESION.CLIENTS c 
	ON
	c2.CLIENT_ID = c.ID
INNER JOIN APP_DATOS_IMPRESION.STRATUMS s 
	ON
	c.STRATUM_ID = s.ID
WHERE
	p.MONTH_YEAR = period
	AND NOT EXISTS (
	SELECT
		CONSUMPTION_ID
	FROM
		APP_DATOS_IMPRESION.BILLS b2
	WHERE
		b2.CONSUMPTION_ID = c2.ID 
    );

EXCEPTION
WHEN value_error THEN
      dbms_output.put_line('Bad input value');
WHEN no_data_found THEN
      dbms_output.put_line('Not data to select');
WHEN program_error THEN
      dbms_output.put_line('Internal problem in function execution');
END spool_gen;
-- Función que genera el average de los últimos Z meses para el print spool

/* Esta función genera el consumo promedio de los X últimos meses para un cliente dado,
 * posteriormente lo envía al print spool*/

FUNCTION cal_avg(id_client IN INT, Z IN INT)  
	RETURN NUMBER    
	IS     
	x NUMBER;

y NUMBER;

BEGIN    
		BEGIN
		    WITH CTE AS
			(
SELECT
	/*+ INDEX CONSUMPTIONS_ID_IDX ON APP_DATOS_IMPRESION.CONSUMPTIONS (ID) */ 
	CUBIC_METERS
FROM
	APP_DATOS_IMPRESION.CONSUMPTIONS
WHERE
	APP_DATOS_IMPRESION.CONSUMPTIONS.CLIENT_ID = id_client
ORDER BY
	APP_DATOS_IMPRESION.CONSUMPTIONS.PERIOD_ID DESC FETCH FIRST Z ROWS ONLY) 
			SELECT
	ROUND(AVG(CUBIC_METERS),
	2) 
		    INTO
	y
FROM
	CTE;
END;

x := y;

RETURN x;

EXCEPTION
WHEN invalid_number THEN
      dbms_output.put_line('Invalid inputs, INT type awaited');
WHEN value_error THEN
      dbms_output.put_line('Bad input value');
WHEN no_data_found THEN
      dbms_output.put_line('Not data to select');
WHEN program_error THEN
      dbms_output.put_line('Internal problem in function execution');
END cal_avg;
-- Función que genera la lista de los Z últimos consumos

/* Esta función toma los últimos X consumos y los devuelve en un string donde cada consumo está separado
 * del otro por una coma, de esta manera se pueden enviar al print spool de forma dinámica */

FUNCTION get_last_Z(id_client IN INT, Z IN INT) RETURN VARCHAR2
	   IS     
	x VARCHAR2(100);

y VARCHAR2(100);

BEGIN    
		BEGIN
		    WITH CTE AS
			(
SELECT
	/*+ INDEX CONSUMPTIONS_ID_IDX ON APP_DATOS_IMPRESION.CONSUMPTIONS (ID) */ 
	CUBIC_METERS
FROM
	APP_DATOS_IMPRESION.CONSUMPTIONS
WHERE
	APP_DATOS_IMPRESION.CONSUMPTIONS.CLIENT_ID = id_client
ORDER BY
	APP_DATOS_IMPRESION.CONSUMPTIONS.PERIOD_ID DESC FETCH FIRST Z ROWS ONLY) 
			SELECT
	LISTAGG(CUBIC_METERS,
	' ')
		    INTO
	y
FROM
	CTE;
END;

x := y;

RETURN x;

EXCEPTION
WHEN invalid_number THEN
      dbms_output.put_line('Invalid inputs, INT type awaited');
WHEN value_error THEN
      dbms_output.put_line('Bad input value');
WHEN no_data_found THEN
      dbms_output.put_line('Not data to select');
WHEN program_error THEN
      dbms_output.put_line('Internal problem in function execution');
END get_last_Z;
END print_spool;