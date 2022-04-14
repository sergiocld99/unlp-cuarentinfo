program ej01;

// Constantes
const valor_alto = 1000000;

// Declaración de tipos de variables
type
  tString = string[20];
  tArchivo = file of integer;

// Procedimientos
// Precondición: se recibe un archivo ya asignado
procedure procesar(var arch:tArchivo; var minimo:integer; var promedio:real);
var
  min,suma,cant,valor:integer;
begin
  // abrir el archivo
  reset(arch);

  // inicializar variables locales
  min:= valor_alto;
  suma:= 0;
  cant:= 0;

  // procesar todos los numeros
  while not eof(arch) do begin
    read(arch,valor);
    suma:= suma + valor;
    cant:= cant + 1;
    if (valor < min) then min:= valor;

    // listar
    writeln(valor);
  end;

  // cerrar el archivo
  close(arch);

  // asignar valores de retorno
  minimo:= min;
  promedio:= suma / cant;
end;

// Variables del programa principal
var
  arch:tArchivo;
  nombre:tString;
  min:integer;
  prom:real;

// Programa principal
begin
  writeln('Ingrese nombre del archivo de numeros a leer: ');
  read(nombre);

  assign(arch,nombre);
  procesar(arch,min,prom);
  writeln('La cantidad minima de votantes en una ciudad fue: ',min);
  writeln('La cantidad promedio de votantes por ciudad fue: ',prom:5:2);

  readln;
  readln;
end.

