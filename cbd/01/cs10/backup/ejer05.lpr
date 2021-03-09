program ejer05;

const
  PALABRA_FIN = 'zzz';
  ARCHIVO_LISTADO = 'flores.txt';

// -- DECLARACIÓN DE TIPOS --
type
  TipoEspecie = record
    numEspecie: integer;
    alturaMax: real;
    nomCientifico: string[25];
    nomVulgar: string[20];
    color: string[15];
  end;

  ArchEspecie = file of TipoEspecie;

// -- PROCEDIMIENTOS --
procedure leerEspecie(var especie: TipoEspecie);
begin
  writeln('Ingrese numero de especie: ');
  readln(especie.numEspecie);
  writeln('Ingrese altura maxima: ');
  readln(especie.alturaMax);
  writeln('Ingrese nombre vulgar: ');
  readln(especie.nomVulgar);
  writeln('Ingrese color: ');
  readln(especie.color);
end;

// PROCEDIMIENTO: EL ARCHIVO ESTA ABIERTO EN MODO ESCRITURA
procedure cargarEspecies(var archivo: ArchEspecie);
var
  nombreC: string[25];
  mEspecie: TipoEspecie;

begin
  writeln('Ingrese el nombre cientifico: ');
  readln(nombreC);

  // Bucle
  while (nombreC <> PALABRA_FIN) do begin
    mEspecie.nomCientifico:= nombreC;
    leerEspecie(mEspecie);
    write(archivo, mEspecie);
    writeln();
    writeln('NUEVA ESPECIE');
    writeln('Ingrese el nombre cientifico: ');
    readln(nombreC);
  end;

end;

procedure imprimirEspecie(var especie: TipoEspecie);
begin
  write('Nombre cientifico: ', especie.nomCientifico);
  write('. Numero de especie: ', especie.numEspecie);
  write('. Altura maxima: ', especie.alturaMax:0:2);
  write('. Nombre vulgar: ', especie.nomVulgar);
  write('. Color: ', especie.color);
  writeln;
end;

procedure imprimirEnArchivo(var especie: TipoEspecie; var archivo: text);
begin
  write(archivo, 'Nombre cientifico: ', especie.nomCientifico);
  write(archivo, '. Numero de especie: ', especie.numEspecie);
  write(archivo, '. Altura maxima: ', especie.alturaMax:0:2);
  write(archivo, '. Nombre vulgar: ', especie.nomVulgar);
  write(archivo, '. Color: ', especie.color);
  writeln(archivo);
end;

// PRECONDICION: EL ARCHIVO ESTÁ ABIERTO EN MODO LECTURA Y CURSOR AL INICIO
procedure reportarResumen(var archivo: ArchEspecie);
var
  cantEspecies: integer;
  alturaMin, alturaMax: real;
  mEspecieMin, mEspecieMax: TipoEspecie;
  mEspecie: TipoEspecie;
begin
  cantEspecies:= 0;
  alturaMin:= 9999;
  alturaMax:= 0;

  while not (EOF(archivo)) do begin
    read(archivo, mEspecie);
    cantEspecies:= cantEspecies + 1;

    // Verificar minima altura
    if (mEspecie.alturaMax < alturaMin) then begin
      alturaMin:= mEspecie.alturaMax;
      mEspecieMin:= mEspecie;
    end;

    // Verificar maxima altura
    if (mEspecie.alturaMax > alturaMax) then begin
      alturaMax:= mEspecie.alturaMax;
      mEspecieMax:= mEspecie;
    end;

  end;

  // Reporte
  writeln('Se tienen ', cantEspecies, ' especies');
  writeln;

  if (cantEspecies > 0) then begin
    writeln('La especie de menor altura a alcanzar es: ');
    imprimirEspecie(mEspecieMin);
    writeln;
    writeln('La especie de mayor altura a alcanzar es: ');
    imprimirEspecie(mEspecieMax);
    writeln;
  end;
end;

// PRECONDICION: EL ARCHIVO ESTA ABIERTO EN MODO LECTURA Y CURSOR AL INICIO
procedure listarEspecies(var archivo: ArchEspecie);
var
  mEspecie: TipoEspecie;
begin
  while not (EOF(archivo)) do begin
    read(archivo, mEspecie);
    imprimirEspecie(mEspecie);
  end;
end;

// PRECONDICION: ARCHIVO FUENTE EN MODO LECTURA, CURSOR AL INICIO
procedure listarEnArchivo(var fuente: ArchEspecie);
var
  mEspecie: TipoEspecie;
  destino: text;
begin
  // Crear archivo destino
  assign(destino, ARCHIVO_LISTADO);
  rewrite(destino);

  // Listar
  while not (EOF(fuente)) do begin
    read(fuente, mEspecie);
    imprimirEnArchivo(mEspecie, destino);
  end;

  // Cerrar destino
  close(destino);
end;

// PRECONDICION: EL ARCHIVO ESTA ABIERTO EN MODO LECTURA Y CURSOR AL INICIO
procedure buscarReemplazar(var archivo: ArchEspecie);
var
  query, nuevoNombre: string[20];
  mEspecie: TipoEspecie;
  encontrado: boolean;
  pos: integer;
begin
  encontrado:= false;
  pos:= 0;

  writeln('Ingrese nombre cientifico a reemplazar: ');
  readln;
  readln(query);

  while (not (EOF(archivo)) and not encontrado) do begin
    read(archivo, mEspecie);
    if (mEspecie.nomCientifico = query) then encontrado:= true
    else pos:= pos+1;
  end;

  if (encontrado) then begin
    writeln('Ingrese nombre cientifico nuevo: ');
    readln(nuevoNombre);
    mEspecie.nomCientifico:= nuevoNombre;
    seek(archivo, pos);
    write(archivo, mEspecie);
    writeln('Reemplazado con exito');
  end else writeln('Nombre no encontrado :(');
end;

procedure mostrarOpciones;
begin
  writeln;
  writeln('Seleccione una opcion: ');
  writeln('0 - Terminar el programa');
  writeln('1 - Cargar especies sobreescribiendo archivo');
  writeln('2 - Mostrar total y alturas maxima y minima');
  writeln('3 - Listar las especies por pantalla');
  writeln('4 - Modificar nombre cientifico de una especie');
  writeln('5 - Agregar mas especies al final por teclado');
  writeln('6 - Listar las especies en un archivo de texto');
  writeln;
end;

var
  miArchivo: ArchEspecie;
  nombreArch: string[20];
  opc: char;

begin
  // 1 - Solicitar nombre de archivo
  writeln('Ingrese el nombre del archivo: ');
  readln(nombreArch);
  assign(miArchivo, nombreArch);

  // 2 - Mostrar menu de opciones
  mostrarOpciones;

  read(opc);
  while (opc <> '0') do begin
    case opc of
      '1' : begin
        rewrite(miArchivo);
        cargarEspecies(miArchivo);
        readln;
      end;
      '2' : begin
        reset(miArchivo);
        reportarResumen(miArchivo);
        readln;
      end;
      '3' : begin
        reset(miArchivo);
        listarEspecies(miArchivo);
        readln;
      end;
      '4': begin
        reset(miArchivo);
        buscarReemplazar(miArchivo);
      end;
      '5': begin
        reset(miArchivo);
        seek(miArchivo, fileSize(miArchivo));
        readln;
        cargarEspecies(miArchivo);
      end;
      '6': begin
        reset(miArchivo);
        listarEnArchivo(miArchivo);
        readln;
      end;
    end;

    // accion comun al terminar
    close(miArchivo);

    // volver a pedir una opcion
    mostrarOpciones;
    read(opc);
  end;

  // Terminar
  writeln('Fin del programa');
  readln;
end.

