program ejer07;
uses sysutils;

const
  NOMBRE_FUENTE = 'productos.txt';
  VALOR_ALTO = 9999;
  N = 10;

type
  str15 = string[15];
  str30 = string[30];

  producto = record
    codigo:integer;
    nombre:str15;
    descripcion:str30;
    precio:real;
    stock:integer;
    stockMin:integer;
  end;

  maestro = file of producto;

  venta_prod = record
    codigo:integer;
    cant:integer;
  end;

  detalle = file of venta_prod;
  arrayDet = array[1..N] of detalle;
  arrayVen = array[1..N] of venta_prod;

procedure leerProductoTxt(var archivo:text; var reg:producto);
begin
  if not eof(archivo) then begin
      read(archivo, reg.codigo);
      read(archivo, reg.precio);
      read(archivo, reg.stock);
      read(archivo, reg.stockMin);
      readln(archivo, reg.nombre);
      readln(archivo, reg.descripcion);
  end else reg.codigo:=VALOR_ALTO;
end;

procedure leerProductoBin(var archivo:maestro; var reg:producto);
begin
  if not eof(archivo) then read(archivo, reg)
  else reg.codigo:=VALOR_ALTO;
end;

procedure leerVenta(var archivo:detalle; var reg:venta_prod);
begin
  if not eof(archivo) then read(archivo, reg)
  else reg.codigo:=VALOR_ALTO;
end;

procedure determinarMin(var reg:arrayVen; var indiceMin:integer);
var
  i,min:integer;
begin
  indiceMin:=1;
  min:=reg[1].codigo;

  for i:=2 to N do if (reg[i].codigo < min) then begin
    indiceMin:= i;
    min:= reg[i].codigo;
  end;
end;

procedure minimo(var ven:arrayVen; var min:venta_prod; var det:arrayDet);
var
  indiceMin:integer;
begin
  determinarMin(ven, indiceMin);
  min:= ven[indiceMin];

  // avanzo en archivo donde estaba min
  leerVenta(det[indiceMin], ven[indiceMin]);
end;

procedure crearMaestro(var destino:maestro; var fuente:text);
var
  reg:producto;
begin
  rewrite(destino);
  reset(fuente);

  leerProductoTxt(fuente, reg);
  while (reg.codigo <> VALOR_ALTO) do begin
    write(destino, reg);
    leerProductoTxt(fuente, reg);
  end;

  close(fuente);
  close(destino);
end;

procedure actualizarMaestro(var mae:maestro; var det:arrayDet);
var
  i:integer;
  ven:arrayVen;
  prod:producto;
  act:venta_prod;
  codActual:integer;

begin
  reset(mae);
  for i:=1 to N do reset(det[i]);

  // leer primera venta de cada detalle
  for i:=1 to N do leerVenta(det[i], ven[i]);

  // leer primer producto del maestro
  leerProductoBin(mae, prod);

  // busco primer minimo
  minimo(ven, act, det);

  // corte de control
  while (act.codigo <> VALOR_ALTO) do begin
    codActual:= act.codigo;

    // busco en maestro
    while (prod.codigo <> codActual) do leerProductoBin(mae, prod);

    // mientras sea mismo producto
    while (act.codigo = codActual) do begin
      prod.stock:=prod.stock - act.cant;
      minimo(ven, act, det);
    end;

    // actualizo maestro
    seek(mae, filePos(mae)-1);
    write(mae, prod);

  end;

  for i:=1 to N do close(det[i]);
  close(mae);
end;

procedure opciones;
begin
  writeln('0 -- Terminar programa');
  writeln('1 -- Crear maestro a partir de ', NOMBRE_FUENTE);
  writeln('2 -- Actualizar maestro a partir de los ', N, ' detalles');
  writeln;
end;

// PROGRAMA PRINCIPAL
var
  fuente:text;
  mae:maestro;
  det:arrayDet;
  i:integer;
  opc:char;

begin
  assign(fuente, NOMBRE_FUENTE);
  assign(mae, 'productos.dat');
  for i:=1 to N do assign(det[i], concat('ventas',intToStr(i),'.dat'));

  writeln('MENU PRINCIPAL');
  opciones;

  read(opc);
  while (opc <> '0') do begin
    case opc of
     '1': crearMaestro(mae, fuente);
     '2': actualizarMaestro(mae, det);
    end;

    readln;
    writeln('MENU PRINCIPAL');
    opciones;
    read(opc);
  end;

  writeln('Fin del programa');
  readln;
end.

