program ej03;
uses strutils;

// Constantes
const
  valor_fin = 0;
  txt_entrada = 'alumnos.txt';
  txt_salida = 'alumnosAEgresar.txt';

// Declaración de tipos de variables
type
  tString = string[30];
  tRegistro = record
    dni:integer;
    legajo:integer;
    nombre:tString;
    apellido:tString;
    direccion:tString;
    anio:integer;
    fechaNac:longInt;
  end;

  tArchivo = file of tRegistro;

// Procedimientos
procedure leer(var reg:tRegistro);
begin
  writeln('Ingrese legajo del alumno - ',valor_fin,' para terminar : ');
  readln(reg.legajo);

  if (reg.legajo <> valor_fin) then with reg do begin
    writeln('Ingrese dni: '); readln(dni);
    writeln('Ingrese apellido: '); readln(apellido);
    writeln('Ingrese nombre: '); readln(nombre);
    writeln('Ingrese direccion: '); readln(direccion);
    writeln('Ingrese anio; '); readln(anio);
    writeln('Ingrese fecha de nacimiento: '); readln(fechaNac);
  end;
end;

procedure imprimirAlumno(var alu:tRegistro);
begin
  with alu do writeln(legajo,' ',dni,' ',apellido,' ',nombre,' ',direccion,' ',anio,' ',fechaNac);
end;

procedure imprimirEnTxt(var alu:tRegistro; var txt:text);
begin
  with alu do writeln(txt,legajo,' ',dni,' ',apellido,' ',nombre,' ',direccion,' ',anio,' ',fechaNac);
end;

// Precondición: se reciben archivos ya asignados
procedure crearDesdeTxt(var arch:tArchivo; var txt:text);
var
  alu:tRegistro;
begin
  // abrir archivos
  rewrite(arch);
  reset(txt);

  // leer cada alumno desde txt
  while not eof(txt) do begin
    with alu do begin
      readln(txt,legajo);
      readln(txt,dni);
      readln(txt,apellido);
      readln(txt,nombre);
      readln(txt,direccion);
      readln(txt,anio);
      readln(txt,fechaNac);
    end;

    // escribir en archivo binario
    write(arch,alu);
  end;

  // cerrar archivos
  close(arch);
  close(txt);
end;

// Precondición: se recibe un archivo ya asignado
procedure listarTerminadosEn(var arch:tArchivo; var sufijo:tString);
var
  alu:tRegistro;
begin
  // abrir archivo
  reset(arch);

  // procesar cada alumno
  while not eof(arch) do begin
    read(arch,alu);
    if (ansiEndsStr(sufijo,alu.nombre)) then imprimirAlumno(alu);
  end;

  // cerrar archivo
  close(arch);
end;

// Precondición: se reciben archivos ya asignados
procedure listarAEgresar(var arch:tArchivo; var txt:text);
var
  alu:tRegistro;
begin
  // abrir archivos
  reset(arch);
  rewrite(txt);

  // procesar cada alumno
  while not eof(arch) do begin
    read(arch,alu);
    if (alu.anio = 5) then imprimirEnTxt(alu,txt);
  end;

  // cerrar archivos
  close(arch);
  close(txt);
end;

// Precondición: se recibe un archivo ya asignado
procedure appendBinario(var arch:tArchivo);
var
  alu:tRegistro;
begin
  // abrir archivo
  reset(arch);

  // mover al final
  seek(arch, filesize(arch));

  // leer primer alumno
  leer(alu);

  // cargar especies hasta valor_fin
  while (alu.legajo <> valor_fin) do begin
    write(arch,alu);

    // leer siguiente
    leer(alu);
  end;

  // cerrar archivo
  close(arch);
end;


// Precondición: se recibe un archivo ya asignado
procedure modificar(var arch:tArchivo; legajoBuscado:integer; fechaNueva:longInt);
var
  alu:tRegistro;
  encontrado:boolean;
begin
  // abrir archivo
  reset(arch);

  // inicializar variables locales
  encontrado:=false;

  // recorrer hasta encontrar nombre a cambiar
  while not (eof(arch) or encontrado) do begin
    read(arch,alu);
    if (alu.legajo = legajoBuscado) then begin
      // cambiar fecha de nacimiento
      alu.fechaNac:= fechaNueva;
      // retroceder una posición y reemplazar
      seek(arch, filepos(arch)-1);
      write(arch,alu);
      // detener la búsqueda
      encontrado:= true;
    end;
  end;

  // cerrar archivo
  close(arch);

  // informar resultado
  if encontrado then writeln('Fecha del alumno con legajo ',legajoBuscado,' actualizada con exito')
  else writeln(legajoBuscado,' no encontrado');
end;

// Variables del programa principal
var
  opc:byte;
  terminar:boolean;
  arch:tArchivo;
  txtIn,txtOut:text;
  nombre:tString;
  legajo:integer;
  fecha:longInt;

// Programa principal
begin
  writeln('Ingrese nombre del archivo binario de alumnos a trabajar: ');
  readln(nombre);

  assign(arch, nombre);
  assign(txtIn,txt_entrada);
  assign(txtOut,txt_salida);
  terminar:= false;

  while not terminar do begin
    writeln;
    writeln('Seleccione una opcion: ');
    writeln('0. Terminar el programa');
    writeln('1. Crear binario a partir de txt');
    writeln('2. Listar en pantalla los alumnos cuyos nombres finalicen con un substring ');
    writeln('3. Listar alumnos a engresar en un txt');
    writeln('4. Agregar alumnos al final del archivo binario por teclado');
    writeln('5. Modificar fecha de un alumno buscando por legajo');

    read(opc);
    readln;

    case opc of
      0 : terminar:= true;
      1 : crearDesdeTxt(arch,txtIn);
      2 : begin
        writeln('Ingrese sufijo a buscar: ');
        readln(nombre);
        listarTerminadosEn(arch,nombre);
      end;
      3 : listarAEgresar(arch,txtOut);
      4 : appendBinario(arch);
      5 : begin
        writeln('Ingrese legajo del alumno a modificar: ');
        readln(legajo);
        writeln('Ingrese nueva fecha de nacimiento: ');
        readln(fecha);
        modificar(arch,legajo,fecha);
      end;
    end;
  end;

  writeln('Fin del programa');
  readln;

end.


