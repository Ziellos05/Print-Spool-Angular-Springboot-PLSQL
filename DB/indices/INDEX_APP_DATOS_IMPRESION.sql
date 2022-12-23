-- Index para estratos

CREATE INDEX STRATUMS_ID_IDX ON APP_DATOS_IMPRESION.STRATUMS (ID);

-- Index para clientes

CREATE INDEX CLIENTS_ID_IDX ON APP_DATOS_IMPRESION.CLIENTS (ID);

-- Index para periodos

/* Se elige el mes_año porque se necesita para seleccionar los consumos a facturar y es un índice que permite 
 * acceder de forma más rápida y óptima a estos a través de otras tablas usando joins */

CREATE INDEX PERIOD_MONTH_YEAR_IDX ON APP_DATOS_IMPRESION.PERIODS (MONTH_YEAR);

-- Index para consumos

CREATE INDEX CONSUMPTIONS_ID_IDX ON APP_DATOS_IMPRESION.CONSUMPTIONS (ID);

-- Index para facturas

CREATE INDEX BILLS_ID_IDX ON APP_DATOS_IMPRESION.BILLS (ID);

-- Creando index para la tabla de print spools generados 

CREATE INDEX PRINTSPOOLS_PERIOD_ID_IDX ON APP_DATOS_IMPRESION.PRINTSPOOLS (PERIOD_ID);

-- Creando index para la tabla de archivos subidos

CREATE INDEX UPLOADS_FILENAME_IDX ON APP_DATOS_IMPRESION.UPLOADS (FILENAME);