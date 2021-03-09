program ejer02;

type
  ArchEnteros = file of integer;

procedure generarDatos(var archivo: ArchEnteros);
var
  i,n:integer;
begin
  // Crear archivo
  rewrite(archivo);

  // Generar y escribir
  randomize;
  for i:=1 to 20 do begin
    n:= random(1000);
    write(archivo, n);
  end;

  // Cerrar archivo
  close(archivo);
end;

function Minimo(num1:integer; num2:integer) : integer;
begin
  if (num1 < num2) then begin
    Minimo:= num1;
  end else begin
    Minimo:= num2;
  end;
end;

function Promedio(suma:integer; cant:integer) : real;
begin
  if (cant = 0) then begin
    Promedio:= 0;
  end else begin
    Promedio:= suma / cant;
  end;
end;

procedure procesarArchivo(var archivo: ArchEnteros; var promVotos: real; var minVotos: integer);
var
  numLeido: integer;
  cant, suma, min: integer;
begin
  // Inicializar resultados
  cant:= 0;
  suma:= 0;
  min:= 9999;

  while not (eof(archivo)) do begin
    read(archivo, numLeido);
    cant:= cant + 1;
    suma:= suma + numLeido;
    min:= Minimo(min,numLeido);
    // Mostrar cada numero leido en pantalla (listar)
    writeln(numLeido);
  end;

  // Devolver resultados
  promVotos:= Promedio(suma, cant);
  minVotos:= min;
end;

var
  archivo: ArchEnteros;
  nombreArch: string[20];
  promedioVotos: real;
  minVotos: integer;

begin
  writeln('Ingrese el nombre del archivo a leer');
  readln(nombreArch);

  // Asignamos el archivo
  assign(archivo, nombreArch);

  // Opcional: generamos datos
  generarDatos(archivo);

  // Abrimos el archivo en modo lectura
  reset(archivo);

  // Procesamos sus datos
  procesarArchivo(archivo, promedioVotos, minVotos);

  // Cerramos el archivo
  close(archivo);

  // Informar resultados
  writeln('El promedio de votos por ciudad es ', promedioVotos:0:2);
  writeln('La cantidad minima de votantes en una ciudad es ', minVotos);

  // Terminar
  writeln('Fin del programa');
  readln();

end.

