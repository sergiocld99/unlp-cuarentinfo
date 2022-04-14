program crearArchivo;

type
	municipioReg = record
		partido:string[15];
		localidad:string[15];
		barrio:string[15];
		cantidadNenes:integer;
		cantidadAdultos:integer;
	end;

	archivoMunicipio = file of municipioReg;

procedure leer(var texto:text; var municipio:municipioReg);
begin
	readln(texto, municipio.partido);
	readln(texto, municipio.localidad);
	readln(texto, municipio.barrio);
	readln(texto, municipio.cantidadNenes,municipio.cantidadAdultos);
end;
var 
	archivoText:text;
	archivo: archivoMunicipio;
	municipio:municipioReg;
begin
	assign(archivo, 'municipios');
	assign(archivoText, 'municipios.txt');
	reset(archivoText); rewrite(archivo);
	while(not eof(archivoText)) do 
	begin
		leer(archivoText,municipio);
		write(archivo,municipio);
	end;
	close(archivoText);
	close(archivo);
	writeln('hecho');
	readln;
end.
