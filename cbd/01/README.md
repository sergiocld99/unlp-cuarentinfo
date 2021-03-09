# Introducción a Archivos
Acá voy a anotar algunos tips (no explicaciones completas) sobre cómo llevar a cabo ciertas acciones que suelen pedirse en los ejercicios:

## Modificar registro de un binario
Si no está ordenado, abriremos el archivo al inicio con **reset(archivo)** e iremos pasando por cada registro hasta encontrar el solicitado usando **eof(archivo)** y una variable booleana auxiliar. 
En cada iteración, el registro queda asignado en una variable local usando un **read(archivo, mRegistro)**, para que al salir del bucle se lo modifique si fue encontrado. 
Luego de modificar los atributos correspondientes, **DEBO REALIZAR UN SEEK(ARCHIVO, FILEPOS(ARCHIVO)-1)** ya que el cursor quedó después del encontrado, y si haríamos un write directamente reemplazamos el siguiente al deseado. 
Una vez hecho el seek, hago el **write(archivo, mRegistro)** y cierro el archivo con **close(archivo)**.
