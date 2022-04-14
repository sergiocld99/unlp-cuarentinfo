program TP2EJ4;
Uses sysutils;
const 

N = 2;

type
	peliculaRegMaestro = record
		codigo:integer;
		nombre:string[20];
		genero:string[15];
		director:string[20];
		duracion:real;
		cantidadDeAsistentes:integer;
	end;

	peliculaRegDetalle = record
		codigo:integer;
		nombre:string[20];
		genero:string[15];
		director:string[20];
		duracion:real;
		fecha:Longint;
		cantidadDeAsistentes:integer;
	end;

	archivoPeliMaestro = file of peliculaRegMaestro;
	archivoPeliDetalle = file of peliculaRegDetalle;
var 
	maestro: archivoPeliMaestro;
	detalles: archivoPeliDetalle;
	maestroReg: peliculaRegMaestro;
	detalleReg: peliculaRegDetalle;
	nombre: string;
	i: integer;
begin
	assign(maestro,'maestro');
	reset(maestro);
	read(maestro,maestroReg);
	for i:= 1 to N do 
	begin
		nombre:= IntToStr(i);
		assign(detalles, nombre);
		reset(detalles);
		read(detalles,detalleReg);
		while(not eof(detalles)) do 
		begin
			while(maestroReg.codigo <> detalleReg.codigo) do 
				begin
					read(maestro,maestroReg);
				end;
			maestroReg.cantidadDeAsistentes:= maestroReg.cantidadDeAsistentes + detalleReg.cantidadDeAsistentes;
			read(detalles,detalleReg);
			seek(maestro,filePos(maestro) - 1);
			write(maestro,maestroReg);
		end;
		close(detalles);
	end;
	close(maestro);
	writeln('hecho!');
	readln;
end.