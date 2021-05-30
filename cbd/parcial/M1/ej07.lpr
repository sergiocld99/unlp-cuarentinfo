program ej07;

type
  str30 = string[30];

  tVehiculo = record
    codigoVehiculo:integer;
    patente:str30;
    nroMotor:str30;
    cantidadPuertas:integer;
    precio:real;
    descripcion:str30;
   end;

  tArchivo = file of tVehiculo;

// Procedimientos
// Precondición: se recibe un archivo ya asignado
procedure agregar(var arch:tArchivo; vehiculo:tVehiculo);
var
  reg:tVehiculo;

  // variables de la lista invertida
  sLibre:string;
  nLibre:integer;
  codError:integer;

begin
  // abrir archivo
  reset(arch);

  // leer encabezado (primera posicion)
  read(arch,reg);
  sLibre:= reg.descripcion;
  val(sLibre,nLibre,codError);

  // desplazar puntero
  if (nLibre = 0) then seek(arch,filePos(arch))
  else begin
    // actualizar encabezado
    seek(arch,nLibre);
    read(arch,reg);
    sLibre:= reg.descripcion;
    seek(arch,0);
    write(arch,reg);
    seek(arch,nLibre);
  end;

  // agregar vehiculo
  write(arch,vehiculo);

  // cerrar archivo
  close(arch);
end;

// Precondición: se recibe un archivo ya asignado
procedure eliminar(var arch:tArchivo; codigoVehiculo:integer);
var
  reg:tVehiculo;

  // variables de la lista invertida
  sLibre:string;
  nLibre:integer;

begin
  // abrir archivo
  reset(arch);

  // leer encabezado
  read(arch,reg);
  sLibre:= reg.descripcion;

  // buscar vehiculo a borrar
  while not (eof(arch) or (reg.codigoVehiculo = codigoVehiculo)) do begin
    read(arch,reg);

    if (reg.codigoVehiculo = codigoVehiculo) then begin
      // guardar posicion a borrar
      nLibre:= filepos(arch)-1;

      // retroceder en archivo y cambiar descripcion
      seek(arch,nLibre);
      reg.descripcion:= sLibre;
      write(arch,reg);

      // actualizar encabezado
      seek(arch,0);
      str(nLibre,sLibre);
      reg.descripcion:= sLibre;
      write(arch,reg);
    end else begin
      // codigo no encontrado
      writeln(codigoVehiculo,' no encontrado');
    end;
  end;

  // cerrar archivo
  close(arch);
end;

// Variables del programa principal
var
  opc:byte;
  terminar:boolean;
  v:tVehiculo;
  cod:integer;
  arch:tArchivo;

// Programa principal
begin
  assign(arch,'ej07.dat');

  terminar:= false;

  while not terminar do begin
    writeln;
    writeln('Seleccione una opcion: ');
    writeln('0. Terminar el programa');
    writeln('1. Agregar un vehiculo');
    writeln('2. Eliminar un vehiculo');
    writeln('3. Listar contenido (TEST)');

    read(opc);
    readln;

    case opc of
      0 : terminar:= true;
      1 : begin
        writeln('Ingrese codigo:'); readln(v.codigoVehiculo);
        writeln('Ingrese patente:'); readln(v.patente);
        writeln('Ingrese nro de motor:'); readln(v.nroMotor);
        writeln('Ingrese cant de puertas:'); readln(v.cantidadPuertas);
        writeln('Ingrese precio:'); readln(v.precio);
        agregar(arch,v);
      end;
      2 : begin
        writeln('Ingrese codigo:');
        readln(cod);
        eliminar(arch,cod);
      end;
      3 : begin
        reset(arch);
        while not eof(arch) do begin
          read(arch,v);
          with v do writeln(codigoVehiculo,' ',patente,' ',nroMotor,' ',cantidadPuertas,' ',precio:5:2);
        end;
        close(arch);
      end;
    end;
  end;

  writeln('Fin del programa');
  readln;

end.

