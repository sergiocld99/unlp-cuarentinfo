program TP1EJ7;

type

    alumnoReg = record
        DNI : integer;
        legajo: integer;
        nombre: string[15];
        apellido: string[15];
        direccion: string[15];
        anioQueCursa: integer;
        nacimiento: Longint;
    end;

	archivo = file of alumnoReg;

procedure cargarAlumno(var texto:text;var alumno:alumnoReg);
begin
    readln(texto, alumno.DNI);
    readln(texto, alumno.legajo);
    readln(texto, alumno.nombre);
    readln(texto, alumno.apellido);
    readln(texto, alumno.direccion);
    readln(texto, alumno.anioQueCursa);
    readln(texto, alumno.nacimiento);
end;

procedure imprimir(alumno:alumnoReg);
begin
    writeln('DNI: ',alumno.DNI);
    writeln('Legajo: ',alumno.legajo);
    writeln('Nombre: ',alumno.nombre);
    writeln('Apellido: ',alumno.apellido);
    writeln('Direccion: ',alumno.direccion);
    writeln('Anio: ',alumno.anioQueCursa);
    writeln('Nacimiento: ',alumno.nacimiento);
end;

procedure buscarAlumno(caracter:char;alumno:alumnoReg);
begin
    if(caracter = alumno.nombre[length(alumno.nombre)]) then
    begin
        imprimir(alumno);
    end;
end;

procedure escribir(alumno:alumnoReg; var texto:text);
begin
    writeln(texto, alumno.DNI);
    writeln(texto, alumno.legajo);
    writeln(texto, alumno.nombre);
    writeln(texto, alumno.apellido);
    writeln(texto, alumno.direccion);
    writeln(texto, alumno.anioQueCursa);
    writeln(texto, alumno.nacimiento);
end;

procedure leer(var alumno: alumnoReg);
begin
    writeln('Ingrese DNI');
    readln(alumno.DNI);
    writeln('Ingrese legajo');
    readln(alumno.legajo);
    writeln('Ingrese nombre');
    readln(alumno.nombre);
    writeln('Ingrese apellido');
    readln(alumno.apellido);
    writeln('Ingrese direccion');
    readln(alumno.direccion);
    writeln('Ingrese anio');
    readln(alumno.anioQueCursa);
    writeln('Ingrese nacimiento');
    readln(alumno.nacimiento);
end;

procedure modificar(var alumno:alumnoReg);
var 
 fecha:Longint;
begin
    writeln('ingrese fecha nueva');
    readln(fecha);
    alumno.nacimiento:=fecha;
end;

var
    archivoLogico: archivo;
    texto,textoAegresar: text;
    alumno: alumnoReg;
    caracter:char;
    opciones,legajo:integer;
    opcion2:integer;
begin
    assign(archivoLogico, 'alumnos');
    assign(texto,'alumnos.txt');
    assign(textoAegresar,'alumnosAEgresar.txt');
    opciones:= 0;
    writeln('1 - crea archivo binario de alumnos');
    writeln('2 - listar usuarios con nombre dado');
    writeln('3 - crear archivo de alumnos a egresar .txt');
    writeln('4 - agregar alumno al archivo binario');
    writeln('5 - modificar fecha de uno por legajo');
    readln(opciones);

    case opciones of
        1: begin
            reset(texto);
            rewrite(archivoLogico);

            repeat
            begin
                cargarAlumno(texto,alumno);
                write(archivoLogico,alumno);
            end;
            until eof(archivoLogico);
            close(texto);
            close(archivoLogico);
            writeln('hecho!');
        end;

        2: begin
            caracter:= ' ';
            writeln('ingrese caracter con el que debe terminar el nombre');
            readln(caracter);
            reset(archivoLogico);
            repeat
            begin
                read(archivoLogico,alumno);
                buscarAlumno(caracter,alumno);
            end;
            until eof(archivoLogico);
            close(archivoLogico);
            writeln('hecho!');
        end;

        3: begin
             rewrite(textoAegresar);
             reset(archivoLogico);
             repeat
             begin
             read(archivoLogico,alumno);
                 if(alumno.anioQueCursa = 5) then
                    begin
                        escribir(alumno,textoAegresar);
                    end;
             end;
             until eof(archivoLogico);
             close(textoAegresar);
             close(archivoLogico);
             writeln('hecho!');
        end;

        4: begin
            reset(archivoLogico);
            opcion2:= 0;
            writeln('agregar archivo?');
            writeln('1-zi;   2-no');
            readln(opcion2);
            while(opcion2=1) do 
                begin
                leer(alumno);
                seek(archivoLogico,fileSize(archivoLogico));
                write(archivoLogico,alumno);
                writeln('agregar archivo?');
                writeln('1-zi;   2-no');
                readln(opcion2);
                end;
            close(archivoLogico);
            writeln('hecho!');
        end;

        5: begin
            legajo:= 0;
            writeln('ingrese legajo');
            readln(legajo);
            reset(archivoLogico);
            repeat  
            begin
                read(archivoLogico,alumno);
                if(legajo = alumno.legajo) then 
                begin
                    modificar(alumno);
                    write(archivoLogico,alumno);
                end;
            end;
            until eof(archivoLogico);
            close(archivoLogico);
            writeln('hecho!');
        end;
    end;
    readln();
end.
