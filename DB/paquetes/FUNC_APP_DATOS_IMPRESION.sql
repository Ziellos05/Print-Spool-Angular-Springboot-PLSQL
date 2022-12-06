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
END print_spool;

CREATE OR REPLACE PACKAGE BODY print_spool AS
-- Función que calcula un rate básico de entrega de las facturas
	FUNCTION del_rate(address VARCHAR2)  
RETURN number 
IS 
    rate number; 
BEGIN 
	rate := Sqrt(POWER(REGEXP_SUBSTR(address, '[^ ]+', 1, 2), 2) + POWER(REGEXP_SUBSTR(address, '[^ ]+', 1, 4), 2)); 
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
	  INSERT INTO APP_DATOS_IMPRESION.BILLS (PAYMENT_DUE, AMOUNT, DELIVERY_RATE, CONSUMPTION_ID) 
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
	WHERE p.MONTH_YEAR = period;
END spool_gen;
END print_spool;

-- Ejemplo de la generación de la facturación para un mes escogido
BEGIN
  print_spool.spool_gen('01/2022'); 
END;

-- Ejemplo del calculador del delivery rate
DECLARE
   a NUMBER;
BEGIN
   a := print_spool.del_rate('Crr 3 Av 4'); 
   dbms_output.put_line(' El rate de delivery es: ' || a); 
END;

-- Ejemplo del calculador de costos
DECLARE
	x NUMBER;
	y NUMBER;
	z NUMBER;
BEGIN
    x := 5;
    y := 10;
    z := print_spool.cal_amount(x, y);
    dbms_output.put_line(' El costo de la factura fue: ' || z); 
END;

-- Ejemplo de la generación de la fecha de pago
DECLARE
	begining VARCHAR2(100);
	time_lapse NUMBER;
	ending DATE;
BEGIN
	begining := '01/2022';
	time_lapse := 60;
    ending := cal_due(begining, time_lapse);
    dbms_output.put_line(' La fecha de pago es: ' || ending); 
END;