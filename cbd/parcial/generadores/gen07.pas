program gen07;

// Declaración de tipos de variables
type
  str30 = string[30];

  tVehiculo = record
    codigoVehiculo:integer;
    patente:str30;
    nroMotor:str30;
    cantidadPuertas:integer;
    precio:real;
    descripcion:str30;
   end;

  tArchivo = file of tVehiculo;

var
  arch:tArchivo;
  reg:tVehiculo;
  i:integer;
  s:str30;

begin
  assign(arch,'ej07.dat');
  rewrite(arch);
  randomize;

  // encabezado
  reg.codigoVehiculo:=-1;
  reg.descripcion:='0';
  reg.precio:=-1;
  reg.patente:='####';
  reg.nroMotor:='####';
  reg.cantidadPuertas:=-1;
  write(arch,reg);

  for i:=1 to 10 do begin
    str(i,s);
    reg.precio:=random(100000) / 100;
    reg.cantidadPuertas:=2+random(4);
    reg.nroMotor:=concat('motor',s);
    reg.patente:=concat('patente',s);
    reg.codigoVehiculo:=i;
    reg.descripcion:='0';
    write(arch,reg);
  end;

  close(arch);
end.

