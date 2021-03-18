program crearArchivos;
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

procedure leer(var registro: peliculaRegMaestro; var texto: text);
begin
	read(texto, registro.codigo);
	readln(texto, registro.nombre);
	readln(texto, registro.genero);
	readln(texto,registro.director);
	readln(texto,registro.duracion);
	readln(texto,registro.cantidadDeAsistentes);
end;

procedure leerDetalle(var registro:peliculaRegDetalle;var texto:text);
begin
	read(texto, registro.codigo);
	readln(texto, registro.nombre);
	readln(texto, registro.genero);
	readln(texto,registro.director);
	readln(texto,registro.duracion);
	readln(texto,registro.fecha);
	readln(texto,registro.cantidadDeAsistentes);
end;

var 
	maestro: archivoPeliMaestro;
	detalles: archivoPeliDetalle;
	maestroReg: peliculaRegMaestro;
	detalleReg: peliculaRegDetalle;
	maestroText,detallesText:text;
	nombre,nombreTxt: string;
	i: integer;
begin
	assign(maestroText, 'maestro.txt');
	assign(maestro, 'maestro');
	reset(maestroText);
	rewrite(maestro);
	while (not(eof(maestroText))) do 
	begin
		leer(maestroReg,maestroText);
		write(maestro,maestroReg);
	end;
	for i:= 1 to N do 
	begin
		nombre:= IntToStr(i);
		nombreTxt:='detalle'+ nombre + '.txt';
		assign(detalles,nombre);
		assign(detallesText, nombreTxt);
		rewrite(detalles);
		reset(detallesText);
		while(not eof(detallesText)) do 
		begin
			leerDetalle(detalleReg,detallesText);
			write(detalles,detalleReg);
		end;
		close(detalles);
		close(detallesText);
	end;
	close(maestro);
	writeln('hecho');
	readln;
end.