program ej04;

// Constantes
const
  valor_alto = 1000000;

// Declaración de tipos de variables
type
  tString = string[30];
  tRegistro = record
    codAutor:integer;
    nombreAutor:tString;
    nombreDisco:tString;
    genero:tString;
    cantVendida:integer;
  end;

  tArchivo = file of tRegistro;

// Procedimientos
procedure leer(var arch:tArchivo; var reg:tRegistro);
begin
  if not eof(arch) then read(arch,reg)
  else reg.codAutor:= valor_alto;
end;

// Precondición: se reciben archivos ya asignados
procedure procesar(var arch:tArchivo; var txtSalida:text);
var
  reg:tRegistro;
  codAutorAct:integer;
  generoAct:tString;
  totalGen,totalAutor,total:integer;
begin
  // abrir archivos
  reset(arch);
  rewrite(txtSalida);

  // inicializar contador general
  total:= 0;

  // leer primer cd
  leer(arch,reg);

  // procesar todos los cds
  while (reg.codAutor <> valor_alto) do begin
    codAutorAct:= reg.codAutor;
    writeln('Autor: ',reg.nombreAutor);
    totalAutor:= 0;

    // mientras sea mismo autor
    while (reg.codAutor = codAutorAct) do begin
      generoAct:= reg.genero;
      writeln('Genero: ',generoAct);
      writeln;
      totalGen:= 0;

      // mientras sea mismo autor y genero
      while (reg.codAutor = codAutorAct) and (reg.genero = generoAct) do begin
        writeln('Nombre Disco: ',reg.nombreDisco,' cantidad vendida: ',reg.cantVendida);
        with reg do writeln(txtSalida,nombreDisco,' ',nombreAutor,' ',cantVendida);
        totalAutor:= totalAutor + 1;
        totalGen:= totalGen + 1;
        total:= total + 1;

        // leer siguiente
        leer(arch,reg);
      end;

      // cambió el genero
      writeln;
      writeln('Total Genero: ',totalGen);

    end;

    // cambió el autor
    writeln('Total Autor: ',totalAutor);
  end;

  // cerrar el archivo
  close(arch);
  close(txtSalida);

  // mostrar total general
  writeln('Total Discografica: ',total);
end;

// Variables del programa principal
var
  arch:tArchivo;
  txt:text;
  nombre:tString;

// Programa principal
begin
  writeln('Ingrese nombre del archivo binario de cds: ');
  readln(nombre);
  assign(arch,nombre);

  writeln('Ingrese nombre del txt donde listar: ');
  readln(nombre);
  assign(txt,nombre);

  procesar(arch,txt);
  readln;
  readln;
end.
