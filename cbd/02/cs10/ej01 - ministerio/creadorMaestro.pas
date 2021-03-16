program creadorMaestro;
uses sysutils;

// ORDENADO POR CÓDIGO DE EMPLEADO

type
  t_fecha = record
    dia: 1..31;
    mes: 1..12;
    anio: 1900..2200;
  end;

  str30 = string[30];

  empleado = record
    codEmpleado: Integer;
    nombre: str30;
    apellido: str30;
    fechaNac: t_fecha;
    direccion: str30;
    cantHijos: Integer;
    telefono: LongInt;
    cantDiasVacaciones: Integer;
  end;

  maestro = file of empleado;


function apellido() : str30;
var
  index:Integer;
begin
  index:= random(25);
  case index of
    0: apellido:= 'Aguirre';
    1: apellido:= 'Alarcon';
    2: apellido:= 'Alonso';
    3: apellido:= 'Araya';
    4: apellido:= 'Argolo';
    5: apellido:= 'Benitez';
    6: apellido:= 'Bonilla';
    7: apellido:= 'Cabral';
    8: apellido:= 'Calderon';
    9: apellido:= 'Castro';
    10: apellido:= 'Colombo';
    11: apellido:= 'Farias';
    12: apellido:= 'Fernandez';
    13: apellido:= 'Galvan';
    14: apellido:= 'Gomez';
    15: apellido:= 'Leroy';
    16: apellido:= 'Mansilla';
    17: apellido:= 'Martinez';
    18: apellido:= 'Ojeda';
    19: apellido:= 'Perez';
    20: apellido:= 'Riveros';
    21: apellido:= 'Romero';
    22: apellido:= 'Ruiz';
    23: apellido:= 'Sanchez';
    24: apellido:= 'Villegas';
  end;
end;

function nombre() : str30;
var
  index:Integer;
begin
  index:=random(25);
  case index of
    0: nombre:= 'Lucas';
    1: nombre:= 'Alejo';
    2: nombre:= 'Matias';
    3: nombre:= 'Elian';
    4: nombre:= 'Nicolas';
    5: nombre:= 'Agustin';
    6: nombre:= 'Emanuel';
    7: nombre:= 'Juan Pablo';
    8: nombre:= 'Kevin';
    9: nombre:= 'Luis';
    10: nombre:= 'Luciano';
    11: nombre:= 'Damian';
    12: nombre:= 'Federico';
    13: nombre:= 'Lucio';
    14: nombre:= 'Juan Cruz';
    15: nombre:= 'Gustavo';
    16: nombre:= 'Alex';
    17: nombre:= 'Fernando';
    18: nombre:= 'Valentin';
    19: nombre:= 'Ezequiel';
    20: nombre:= 'Camila';
    21: nombre:= 'Ariana';
    22: nombre:= 'Agustina';
    23: nombre:= 'Milagros';
    24: nombre:= 'Malena';
  end;
end;

function generarFecha() : t_fecha;
var
  aux: t_fecha;
begin
  aux.dia:= random(28)+1;
  aux.mes:= random(12)+1;
  aux.anio:= random(50)+1960;
  generarFecha:= aux;
end;

procedure crearEmpleado(var emp:empleado; num:Integer);
begin
  emp.apellido:= apellido();
  emp.cantDiasVacaciones:= random(10)+1;
  emp.cantHijos:= random(3);
  emp.codEmpleado:= num;
  emp.direccion:='Calle Falsa 123';
  emp.fechaNac:= generarFecha();
  emp.nombre:= nombre();
  emp.telefono:= random(99999999);
end;



// --------------------- PROGRAMA PRINCIPAL -------------------------
var
  archivoMaestro: maestro;
  i:Integer;
  regEmp:empleado;

begin
  assign(archivoMaestro, 'maestro1.dat');
  rewrite(archivoMaestro);

  for i:=1 to 1000 do begin
    crearEmpleado(regEmp, i);
    writeln('Empleado creado: ',regEmp.apellido, ' ', regEmp.nombre, ' - codigo ', regEmp.codEmpleado);
    write(archivoMaestro, regEmp);
  end;

  close(archivoMaestro);
  writeln('Fin del programa');
  readln;
  readln;
end.

