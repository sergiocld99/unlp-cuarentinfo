program ejer08;
uses sysutils;

const
  VALOR_ALTO = 9999;
  N = 15;

type
  str15 = string[15];
  str30 = string[30];

  zona_det = record
    codigo:integer;
    nombre:str15;
    descripcion:str30;
    fecha:longint;
    metros:real;
  end;

  zona_mae = record
    codigo:integer;
    nombre:str15;
    metros:real;
  end;

  detalle = file of zona_det;
  maestro = file of zona_mae;

  arrayDet = array[1..N] of detalle;
  arrayZon = array[1..N] of zona_det;

procedure leer(var archivo:detalle; var reg:zona_det);
begin
  if not eof(archivo) then read(archivo, reg)
  else reg.codigo:=VALOR_ALTO;
end;

procedure determinarMin(var reg:arrayZon; var indiceMin:integer);
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

procedure minimo(var reg:arrayZon; var min:zona_det; var det:arrayDet);
var
  indiceMin:integer;
begin
  determinarMin(reg, indiceMin);
  min:= reg[indiceMin];

  // avanzo en archivo donde estaba min
  leer(det[indiceMin], reg[indiceMin]);
end;

procedure escribir(var archivo:maestro; var base:zona_det; total:real);
var
  reg:zona_mae;
begin
  reg.codigo:= base.codigo;
  reg.nombre:= base.nombre;
  reg.metros:= total;
  write(archivo, reg);
end;

procedure escribirTxt(var archivo:text; var base:zona_det; total:real);
begin
  writeln(archivo, base.codigo, ' ', base.nombre);
  writeln(archivo, base.descripcion);
  writeln(archivo, total:0:2);
end;

procedure crearMaestro(var det:arrayDet);
var
  destino:maestro;
  aux,act:zona_det;
  i:integer;
  total:real;
  zonas:arrayZon;
  filetext:text;

begin
  assign(destino, 'maestro.dat');
  assign(filetext, 'informe.txt');
  rewrite(destino);
  for i:=1 to N do reset(det[i]);

  // leer primer elemento de cada detalle
  for i:=1 to N do leer(det[i], zonas[i]);

  // busco primer minimo
  minimo(zonas, act, det);

  // corte de control
  while (act.codigo <> VALOR_ALTO) do begin
    total:= 0;
    aux:=act;

    // mientras sea mismo codigo
    while (act.codigo = aux.codigo) do begin
      total:= total + act.metros;
      minimo(zonas, act, det);
    end;

    // agrego a maestro
    escribir(destino, aux, total);

    // agrego a filetext
    escribirTxt(filetext, aux, total);

  end;

  for i:=1 to N do close(det[i]);
  close(filetext);
  close(destino);
end;

// PROGRAMA PRINCIPAL
var
  det:arrayDet;
  i:integer;

begin
  for i:=1 to N do assign(det[i], concat('zona',intToStr(i),'.dat'));
  crearMaestro(det);

  writeln('Fin del programa');
  readln;
end.

