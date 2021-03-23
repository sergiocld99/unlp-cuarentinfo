program ejer03;

// para concatenar
uses sysutils;

const
  VALOR_ALTO = 9999;
  N = 20;

type
  // common
  str10 = string[10];
  str30 = string[30];

  // detalle
  venta = record
    codCalzado: Integer;
    numero: Integer;
    cantVendida: Integer;
  end;

  detalle = file of venta;

  // maestro
  calzado = record
    codigo: Integer;
    numero: Integer;
    descripcion: str30;
    precioUn: real;
    color: str10;
    stock: Integer;
    stockMin: Integer;
  end;

  maestro = file of calzado;

  // arreglos de detalles
  arrayVen = array[1..N] of venta;
  arrayDet = array[1..N] of detalle;

// MAESTRO Y DETALLES ORDENADOS POR CODIGO CALZADO Y NUMERO (SUPONGO QUE ES TALLE)
procedure leerVenta(var archivo:detalle; var reg:venta);
begin
  if eof(archivo) then reg.codCalzado:= VALOR_ALTO
  else read(archivo, reg);
end;

// determina indice donde esta el codigo minimo
// no puede ser una funcion porque tiene parametros por referencia
procedure determinarMin(var arreglo:arrayVen; var resultado: Integer);
var
  i:Integer;
  min:venta;
begin
  min:=arreglo[1];
  resultado:= 1;

  for i:=2 to N do begin
     if (arreglo[i].codCalzado < min.codCalzado) then begin
        min:=arreglo[i];
        resultado:= i;
     end else if (arreglo[i].codCalzado = min.codCalzado) and (arreglo[i].numero < min.numero) then begin
        min:=arreglo[i];
        resultado:= i;
     end;
  end;
end;

// devuelve la venta con menor codigo de calzado y avanza en dicho archivo
// precondicion: archivos abiertos y arreglo de ventas con algun registro leido en cada posicion
procedure minimo(var ventas:arrayVen; var min:venta; var detalles:arrayDet);
var
  indiceMin: Integer;
begin
  determinarMin(ventas, indiceMin);

  // asigno valor de retorno
  min:= ventas[indiceMin];

  // avanzo en archivo donde estaba minimo
  leerVenta(detalles[indiceMin], ventas[indiceMin]);
end;


procedure actualizar(var mae:maestro; var det:arrayDet; var ven:arrayVen; var reporte:text);
var
  venActual:Integer;
  codActual:Integer;

  // el que se lee del maestro
  cal:calzado;

  // la que se lee del detalle
  reg: venta;

begin
  // determinar primer minimo
  minimo(ven, reg, det);

  // leo el primer calzado del maestro
  read(mae, cal);

  // mientras hayan ventas por procesar
  while (reg.codCalzado <> VALOR_ALTO) do begin
     codActual:= reg.codCalzado;

    // mientras sea mismo calzado
    while (reg.codCalzado = codActual) do begin

      // nuevo talle
      venActual:= 0;

      // busco calzado y talle en el maestro
      while (cal.codigo <> reg.codCalzado) or (cal.numero <> reg.numero) do begin
        // imprimir por pantalla que no tuvo ventas
        writeln(cal.codigo, ' ', cal.numero);
        read(mae, cal);
      end;

      // mientras sea mismo talle y calzado
      while (reg.codCalzado = cal.codigo) and (reg.numero = cal.numero) do begin

        // llevo la cuenta de la cantidad de ventas
        venActual:= venActual + reg.cantVendida;

        // avanzo en detalles
        minimo(ven, reg, det);
      end;

      // acciones finales para dicho calzado
      cal.stock:= cal.stock - venActual;
      if (cal.stock < cal.stockMin) then with(cal) do writeln(reporte, codigo, ' ', numero, ' ', stock, ' ', stockMin);

      // actualizo maestro (RECORDAR RETROCEDER EL CURSOR)
      seek(mae, filePos(mae)-1);
      write(mae, cal);

      // avanzo en maestro (para evitar imprimirlo como si no tuviera ventas)
      if not eof(mae) then read(mae, cal);

    end;
  end;

  // imprimir por pantalla que no tuvo ventas
  writeln(cal.codigo, ' ', cal.numero);

  // informar resto de calzados sin ventas
  while not eof(mae) do begin
    read(mae, cal);
    // imprimir por pantalla que no tuvo ventas
    writeln(cal.codigo, ' ', cal.numero);
  end;

end;


// PROGRAMA PRINCIPAL
var
  i:Integer;
  mae:maestro;
  det:arrayDet;
  ven:arrayVen;
  reporte:text;

begin
  // Asignar archivos
  assign(mae, 'maestro.dat');
  for i:=1 to N do assign(det[i], concat('detalle', intToStr(i),  '.dat'));
  assign(reporte, 'calzadosintock.txt');

  // abrir archivos
  reset(mae);
  for i:=1 to N do reset(det[i]);
  rewrite(reporte);

  // leer primeros
  for i:=1 to N do read(det[i], ven[i]);

  // operacion principal
  actualizar(mae, det, ven, reporte);

  // cerrar archivos
  close(mae);
  for i:=1 to N do close(det[i]);
  close(reporte);

  // terminar
  writeln('fin del programa');
  readln;
  readln;
end.

