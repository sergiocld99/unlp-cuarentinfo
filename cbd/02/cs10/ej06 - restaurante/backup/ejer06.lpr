program ejer06;

const
  VALOR_ALTO = 9999;

type
  servicio = record
    codigo:integer;
    fecha:longint;
    monto:real;
  end;

  mozo = record
    codigo:integer;
    total:real;
  end;

  // un mozo puede tener n servicios
  detalle = file of servicio;
  maestro = file of mozo;


procedure leer(var arch:detalle; var reg:servicio);
begin
  if eof(arch) then reg.codigo:= VALOR_ALTO
  else read(arch, reg);
end;

// precondicion: archivo asignado
procedure compactar(var fuente:detalle);
var
  destino:maestro;
  act:servicio;
  aux:mozo;
  suma:real;

begin
  // crear destino
  assign(destino, 'resumen.dat');

  // abrir archivos
  rewrite(destino);
  reset(fuente);

  // leer primero
  leer(fuente, act);

  // corte de control
  while (act.codigo <> VALOR_ALTO) do begin
    // nuevo mozo
    aux.codigo:= act.codigo;
    aux.total:= 0;

    // mientras sea mismo mozo
    while (act.codigo = aux.codigo) do begin
      aux.total:= aux.total + act.monto;
      leer(fuente, act);
    end;

    // cambio
    write(destino, aux);
  end;

  // cerrar archivos
  close(fuente);
  close(destino);
end;

// PROGRAMA PRINCIPAL
var
  miFuente: detalle;

begin
  assign(miFuente, 'servicios.dat');
  compactar(miFuente);
end.

