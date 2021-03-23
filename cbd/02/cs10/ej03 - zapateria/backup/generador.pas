program generador;

// para concatenar
uses sysutils;

const
  N = 20;

type
   // common
  str10 = string[10];
  str30 = string[30];

  // detalle
  venta = record
    codCalzado: Integer;
    numero: Integer;
    cantVendida: Integer;
  end;

  // maestro
  calzado = record
    codigo: Integer;
    numero: Integer;
    descripcion: str30;
    precioUn: real;
    color: str10;
    stock: Integer;
    stockMin: Integer;
  end;

  maestro = file of calzado;
  detalle = file of venta;
  arrayDet = array[1..N] of detalle;

function ventaRand(cod:integer; j:integer) : venta;
begin
  ventaRand.codCalzado:=cod;
  case j of
    0: ventaRand.numero:=random(4)+32;
    1: ventaRand.numero:=random(4)+36;
    2: ventaRand.numero:=random(4)+40;
  end;
  ventaRand.cantVendida:=random(10)+1;

  // debug
  with (ventaRand) do writeln(codCalzado, ', talle ', numero, ', vendio ', cantVendida);
end;

function colorRand : str10;
begin
  case random(6) of
    0: colorRand:= 'rojo';
    1: colorRand:= 'naranja';
    2: colorRand:= 'verde';
    3: colorRand:= 'azul';
    4: colorRand:= 'negro';
    5: colorRand:= 'marron';
  end;
end;

var
  arreglo:arrayDet;
  mae:maestro;
  i:integer;

  // auxiliares
  cod,j:integer;
  cal:calzado;

begin
  for i:=1 to N do assign(arreglo[i], concat('detalle',intToStr(i),'.dat'));
  for i:=1 to N do rewrite(arreglo[i]);

  // maestro
  assign(mae, 'maestro.dat');
  rewrite(mae);

  // generacion de detalles
  randomize;

  for i:=1 to N do begin
    writeln('DETALLE ', i);
    cod:= 100;

    while (cod <= 150) do begin
       j:= random(3);
       write(arreglo[i], ventaRand(cod, j));
       cod:= cod+1;
    end;
  end;

  // generacion de maestro
  writeln('MAESTRO');
  cal.descripcion:='soy un zapato xd';

  for cod:=100 to 150 do begin
      cal.codigo:=cod;

      for j:=32 to 43 do begin
        cal.numero:=j;
        cal.color:=colorRand;
        cal.stock:=random(15)*10+30;
        cal.stockMin:=random(5)+10;
        write(mae, cal);
        with (cal) do writeln(codigo, ', talle ', numero, ', stock ', stock, ' stockMin ', stockMin, ' color ', color);
      end;
  end;

  writeln('Se genero el maestro. Todo en orden');

  close(mae);
  for i:=1 to N do close(arreglo[i]);
  readln;
end.

