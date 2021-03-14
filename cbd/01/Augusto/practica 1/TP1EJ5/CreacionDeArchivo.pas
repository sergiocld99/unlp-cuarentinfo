Program CreacionDeArchivo;

const 

tamanio = 50;

type

    pajaroReg = record
        numeroEspecie : Integer;
        alturaMax: Integer;
        nombreCientifico: String[tamanio];
        nombreVulgar: String[tamanio];
        color: String[tamanio];
    end;

	archivo = file of pajaroReg;

procedure leer(var pajaro: pajaroReg);
begin
    writeln('ingrese numero de especie: ');
    readln(pajaro.numeroEspecie);
    writeln('ingrese altura maxima: ');
    readln(pajaro.alturaMax);
    writeln('ingrese nombre vulgar: ');
    readln(pajaro.nombreVulgar);
    writeln('ingrese color: ');
    readln(pajaro.color);
end;

var
	nombreArchivo: String[12];
    archivoLogico: archivo;
    pajaro : pajaroReg;
begin
    writeln('ingrese nombre del archivo');
    readln(nombreArchivo);
    assign(archivoLogico, nombreArchivo);
    rewrite(archivoLogico);
    writeln('ingrese nombre cientifico: ');
    read(pajaro.nombreCientifico);
    while(pajaro.nombreCientifico <> 'zzz') do 
        begin
            leer(pajaro);
            write(archivoLogico,pajaro);
            writeln('ingrese nombre cientifico: ');
            read(pajaro.nombreCientifico);
        end;
    close(archivoLogico);
    writeln('hecho!');
    read;
end.
