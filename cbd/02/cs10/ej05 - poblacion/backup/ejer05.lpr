program ejer05;

const
  PARTIDO_FIN = 'zzzz';

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

procedure leer(var arch:archivo; var reg:registro);
begin
  if not eof(arch) then read(arch, reg)
  else reg.partido:= PARTIDO_FIN;
end;

procedure informar(var arch:archivo);
var
  reg:registro;

  // para corte de control
  parActual: str30;
  locActual: str30;

  // para totalizar en localidad
  ninosLoc: Integer;
  adultosLoc: Integer;
  indiceLoc: Integer;

  // para totalizar en partido
  ninosPartido: Integer;
  adultosPartido: Integer;

begin
  // abro el archivo
  reset(arch);

  // leo el primero
  leer(arch, reg);

  // mientras hayan registros
  while (reg.partido <> PARTIDO_FIN) do begin

    // nuevo partido
    parActual:= reg.partido;
    writeln('Partido: ', parActual);
    ninosPartido:= 0;
    adultosPartido:= 0;
    indiceLoc:= 0;

    // mientras sea mismo partido
    while (reg.partido = parActual) do begin

      // nueva localidad
      locActual:= reg.localidad;
      indiceLoc:= indiceLoc + 1;
      writeln('Localidad ',indiceLoc, ': ', locActual);
      ninosLoc:= 0;
      adultosLoc:= 0;

      // mientras sea misma localidad y partido
      while (reg.partido = parActual) and (reg.localidad = locActual) do begin
        ninosLoc:= ninosLoc + reg.cantNinos;
        adultosLoc:= adultosLoc + reg.cantAdMay;

        // informar barrio
        writeln('Nombre del Barrio: ', reg.barrio, ' Cantidad ninos:  ', reg.cantNinos, ' Cantidad adultos: ', reg.cantAdMay);

        // leer siguiente barrio
        leer(arch, reg);
      end;

      // totalizar localidad
      writeln('Total ninos localidad ', indiceLoc, ': ', ninosLoc, ' Total adultos localidad ', indiceLoc, ': ', adultosLoc);
    end;

    // totalizar partido
    writeln('TOTAL NINOS PARTIDO: ', ninosPartido, ' TOTAL ADULTOS PARTIDO: ', adultosPartido);
  end;

  // cerrar archivo
  close(arch);
end;

var
  miArchivo:archivo;

begin
  assign(miArchivo, 'datos.dat');
  informar(miArchivo);
  writeln('Fin del programa');
  readln;
end.

