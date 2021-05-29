program gen02;
uses sysutils;

// Declaración de tipos de variables
type
  tString = string[30];
  tEspecie = record
    num:integer;
    alturaMax:real;
    nomCientifico:tString;
    nomVulgar:tString;
    color:tString;
    alturaMaxAlcanzar:real;
  end;

  tArchivo = file of tEspecie;

var
  arch:tArchivo;
  esp:tEspecie;
  i:integer;

begin
  assign(arch,'ej02.dat');
  rewrite(arch);
  randomize;

  for i:=1 to 10 do begin
    esp.alturaMax:=random(1000)/100;
    esp.alturaMaxAlcanzar:=esp.alturaMax+random(500)/100;
    esp.color:=concat('color',intToStr(i));
    esp.nomCientifico:=concat('nomCientifico',intToStr(i));
    esp.nomVulgar:=concat('nomVulgar',intToStr(i));
    esp.num:=i;
    write(arch,esp);
  end;

  close(arch);
end.

