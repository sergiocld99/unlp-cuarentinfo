program TP2EJ8;
Uses sysutils;
const
	N = 2;
type
	arquitectoReg = record
		cod_zona : integer;
		nombreDeZona: string[20];
		descripcionUbicacion: string[25];
		fecha:Longint;
		cantidadMetrosDia: integer;
	end;

	zonaReg = record
		cod_zona : integer;
		nombreDeZona: string[20];
		cantidadMetros: integer;
	end;

	arquitectoArchivo = file of arquitectoReg;
	zonaArchivo = file of zonaReg;
var 
	arquitectos:arquitectoArchivo;
	zonas:zonaArchivo;
	arquitecto:arquitectoReg;
	zona:zonaReg;
	nombre:string;
	aux,i: integer;
begin
	assign(zonas,'zonas');
	rewrite(zonas);
	for i:= 1 to N do 
	begin
		nombre:='arquitecto'+IntToStr(i);
		assign(arquitectos,nombre);
		reset(arquitectos);
		read(arquitectos,arquitecto);
		aux:= arquitecto.cod_zona;
		while(not eof(arquitectos)) do 
		begin
			zona.cod_zona:=arquitecto.cod_zona;
			while(arquitecto.cod_zona = aux) do 
			begin
				zona.cantidadMetros:=zona.cantidadMetros + arquitecto.cantidadMetrosDia;
				read(arquitectos,arquitecto);
			end;
			aux:= arquitecto.cod_zona;
			write(zonas,zona);
		end;
		close(arquitectos);
	end;
	close(zonas);
	writeln('hecho');
	readln;
end.