alter session set "_ORACLE_SCRIPT"=true;

-- Creando esquema/user

CREATE USER APP_DATOS_IMPRESION IDENTIFIED BY oracle;
-- Dando permisos al esquema para realizar acciones como admin
PROMPT APP_DATOS_IMPRESION creado

GRANT SYSDBA TO APP_DATOS_IMPRESION;

-- Asignando cuota ilimitada sobre la tabla 

/* Esta linea permite que el esquema a usar pueda
 *  realizar modificaciones sobre si mismo */

ALTER USER APP_DATOS_IMPRESION quota unlimited ON USERS;

ALTER USER SYS quota unlimited ON USERS;

PROMPT Cuota permitida al esquema

-- Cambiando al esquema creado

ALTER SESSION SET
CURRENT_SCHEMA = APP_DATOS_IMPRESION;
PROMPT Cambiando a APP_DATOS_IMPRESION