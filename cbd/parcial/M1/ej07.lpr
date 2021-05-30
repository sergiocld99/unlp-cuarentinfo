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

// Opcional: para un mejor listado
procedure vaciar(var reg:tVehiculo);
begin
  reg.cantidadPuertas:=-1;
  reg.codigoVehiculo:=-1;
  reg.nroMotor:='####';
  reg.patente:='####';
  reg.precio:=-1;
end;

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
  if (nLibre = 0) then seek(arch,fileSize(arch))
  else begin
    // leer registro a reemplazar
    seek(arch,nLibre);
    read(arch,reg);

    // actualizar encabezado
    seek(arch,0);
    vaciar(reg); //opcional
    write(arch,reg);

    // mover puntero a pos a reemplazar
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
  encontrado:boolean;

  // variables de la lista invertida
  sLibre:string;
  nLibre:integer;

begin
  // abrir archivo
  reset(arch);

  encontrado:=false;

  // leer encabezado
  read(arch,reg);
  sLibre:= reg.descripcion;

  // buscar vehiculo a borrar
  while not (eof(arch) or encontrado) do begin
    read(arch,reg);

    if (reg.codigoVehiculo = codigoVehiculo) then begin
      // guardar posicion a borrar
      nLibre:= filepos(arch)-1;

      // retroceder en archivo y cambiar descripcion
      seek(arch,nLibre);
      vaciar(reg);
      reg.descripcion:= sLibre;
      write(arch,reg);

      // actualizar encabezado
      seek(arch,0);
      str(nLibre,sLibre);
      reg.descripcion:= sLibre;
      write(arch,reg);

      encontrado:= true;
    end;
  end;

  // cerrar archivo
  close(arch);

  if encontrado then writeln(codigoVehiculo,' eliminado con exito')
  else writeln(codigoVehiculo,' no encontrado');
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
        v.descripcion:='0';
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
          with v do writeln(codigoVehiculo,' ',patente,' ',nroMotor,' ',cantidadPuertas,' ',precio:5:2,' ',descripcion);
        end;
        close(arch);
      end;
    end;
  end;

  writeln('Fin del programa');
  readln;

end.

