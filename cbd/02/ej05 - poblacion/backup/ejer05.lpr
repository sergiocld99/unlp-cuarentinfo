program ejer05;

// TODO: Completar este ejercicio esta semana

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
    indiceLoc:= 1;

    // mientras sea mismo partido
    while (reg.partido = parActual) do begin

      // nueva localidad
      locActual:= reg.localidad;
      writeln('Localidad ',indiceLoc, ': ', locActual);
      indiceLoc:= indiceLoc + 1;
      ninosLoc:= 0;
      adultosLoc:= 0;

      // mientras sea misma localidad (cuidado: revisar corte de control principal)
      while (reg.partido <> PARTIDO_FIN) and (reg.localidad = locActual) do begin
        ninosLoc:= ninosLoc + reg.cantNinos;
        adultosLoc:= adultosLoc + reg.cantAdMay;
        // leer siguiente barrio
        leer(arch, reg);
      end;

      // totalizar localidad
      writeln('Cantidad ninos: ', ninosLoc, ' Cantidad adultos: ', adultosLoc);

    end;

  end;
end;

var

begin
end.

