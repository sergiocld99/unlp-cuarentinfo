program plantas;

type
  str30 = string[30];
  str50 = string[50];

  especie = record
    codigo:integer;
    nombreVulgar: str30;
    nombreCient:str30;
    avgHeight:real;
    description:str50;
    geoZone:str50;
  end;

  archivo = file of especie;

function alturaPlanta() : real;
begin
  alturaPlanta:=random(60)/10.0+0.2;
end;

var
  arch:archivo;
  esp:especie;

begin
  assign(arch, 'plantas.dat');
  rewrite(arch);
  randomize;
  esp.description:='soy una planta xd';

  // generar 1
  esp.avgHeight:=random(30)+20;
  esp.codigo:=129;
  esp.geoZone:='regiones montanosas';
  esp.nombreCient:='abies alba';
  esp.nombreVulgar:='abeto comun';
  write(arch, esp);

  // 2
  esp.avgHeight:=alturaPlanta();
  esp.codigo:=130;
  esp.geoZone:='suecia y mediterraneo';
  esp.nombreCient:='anthericum liliago';
  esp.nombreVulgar:='falangera';
  write(arch,esp);

  // 3
  esp.avgHeight:=alturaPlanta();
  esp.codigo:=131;
  esp.geoZone:='europa meridional y central';
  esp.nombreCient:='bryonia dioica';
  esp.nombreVulgar:='nueza';
  write(arch,esp);

  // 4
  esp.avgHeight:=alturaPlanta();
  esp.codigo:=132;
  esp.geoZone:='eurasia';
  esp.nombreCient:='chenopodium foliosum';
  esp.nombreVulgar:='bledomera';
  write(arch,esp);

  // 5
  esp.avgHeight:=alturaPlanta();
  esp.codigo:=623;
  esp.geoZone:='prados calcareos secos';
  esp.nombreCient:='fumana procumbens';
  esp.nombreVulgar:='jarilla rastrera';
  write(arch,esp);

  // 6
  esp.avgHeight:=alturaPlanta();
  esp.codigo:=624;
  esp.geoZone:='europa occidental';
  esp.nombreCient:='meconopsis cambrica';
  esp.nombreVulgar:='amapola amarilla';
  write(arch,esp);

  // 7
  esp.avgHeight:=random(10)+20;
  esp.codigo:=794;
  esp.geoZone:='eurasia';
  esp.nombreCient:='pinus sylvestris';
  esp.nombreVulgar:='pino silvestre';
  write(arch,esp);

  // 8
  esp.avgHeight:=alturaPlanta();
  esp.codigo:=795;
  esp.geoZone:='sur de islandia y al este de los pirineos';
  esp.nombreCient:='rubus saxatilis';
  esp.nombreVulgar:='zarza de las rocas';
  write(arch,esp);

  close(arch);
  writeln('Fin del programa');
  readln;
end.

