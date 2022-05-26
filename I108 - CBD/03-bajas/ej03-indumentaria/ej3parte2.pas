program ej3parte2;

type
  str30 = string[30];

  tProducto = record
    cod:integer;
    nombre:str30;
    descripcion:str30;
    stock:integer;
  end;

  tArchivo = file of tProducto;

procedure leerDesdeTexto(var archT:text; var reg:tProducto);
begin
  read(archT, reg.cod);
  read(archT, reg.stock);
  readln(archT, reg.nombre);
  readln(archT, reg.descripcion);
end;

procedure generarBinario(var archT:text);
var
  act:tProducto;
  dest:tArchivo;
begin
  reset(archT);

  assign(dest, 'binario.dat');
  rewrite(dest);

  // header
  act.descripcion:='0';
  write(dest, act);

  while not eof(archT) do begin
    leerDesdeTexto(archT, act);
    write(dest, act);
  end;

  close(dest);
  close(archT);
end;
Aa

procedure bajar(var arch:tArchivo);
var
  cod:integer;
  encontrado:boolean;
  act,header:tProducto;
begin
  writeln('ingrese codigos de indumentaria obsoleta, -1 para terminar: ');
  read(cod);
  reset(arch);

  read(arch, header);

  while (cod <> -1) do begin
    seek(arch, 1);
    encontrado:= false;

    // buscar en archivo
    while (not encontrado) and (not eof(arch)) do begin
      read(arch, act);
      encontrado:= (act.cod = cod);
    end;

    if (encontrado) then begin
      act.descripcion:=header.descripcion;
      seek(arch, filepos(arch)-1);
      str(filepos(arch), header.descripcion);
      WRITE(ARCH, ACT);

      // actualizo el header
      seek(arch, 0);
      write(arch, header);

    end ELSE WRITELN('No encontrado :(');

    // pedir siguiente
    read(cod);
  end;

  close(arch);
end;

procedure pedirProducto(var p:tProducto);
begin
  writeln('Ingrese codigo: ');
  readln(p.cod);
  writeln('Ingrese nombre: ');
  readln(p.nombre);
  p.descripcion:='0';
  writeln('Ingrese stock (positivo): ');
  readln(p.stock);
end;

procedure agregar(var arch:tProducto; var p:tProducto);
var
  pos,cod:integer;
  act,header:tProducto;
begin
  reset(arch);
  escrito:=false;

  // leer header
  read(arch, header);
  val(header.descripcion, pos, cod);

  if (pos = 0) then pos:=filesize(arch)
  else begin
    seek(arch, pos);
    read(arch, act);
    header.descripcion:=act.descripcion;
    seek(arch, 0);
    write(arch, header);
  end;

  seek(arch, pos);
  write(arch, p);

  close(arch);
end;

// programa principal
var


begin
end.

