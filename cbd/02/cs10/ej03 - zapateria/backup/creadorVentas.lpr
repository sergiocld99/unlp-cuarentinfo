program creadorVentas;

// para concatenar
uses sysutils;

const
  N = 20;

type
  // detalle
  venta = record
    codCalzado: Integer;
    numero: Integer;
    cantVendida: Integer;
  end;

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
  ventaRand.cantVendida:=random(60);

  // debug
  with (ventaRand) do writeln(codCalzado, ', talle ', numero, ', vendio ', cantVendida);
end;

var
  arreglo:arrayDet;
  i:integer;

  // auxiliares
  cod,j:integer;

begin
  for i:=1 to N do assign(arreglo[i], concat('detalle',intToStr(i),'.dat'));
  for i:=1 to N do rewrite(arreglo[i]);

  // generacion
  randomize;

  for i:=1 to N do begin
    for cod:=100 to 200 do begin
        for j:=0 to 3 do begin
            write(arreglo[i], ventaRand(cod, j));
        end;
    end;
  end;

  for i:=1 to N do close(arreglo[i]);
  readln;
end.

