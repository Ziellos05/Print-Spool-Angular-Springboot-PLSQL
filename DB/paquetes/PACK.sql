-- Creando paquete que agrupar� funciones y procedimientos necesarios para gestionar el spool

-- Paquete agrupando procedimientos y funciones

CREATE OR REPLACE PACKAGE app_datos_impresion.print_spool AS 
-- Funci�n que calcula un rate de entrega de las facturas
   FUNCTION del_rate(address VARCHAR2) RETURN NUMBER;
-- Funci�n que retorna el costo para cada factura
   FUNCTION cal_amount(cost_per_cm NUMBER, cubic_meters NUMBER) RETURN NUMBER;
-- Generaci�n de la fecha de pago
   FUNCTION cal_due(begining VARCHAR2, time_lapse NUMBER) RETURN DATE;
-- Procedimiento que inserta los elementos en la tabla BILLS para generar el spool mensual
   PROCEDURE spool_gen (period VARCHAR2);
-- Funci�n que genera el average de los �ltimos Z meses para el print spool
   FUNCTION cal_avg(id_client IN INT, Z IN INT) RETURN NUMBER;
-- Funci�n que genera la lista de los Z �ltimos consumos
   FUNCTION get_last_Z(id_client IN INT, Z IN INT) RETURN VARCHAR2;
END print_spool;