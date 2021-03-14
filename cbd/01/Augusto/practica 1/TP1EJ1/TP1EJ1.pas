Program TP1EJ1;
const
	tamanio = 12;
type
	nombre  = string[tamanio];
	Materias = text;
var
	materiasFile: Materias;
	materia: nombre;
begin
	materia := '';
    Assign(materiasFile,'materias.txt');
    rewrite(materiasFile);
    readln(materia);
    while(materia <> 'cemento') do 
    	begin
    		writeln(materiasFile,materia);
    		writeln('ingrese materia');
    		readln(materia);
    	end;
    close(materiasFile);
    read;
end.
