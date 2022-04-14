Program TP1EJ2;

type
	Votantes = file of integer;
var
	VotantesFile: Votantes;
	votantesPorCiudad: integer;
    total,cantidad,max: integer;
begin
    total := 0; votantesPorCiudad := 0; cantidad := 0; max := 0;
    Assign(VotantesFile,'votantes');
    reset(VotantesFile);
    while(not eof(VotantesFile)) do 
        begin
            read(VotantesFile,votantesPorCiudad);
            total:= total + votantesPorCiudad;
            cantidad := cantidad + 1;
            if(max < votantesPorCiudad) then
            begin
                max := votantesPorCiudad;
            end;
        end;
    writeln('promedio ',total/cantidad);
    writeln('maxima cantidad de habitantes: ',max);
    close(VotantesFile);
    readln();
end.
