-- Creando paquete que agrupará funciones y procedimientos necesarios para gestionar el spool

-- Paquete agrupando procedimientos y funciones

CREATE OR REPLACE PACKAGE print_spool AS 
-- Función que calcula un rate básico de entrega de las facturas
   FUNCTION del_rate(address VARCHAR2) RETURN NUMBER;
-- Función que retorna el costo para cada factura
   FUNCTION cal_amount(cost_per_cm NUMBER, cubic_meters NUMBER) RETURN NUMBER;
-- Generación de la fecha de pago
   FUNCTION cal_due(begining VARCHAR2, time_lapse NUMBER) RETURN DATE;
-- Procedimiento que inserta los elementos en la tabla BILLS para generar el spool mensual
   PROCEDURE spool_gen (period VARCHAR2);
-- Función que genera el average de los últimos Z meses para el print spool
   FUNCTION cal_avg(id_client IN INT, Z IN INT) RETURN NUMBER;
-- Función que genera la lista de los Z últimos consumos
   FUNCTION get_last_Z(id_client IN INT, Z IN INT) RETURN VARCHAR2;
END print_spool;

CREATE OR REPLACE PACKAGE BODY print_spool AS
-- Función que calcula un rate básico de entrega de las facturas
	FUNCTION del_rate(address VARCHAR2)  
RETURN number 
IS 	
	st NUMBER;
	av NUMBER;
	avV VARCHAR2(100);
    rate NUMBER;
BEGIN 
	IF REGEXP_SUBSTR(address, '[^ ]+', 1, 1) = 'St' THEN
		st := REGEXP_SUBSTR(address, '[^ ]+', 1, 2);
		av := REGEXP_SUBSTR(address, '[^ ]+', 1, 4);
	ELSE
		av := REGEXP_SUBSTR(address, '[^ ]+', 1, 2);
		st := REGEXP_SUBSTR(address, '[^ ]+', 1, 4);
	END IF;
	IF MOD(st, 2) = 0 THEN
		av := ABS(av-999);
	END IF;
	avV := TO_CHAR(av);
	IF LENGTH(avV) = 1 THEN
		st := st*100;
	ELSIF LENGTH(avV) = 2 THEN
		st := st*10;
	END IF;
	avV := CONCAT(TO_CHAR(st), avV);
	rate := TO_NUMBER(avV);
	RETURN rate;
END del_rate;
-- Función que retorna el costo para cada factura
	FUNCTION cal_amount(cost_per_cm NUMBER, cubic_meters NUMBER)  
RETURN number 
IS 
    amount number; 
BEGIN 
	amount := cost_per_cm * cubic_meters; 
	RETURN amount;
END cal_amount;
-- Generación de la fecha de pago
	FUNCTION cal_due(begining VARCHAR2, time_lapse NUMBER)  
RETURN DATE 
IS 
    ending DATE; 
BEGIN 
	ending := TO_DATE(begining, 'MM/YYYY')+time_lapse+(17/24); 
	RETURN ending;
END cal_due;
-- Procedimiento que inserta los elementos en la tabla BILLS para generar el spool mensual
   PROCEDURE spool_gen (period VARCHAR2) AS
BEGIN
	  INSERT INTO APP_DATOS_IMPRESION.BILLS b (PAYMENT_DUE, AMOUNT, DELIVERY_RATE, CONSUMPTION_ID) 
	SELECT print_spool.cal_due(p.MONTH_YEAR, s.BUSINESS_DAYS), 
		print_spool.cal_amount(s.COST_PER_CM, c2.CUBIC_METERS),
		print_spool.del_rate(c.ADDRESS),
		c2.ID
	FROM APP_DATOS_IMPRESION.PERIODS p
	INNER JOIN APP_DATOS_IMPRESION.CONSUMPTIONS c2
	ON c2.PERIOD_ID  = p.ID
	INNER JOIN APP_DATOS_IMPRESION.CLIENTS c 
	ON c2.CLIENT_ID  = c.ID 
	INNER JOIN APP_DATOS_IMPRESION.STRATUMS s 
	ON c.STRATUM_ID = s.ID
	WHERE p.MONTH_YEAR = period AND NOT EXISTS (
        SELECT
            CONSUMPTION_ID 
        FROM
            APP_DATOS_IMPRESION.BILLS b2 
        WHERE
            b2.CONSUMPTION_ID  = c2.ID 
    );
END spool_gen;
-- Función que genera el average de los últimos Z meses para el print spool
	FUNCTION cal_avg(id_client IN INT, Z IN INT)  
	RETURN NUMBER    
	IS     
	x NUMBER;
	y NUMBER;
	BEGIN    
		BEGIN
		    WITH CTE AS
			(SELECT CUBIC_METERS FROM APP_DATOS_IMPRESION.CONSUMPTIONS 
			WHERE APP_DATOS_IMPRESION.CONSUMPTIONS.CLIENT_ID = id_client 
			ORDER BY APP_DATOS_IMPRESION.CONSUMPTIONS.PERIOD_ID DESC FETCH FIRST Z ROWS ONLY) 
			SELECT ROUND(AVG(CUBIC_METERS),2) 
		    INTO y
		    FROM CTE;
		END;
		x :=y;    
	RETURN x;    
	END cal_avg;
-- Función que genera la lista de los Z últimos consumos
   FUNCTION get_last_Z(id_client IN INT, Z IN INT) RETURN VARCHAR2
	   IS     
	x VARCHAR2(100);
	y VARCHAR2(100);
	BEGIN    
		BEGIN
		    WITH CTE AS
			(SELECT CUBIC_METERS FROM APP_DATOS_IMPRESION.CONSUMPTIONS 
			WHERE APP_DATOS_IMPRESION.CONSUMPTIONS.CLIENT_ID = id_client 
			ORDER BY APP_DATOS_IMPRESION.CONSUMPTIONS.PERIOD_ID DESC FETCH FIRST Z ROWS ONLY) 
			SELECT LISTAGG(CUBIC_METERS, '/')
		    INTO y
		    FROM CTE;
		END;
		x :=y;    
	RETURN x;    
	END get_last_Z;
END print_spool;