program creadorDetalle;
uses sysutils;

// ORDENADO POR CÓDIGO DE EMPLEADO

type
  t_fecha = record
    dia: 1..31;
    mes: 1..12;
    anio: 1900..2200;
  end;

  licencia = record
    codEmpleado: Integer;
    fecha: t_fecha;
    cantDias: Integer;
  end;

  detalle = file of licencia;


function generarFecha() : t_fecha;
var
  aux: t_fecha;
begin
  aux.dia:= random(28)+1;
  aux.mes:= random(12)+1;
  aux.anio:= random(50)+1960;
  generarFecha:= aux;
end;

procedure crearLicencia(var lic:licencia; num:Integer);
begin
  lic.cantDias:= random(3)+1;
  lic.codEmpleado:= num;
  lic.fecha:= generarFecha();
end;



// --------------------- PROGRAMA PRINCIPAL -------------------------
var
  det:detalle;
  i,j:Integer;
  regLic:licencia;

begin
  writeln('Ingrese numero de detalle (1 al 10):');
  read(i);
  j:= 1;

  assign(det, concat('detalle', intToStr(i), '.dat'));
  rewrite(det);
  randomize;

  for i:=1 to 10 do begin
    j:= j + random(100);
    crearLicencia(regLic, j);
    writeln('Licencia creada por ',regLic.cantDias, ' dias para el empledo con codigo ', regLic.codEmpleado);
    write(det, regLic);
  end;

  close(det);
  writeln('Fin del programa');
  readln;
  readln;
end.

