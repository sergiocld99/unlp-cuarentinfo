program TP2EJ6;
const
	valorAlto = 9999;
type 	

	servicioReg = record
		codigoMozo : integer;
		fecha: Longint;
		montoServicio: real;
	end;

	mozoReg = record
		codigoMozo : integer;
		montoServicio: real;
	end;

	archivoServicio = file of servicioReg;
	archivoMozo = file of mozoReg;

procedure leer(var archivo: archivoServicio; var dato: servicioReg);
begin
	if(not eof(archivo)) then 
	begin
		read(archivo,dato);
	end
	else
	begin
		dato.codigoMozo:= valorAlto;
	end;
end;

var 
servicios: archivoServicio;
mozos: archivoMozo;
servicio:servicioReg;
mozo:mozoReg;
aux: integer;
montoTotal: real;
begin
	assign(mozos,'mozos');
	assign(servicios, 'servicios');
	reset(servicios);
	reset(mozos);
	leer(servicios,servicio);
	montoTotal:= 0;
	while(servicio.codigoMozo <> valorAlto) do 
	begin
		aux:= servicio.codigoMozo;
		mozo.codigoMozo:= servicio.codigoMozo;
		while(servicio.codigoMozo = aux) do 
			begin
				montoTotal:= montoTotal + servicio.montoServicio;
				leer(servicios,servicio);
			end;
		mozo.montoServicio:= montoTotal;
		montoTotal:= 0;
		write(mozos,mozo);
	end;
	close(mozos);
	close(servicios);

	reset(mozos);
	while(not eof(mozos)) do 
	begin
		read(mozos,mozo);
		writeln('Codigo de mozo: ',mozo.codigoMozo);
		writeln('Paga total: ', mozo.montoServicio);
		writeln(' ');
	end;
	close(mozos);
	writeln('hecho');
	readln;
end.