program ej1;

const CODIGO_FIN = 100000;

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

procedure marcarParaBorrar(var a:archivo);
var
  cod:integer;
  act:especie;
  encontrado:boolean;
begin
  writeln('Ingrese codigo de especie a eliminar (ingrese ', CODIGO_FIN, ' para terminar)');
  read(cod);

  while (cod <> CODIGO_FIN) do begin
    reset(a);
    encontrado:= false;

    while (not encontrado) and (not eof(a)) do begin
      read(a, act);
      encontrado:= (act.codigo = cod);
    end;

    if (encontrado) then begin
      act.codigo:= -1;
      seek(a, filepos(a)-1);
      write(a, act);
    end else writeln('No encontrado :(');

    // leer siguiente
    read(cod);
  end;

  close(a);
end;

procedure crearCompactado(var fuente:archivo);
var
  dest:archivo;
  act:especie;
begin
  assign(dest, 'compactado.dat');
  rewrite(dest);
  reset(fuente);

  while not eof(fuente) do begin
    read(fuente, act);
    if (act.codigo <> -1) then write(dest, act);
  end;

  close(fuente);
  close(dest);
end;

procedure recuperarEspacio(var a:archivo);
var
  act,aux:especie;
  posBorrar:integer;
begin
  reset(a);

  while not eof(a) do begin
    read(a, act);
    if (act.codigo = -1) then begin
      posBorrar:= filepos(a)-1;
      seek(a, filesize(a)-1);
      read(a, aux);
      seek(a, posBorrar);
      write(a, aux);
      seek(a, filesize(a)-1);
      truncate(a);

      // dejar cursor en posicion previa
      seek(a, posBorrar);
    end;
  end;

  close(a);
end;

procedure mostrarOp;
begin
  writeln('0 - Terminar programa');
  writeln('1 - Marcar especies a borrar');
  writeln('2 - Crear archivo compactado');
  writeln('3 - Recuperar espacio en archivo fuente');
end;

var
  op:char;
  a:archivo;

begin
  assign(a, 'plantas.dat');
  writeln('Bienvenido. Por favor, ingrese una opcion');
  mostrarOp;
  read(op);

  while (op <> '0') do begin
    case op of
      '1': marcarParaBorrar(a);
      '2': crearCompactado(a);
      '3': recuperarEspacio(a);
    end;

    readln;
    writeln;
    mostrarOp;
    read(op);
  end;
end.

