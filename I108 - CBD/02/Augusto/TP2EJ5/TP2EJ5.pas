program TP2EJ5;
const
	valorAlto = '9999';
type
	municipioReg = record
		partido:string[15];
		localidad:string[15];
		barrio:string[15];
		cantidadNenes:integer;
		cantidadAdultos:integer;
	end;

	archivoMunicipio = file of municipioReg;

procedure leer(var archivo: archivoMunicipio; var dato: municipioReg);
begin
	if(not eof(archivo)) then 
	begin
		read(archivo,dato);
	end
	else
	begin
		dato.partido:= valorAlto;
	end;
end;

var
	archivo: archivoMunicipio;
	municipio: municipioReg;
	nenes,adultos:integer;
	ant_partido,ant_localidad:string;
begin
	nenes:= 0; adultos:= 0;
	assign(archivo,'municipios');
	reset(archivo);
	leer(archivo,municipio);
	writeln('Partido: ',municipio.partido);
	writeln('Localidad: ',municipio.localidad);
	while(municipio.partido <> valorAlto) do 
	begin
		ant_partido:= municipio.partido;
		ant_localidad:= municipio.localidad;
		while((ant_partido = municipio.partido) and (ant_localidad = municipio.localidad)) do 
		begin
			writeln('barrio: ',municipio.barrio);
			writeln('Cantidad de nenes: ',municipio.cantidadNenes,'  Cantidad de adultos: ',municipio.cantidadAdultos);
			nenes:= nenes + municipio.cantidadNenes;
			adultos:= adultos + municipio.cantidadAdultos;
			leer(archivo,municipio);
		end;
		ant_localidad:= municipio.localidad;
		if(ant_partido <> municipio.partido) then 
			begin
				writeln('Total de nenes: ',nenes,' Total de adultos: ',adultos);
				nenes:= 0;
				adultos:= 0;
			end;
		writeln(' ');
		writeln('Partido: ',municipio.partido);
		writeln('Localidad: ',municipio.localidad);
	end;
	close(archivo);
	writeln('hecho!');
	readln;
end.