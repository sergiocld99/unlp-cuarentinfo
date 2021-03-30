program ej5;

const
  NOMBRE_ARCHIVO = 'maestro.dat';
  CODIGO_FIN = 9999;

type
  str30 = string[30];
  str10 = string[10];

  tArticulo = record
    nro:integer;
    descripcion:str30;
    color:str10;
    talle:integer;
    stock:integer;
    precio:real;
  end;

  tArchivo = file of tArticulo;

  tNodo = ^nodo;
  nodo = record
    dato:integer;
    sig:tNodo;
  end;

procedure leerBajas(var lista:tNodo);
var
  cod:integer;
  ant,act,nuevo:tNodo;
begin

  WRITELN('Ingrese codigos de articulos a eliminar, 9999 para terminar: ');
  read(cod);

  while (cod <> CODIGO_FIN) do begin
    // crear nodo
    new(nuevo);
    nuevo^.dato:=cod;

    // inicializar auxiliares
    act:= lista;
    ant:= NIL;

    // buscar posicion
    while (act <> NIL) and (act^.dato < cod) do begin
      ant:= act;
      act:= act^.sig;
    end;

    nuevo^.sig:=act;

    // insercion
    if (ant = NIL) then lista:=nuevo
    else ant^.sig:=nuevo;

    // leer nuevo codigo
    read(cod);
  end;

end;

procedure bajaLogica(var lista:tNodo; var arch:tArchivo);
var
  nodoAct:tNodo;
  regAct:tArticulo;
  encontrado:boolean;
begin
  // inicializar
  nodoAct:= lista;
  reset(arch);

  // recorrer lista
  while (nodoAct <> NIL) do begin
    // buscar en archivo ordenado
    encontrado:= false;

    while (not encontrado) and (not eof(arch)) do begin
      read(arch, regAct);
      encontrado:= (regAct.nro = nodoAct^.dato);
    end;

    if (encontrado) then begin
      regAct.descripcion:='0';
    end else writeln('articulo nro ', nodoAct^.dato, ' no encontrado');

    // avanzar en la lsta
    nodoAct:= nodoAct^.sig;

  end;

  close(arch);
end;

procedure bajaFisica(var arch:tArchivo);
var
  act:tArticulo;
  arch2:tArchivo;
begin
  assign(arch2, 'nuevo.dat');
  rewrite(arch2);
  reset(arch);

  while not eof(arch) do begin
    read(arch, act);
    // copiar los no marcados
    if (act.descripcion <> '0') then write(arch2, act);
  end;

  close(arch);
  close(arch2);

  // borrar archivo viejo
  erase(arch);

  // renombrar nuevo
  rename(arch2, NOMBRE_ARCHIVO);
end;

procedure mostrarOp;
begin
  writeln('0 - terminar programa');
  writeln('1 - marcar articulos a bajar');
  writeln('2 - compactar fisicamente el archivo');
end;

// programa principal
var
  lsta:tNodo;
  arch:tArchivo;
  op:char;

begin
  assign(arch, NOMBRE_ARCHIVO);
  writeln('bienvenido, ingrese una opcion');
  mostrarOp;
  read(op);

  while (op <> '0') do begin
    case op of
      '1': begin
        lsta:=NIL;
        leerBajas(lsta);
        bajaLogica(lsta, arch);
      end;
      '2': bajaFisica(arch);
    end;

    // leer opcion
    readln;
    writeln;
    mostrarOp;
    read(op);
  end;

  writeln('fin del programa');
  readln;
  readln;
end.

