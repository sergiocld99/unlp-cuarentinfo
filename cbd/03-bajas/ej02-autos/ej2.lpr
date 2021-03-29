program ej2;

type
  str8=string[8];
  str30=string[30];

  tVehiculo=record
    codigoVehiculo:integer;
    patente:str8;
    nroMotor:str30;
    cantidadPuertas:integer;
    descripcion:str8;
  end;

  tArchivo=file of tVehiculo;

procedure leerVehiculo(var v:tVehiculo);
begin
  writeln('Ingrese codigo: ');
  readln(v.codigoVehiculo);
  writeln('Ingrese patente (hasta 7 caracteres): ');
  readln(v.patente);
  writeln('Ingrese numero de motor (hasta 29 caracteres): ');
  readln(v.nroMotor);
  writeln('Ingrese cantidad de puertas: ');
  readln(v.cantidadPuertas);
  v.descripcion:='0';
end;

{Abre el archivo y agrega un vehículo para alquiler, el mismo se recibe como parámetro y debe
utilizar la política descripta anteriormente para recuperación de espacio}
procedure agregar (var arch: tArchivo; vehiculo: tVehiculo);
var
  header,aux:tVehiculo;
  indexLibre:integer;
  cod:integer;
begin
  reset(arch);

  // leer encabezado para encontrar lugar libre (si es que hay)
  read(arch, header);
  val(header.descripcion, indexLibre, cod);

  if (indexLibre = 0) then begin
    // voy hasta el final y escribo
    seek(arch, filesize(arch)-1);
    write(arch, vehiculo);
  end else begin
    // voy hasta la posicion libre
    seek(arch, indexLibre);

    // leo su contenido
    read(arch, aux);

    // el header tendra el indice al proximo libre
    header.descripcion:=aux.descripcion;

    // retrocedo el cursor y escribo
    seek(arch, filepos(arch)-1);
    write(arch, vehiculo);

    // retrocedo al inicio y actualizo header
    seek(arch, 0);
    write(arch, header);
  end;

  writeln('Agregado con exito :)');
  close(arch);
end;

{Abre el archivo y elimina el vehículo que posea el código recibido como parámetro
manteniendo la política descripta anteriormente}
Procedure eliminar (var arch: tArchivo; codigoVehiculo: integer);
var
  encontrado:boolean;
  header,act:tVehiculo;
Begin
  reset(arch);
  encontrado:= false;

  // leer header
  read(arch, header);

  // buscar objetivo
  while (not encontrado) and (not eof(arch)) do begin
    read(arch, act);
    encontrado:= (act.codigoVehiculo = codigoVehiculo);
  end;

  if (encontrado) then begin
    // la pos borrada tendra el indice del primero de la lista invertida
    act.descripcion:=header.descripcion;

    // retrocedo el cursor para marcar
    seek(arch, filepos(arch)-1);

    // el header tendra el indice del elemento borrado
    str(filepos(arch), header.descripcion);

    // lo marco como borrado
    write(arch, act);

    // retrocedo hasta el inicio y actualizo header
    seek(arch, 0);
    write(arch, header);
    writeln('Eliminado con exito :)');
  end else writeln('No encontrado :(');

  close(arch);
End;

procedure mostrarOp;
begin
  writeln('0 - Terminar programa');
  writeln('1 - Agregar un vehiculo');
  writeln('2 - Eliminar un vehiculo');
end;

// programa principal
var
  op:char;
  arch:tArchivo;
  v:tVehiculo;
  cod:integer;

begin
  assign(arch, 'autos.dat');
  writeln('Bienvenido. Por favor ingrese una opcion');
  mostrarOp;
  read(op);

  while (op <> '0') do begin
    case op of
      '1': begin
        leerVehiculo(v);
        agregar(arch, v);
      end;
      '2': begin
        writeln('Ingrese codigo del vehiculo a eliminar: ');
        readln(cod);
        eliminar(arch, cod);
      end;
    end;

    writeln;
    mostrarOp;
    read(op);
  end;

end.

