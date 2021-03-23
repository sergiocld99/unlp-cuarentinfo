program autos;
uses sysutils;

type
  str8=string[8];
  str30=string[30];

  tVehiculo=record
    codigoVehiculo:integer;
    patente:str8;
    nroMotor:str30;
    cantidadPuertas:integer;
    descripcion:str8;
  end;

  tArchivo=file of tVehiculo;

function letraRandom() : char;
begin
  letraRandom:=chr(random(20)+65);
end;

function numeroRandom() : char;
begin
  numeroRandom:=chr(random(10)+48);
end;

function patenteRandom() : str8;
begin
  patenteRandom[1]:=letraRandom();
  patenteRandom[2]:=letraRandom();
  patenteRandom[3]:=numeroRandom();
  patenteRandom[4]:=numeroRandom();
  patenteRandom[5]:=numeroRandom();
  patenteRandom[6]:=letraRandom();
  patenteRandom[7]:=letraRandom();
end;

function randomCode(): str30;
var
  aux:integer;
  i:integer;
begin
  setLength(randomCode,30);

  for i:=1 to 30 do begin
    aux:=random(2);
    if (aux=0) then randomCode[i]:= letraRandom()
    else randomCode[i]:= numeroRandom();
  end;
end;

var
   arch:tArchivo;
   auto:tVehiculo;
   i:integer;

begin
  assign(arch,'autos.dat');
  rewrite(arch);
  randomize;

  writeln('CODIGO PUERTAS DESCRIPCION MOTOR PATENTE');

  for i:=1 to 30 do begin
    auto.cantidadPuertas:=random(4)+2;
    auto.codigoVehiculo:=100+i;
    auto.descripcion:='0';
    auto.nroMotor:=randomCode();
    auto.patente:=patenteRandom();
    with(auto) do writeln(codigoVehiculo,' ',cantidadPuertas,' ',descripcion,' ',nroMotor,' ',patente);
    write(arch,auto);
  end;

  close(arch);
  writeln('Fin');
  readln;
end.

