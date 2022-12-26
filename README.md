# Print Spool Mananger APP

Esta aplicación administra la generación del spool de impresión para la facturación periódica de una empresa prestadora del servicio de acueducto, este readme pretende explicar el paso a paso en orden para poder lanzar y probar la aplicación. Todas las rutas a comentar van a hacerse desde una ruta relativa que es la carpeta principal que contiene 4 carpetas, el Frontend, el Backend, la BD y una carpeta con archivos para probar la subida.

1. IDEs recomendados 

- SQL Developer para el uso de la base de datos con Oracle.
- Spring Tool Suite 4 para el uso del Backend con SpringBoot.
- Visual Studio Code para el uso del Frontend con Angular.

2. Montaje de la base de datos.

Para montar la base de datos es importante tener en cuenta la conexión usada, ya que esta debe ser la misma conexión a configurar en el backend, la conexión utilizada por defecto es la siguiente:

- User: SYS as SYSDBA
- Password: oracle
- SIN NOMBRE DEL SERVICIO NI SID

Sin embargo, es posible que usted trabaje con el servicio xe o xepdb1, en ese caso, puede crear la base de datos en esta conexión, más adelante se explica como colocar esta configuración en el microservicio. 

Ahora lo que debe hacer es abrir y lanzar el archivo ../DB/APP_DATOS_IMPRESION.sql, este archivo es un orquestador que correrá en secuencia todos los scripts .sql ubicados en las distintas carpetas para crear el esquema APP_DATOS_IMPRESION, además de crear los paquetes necesarios, agregar los datos para poder probar y por último le otorga permisos de administrador al mismo esquema nuevo, esto no es un inconveniente, pues cuando haya terminado de probar el software, puede proceder a correr el script ..DB/rollback/ROLLBACK.sql, el cual eliminará el esquema APP_DATOS_IMPRESION y con esto, eliminará todo lo creado por completo.

3. Montaje del Backend

- Utilizando Spring Tools Suite 4, el primer paso es abrir el proyecto con la opción File > Open Project From File System, luego da click en la opción "Show other specialized import wizards" y utiliza la opción Gradle Project, la versión de Gradle es 7.5.1. En caso de exportar por segunda ocasión el proyecto, es necesario eliminar la exportación anterior o modificar el nombre del proyecto en la sección settings.gradle.

- El segundo paso es actualizar el proyecto para que se instalen las dependencias necesarias haciendo click derecho sobre la carpeta raiz del proyecto y seleccionando la opción Gradle > Refresh Gradle Project.

- El tercer paso es importante, si en el paso anterior de la creación de la base de datos, utilizó otra conexión distinta a la presentada, es necesario modificarla en el archivo application.properties, la conexión por defecto es la siguiente:

spring.datasource.url=jdbc:oracle:thin:@localhost:1521
spring.datasource.username=APP_DATOS_IMPRESION as SYSDBA
spring.datasource.password=oracle
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver,

Si está utilizando una conexión con un nombre de servicio específico, modificar el primer campo agregando /NOMBRE_SERVICIO, por ejemplo:
jdbc:oracle:thin:@localhost:1521/xepdb1 o jdbc:oracle:thin:@localhost:1521/xe.

Si la conexión en general es diferente, modificar a voluntad, puede observarse que como username está el mismo esquema creado y por esta razón se dan los permisos dados en el orquestador que lo crea, se puede modificar a voluntad si no se desea utilizar este esquema como user, puede usar por ejemplo SYS as SYSDBA.

- El último paso es lanzar el Backend desde el archivo llamado PrintSpoolApplication.java, ubicado en la carpeta ..Backend/PrintSpool/src/main/java/in/printspool, puede leer la documentación para el uso de la API (Y a su vez probarlo) en el siguiente link:
http://localhost:8080/swagger-ui/index.html, en caso de no poder utilizar el puerto 8080, este se puede modificar en el archivo de propiedades y en ese caso, puede redirigirse con el puerto correcto para leer la documentación.

4. Montaje del Frontend

El Frontend es por mucho el desarrollo con menos inconvenientes para lanzar, en caso de haber llegado hasta acá ¡ánimo! falta poco.

- El primero paso es abrir el projecto en Visual Studio Code, la ruta específica sería ../Frontend/printspool-app.

- El segundo paso es utilizando la consola de comandos, agregar los paquetes con la órden "npm i", esperar.

- El último paso es lanzar la app con el comando "ng serve -o", lanzando la app y abriendo el home de la misma.

En el mejor de los casos, en este momento Frontend estará conectando con el Backend y a su vez este último realizando peticiones a la base de datos para que usted pueda probar el aplicativo correctamente. Enjoy it!

En el peor de los casos, por favor contactarse conmigo. Correo: roland.ortega@segurosbolivar.con, wa: 3167178288, o escribir por Slack.