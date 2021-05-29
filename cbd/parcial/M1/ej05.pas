program ej05;

// Constantes
const
  valor_alto = 'zzz';

// Declaración de tipos de variables
type
  tString = string[30];
  tRegistro = record
    partido,localidad,barrio:tString;
    cantNinos,cantMayores:integer;
  end;

  tArchivo = file of tRegistro;

// Procedimientos
procedure leer(var arch:tArchivo; var reg:tRegistro);
begin
  if not eof(arch) then read(arch,reg)
  else reg.partido:= valor_alto;
end;

// Precondición: se recibe archivo ya asignado
procedure procesar(var arch:tArchivo);
var
  reg:tRegistro;
  partidoAct,localidadAct:tString;
  indiceLoc,totalNinosLoc,totalMayoresLoc,totalNinosPar,totalMayoresPar:integer;
begin
  // abrir archivo
  reset(arch);

  // inicializar contador general
  indiceLoc:= 1;

  // leer primer registro
  leer(arch,reg);

  // procesar todos los registros
  while (reg.partido <> valor_alto) do begin
    partidoAct:= reg.partido;
    writeln('Partido: ',reg.partido);
    totalNinosPar:= 0;
    totalMayoresPar:= 0;

    // mientras sea mismo partido
    while (reg.partido = partidoAct) do begin
      localidadAct:= reg.localidad;
      writeln('Localidad ',indiceLoc,': ',localidadAct);
      totalNinosLoc:= 0;
      totalMayoresLoc:= 0;

      // mientras sea mismo partido y localidad
      while (reg.partido = partidoAct) and (reg.localidad = localidadAct) do begin
        with reg do begin
          write('Nombre del Barrio: ',barrio);
          write(' Cantidad ninos: ',cantNinos);
          writeln(' Cantidad adultos: ',cantMayores);
          totalNinosLoc:= totalNinosLoc+cantNinos;
          totalNinosPar:= totalNinosPar+cantNinos;
          totalMayoresLoc:= totalMayoresLoc+cantMayores;
          totalMayoresPar:= totalMayoresPar+cantMayores;
        end;

        // leer siguiente
        leer(arch,reg);
      end;

      // cambió la localidad
      write('Total ninos localidad ',indiceLoc,': ',totalNinosLoc);
      writeln(' Total adultos localidad ',indiceLoc,': ',totalMayoresLoc);
      indiceLoc:= indiceLoc + 1;
    end;

    // cambió el partido
    write('TOTAL NINOS PARTIDO: ',totalNinosPar);
    writeln(' TOTAL ADULTOS PARTIDO: ',totalMayoresPar);
  end;

  // cerrar el archivo
  close(arch);
end;

// Variables del programa principal
var
  arch:tArchivo;
  nombre:tString;

// Programa principal
begin
  writeln('Ingrese nombre del archivo binario: ');
  readln(nombre);
  assign(arch,nombre);

  procesar(arch);
  readln;
end.
