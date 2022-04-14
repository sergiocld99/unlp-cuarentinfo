Program TP1EJ5;

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

    PajarosFile = file of pajaroReg;

procedure evaluarAlturas(altura:Integer; var menorAlt,maxAlt:Integer);
begin
    if(altura>maxAlt)then
    begin
        maxAlt:=altura;
    end;
    if(altura<menorAlt)then
    begin
        menorAlt:=altura;
    end;
end;

procedure imprimir(pajaro:pajaroReg);
begin
    writeln(pajaro.numeroEspecie,'; ',pajaro.alturaMax,'; ',pajaro.nombreCientifico,'; ',pajaro.nombreVulgar,'; ',
        pajaro.color);
end;

var 
    archivo: PajarosFile;
    pajaro: pajaroReg;
    cantidad, menorAlt, maxAlt: Integer;
begin
    assign(archivo, 'pajaros');
    reset(archivo);
    cantidad:= 0; menorAlt:= 999; maxAlt:= 0;
    while(not eof(archivo)) do 
    begin
        read(archivo,pajaro);
        cantidad:= cantidad + 1;
        evaluarAlturas(pajaro.alturaMax,menorAlt,maxAlt);
        imprimir(pajaro);
        if(pajaro.nombreCientifico = 'Victoria amazonia')then
        begin
            pajaro.nombreCientifico:= 'Victoria amazonica';
            write(archivo,pajaro);
        end;
    end;
    writeln('cantidad de pajaros: ',cantidad);
    close(archivo);
    writeln('altura maxima: ',maxAlt);
    writeln('altura minima: ',menorAlt);
    readln();
end.
