-- Eliminando el esquema/user completo

/* Al haber utilizado este esquema para crear tablas, agregar datos,
*  agregar paquetes y conectar con la API, la siguiente linea elimina todo*/ 

ALTER SESSION SET CURRENT_SCHEMA = SYS;

ALTER SESSION SET "_oracle_script"=TRUE;

DROP USER APP_DATOS_IMPRESION CASCADE;

PROMPT APP_DATOS_IMPRESION eliminado correctamente :)