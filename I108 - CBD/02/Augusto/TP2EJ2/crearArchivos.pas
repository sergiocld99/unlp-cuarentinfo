program TP2EJ2;

type
	discoReg= record
		codigoAutor : integer;
		nombre: string[10];
		disco: string[15];
		genero: string[10];
		cantidadVendida: integer; 
	end;

	discoArchivo = file of discoReg;
	archivoDestino = text;

procedure leer(var archivo: text;var registro:discoReg);
begin
	read(archivo,registro.codigoAutor);
	readln(archivo,registro.nombre);
	readln(archivo,registro.disco);
	readln(archivo,registro.genero);
	readln(archivo,registro.cantidadVendida);
end;

var
	discos:discoArchivo;
	listado:archivoDestino;
	disco: discoReg;
begin
	assign(discos, 'discos');
	assign(listado, 'discos.txt');
	reset(listado);
	rewrite(discos);

	while(not eof(listado)) do 
	begin
		leer(listado,disco);
		writeln(disco.codigoAutor);
		write(discos,disco);
	end;
	close(discos);
	close(listado);
	writeln('HECHO!');
	readln;
end.