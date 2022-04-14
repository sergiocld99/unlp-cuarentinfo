program ej06;
uses sysutils;

// Constantes
const
  valor_alto = 1000000;
  cant_detalles = 10;
  txt_fuente = 'productos.txt';

// Declaración de tipos
type
  str30 = string[30];
  producto = record
    codigo:integer;
    nombre:str30;
    descripcion:str30;
    precio:real;
    stock,stockMin:integer;
  end;

  venta = record
    codigo:integer;
    cant:integer;
  end;

  // tipos de archivos
  tMaestro = file of producto;
  tDetalle = file of venta;

  // arreglo de detalles
  arrayDetalles = array[1..cant_detalles] of tDetalle;
  arrayVentas = array[1..cant_detalles] of venta;

// Procedimientos
procedure leerProducto(var txt:text; var prod:producto);
begin
  with prod do begin
    readln(txt,codigo);
    readln(txt,nombre);
    readln(txt,descripcion);
    readln(txt,precio);
    readln(txt,stock);
    readln(txt,stockMin);
  end;
end;

procedure leer(var det:tDetalle; var ven:venta);
begin
  if not eof(det) then read(det,ven)
  else ven.codigo:=valor_alto;
end;

// Precondición: archivos ya asignados
procedure crearMaestro(var arch:tMaestro; var txt:text);
var
  reg:producto;
begin
  // abrir archivos
  rewrite(arch);
  reset(txt);

  // leer cada producto del txt
  while not eof(txt) do begin
    leerProducto(txt,reg);
    write(arch,reg);
  end;

  // cerrar archivos
  close(arch);
  close(txt);
end;

// Precondición: archivos ya abiertos, arreglos inicializados
procedure minimo(var adet:arrayDetalles; var aven:arrayVentas; var min:venta);
var
  i,indiceMin:integer;
  minLocal:venta;
begin
  minLocal:= aven[1];
  indiceMin:= 1;

  // buscar minimo
  for i:=2 to cant_detalles do begin
    if (aven[i].codigo < minLocal.codigo) then begin
      minLocal:= aven[i];
      indiceMin:= i;
    end;
  end;

  min:= minLocal;

  // avanzar en archivo donde se encontro minimo
  leer(adet[indiceMin], aven[indiceMin]);
end;

// Precondición: archivos ya asignados
procedure actualizar(var mae:tMaestro; var adet:arrayDetalles);
var
  i:integer;

  // variables para seleccion de minimos
  aven:arrayVentas;
  min:venta;

  // variables para cortes de control
  codActual:integer;
  vendido:integer;
  prod:producto;

begin
  // abrir archivos
  reset(mae);
  for i:=1 to cant_detalles do reset(adet[i]);

  // inicializar
  for i:=1 to cant_detalles do leer(adet[i],aven[i]);
  read(mae,prod);

  // primer minimo
  minimo(adet,aven,min);

  // proceso cada venta en orden
  while (min.codigo <> valor_alto) do begin
    codActual:= min.codigo;
    vendido:= 0;

    // mientras sea mismo producto
    while (min.codigo = codActual) do begin
      vendido:= vendido+min.cant;
      minimo(adet,aven,min);
    end;

    // buscar en maestro (seguro existe)
    while (prod.codigo <> codActual) do read(mae,prod);

    // actualizar stock
    prod.stock:= prod.stock - vendido;

    // retroceder en maestro y reemplazar
    seek(mae,filepos(mae)-1);
    write(mae,prod);

  end;

  // cerrar archivos
  close(mae);
  for i:=1 to cant_detalles do close(adet[i]);
end;

// TEST
procedure listar(var mae:tMaestro);
var
  prod:producto;
begin
  reset(mae);
  while not eof(mae) do begin
    read(mae,prod);
    with prod do writeln(codigo,' ',nombre,' ',descripcion,' ',precio:5:2,' ',stock,' ',stockMin);
  end;
  close(mae);
end;

// Variables del programa principal
var
  opc:byte;
  terminar:boolean;

  // archivos
  mae:tMaestro;
  txt:text;
  adet:arrayDetalles;

  nombre:str30;
  i:integer;

// Programa principal
begin
  writeln('Ingrese nombre del archivo maestro a trabajar: ');
  readln(nombre);
  assign(mae,nombre);

  writeln('Ingrese prefijo de los archivos detalles: ');
  readln(nombre);
  for i:=1 to cant_detalles do assign(adet[i], concat(nombre,intToStr(i)));

  assign(txt,txt_fuente);

  terminar:= false;

  while not terminar do begin
    writeln;
    writeln('Seleccione una opcion: ');
    writeln('0. Terminar el programa');
    writeln('1. Crear maestro a partir de txt');
    writeln('2. Actualizar maestro a partir de detalles');
    writeln('3. Listar maestro (TEST)');

    read(opc);
    readln;

    case opc of
      0 : terminar:= true;
      1 : crearMaestro(mae,txt);
      2 : actualizar(mae,adet);
      3 : listar(mae);
    end;
  end;

  writeln('Fin del programa');
  readln;

end.


