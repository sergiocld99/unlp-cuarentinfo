Program TP1EJ4;

type
	VotantesText = text;
    Votantes = file of integer;

procedure generarArchivoTexto(var archivoText: VotantesText);
var
    VotantesFile: Votantes;
    votantesPorCiudad: integer;
begin
    Assign(archivoText,'votantes.txt');
    Assign(VotantesFile,'votantes');
    reset(VotantesFile);
    rewrite(archivoText);
    while(not eof(VotantesFile)) do 
        begin
            read(VotantesFile,votantesPorCiudad);
            writeln(archivoText,votantesPorCiudad);
        end;
    close(VotantesFile);
    close(archivoText);
end;

var 
    archivo: VotantesText;
begin
    generarArchivoTexto(archivo);
    readln();
end.
