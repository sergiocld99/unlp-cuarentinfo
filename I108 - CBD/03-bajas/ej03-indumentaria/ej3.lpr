program ej3;

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

  while not eof(archT) do begin
    leerDesdeTexto(archT, act);
    write(dest, act);
  end;

  close(dest);
  close(archT);
end;

procedure marcarParaBorrar(var arch:tArchivo);
var
  cod:integer;
  encontrado:boolean;
  act:tProducto;
begin
  writeln('ingrese codigos de indumentaria obsoleta, -1 para terminar: ');
  read(cod);
  reset(arch);

  while (cod <> -1) do begin
    seek(arch, 0);
    encontrado:= false;

    // buscar en archivo
    while (not encontrado) and (not eof(arch)) do begin
      read(arch, act);
      encontrado:= (act.cod = cod);
    end;

    if (encontrado) then begin
      act.stock:=-1;
      seek(arch, filepos(arch)-1);
      WRITE(ARCH, ACT);
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
  writeln('Ingrese descripcion: ');
  readln(p.descripcion);
  writeln('Ingrese stock (positivo): ');
  readln(p.stock);
end;

procedure agregar(var arch:tProducto; var p:tProducto);
var
  escrito:boolean;
  act:tProducto;
begin
  reset(arch);
  escrito:=false;

  while (not escrito) and (not eof(arch)) do begin
    read(arch, act);
    if (act.stock < 0) then begin
      escrito:= true;
      seek(arch, filepos(arch)-1);
      write(arch, p);
    end;
  end;

  if not escrito then write(arch, p);

  close(arch);
end;

// programa principal
var


begin
end.

