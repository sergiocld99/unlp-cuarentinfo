program TP2EJ3;
Uses sysutils;
const 
	N = 2;
	valoralto = 9999;
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

procedure leer(var archivo: archivoDetalle; var calzado: calzadoRegDealle);
begin
	if(not eof(archivo)) then
	begin
		read(archivo,calzado);
	end
	else
	begin
		calzado.codigoCalzado:=valoralto;
	end;	
end;	

procedure informar(calzado: calzadoRegMaster);
begin
	writeln('codigo de calzado: ',calzado.calzado);
	writeln('numero: ',calzado.numero);
	writeln('descripcion: ',calzado.descripcion);
	writeln('precio: ',calzado.precioUnitario);
	writeln('color: ', calzado.color);
	writeln('stock: ', calzado.stock);
	writeln('stock minimo: ',calzado.stockMin);
end;

procedure escribir(calzado: calzadoRegMaster; var sinStock: text);
begin
	writeln(sinStock, 'codigo de calzado: ',calzado.calzado);
	writeln(sinStock,'numero: ',calzado.numero);
	writeln(sinStock,'descripcion: ',calzado.descripcion);
	writeln(sinStock,'precio: ',calzado.precioUnitario);
	writeln(sinStock,'color: ', calzado.color);
	writeln(sinStock,'stock: ', calzado.stock);
	writeln(sinStock,'stock minimo: ',calzado.stockMin);
end;

var 
	maestro: archivoMaestro;
	detalles: archivosDetalles;
	sinStock: text;
	maestroReg: calzadoRegMaster;
	detalleReg: calzadoRegDealle;
	i: integer;
	nombre:string;
begin
	assign(maestro, 'maestro');
	assign(sinStock,'calzadosinstock.txt');
	reset(maestro);
	rewrite(sinStock);
	for i:= 1 to N do 
	begin
		nombre:= IntToStr(i);
		assign(detalles[i],nombre);
		reset(detalles[i]);
		leer(detalles[i],detalleReg);
		while(not eof(detalles[i])) do 
		begin
			read(maestro,maestroReg);
			read(detalles[i],detalleReg);
			while(maestroReg.numero <> detalleReg.numero) do 
			begin
				read(maestro,maestroReg);
			end;
			maestroReg.stock:= maestroReg.stock - detalleReg.cantidadVendida;
			seek(maestro,filepos(maestro) - 1);
			write(maestro,maestroReg);
			if(detalleReg.cantidadVendida = 0) then 
			begin
				informar(maestroReg);
			end;	
			if(maestroReg.stock < maestroReg.stockMin) then
			begin
				escribir(maestroReg,sinStock);
			end;
		end;
		close(detalles[i]);
	end;
	close(maestro);
	close(sinStock);
	writeln('hecho!');
	readln;
end.