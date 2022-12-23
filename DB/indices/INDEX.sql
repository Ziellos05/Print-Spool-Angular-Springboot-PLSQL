PROMPT INDEX para STRATUMS por defecto

PROMPT INDEX para CLIENTS por defecto

-- Index para periodos

/* Se elige el mes_año porque se necesita para seleccionar los consumos a facturar y es un índice que permite 
 * acceder de forma más rápida y óptima a estos a través de otras tablas usando joins */

CREATE INDEX PERIOD_MONTH_YEAR_IDX ON
APP_DATOS_IMPRESION.PERIODS (MONTH_YEAR);

PROMPT INDEX para PERIODS creado

PROMPT INDEX para CONSUMPTIONS por defecto

PROMPT INDEX para BILLS por defecto

-- Creando index para la tabla de print spools generados 

CREATE INDEX PRINTSPOOLS_PERIOD_ID_IDX ON
APP_DATOS_IMPRESION.PRINTSPOOLS (PERIOD_ID);

PROMPT INDEX para PRINTSPOOLS creado

-- Creando index para la tabla de archivos subidos

CREATE INDEX UPLOADS_FILENAME_IDX ON
APP_DATOS_IMPRESION.UPLOADS (FILENAME);

PROMPT INDEX para UPLOADS creado