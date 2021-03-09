program ejer06;

const
  NOMBRE_FUENTE = 'libros.txt';
  ISBN_LENGTH = 14;

type
  TipoLibro = record
    isbn: string[ISBN_LENGTH];
    titulo: string[30];
    year: integer;
    editorial: string[20];
    genero: string[15];
  end;

  BinarioLibro = file of TipoLibro;

// PROCEDIMIENTO DEL PRIMER INCISO
procedure leerLibro(var libro: TipoLibro; var fuente: text);
begin
  read(fuente, libro.isbn);
  readln(fuente, libro.titulo);
  read(fuente, libro.year);
  readln(fuente, libro.editorial);
  readln(fuente, libro.genero);
end;

// PROCEDIMIENTO DEL SEGUNDO INCISO
procedure pedirLibro(var libro: TipoLibro);
begin
  write('Ingrese ISBN (13 digitos): ');
  readln(libro.isbn);
  write('Ingrese titulo del libro: ');
  readln(libro.titulo);
  write('Ingrese anio de edicion: ');
  readln(libro.year);
  write('Ingrese editorial: ');
  readln(libro.editorial);
  write('Ingrese genero: ');
  readln(libro.genero);
end;

// PRECONDICION: ARCHIVOS ABIERTOS EN EL MODO CORRECTO
procedure crearBinario(var fuente: text; var destino: BinarioLibro);
var
  mLibro: TipoLibro;
begin
  while not (EOF(fuente)) do begin
    leerLibro(mLibro, fuente);
    write(destino, mLibro);
  end;
end;

// PRECONDICION: ARCHIVO ABIERTO EN MODO ESCRITURA, CURSOR AL FINAL
procedure agregarLibro(var binario: BinarioLibro);
var
  mLibro : TipoLibro;
begin
  readln;
  pedirLibro(mLibro);
  write(binario, mLibro);
end;

// PRECONDICION: ARCHIVO ABIERTO EN MODO LECTURA/ESCRITURA, CURSOR AL INICIO
procedure modificarLibro(var binario: BinarioLibro; isbn: string);
var
  pos: integer;
  encontrado: boolean;
  mLibro: TipoLibro;
begin
  pos:= 0;
  encontrado:= false;

  while (not encontrado and not EOF(binario)) do begin
    read(binario, mLibro);
    if (mLibro.isbn = isbn) then encontrado:= true
    else pos:= pos+1;
  end;

  if (encontrado) then begin
    pedirLibro(mLibro);
    seek(binario, pos);
    write(binario, mLibro);
    writeln('Modificado con exito');
  end else writeln('ISBN no encontrado :(');
end;

// PROCEDIMIENTO PARA MOSTRAR OPCIONES UNICAMENTE
procedure mostrarOpcionesMP;
begin
  writeln('Elija una opcion:');
  writeln('0 - Terminar el programa');
  writeln('1 - Crear binario a partir de ', NOMBRE_FUENTE);
  writeln('2 - Agregar un libro al binario');
  writeln('3 - Modificar libro existente en el binario');
  writeln('4 - Listar binario');
  writeln;
end;

// PROCEDIMIENTO OPCIONAL PARA LISTAR BINARIO (VERIFICACION)
procedure listarBinario(var binario : BinarioLibro);
var
  mLibro: TipoLibro;
begin
  while not EOF(binario) do begin
    read(binario, mLibro);
    write('ISBN: ', mLibro.isbn);
    write('. Titulo: ', mLibro.titulo);
    write('. Edicion: ', mLibro.year);
    write('. Editorial: ', mLibro.editorial);
    write('. Genero: ', mLibro.genero);
    writeln;
  end;
end;

// VARIABLES DEL PROGRAMA PRINCIPAL
var
  archFuente: text;
  opc: char;

  archDest: BinarioLibro;
  nombreDest: string[20];
  isbn: string[ISBN_LENGTH];

// PROGRAMA PRINCIPAL
begin
  // Inicializaciones
  assign(archFuente, NOMBRE_FUENTE);

  // Nombre de binario definido por el usuario
  writeln('Ingrese el nombre del archivo binario a trabajar');
  readln(nombreDest);
  assign(archDest, nombreDest);

  mostrarOpcionesMP;
  read(opc);

  while (opc <> '0') do begin
    case opc of
      '1' : begin
        reset(archFuente);
        rewrite(archDest);
        crearBinario(archFuente, archDest);
        writeln('Binario creado con exito');

        // Cerrar archivos
        close(archFuente);
        close(archDest);
        readln;
      end;
      '2' : begin
        // Abrir binario
        reset(archDest);

        // Posicionarse al final
        seek(archDest, fileSize(archDest));

        // Agregar un libro
        agregarLibro(archDest);
        writeln('Libro agregado con exito');

        // Cerrar binario
        close(archDest);
      end;
      '3': begin
        // Pedir isbn
        writeln('Ingrese ISBN (13 digitos) del libro a modificar: ');
        readln(isbn);

        // Abrir binario
        reset(archDest);

        // Modificar
        modificarLibro(archDest, isbn);

        // Cerrar binario
        close(archDest);
      end;
      '4': begin
        reset(archDest);
        listarBinario(archDest);

        // Cerrar binario
        close(archDest);
        readln;
      end;
    end;

    // Pedir opcion
    writeln;
    mostrarOpcionesMP;
    read(opc);
  end;

  // Fin del programa
  writeln('Fin del programa');
  readln;

end.

