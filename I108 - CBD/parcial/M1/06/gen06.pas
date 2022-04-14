program gen06;
uses sysutils;

// Declaración de tipos de variables
type
  venta = record
    codigo:integer;
    cant:integer;
  end;

  tArchivo = file of venta;
  arrayDetalles = array[1..10] of tArchivo;

var
  arch:text;
  adet:arrayDetalles;

  ven:venta;
  i:integer;
  valor:real;

begin
  assign(arch,'productos.txt');
  for i:=1 to 10 do assign(adet[i],concat('ej06_det',intToStr(i)));

  // abrir archivos
  rewrite(arch);
  for i:=1 to 10 do rewrite(adet[i]);

  randomize;

  // crear txt
  for i:=1 to 10 do begin
    writeln(arch,i);
    writeln(arch,concat('producto',intToStr(i)));
    writeln(arch,concat('descripcion de producto',intToStr(i)));
    valor:= random(10000)/100;
    writeln(arch,valor:5:2);
    writeln(arch,100+random(50));
    writeln(arch,10+random(5));
  end;

  // crear detalles
  for i:=10 to 100 do begin
    ven.codigo:=i div 10;
    ven.cant:=5+random(2);
    write(adet[1+random(10)],ven);
  end;

  close(arch);
  for i:=1 to 10 do close(adet[i]);
end.

