/* ESTE ARCHIVO .SQL RECOPILA TODOS LOS COMANDOS PARA GENERAR LAS TABLAS, LOS ÍNDICES
 * Y EL PAQUETE CON FUNCIONES Y PROCEDIMIENTOS A UTILIZAR, PARA AGREGAR ELEMENTOS A LAS TABLAS, 
 * TEST DE LAS FUNCIONES Y DEMÁS */

-- RUN FULL SCRIPT

PROMPT Creando esquema APP_DATOS_IMPRESION y asignando permisos

@./sys/SYS.sql

ALTER SESSION SET
CURRENT_SCHEMA = SYS;

PROMPT Creando tablas con Foreign keys incluídas

@./tablas/TABLES.sql

PROMPT Creando índices

@./indices/INDEX.sql

PROMPT Creando estratos

@./tablas/STRATUMS.sql

PROMPT Creando periodos

@./tablas/PERIODS.sql

PROMPT Creando clientes

@./tablas/CLIENTS.sql

PROMPT Creando consumos

@./tablas/consumos/CONSUMPTIONS1.sql
@./tablas/consumos/CONSUMPTIONS2.sql
@./tablas/consumos/CONSUMPTIONS3.sql
@./tablas/consumos/CONSUMPTIONS4.sql
@./tablas/consumos/CONSUMPTIONS5.sql
@./tablas/consumos/CONSUMPTIONS6.sql
@./tablas/consumos/CONSUMPTIONS7.sql
@./tablas/consumos/CONSUMPTIONS8.sql
@./tablas/consumos/CONSUMPTIONS9.sql
@./tablas/consumos/CONSUMPTIONS10.sql
@./tablas/consumos/CONSUMPTIONS11.sql
@./tablas/consumos/CONSUMPTIONS12.sql

PROMPT Creando paquete con funciones y precedimientos a usar

@./paquetes/PACK.sql
@./paquetes/PACK_BODY.sql

PROMPT Actualizando facturas hasta la fecha

@./actualizacion/UPDATE.sql

PROMPT Facturas actualizadas hasta la fecha