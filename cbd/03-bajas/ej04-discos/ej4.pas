program ej4;

const CODIGO_FIN = -1;

type
  str30 = string[30];

  cd = record
    cod: Integer;
    nombreAlbum: str30;
    nombreArtista: str30;
    genero: str30;
    descripcion:str30;
    anio:integer;
    stock:integer;
  end;

  tArchivo = file of cd;

procedure marcarSinStock(var arch:tArchivo);
var
  cod:integer;
  encontrado:boolean;
  act:cd;
begin
  writeln('Ingrese codigos sin stock, ', CODIGO_FIN, ' para terminar:');
  reset(arch);
  read(cod);

  while (cod <> CODIGO_FIN) do begin
    seek(arch, 0);
    encontrado:= false;

    while (not encontrado) and (not eof(arch))  do begin
      read(arch, act);
      encontrado:= (act.cod = cod);
    end;

    if (encontrado) then begin
      // informar nombre de album
      writeln(act.nombreAlbum);

      // efectuar marcado
      seek(arch, filepos(arch)-1);
      act.stock:=0;
      write(arch, act);
    end else writeln('No encontrado :(');

    // leer otro codigo
    read(cod);
  end;

  writeln('Archivo actualizado');
  close(arch);
end;

procedure bajaFisica(var arch:tArchivo);
var
  act,last:cd;
  posBorrar:integer;
begin
  reset(arch);

  while not eof(arch) do begin
    read(arch, act);
    if (act.stock = 0) then begin
      posBorrar:= filepos(arch)-1;

      // i need to move last cd
      seek(arch, filesize(arch)-1);
      read(arch, last);
      seek(arch, posBorrar);
      write(arch, last);
      seek(arch, filesize(arch)-1);
      truncate(arch);

      // dejar cursor en posicion anterior
      seek(arch, posBorrar);
    end;
  end;

  close(arch);
end;

procedure mostrarOp;
begin
  writeln('0 - Terminar programa');
  writeln('1 - Ingresar codigos de cds sin stock');
  writeln('2 - Compactar archivo');
end;

// programa principal
var
  op:char;
  arch:tArchivo;

begin
  assign(arch, 'cds.dat');
  writeln('Bienvenido. Elija una opcion');
  mostrarOp;
  read(op);

  while (op <> '0') do begin
    case op of
      '1': marcarSinStock(arch);
      '2': bajaFisica(arch);
    end;

    readln;
    writeln;
    mostrarOp;
    read(op);
  end;

end.

