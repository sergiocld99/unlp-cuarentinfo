program ejer03;

// TODO: CHECK THIS LATER :wink:

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
  codMin: Integer;
begin
  codMin:= arreglo[1].codCalzado;
  resultado:= 1;

  for i:=2 to N do if (arreglo[i].codCalzado < codMin) then begin
    codMin:= arreglo[i].codCalzado;
    resultado:= i;
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
  reg: venta;
  codActual:Integer;
  numActual:Integer;
  cal:calzado;
begin
  // determinar primer minimo
  minimo(ven, reg, det);

  // leo el primer calzado del maestro
  read(mae, cal);

  // mientras hayan ventas
  while (reg.codCalzado <> VALOR_ALTO) do begin
    // nuevo codigo
    codActual:= reg.codCalzado;

    // busco en el maestro
    while (cal.codigo <> codActual) do read(mae, cal);

    // mientras sea mismo codigo
    while (reg.codCalzado = codActual) do begin
      // nuevo talle
      numActual:= reg.numero;

      // busco en el maestro
      while (cal.numero <> numActual) do read(mae, cal);

      // mientras sea mismo talle y codigo
      while (reg.codCalzado = codActual) and (reg.numero = numActual) do begin
        // informo si no tuvo ventas
        if (reg.cantVendida = 0) then with(reg) do writeln(codCalzado, ' ', numero)
        else cal.stock:= cal.stock - reg.cantVendida;

        // avanzo en detalles
        minimo(ven, reg, det);
      end;

      // acciones finales para dicho numero
      if (cal.stock < cal.stockMin) then with(cal) do writeln(reporte, codigo, ' ', numero, ' ', stock, ' ', stockMin);

      // actualizo maestro (RECORDAR RETROCEDER EL CURSOR)
      seek(mae, filePos(mae)-1);
      write(mae, cal);
    end;
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
  assign(mae, 'maestro3.dat');
  for i:=1 to N do assign(det[i], concat('ventas', intToStr(i),  '.dat'));
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
end.

