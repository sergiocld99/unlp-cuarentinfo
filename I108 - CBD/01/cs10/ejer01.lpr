program ejer01;

const
  PALABRA_FIN = 'cemento';

type
  TipoNombre = string[20];
  ArchMateriales = file of TipoNombre;

procedure escribirMateriales(var archivo: ArchMateriales);
var
  input: TipoNombre;
begin
  repeat
    readln(input);
    write(archivo, input);
  until(input = PALABRA_FIN) ;
end;

var
  archivo: ArchMateriales;
  nombreArch: TipoNombre;

begin
  {Paso 1 - Creación del archivo}
  writeln('Ingrese nombre del archivo a crear');
  readln(nombreArch);
  assign(archivo, nombreArch);
  rewrite(archivo);
  writeln('Se creo el archivo ', nombreArch);

  {Paso 2 - Carga de materiales}
  writeln('Escriba nombres de materiales, ', PALABRA_FIN, ' para terminar');
  escribirMateriales(archivo);

  {Terminar}
  close(archivo);
  writeln('Se cerro el archivo, fin del programa');
  readln();

end.

