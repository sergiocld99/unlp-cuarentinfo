program ej02;

// Constantes
const
  valor_fin = 'zzz';
  valor_alto = 1000000;
  nombre_txt = 'flores.txt';

// Declaración de tipos de variables
type
  tString = string[30];
  tEspecie = record
    num:integer;
    alturaMax:real;
    nomCientifico:tString;
    nomVulgar:tString;
    color:tString;
    alturaMaxAlcanzar:real;
  end;

  tArchivo = file of tEspecie;

// Procedimientos
procedure imprimirEspecie(var esp:tEspecie);
begin
  with esp do writeln(num,' ',alturaMax:5:2,' ',nomCientifico,' ',nomVulgar,' ',color,' ',alturaMaxAlcanzar:5:2);
end;

procedure leerEspecie(var esp:tEspecie);
begin
  writeln('Ingrese nombre cientifico - ',valor_fin,' para terminar : ');
  readln(esp.nomCientifico);

  if (esp.nomCientifico <> valor_fin) then begin
    writeln('Ingrese numero de especie: ');
    readln(esp.num);
    writeln('Ingrese altura maxima: ');
    readln(esp.alturaMax);
    writeln('Ingrese nombre vulgar: ');
    readln(esp.nomVulgar);
    writeln('Ingrese color: ');
    readln(esp.color);
    writeln('Ingrese altura maxima a alcanzar: ');
    readln(esp.alturaMaxAlcanzar);
  end;
end;

// Precondición: se recibe un archivo ya asignado
procedure resumen(var arch:tArchivo);
var
  cant:integer;
  valorMin,valorMax:real;
  min,max,esp:tEspecie;
begin
  // abrir el archivo
  reset(arch);

  // inicializar variables locales
  cant:= 0;
  valorMin:=valor_alto;
  valorMax:=-1;

  // procesar todas las especies
  while not eof(arch) do begin
    read(arch, esp);
    cant:= cant + 1;

    if (esp.alturaMaxAlcanzar > valorMax) then begin
      valorMax:= esp.alturaMaxAlcanzar;
      max:=esp;
    end;

    if (esp.alturaMaxAlcanzar < valorMin) then begin
      valorMin:= esp.alturaMaxAlcanzar;
      min:=esp;
    end;
  end;

  // cerrar el archivo
  close(arch);

  // mostrar resumen
  writeln('Se tiene un total de ',cant,' especies');

  if (cant > 0) then begin
    write('La especie de mayor altura a alcanzar es: ');
    imprimirEspecie(max);
    write('La especie de menor altura a alcanzar es: ');
    imprimirEspecie(min);
  end;
end;

// Precondición: se recibe un archivo ya asignado
procedure listar(var arch:tArchivo);
var
  esp:tEspecie;
begin
  // abrir archivo
  reset(arch);

  // recorrer todas las especies
  while not eof(arch) do begin
    read(arch,esp);
    imprimirEspecie(esp);
  end;

  // cerrar archivo
  close(arch);
end;

// Precondición: se recibe un archivo ya asignado
procedure modificar(var arch:tArchivo; var nombreViejo:tString; var nombreNuevo:tString);
var
  esp:tEspecie;
  encontrado:boolean;
begin
  // abrir archivo
  reset(arch);

  // inicializar variables locales
  encontrado:=false;

  // recorrer hasta encontrar nombre a cambiar
  while not (eof(arch) or encontrado) do begin
    read(arch,esp);
    if (esp.nomCientifico = nombreViejo) then begin
      // cambiar nombre
      esp.nomCientifico:= nombreNuevo;
      // retroceder una posición y reemplazar
      seek(arch, filepos(arch)-1);
      write(arch,esp);
      // detener la búsqueda
      encontrado:= true;
    end;
  end;

  // cerrar archivo
  close(arch);

  // informar resultado
  if encontrado then writeln(nombreViejo,' actualizado con exito a ',nombreNuevo)
  else writeln(nombreViejo,' no encontrado');
end;

// Precondición: se recibe un archivo ya asignado
procedure agregarEspecies(var arch:tArchivo);
var
  esp:tEspecie;
begin
  // abrir archivo
  reset(arch);

  // mover al final
  seek(arch, filesize(arch));

  // leer una especie
  leerEspecie(esp);

  // cargar especies hasta valor_fin
  while (esp.nomCientifico <> valor_fin) do begin
    write(arch,esp);
    leerEspecie(esp);
  end;

  // cerrar archivo
  close(arch);
end;

// Precondición: se reciben archivos ya asignados
procedure listarEnTxt(var arch:tArchivo; var txt:text);
var
  esp:tEspecie;
begin
  // abrir archivos
  reset(arch);
  rewrite(txt);

  // listar todas las especies
  while not eof(arch) do begin
    read(arch,esp);
    with(esp) do writeln(txt, num,' ',alturaMax:5:2,' ',nomCientifico,' ',nomVulgar,' ',color,' ',alturaMaxAlcanzar:5:2);
  end;

  // cerrar archivos
  close(arch);
  close(txt);
end;

// Variables del programa principal
var
  opc:byte;
  terminar:boolean;
  arch:tArchivo;
  txt:text;
  nombre,nombre2:tString;

// Programa principal
begin
  writeln('Ingrese nombre del archivo con especies cargadas: ');
  readln(nombre);

  assign(arch,nombre);
  assign(txt,nombre_txt);
  terminar:= false;

  while not terminar do begin
    writeln;
    writeln('Seleccione una opcion: ');
    writeln('1. Reportar en pantalla la cantidad total de especies y la especie de menor y de mayor altura a alcanzar');
    writeln('2. Listar todo el contenido del archivo de a una especie por linea');
    writeln('3. Modificar el nombre cientifico de una especie');
    writeln('4. Agregar especies al final del archivo con datos obtenidos por teclado');
    writeln('5. Listar todo el contenido del archivo, en un archivo de texto llamado ',nombre_txt);
    writeln('6. Terminar el programa');
    read(opc);
    readln;

    case opc of
      1 : resumen(arch);
      2 : listar(arch);
      3 : begin
        writeln('Inserte nombre cientifico de la especie a modificar: ');
        readln(nombre);
        writeln('Inserte nuevo nombre: ');
        readln(nombre2);
        modificar(arch,nombre,nombre2);
      end;
      4 : agregarEspecies(arch);
      5 : listarEnTxt(arch,txt);
      6 : terminar:= true;
    end;
  end;

  writeln('Fin del programa');
  readln;
  readln;

end.

