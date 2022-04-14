Program crearArchivo;

type
	archivo = file of integer;
var
	nombreArchivo: String[12];
    archivoLogico: archivo;
    nro: integer;
begin
    writeln('ingrese nombre del archivo');
    readln(nombreArchivo);
    assign(archivoLogico, nombreArchivo);
    rewrite(archivoLogico);
    read(nro);
    while(nro <> 0) do 
        begin
            write(archivoLogico,nro);
            read(nro);
        end;
    close(archivoLogico);
    writeln('hecho!');
    read;
end.
