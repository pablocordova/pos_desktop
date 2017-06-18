# SISTEMA DE CONTROL PARA VENTAS #

**Nota:** Debido a modificaciones exteriores, y a pedido de nuevas actualizaciones se tuvo que crear un nuevo repositorio, el antiguo repositorio se encuentra en:

- https://github.com/pablocordova/POS

Sistema de control de ventas con las siguientes caracteristicas:

1. Opcion de administrador y vendedor(diferenciarlo al hacer login) 
2. Lista de clientes(Autocompletado en busqueda de clientes) 
3. Ventas con fecha y hora
4. Facturacion con igv(que se vea vuelto)
5. Ver stock 
6. Ingreso de mercaderia (administrador)
7. Opcion de 3 precios (para cantidades de 1, 10, 20)
8. Estadisticas para ver el producto mas vendido por mes y año (administrador)
9. Estadisticas por cliente (Para ver que es lo que mas compro) (administrador) 
10. Cuanto se ha vendido con el precio 1,2 y 3.

### ACTUALIZACIONES 18/06/2017 ###

1. Cuando es estadisticas productos por año, se raya a veces y ya no funciona otras opciones. -- Bug
2. Precios fijos para clientes especificos y tambien cuando el cliente se agrege al final. -- Caracteristica
3. Reporte diario en estadisticas por precio  -- Caracteristica
4. El vendedor minoreo solo pueda elegir/ver los 2 primeros precios. -- Caracteristica
5. Actualmente hay 3 precios,debe haber 5 precios. -- Caracteristica
6. En la boleta cambiar la cantidad directamente. -- Caracteristica
7. Usuario vendedor no debe eliminar ventas. -- Caracteristica
8. Reporte precio 1 no aparece. -- Bug
9. En la ventana de ventas, la tabla dice,cliente, precio y fecha, agregarle la hora y comentario. -- Caracteristica

Nota sobre base de datos: 

1. Se añadio columna "comentario" en la tabla "ventas" VARCHAR(400)
2. Se añadio columna "precio4" y "precio5" en la tabla "productos", VARCHAR(10) los dos
3. Se añadio columna "preciofijo" en la tabla "clientes", VARCHAR(10)

### REQUERIMIENTOS ###

Descargar Netbeans

https://netbeans.org/downloads/

Descargar MySQL Workbench

http://www.mysql.com/products/workbench/
Cuando se instala MySQL dejar por defecto el usuario: root
y colocar como contraseña: 1234

### PASOS PARA EL USO ###

1. Abrir el Workbench y crear una base de datos con nombre : ventas_isaac
2. Importar la base de datos "ventas_isaac_usuarios" dando click en "Data Import/Restore" en la seccion MANAGEMENT
3. Abrir el proyecto en netbeans y ejecutarlo.
4. (Administrador: dni:44162124, pass: 123)  (Vendedor: dni:44162123, pass: 456)