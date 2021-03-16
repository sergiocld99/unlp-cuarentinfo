program ejer01;
uses sysutils;

// SE TIENE UN ARCHIVO MAESTRO DE EMPLEADOS Y 10 DETALLES DE LICENCIAS
// AMBOS TIPOS DE ARCHIVOS ESTÁN ORDENADOS POR CÓDIGO DE EMPLEADO
// SUPOSICION: TODOS LOS EMPLEADOS EN DETALLES EXISTEN EN EL MAESTRO
// SUPOSICION: TANTO EL MAESTRO COMO CADA DETALLE TIENEN AL MENOS UN REGISTRO

const
  VALOR_ALTO = 9999;
  N = 10;

type
  t_fecha = record
    dia: 1..31;
    mes: 1..12;
    anio: 1900..2200;
  end;

  str30 = string[30];
  t_indice  = 1..N;

  licencia = record
    codEmpleado: Integer;
    fecha: t_fecha;
    cantDias: Integer;
  end;

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

  detalle = file of licencia;
  maestro = file of empleado;

  arrayLic = array[1..N] of licencia;
  arrayDet = array[1..N] of detalle;


// PRECONDICION: ARCHIVO ABIERTO
procedure leerLicencia(var archivo: detalle; var reg: licencia);
begin
  if eof(archivo) then reg.codEmpleado:= VALOR_ALTO
  else read(archivo, reg);
end;


// Devuelve el índice del registro minimo por referencia
procedure determinarMin(var licencias: arrayLic; var indiceMin: t_indice);
var
  i, res: t_indice;
  codMin: Integer;
begin
  codMin:= licencias[1].codEmpleado;
  res:= 1;

  for i:=2 to N do begin
    if (licencias[i].codEmpleado < codMin) then begin
      codMin:= licencias[i].codEmpleado;
      res:= i;
    end;
  end;

  indiceMin:= res;
end;


procedure minimo(var detalles: arrayDet; var licencias: arrayLic; var regMin: licencia);
var
  indiceMin: t_indice;
begin
  // Busco el registro con menor codigo de empleado
  determinarMin(licencias, indiceMin);
  writeln('El minimo se encuentra en el detalle ', indiceMin);

  // Seteo el registro mínimo de retorno
  regMin:= licencias[indiceMin];

  // Avanzo en el archivo donde se encontró el mínimo
  leerLicencia(detalles[indiceMin], licencias[indiceMin]);
end;


// PRECONDICION: ARCHIVOS ASIGNADOS Y EXISTENTES (MAESTRO AL MENOS 1 EMPLEADO)
procedure actualizarMaestro(var mae:maestro; var detalles: arrayDet; var reporte: text);
var
  i: t_indice;
  licenciaMin: licencia;
  licencias: arrayLic;

  codActual: Integer;
  diasAcumulados: Integer;

  regMaestro: empleado;

begin
  // Abrir los archivos
  reset(mae);
  rewrite(reporte);

  for i:=1 to N do begin
    reset(detalles[i]);

    // Inicializar el vector de registros
    read(detalles[i], licencias[i]);
  end;

  // Busco el primer minimo
  minimo(detalles, licencias, licenciaMin);
  read(mae, regMaestro);

  // Corte de control
  while (licenciaMin.codEmpleado <> VALOR_ALTO) do begin
    codActual:= licenciaMin.codEmpleado;
    diasAcumulados:= 0;

    // Mientras no cambie el codigo de empleado
    while (licenciaMin.codEmpleado = codActual) do begin
      diasAcumulados:= diasAcumulados + licenciaMin.cantDias;
      minimo(detalles, licencias, licenciaMin);
    end;

    // Buscar en el maestro
    while (regMaestro.codEmpleado <> codActual) do begin
      read(mae, regMaestro);
    end;

    // Actualizo el empleado
    if (regMaestro.cantDiasVacaciones < diasAcumulados) then begin
       with (regMaestro) do writeln(reporte, codEmpleado, ' ', nombre, ' ', apellido, ' ', cantDiasVacaciones, ' ', diasAcumulados);
    end else begin
       writeln('Restando ', diasAcumulados, ' dias al empleado de codigo ', regMaestro.codEmpleado);
       regMaestro.cantDiasVacaciones:= regMaestro.cantDiasVacaciones - diasAcumulados;
       seek(mae, filePos(mae)-1);
       write(mae, regMaestro);
    end;
  end;

  // Cerrar archivos
  close(mae);
  close(reporte);
  for i:=1 to N do close(detalles[i]);

end;




// --------------------- PROGRAMA PRINCIPAL -------------------------
var
  archivoMaestro: maestro;
  arrayDetalles: arrayDet;
  archivoTexto: text;

  i: t_indice;
  nombreAux: str30;

begin
  // Asignación del maestro
  assign(archivoMaestro, 'maestro1.dat');

  // Asignación de los detalles
  for i:=1 to N do begin
      nombreAux:= concat('detalle', IntToStr(i), '.dat');
      assign(arrayDetalles[i], nombreAux);
  end;

  // Asignación del reporte de texto
  assign(archivoTexto, 'insuficientes.txt');

  // Actualizar
  actualizarMaestro(archivoMaestro, arrayDetalles, archivoTexto);

  // Terminar
  writeln('Fin del programa');
  readln;
  readln;
end.

