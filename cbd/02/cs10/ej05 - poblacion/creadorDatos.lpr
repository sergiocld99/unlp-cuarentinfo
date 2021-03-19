program creadorDatos;

type
  str30 = string[30];

  registro = record
    partido: str30;
    localidad: str30;
    barrio: str30;
    cantNinos: Integer;
    cantAdMay: Integer;
  end;

  archivo = file of registro;

function generar(base:integer) : integer;
begin
  generar:= random(base) + base;
end;

var
  miArchivo:archivo;
  reg:registro;

begin
  assign(miArchivo, 'datos.dat');
  rewrite(miArchivo);
  randomize;

  // ORDENADOS POR PARTIDO Y LOCALIDAD

  // partido 1
  reg.partido:='Florencio Varela';

  // localidad 1
  reg.localidad:='Bosques';
  reg.barrio:='Bosques Centro';
  reg.cantNinos:=generar(1000);
  reg.cantAdMay:=generar(200);
  write(miArchivo, reg);

  reg.barrio:='El Rocio';
  reg.cantNinos:=generar(900);
  reg.cantAdMay:=generar(190);
  write(miArchivo, reg);

  reg.barrio:='Villa Hudson';
  reg.cantNinos:=generar(1100);
  reg.cantAdMay:=generar(210);
  write(miArchivo, reg);

  // localidad 2
  reg.localidad:='Gobernador Costa';
  reg.barrio:='Km 26';
  reg.cantNinos:=generar(1000);
  reg.cantAdMay:=generar(200);
  write(miArchivo, reg);

  reg.barrio:='Lujan';
  reg.cantNinos:=generar(900);
  reg.cantAdMay:=generar(190);
  write(miArchivo, reg);

  reg.barrio:='San Jorge';
  reg.cantNinos:=generar(1100);
  reg.cantAdMay:=generar(210);
  write(miArchivo, reg);

  reg.barrio:='Villa Argentina';
  reg.cantNinos:=generar(1300);
  reg.cantAdMay:=generar(250);
  write(miArchivo, reg);

  // partido 2
  reg.partido:='Quilmes';

  // localidad 1
  reg.localidad:='Quilmes Oeste';
  reg.barrio:='La Estrella';
  reg.cantNinos:=generar(800);
  reg.cantAdMay:=generar(150);
  write(miArchivo, reg);

  reg.barrio:='Parque Calchaqui';
  reg.cantNinos:=generar(800);
  reg.cantAdMay:=generar(150);
  write(miArchivo, reg);

  // localidad 2
  reg.localidad:='San Francisco Solano';
  reg.barrio:='Los Eucaliptos';
  reg.cantNinos:=generar(1100);
  reg.cantAdMay:=generar(180);
  write(miArchivo, reg);

  reg.barrio:='Solano Centro';
  reg.cantNinos:=generar(1500);
  reg.cantAdMay:=generar(220);
  write(miArchivo, reg);

  // terminar
  close(miArchivo);
  writeln('Datos generados. Fin del programa');
  readln;

end.

