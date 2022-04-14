Program TP1EJ3;
const
	tamanio = 12;
type
	nombre  = string[tamanio];
	Dinos = text;
var
	dinosFile: Dinos;
	dinosaurio: nombre;
begin
	dinosaurio:= '';
    Assign(dinosFile,'dinosaurios.txt');
    rewrite(dinosFile);
    readln(dinosaurio);
    while(dinosaurio <> 'zzz') do 
    	begin
    		writeln(dinosFile,dinosaurio);
    		writeln('ingrese nombre de dinosaurio');
    		readln(dinosaurio);
    	end;
    close(dinosFile);
    readln;
end.
