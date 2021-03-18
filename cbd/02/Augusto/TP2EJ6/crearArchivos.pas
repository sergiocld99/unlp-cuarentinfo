program creaArchivos;
type 	

	servicioReg = record
		codigoMozo : integer;
		fecha: Longint;
		montoServicio: real;
	end;

	archivoServicios = file of servicioReg;

procedure leer(var texto:text; var servicio:servicioReg);
begin
	readln(texto, servicio.codigoMozo, servicio.fecha, servicio.montoServicio);
end;

var 
	archivoTexto: text;
	servicios: archivoServicios;
	servicio: servicioReg;
begin
	assign(archivoTexto, 'servicios.txt');
	assign(servicios,'servicios');
	rewrite(servicios);
	reset(archivoTexto);
	while(not(eof(archivoTexto))) do 
	begin
		leer(archivoTexto,servicio);
		write(servicios,servicio);
	end;

	close(servicios);
	close(archivoTexto);
	writeln('hecho');
	readln;
end.