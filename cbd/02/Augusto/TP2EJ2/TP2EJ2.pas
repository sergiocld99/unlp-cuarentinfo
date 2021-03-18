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

procedure listar(disco:discoReg; var listado: archivoDestino;total:integer);
begin
	writeln(listado,'Autor: ',disco.nombre);
	writeln(listado,'Genero: ',disco.genero);
	writeln(listado, ' ');
	write(listado,'Nombre Disco: ',disco.disco); writeln(listado,' cantidad vendida: ',total);
	writeln(listado, ' ');
end;
var 
	discos:discoArchivo;
	listado:archivoDestino;
	disco: discoReg;
	total,aux:integer;
begin
	total:=0; aux:= 0;
	assign(discos, 'discos');
	assign(listado, 'listaNuevaDiscos.txt');
	reset(discos);
	rewrite(listado);

	while(not eof(discos)) do 
	begin
		read(discos, disco);
		total:= total + disco.cantidadVendida;
		if(aux = disco.codigoAutor) then
			begin
			total:= total + disco.cantidadVendida;
			listar(disco,listado,total);
			end
		else 
		begin
			aux:= disco.codigoAutor;
			total:= 0
		end;
	end;
	close(discos);
	close(listado);
	readln;
end.