program ejer04;

const
  // todos los archivos vienen ordenado por X (define si es str o integer)
  // en este caso, codigo de pelicula
  VALOR_ALTO = 9999;

  // cantidad de archivos detalles (en este caso, cines)
  N = 20;

type
  // comunes
  str10 = string[10];
  str30 = string[30];

  // para detalles
  // primero el registro
  pelicula = record
    codigo: Integer;
    nombre: str30;
    genero: str10;
    director: str30;
    duracion: Integer;
    fecha: LongInt;
    asistentes: Integer;
  end;

  // luego el archivo
  detalle = file of pelicula;

  // por ultimo los arreglos
  arregloPel = array[1..N] of pelicula;
  arregloDet = array[1..N] of detalle;

  // en este caso se hace un merge
  // asi que coinciden registro y archivo para maestro


procedure leer(var archivo: detalle; var pel:pelicula);
begin
  if not eof(archivo) then read(archivo, pel)
  else pel.codigo:= VALOR_ALTO;
end;

procedure quienCodMin(var registros:arregloPel; var indiceMin:integer);
var
  min,i,res:integer;
begin
  min:=registros[1].codigo;
  res:=1;

  for i:=2 to N do if (registros[i].codigo < min) then begin
    min:= registros[i].codigo;
    res:= i;
  end;

  indiceMin:= res;
end;

procedure pelCodMin(var archivos: arregloDet; var pel:pelicula; var registros: arregloPel);
var
  indiceMin:integer;
begin
  quienCodMin(registros, indiceMin);
  pel:= registros[indiceMin];

  // avanzar en archivo donde estaba el minimo
  leer(archivos[indiceMin], registros[indiceMin]);
end;

// PROCEDIMIENTO PEDIDO
// PRECONDICION: ARREGLO DE ARCHIVOS ASIGNADO
procedure merge(var archivos: arregloDet; var dirMaestro: str30);
var
  maestro: detalle;
  i:Integer;
  peliculas: arregloPel;

  act,ant:pelicula;
  act_cod:integer;
  act_asis:integer;

begin
  // primero creamos el maestro
  assign(maestro, dirMaestro);
  rewrite(maestro);

  // abrimos los archivos detalles
  for i:=1 to N do reset(archivos[i]);

  // leemos una pelicula de cada detalle
  for i:=1 to N do leer(archivos[i], peliculas[i]);

  // buscamos el primer minimo
  pelCodMin(archivos, act, peliculas);

  // corte de control principal
  while (act.codigo <> VALOR_ALTO) do begin
    // es una nueva pelicula
    act_cod:= act.codigo;
    act_asis:= 0;
    ant:= act;

    // mientras sea misma pelicula
    while (act.codigo = act_cod) do begin
      act_asis:= act_asis + act.asistentes;
      pelCodMin(archivos, act, peliculas);
    end;

    // crear en maestro
    // no desplazar cursor porque no existia
    ant.asistentes:= act_asis;
    write(maestro, ant);

  end;

  // cerrar archivos
  close(maestro);
  for i:=1 to N do close(archivos[i]);

end;

begin
end.

