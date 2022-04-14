program crearArchivos;
Uses sysutils;
const 
	N = 2;
type
	calzadoRegMaster = record
		calzado : integer;
		numero: integer;
		descripcion: string[30];
		precioUnitario: real;
		color: string[10];
		stock: integer;
		stockMin: integer;
		end;

	calzadoRegDealle = record
		codigoCalzado : integer;
		numero: integer;
		cantidadVendida: integer;
	end;
	archivoMaestro = file of calzadoRegMaster;
	archivoDetalle = file of calzadoRegDealle;
	archivosDetalles = array[1..N] of archivoDetalle;

procedure leer(var registro: calzadoRegMaster; var texto: text);
begin
	read(texto, registro.calzado);
	readln(texto, registro.numero);
	readln(texto, registro.descripcion);
	readln(texto,registro.precioUnitario);
	readln(texto,registro.color);
	read(texto,registro.stock);
	readln(texto,registro.stockMin);
end;

procedure leerDetalle(var registro:calzadoRegDealle;var texto:text);
begin
	read(texto, registro.codigoCalzado);
	readln(texto,registro.numero);
	readln(texto,registro.cantidadVendida);
end;

var 
	maestro: archivoMaestro;
	maestroText, detallesText: text;
	detalles: archivosDetalles;
	nombre,nombreTxt:string;
	maestroReg:calzadoRegMaster;
	detalleReg:calzadoRegDealle;
	i:integer;
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
		nombreTxt:=nombre+'.txt';
		assign(detalles[i],nombre);
		assign(detallesText, nombreTxt);
		rewrite(detalles[i]);
		reset(detallesText);
		while(not eof(detallesText)) do 
		begin
			leerDetalle(detalleReg,detallesText);
			write(detalles[i],detalleReg);
		end;
		close(detalles[i]);
		close(detallesText);
	end;
	close(maestro);
	writeln('hecho');
	readln;
end.