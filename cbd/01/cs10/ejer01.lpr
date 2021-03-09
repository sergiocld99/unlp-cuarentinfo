program ejer01;

const
  PALABRA_FIN = 'cemento';

type
  TipoNombre = string[20];

procedure crearArchivoVacio(var archivo: text; var nombre: TipoNombre);
begin
  Assign(archivo, nombre);
  rewrite(archivo);
end;

procedure escribirMateriales(var archivo: text);
var
  input: TipoNombre;
begin
  repeat
    readln(input);
    writeln(archivo, input);
  until(input = PALABRA_FIN) ;
end;

var
  archivo: text;
  nombreArch, palabra: TipoNombre;

begin
  writeln('hola mundo');
  writeln();

  {Paso 1 - Creación del archivo}
  writeln('ingrese nombre del archivo a crear');
  readln(nombreArch);
  crearArchivoVacio(archivo, nombreArch);
  writeln('se creo el archivo ', nombreArch);

  {Paso 2 - Carga de materiales}
  writeln('escriba nombres de materiales, ', PALABRA_FIN, ' para terminar');
  escribirMateriales(archivo);

  {Terminar}
  close(archivo);
  writeln('se cerro el archivo, fin del programa');
  readln();

end.

