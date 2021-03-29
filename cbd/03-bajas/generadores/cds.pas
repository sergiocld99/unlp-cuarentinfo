program cds;
uses sysutils;

type
  str30 = string[30];

  cd = record
    cod: Integer;
    nombreAlbum: str30;
    nombreArtista: str30;
    genero: str30;
    descripcion:str30;
    anio:integer;
    stock:integer;
  end;

  tArchivo = file of cd;

procedure generarCds(var miArchivo: tArchivo);
var
  miCd: cd;
begin
  randomize;
  rewrite(miArchivo);

  miCd.cod:= 101;
  miCd.nombreArtista:= 'ZAYN';
  miCd.genero:= 'dance pop';
  miCd.nombreAlbum:= 'Icarus Falls';
  miCd.descripcion:='2do album';
  miCd.anio:=2018;
  miCd.stock:= random(2000);
  write(miArchivo, miCd);

  miCd.cod:=102;
  miCd.nombreAlbum:= 'Mind of Mine';
  miCd.stock:= random(3000);
  miCd.anio:=2016;
  miCd.descripcion:='album debut';
  write(miArchivo, miCd);

  miCd.cod:=103;
  miCd.genero:= 'pop';
  miCd.nombreAlbum:='Nobody is Listening';
  miCd.stock:= random(1000);
  miCd.anio:=2021;
  miCd.descripcion:='3er album';
  write(miArchivo, miCd);

  miCd.cod:= 201;
  miCd.nombreArtista:= 'Dua Lipa';
  miCd.genero:= 'dance pop';

  miCd.nombreAlbum:= 'Dua Lipa (Complete Edition)';
  miCd.stock:= random(1000);
  miCd.anio:=2017;
  miCd.descripcion:='album debut';
  write(miArchivo, miCd);

  miCd.cod:=202;
  miCd.nombreAlbum:= 'Future Nostalgia';
  miCd.stock:= random(2000);
  miCd.anio:=2020;
  miCd.descripcion:='2do album';
  write(miArchivo, miCd);

  miCd.cod:= 301;
  miCd.nombreArtista:= 'The Weeknd';
  miCd.genero:= 'rap';
  miCd.nombreAlbum:= 'After Hours';
  miCd.stock:= random(1500);
  miCd.anio:=2020;
  miCd.descripcion:='buen album xd';
  write(miArchivo, miCd);

  close(miArchivo);
end;

var
  arch:tArchivo;

begin
  assign(arch, 'cds.dat');
  writeln('generando...');
  generarCds(arch);
  writeln('done :)');
  readln;
end.

