program ej08;

type
  str30 = string[30];
  articulo = record
    nro:integer;
    descripcion:str30;
    color:str30;
    talle:integer;
    stock:integer;
    precio:real;
  end;

  tArchivo = file of articulo;

// Se reciben archivos ya asignados, solo se efectuan marcas de borrado (no cambia el tamaño del archivo)
procedure bajaLogica(var arch:tArchivo; var txt:text);
var
  art:articulo;
  nroEliminar:integer;
begin
  reset(arch);
  rewrite(txt);

  writeln('Ingrese nro de articulo a eliminar (ingrese -1 para terminar):');
  readln(nroEliminar);
  read(arch,art);

  // Suponemos que todos los codigos ingresados existen en el binario
  // y además, los códigos se ingresan en orden ascendente
  while (nroEliminar <> -1) do begin
    while (art.nro <> nroEliminar) do read(arch,art);
    // marca de borrado es -1 en nro de articulo
    art.nro:=-1;

    // retroceder en archivo binario y reemplazar
    seek(arch,filepos(arch)-1);
    write(arch,art);

    // listar en archivo de texto
    with art do writeln(txt,nro,' ',descripcion,' ',color,' ',talle,' ',stock,' ',precio:5:2);

  end;

  close(arch);
  close(txt);
end;


var
  arch:tArchivo;
  txt:text;
  nombre:str30;

begin
  writeln('Ingrese nombre del archivo binario a trabajar: ');
  readln(nombre);
  assign(arch,nombre);

  writeln('Ingrese nombre del archivo de texto donde listar: ');
  readln(nombre);
  assign(txt,nombre);

  bajaLogica(arch,txt);
  readln;
end.

