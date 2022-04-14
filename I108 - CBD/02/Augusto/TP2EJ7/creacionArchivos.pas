program TP2EJ7;
Uses sysutils;
const 
	N = 2;
type
	productoReg = record
		codigo : integer;
		nombre: string[15];
		descripcion: string[25];
		precio: real;
		stock: integer;
		stockMin: integer;
	end;

	ventaReg = record
		codigo : integer;
		cantidadVendida: integer;
	end;

	archivoProducto = file of productoReg;
	archivoVenta = file of ventaReg;

procedure leerMaster(var master:text;var registro:productoReg);
begin
	read(master,registro.codigo);
	readln(master,registro.nombre);
	readln(master,registro.descripcion);
	readln(master,registro.precio,registro.stock,registro.stockMin);
end;

procedure leerDetalle(var detalle: text;var registro:ventaReg);
begin
	readln(detalle,registro.codigo,registro.cantidadVendida);
end;

var 
	productos: archivoProducto;
	ventas: archivoVenta;
	productosTxt,ventaTxt: text;
	nombre,nombreTxt:string;
	producto:productoReg;
	venta:ventaReg;
	i:integer;
begin

	assign(productos,'productos');
	assign(productosTxt, 'productos.txt');
	reset(productosTxt);
	rewrite(productos);
	while(not eof(productosTxt)) do 
	begin
		leerMaster(productosTxt,producto);
		write(productos,producto);
	end;
	close(productos);
	close(productosTxt);
	for i:= 1 to N do 
	begin
		nombre:= 'ventas'+IntToStr(i);
		nombreTxt:=nombre+'.txt';
		assign(ventas,nombre);
		assign(ventaTxt, nombreTxt);
		reset(ventaTxt);
		rewrite(ventas);
		while(not eof(ventaTxt)) do 
		begin
			leerDetalle(ventaTxt,venta);
			write(ventas,venta);
		end;
		close(ventas);
		close(ventaTxt);
	end;
	writeln('hecho!');
	readln;
end.