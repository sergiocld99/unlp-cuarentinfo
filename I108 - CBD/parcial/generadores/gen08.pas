program gen08;

// Declaración de tipos de variables
type
  str30 = string[30];

  articulo = record
    nro:integer;
    descripcion:str30;
    color:str30;
    talle:integer;
    stock:integer;
    precio:real;
  end;

  tArchivo = file of articulo;

var
  arch:tArchivo;
  reg:articulo;
  i:integer;
  s:str30;

begin
  assign(arch,'ej08.dat');
  rewrite(arch);
  randomize;

  for i:=1 to 10 do begin
    str(i,s);
    reg.nro:=i;
    reg.color:=concat('color',s);
    reg.descripcion:=concat('una descripcion de ',s);
    reg.precio:=random(10000)/100;
    reg.stock:=10+random(5);
    reg.talle:=30+random(10);
    write(arch,reg);
  end;

  close(arch);
end.

