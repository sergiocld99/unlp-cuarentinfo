program TP2EJ1;

const
	N = 1;
	valoralto=9999;
type 
	empleadoReg = record
		codigo : integer;
		nombre: string[10];
		apellido: string[15];
		nacimiento: Longint;
		direccion: string[8];
		hijos: integer;
		telefono: string[10];
		diasDeVacaciones: integer;
	end;

	archivoType = file of empleadoReg;
	archDetalles = array[0..N] of archivoType;
	detallesRegister = array[0..N] of empleadoReg;  

procedure leer (var archivo:archivoType; var registro:empleadoReg);
begin
	if (not eof(archivo))then
	begin
		read(archivo, registro);
	end
	else
		registro.codigo := valoralto;
end;

procedure minimo (var detalles1,detalles2,min: empleadoReg;  var archivosDetalles1,archivosDetalles2:archivoType);
begin

	if (detalles1.codigo <= detalles2.codigo) then 
	begin
		min := detalles2;
		leer(archivosDetalles1,detalles1);
	end
	else 
		min := detalles2;
		writeln(min.codigo);
		leer(archivosDetalles2,detalles2);

end;


var
	maestro,nuevoArchivo,detalles1,detalles2: archivoType;
	//detalles: archDetalles;
	maestroReg,min,detallesReg1,detallesReg2: empleadoReg;
	aux: integer;
	//detallesReg: detallesRegister;
begin
	assign(maestro, 'maestro');
	assign(nuevoArchivo, 'empleadosConMasDiasDeVacaciones');
	assign(detalles1,'detalle1');
	assign(detalles2,'detalle2');
	rewrite(nuevoArchivo);
	reset(maestro);
	reset(detalles1);
	reset(detalles2);
	leer(maestro,maestroReg);
	leer(detalles1,detallesReg1);
	leer(detalles2,detallesReg2);
	minimo(detallesReg1,detallesReg2,min,detalles1,detalles2);
	while (min.codigo <> valoralto) do
		begin
		while (maestroReg.codigo <> min.codigo) do
		read(maestro, maestroReg);
		aux := min.codigo;
			while (aux = min.codigo) do {procesa el mínimo}
			begin
				if(maestroReg.diasDeVacaciones < min.diasDeVacaciones)then 
				begin
					write(nuevoArchivo,min);
					writeln(min.nombre);
					minimo(detallesReg1,detallesReg2,min,detalles1,detalles2);
				end;
			end;
		seek (maestro, filepos(maestro)-1); {se reubica el puntero y actualiza}
		write(maestro, maestroReg);
		end;
		close(maestro); {se cierran los archivos}
		close(detalles1); close(detalles2); 
		close(nuevoArchivo);
	writeln('hecho!!');
	readln;
end.