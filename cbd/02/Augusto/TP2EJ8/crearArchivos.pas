program crearArchivos;
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


	arquitectoArchivo = file of arquitectoReg;

procedure leer(var archivo:text;var arquitecto:arquitectoReg);
begin
	read(archivo,arquitecto.cod_zona, arquitecto.nombreDeZona);
	readln(archivo, arquitecto.nombreDeZona);
	readln(archivo,arquitecto.descripcionUbicacion);
	read(archivo,arquitecto.fecha);
	readln(archivo, arquitecto.cantidadMetrosDia);
end;
var
	arquitecto: arquitectoArchivo;
	i:integer;
	arquitectoTxt:text;
	arqui:arquitectoReg;
	nombre,nombreTxt:string;
begin
	for i:= 1 to N do 
	begin 
	nombre:='arquitecto'+IntToStr(i);
	nombreTxt:=nombre+'.txt';
	assign(arquitectoTxt,nombreTxt);
	assign(arquitecto, nombre);
	rewrite(arquitecto);
	reset(arquitectoTxt);
	while(not eof(arquitectoTxt)) do 
	begin
		leer(arquitectoTxt,arqui);
		write(arquitecto,arqui);
	end;

	close(arquitecto);
	close(arquitectoTxt);	
	end;
	writeln('hecho');
	readln;
end.