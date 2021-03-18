program TP2EJ7;
Uses sysutils;
const 
	N = 2;
	valor = 9999;
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
	archivosVenta = array[1..N] of archivoVenta; 

procedure leer(var detalle:archivoVenta; registro:ventaReg);
begin
	if(not eof(detalle)) then 
	begin
		read(detalle,registro);
	end
	else
	begin
		registro.codigo:=valor;
	end;
end;

var 
	productos: archivoProducto;
	ventas: archivosVenta;
	producto:productoReg;
	venta:ventaReg;
	nombre:string;
	i:integer;
begin
	for i:= 1 to N do 
	begin
		assign(productos, 'productos');
		reset(productos);
		read(productos,producto);
		nombre:= 'ventas'+IntToStr(i);
		assign(ventas[i], nombre);
		reset(ventas[i]);
		leer(ventas[i],venta);
		while(venta.codigo <> valor) do 
		begin
			while(venta.codigo <> producto.codigo) do 
			begin
				read(productos,producto);
			end;
			producto.stock:=producto.stock - venta.cantidadVendida;
			leer(ventas[i],venta);
			seek(productos,filePos(productos) - 1);
			write(productos,producto);
		end;		
		close(ventas[i]);
		close(productos);
	end;
	writeln('hecho!');
	readln;
end.