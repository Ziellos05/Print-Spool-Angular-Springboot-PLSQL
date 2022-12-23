-- Ejemplos de uso de las funciones y procedimientos creados, además de queries que pruebo aquí para usar luego en el Backend

-- Ejemplo de la generación de la facturación para los meses hasta el mes anterior
BEGIN
	app_datos_impresion.print_spool.spool_gen('12/2022');
END;
-- Ejemplo del calculador del delivery rate
DECLARE
   a NUMBER;
BEGIN
   a := print_spool.del_rate('Av 400 Av 900 12'); 
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
    ending := print_spool.cal_due(begining, time_lapse);
    dbms_output.put_line(' La fecha de pago es: ' || ending); 
END;

SELECT /*+ INDEX BILLS_ID_IDX ON APP_DATOS_IMPRESION.BILLS (ID) */ b.ID, TO_CHAR(b.PAYMENT_DUE, 'DD-MM-YYYY') AS PAYMENTDUE,
            b.AMOUNT, 
            c.NAME, 
			c.ADDRESS, 
			c2.CUBIC_METERS 
			AS CONSUMPTION
FROM APP_DATOS_IMPRESION.BILLS b 
			INNER JOIN APP_DATOS_IMPRESION.CONSUMPTIONS c2
			ON c2.ID = b.CONSUMPTION_ID 
			INNER JOIN APP_DATOS_IMPRESION.CLIENTS c
			ON c.ID = c2.CLIENT_ID 
			INNER JOIN APP_DATOS_IMPRESION.PERIODS p 
			ON p.ID = c2.PERIOD_ID 
			WHERE p.MONTH_YEAR = 12/2022
			ORDER BY b.DELIVERY_RATE