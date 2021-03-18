program TP2EJ2;

const
	N = 10;

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

procedure leer(var archivo: text;var registro:empleadoReg);
begin
	read(archivo,registro.codigo);
	readln(archivo,registro.nombre);
	read(archivo,registro.nacimiento);
	readln(archivo,registro.apellido);
	read(archivo,registro.hijos);
	readln(archivo,registro.direccion);
	read(archivo,registro.telefono);
	readln(archivo,registro.diasDeVacaciones);
end;

var
	maestro,detalles: text;
	maestroReg, detallesReg: archivoType;
	registro: empleadoReg;
begin
	assign(maestro, 'maestro.txt');
	assign(detalles, 'detalle.txt');
	assign(maestroReg, 'maestro');
	assign(detallesReg, 'detalle2');
	reset(maestro);
	rewrite(maestroReg);

	while(not eof(maestro)) do 
	begin
		leer(maestro,registro);
		writeln(registro.codigo);
		write(maestroReg,registro);
	end;
	close(maestro);
	close(maestroReg);
	reset(detalles);
	rewrite(detallesReg);
	while(not eof(detalles)) do 
	begin
		leer(detalles,registro);
		write(detallesReg,registro);
	end;
	
	close(detalles);
	close(detallesReg);

	writeln('HECHO!');
	readln;
end.