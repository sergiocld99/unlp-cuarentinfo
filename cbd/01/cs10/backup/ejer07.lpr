program ejer07;
uses strutils;

const
  NOMBRE_FUENTE = 'alumnos.txt';
  NOMBRE_DEST_EGR = 'alumnosAEgresar.txt';

type
  TipoLegajo = string[7];
  TipoNombre = string[20];

  TipoAlumno = record
    dni: integer;
    legajo: TipoLegajo;
    nombre: TipoNombre;
    apellido: TipoNombre;
    direccion: string[30];
    anio: integer;
    fechaNac: longInt;
  end;

  ArchBinario = file of TipoAlumno;

// PROCEDIMIENTO PARA LEER UN ALUMNO POR TECLADO
procedure leerAlumno(var alumno: TipoAlumno);
begin
  write('Ingrese DNI (sin puntos):  ');
  readln(alumno.dni);
  write('Ingrese legajo: ');
  readln(alumno.legajo);
  write('Ingrese apellido: ');
  readln(alumno.apellido);
  write('Ingrese nombres: ');
  readln(alumno.nombre);
  write('Ingrese direccion: ');
  readln(alumno.direccion);
  write('Ingrese anio en el que se encuentra: ');
  readln(alumno.anio);
  write('Ingrese fecha de nacimiento en formato DDMMYYYY: ');
  readln(alumno.fechaNac);
end;

// PROCEDIMIENTO PARA LEER UN ALUMNO DESDE ARCHIVO DE TEXTO (YA ABIERTO)
procedure leerAlumno(var alumno: TipoAlumno; var archivo: text);
begin
  read(archivo, alumno.dni);
  read(archivo, alumno.anio);
  read(archivo, alumno.fechaNac);
  readln(archivo, alumno.legajo);
  readln(archivo, alumno.apellido);
  readln(archivo, alumno.nombre);
  readln(archivo, alumno.direccion);
end;

// PROCEDIMIENTO PARA MOSTRAR UN ALUMNO POR PANTALLA
procedure mostrarAlumno(var alumno: TipoAlumno);
begin
  write('DNI ', alumno.dni);
  write(' - Legajo ', alumno.legajo);
  write(' - ', alumno.apellido, ' ', alumno.nombre);
  write(' - Direccion: ', alumno.direccion);
  write(' - Anio: ', alumno.anio);
  write(' - Fecha Nac: ', alumno.fechaNac);
end;

// PROCEDIMIENTO PARA ESCRIBIR UN ALUMNO EN ARCHIVO DE TEXTO
procedure escribirAlumno(var alumno: TipoAlumno; var archivo: text);
begin
  write(archivo, 'DNI ', alumno.dni);
  write(archivo, ' - Legajo ', alumno.legajo);
  write(archivo, ' - ', alumno.apellido, ' ', alumno.nombre);
  write(archivo, ' - Direccion: ', alumno.direccion);
  write(archivo, ' - Anio: ', alumno.anio);
  write(archivo, ' - Fecha Nac: ', alumno.fechaNac);
end;

// PROCEDIMIENTO PARA AGREGAR UN ALUMNO AL ARCHIVO BINARIO
procedure agregarAlumno(var binario: ArchBinario);
var
  mAlumno: TipoAlumno;
begin
  leerAlumno(mAlumno);
  seek(binario, fileSize(binario));
  write(binario, mAlumno);
end;

// PROCEDIMIENTO DEL INCISO B (PRECONDICION: ARCHIVO ABIERTO, CURSOR AL INICIO)
procedure listarAlumnosTerminadosEn(var sufijo: TipoNombre; var archivo: ArchBinario);
var
  mAlumno: TipoAlumno;
begin
  while not EOF(archivo) do begin
     read(archivo, mAlumno);
     if (AnsiEndsStr(sufijo, mAlumno.nombre)) then mostrarAlumno(mAlumno);
  end;
end;

// PROCEDIMIENTO DEL INCISO C (PRECONDICION: ARCHIVOS ABIERTOS)
procedure listarAlumnosQuinto(var fuente: ArchBinario; var destino: text);
var
  mAlumno: TipoAlumno;
begin
  // leer hasta el final
  while not EOF(fuente) do begin
     read(fuente, mAlumno);
     if (mAlumno.anio = 5) then begin
        // listarlo en el archivo
        escribirAlumno(mAlumno, destino);
     end;
  end;

  writeln('Finalizo el listado de los alumnos de 5to en archivo de texto');
end;

// PROCEDIMIENTO DEL INCISO E (PRECONDICION: CURSOR AL INICIO)
procedure modificarAlumno(var legajo: TipoLegajo; var archivo: ArchBinario);
var
  pos: integer;
  encontrado: boolean;
  mAlumno: TipoAlumno;
begin
  // Inicializaciones
  pos:= 0;
  encontrado:= false;

  // Busco alumno
  while (not encontrado and not EOF(archivo)) do begin
     // revisar si coincide
     read(archivo, mAlumno);
     if (mAlumno.legajo = legajo) then encontrado:= true
     else pos:= pos+1;
  end;

  if (encontrado) then begin
     // Cambio fecha
     writeln('Ingrese la nueva fecha:  ');
     readln(mAlumno.fechaNac);
     seek(archivo, pos);
     write(archivo, mAlumno);
  end else writeln('Alumno no encontrado');
end;

// PROCEDIMIENTO DEL INCISO A (PRECONDICIÓN: ARCHIVOS ABIERTOS)
procedure generarBinario(var fuente: text; var destino: ArchBinario);
var
  mAlumno: TipoAlumno;
begin
  while not EOF(fuente) do begin
     leerAlumno(mAlumno, fuente);
     write(destino, mAlumno);
  end;
end;

// PROCEDIMIENTO PARA MOSTRAR LAS OPCIONES UNICAMENTE
procedure mostrarOpcionesMP;
begin
  writeln('MENU PRINCIPAL - Elija una opcion');
  writeln('0 - Terminar el programa');
  writeln('1 - Crear archivo binario a partir de ', NOMBRE_FUENTE);
  writeln('2 - Listar alumnos con nombres finalizados en...');
  writeln('3 - Listar alumnos de 5to en ', NOMBRE_DEST_EGR);
  writeln('4 - Agregar alumnos por teclado al final del archivo binario');
  writeln('5 - Modificar la fecha de un alumno');
  writeln;
end;

// --------------------------------------
// |                                    |
// |      PROGRAMA PRINCIPAL            |
// |                                    |
// --------------------------------------
var
  opc: char;
  archFuente: text;
  mBinario: ArchBinario;
  archDest: text;
  nombreBinario: string[20];
  legajo: TipoLegajo;
  sufijo: TipoNombre;

begin
  // Pre-acciones
  writeln('Ingrese el nombre del archivo binario con el cual trabajar: ');
  readln(nombreBinario);
  assign(mBinario, nombreBinario);
  assign(archFuente, NOMBRE_FUENTE);
  assign(archDest, NOMBRE_DEST_EGR);

  // Bucle principal
  mostrarOpcionesMP;
  read(opc);

  while (opc <> '0') do begin
     case opc of
       '1': begin
          rewrite(mBinario);
          reset(archFuente);
          generarBinario(archFuente, mBinario);
          close(mBinario);
          close(archFuente);
          readln;
       end;
       '2' : begin
          writeln('Ingrese el sufijo a buscar: ');
          readln;
          readln(sufijo);
          reset(mBinario);
          listarAlumnosTerminadosEn(sufijo, mBinario);
          close(mBinario);
          readln;
       end;
       '3': begin
          reset(mBinario);
          rewrite(archDest);
          listarAlumnosQuinto(mBinario, archDest);
          close(archDest);
          close(mBinario);
          readln;
       end;
       '4': begin
          reset(mBinario);
          agregarAlumno(mBinario);
          close(mBinario);
       end;
       '5': begin
          reset(mBinario);
          writeln('Ingrese legajo del alumno a modificar: ');
          readln(legajo);
          modificarAlumno(legajo, mBinario);
          close(mBinario);
       end;
     end;

     // Volver a pedir opcion
     mostrarOpcionesMP;
     read(opc);
  end;

  // Terminar programa
  writeln('Fin del programa');
  readln;
end.

