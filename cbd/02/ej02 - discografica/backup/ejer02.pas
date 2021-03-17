program ejer02;
uses sysutils;

const
  VALOR_ALTO = 9999;

type
  str30 = string[30];

  cd = record
    codAutor: Integer;
    nombreAutor: str30;
    nombreDisco: str30;
    genero: str30;
    cantVendida: Integer;
  end;

  // Archivo origen ordenado por codigo autor, genero y nombre disco.
  origen = file of cd;

procedure leerCd(var archivo:origen; var reg:cd);
begin
  if eof(archivo) then reg.codAutor:=VALOR_ALTO
  else read(archivo, reg);
end;

// algoritmica de corte de control
// precondicion: archivos abiertos
procedure listar(var miArchivo:origen; var miListado:text);
var
  miCd: cd;

  // para corte de control
  autorActual: Integer;
  generoActual: str30;

  // para totalizar
  sumaGenero: Integer;
  sumaAutor: Integer;
  sumaTotal: Integer;

begin
  // contador total
  sumaTotal:= 0;

  // leer primer cd
  leerCd(miArchivo, miCd);

  // mientras queden cds
  while (miCd.codAutor <> VALOR_ALTO) do begin
    // guardo autor actual
    autorActual:= miCd.codAutor;
    writeln('Autor: ', autorActual);
    write(miListado, 'Autor: ', autorActual);
    sumaAutor:= 0;

    // mientras sea el mismo autor
    while (miCd.codAutor = autorActual) do begin
      // guardo genero actual
      generoActual:= miCd.genero;
      writeln('Genero: ', generoActual);
      writeln(miListado, 'Genero: ', generoActual);
      sumaGenero:= 0;

      // mientras sea mismo genero
      while (miCd.codAutor <> VALOR_ALTO) and (miCd.genero = generoActual) do begin
        // suposicion: no hay discos repetidos
        with (miCd) do begin
             writeln('Nombre Disco: ', nombreDisco, ' cantidad vendida: ', cantVendida);
             writeln(miListado, 'Nombre Disco: ', nombreDisco, ' cantidad vendida: ', cantVendida);
        end;
        sumaGenero:= sumaGenero + miCd.cantVendida;
        sumaAutor:= sumaAutor + miCd.cantVendida;
        sumaTotal:= sumaTotal + miCd.cantVendida;
        // avanzo al siguiente cd
        leerCd(miArchivo, miCd);
      end;

      // totalizar genero
      writeln('Total Genero: ', sumaGenero);
      writeln(miListado, 'Total Genero: ', sumaGenero);
    end;

    // totalizar autor
    writeln('Total Autor: ', sumaAutor);
    writeln(miListado, 'Total Autor: ', sumaAutor);
  end;

  // totalizar discografica
  writeln('Total Discografica: ', sumaTotal);

end;

// -------------------- GENERADOR DE CDS -------------------------

procedure generarCds(var miArchivo: origen);
var
  miCd: cd;
begin
  randomize;

  // generar ordenado por codigo autor, genero y nombre disco

  miCd.codAutor:= 1;
  miCd.nombreAutor:= 'ZAYN';
  miCd.genero:= 'dance pop';
  miCd.nombreDisco:= 'Icarus Falls';
  miCd.cantVendida:= random(2000);
  write(miArchivo, miCd);

  miCd.nombreDisco:= 'Mind of Mine';
  miCd.cantVendida:= random(3000);
  write(miArchivo, miCd);

  miCd.genero:= 'pop';
  miCd.nombreDisco:='Nobody is Listening';
  miCd.cantVendida:= random(1000);
  write(miArchivo, miCd);

  miCd.codAutor:= 2;
  miCd.nombreAutor:= 'Dua Lipa';
  miCd.genero:= 'dance pop';

  miCd.nombreDisco:= 'Dua Lipa (Complete Edition)';
  miCd.cantVendida:= random(1000);
  write(miArchivo, miCd);

  miCd.nombreDisco:= 'Future Nostalgia';
  miCd.cantVendida:= random(2000);
  write(miArchivo, miCd);

  miCd.codAutor:= 3;
  miCd.nombreAutor:= 'The Weeknd';
  miCd.genero:= 'rap';
  miCd.nombreDisco:= 'After Hours';
  miCd.cantVendida:= random(1500);
  write(miArchivo, miCd);

end;


// --------------------- PROGRAMA PRINCIPAL -------------------------
var
  miArchivo: origen;
  miListado: text;

begin
  assign(miListado, 'cds.txt');
  assign(miArchivo, 'cds.dat');

  // crear datos (no pedido)
  rewrite(miArchivo);
  generarCds(miArchivo);
  close(miArchivo);

  // abrir archivo origen
  reset(miArchivo);

  // crear archivo de listado
  rewrite(miListado);

  // listar
  listar(miArchivo, miListado);

  // cerrar archivos
  close(miArchivo);
  close(miListado);

  // terminar
  writeln('Fin del programa');
  readln;
  readln;
end.

