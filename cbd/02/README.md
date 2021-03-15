# Archivos maestro y detalle
Ambos son estructuras ordenadas (en general). El primero es único, contiene información en forma de resumen/reporte de un tipo de dato (registro). 
Los detalles son de estructura similar entre sí, y permiten actualizar el archivo maestro, o bien, crear uno en fusión con los otros detalles.

## Algunos tips
* Definir constantes para valores de corte, como 9999 o 'zzzz', de modo de usarlos en registros al momento de leer detalles (representa el eof)
* Definir tipos de Strings como str10 o str30 para evitar el uso de string[10] y similares en cada variable, argumento o campo de registro.
* Definir registros con nombre muy simple, y los tipos de archivo directamente con nombres "maestro" y "detalle" (file of registro).
* Usar un procedimiento para leer un registro de un archivo detalle, el cual "incrustará" el valor de corte si se llega al eof.
* Aprovechar al máximo el ordenamiento de los datos, empleando un procedimiento **mínimo** para avanzar sin necesidad de volver a buscar en el maestro desde el inicio.
* Leer bien las precondiciones, y tener precaución cuando un elemento puede encontrarse repetidas veces entre los archivos detalles (no solo en uno de ellos).
* El uso de variables auxiliares para almacenar "valores anteriores" ESTÁ BIEN, así que no dudes en usarlas.
* Tratar de escribir y mover el cursor la menor cantidad de veces posible en el maestro, con ayuda de contadores, sumadores y otras variables auxiliares.
