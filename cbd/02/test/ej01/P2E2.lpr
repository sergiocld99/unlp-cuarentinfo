program P2E2;
uses sysutils;

const
VALOR_ALTO = 9999;

type
str30 = string[30];

cd=record
    codA: integer;
    nomA: str30;
    nomD: str30;
    gen: str30;
    cantV: integer;
end;

fuente = File of cd;

procedure leer(var archivo: fuente; var dato: cd);

begin
    if not eof(archivo) then
       read(archivo, dato)
    else dato.codA:= VALOR_ALTO;
end;

procedure listar(var archDat: fuente);
var
   archTxt: Text;
   cdX: cd;
   aut: integer;
   genX: str30;
   cantGen, cantAut, cantDis: integer;

begin
     assign(archTxt, 'cds.txt'); rewrite(archTxt);
     leer(archDat, cdX);
     cantDis:= 0;
     while cdX.codA <> VALOR_ALTO do begin
         aut:= cdX.codA;
         cantAut:= 0;
         writeln('Autor: ', aut);
         while aut = cdX.codA do begin
             genX:= cdX.gen;
             cantGen:= 0;
             writeln('Genero: ', genX);
             while (aut = cdX.codA) and (genX = cdX.gen) do begin
                 with cdX do begin
                     writeln('Nombre Disco: ', nomD, ' cantidad vendida: ', cantV);
                     writeln(archTxt, nomD, ' ', nomA, ' ', cantV);
                 end;
                 cantGen:= cantGen + cdX.cantV;
                 cantAut:= cantAut + cdX.cantV;
                 cantDis:= cantDis + cdX.cantV;
                 leer(archDat, cdX);
             end;
             writeln('Total Genero: ', cantGen);
         end;
         writeln('Total Autor: ', cantAut);
     end;
     writeln('Total Discografica: ', cantDis);
     writeln;
     close(archTxt);
end;

var
   archCds: fuente;

begin
     assign(archCds, 'cds.dat'); reset(archCds);
     listar(archCds);
     close(archCds);
     writeln('Operacion completada! Presione enter para finalizar');
     readln;
end.

