Program TP1EJ6;
	
type
	
    libroReg = record
        ISBN : string[14];
        titulo: string[30];
        genero: string[15];
        editorial: string[20];
        anio: integer;
    end;
    archivo = file of libroReg;

procedure cargarLibros(var texto:text;var libro:libroReg);
begin
	read(texto, libro.ISBN); //el puntero queda en el ultimo espacio del string
	readln(texto, libro.titulo); //lee y se pone en la siguiente linea!
	read(texto, libro.anio);
	readln(texto, libro.editorial);
	readln(texto, libro.genero);
end;

procedure leerLibro(var libro: libroReg);
begin
	writeln('Ingrese ISBN');
	readln(libro.ISBN);
	writeln('Ingrese titulo');
	readln(libro.titulo);
	writeln('Ingrese año');
	readln(libro.anio);
	writeln('Ingrese editorial');
	readln(libro.editorial);
	writeln('Ingrese genero');
	readln(libro.genero);
end;

procedure escribirLibro(var archivoLogico:archivo; libro:libroReg);
begin
	write(archivoLogico,libro);
end;

procedure buscarRegistro(ISBN: string;var archivoLogico:archivo; var libro:libroReg);
begin
	repeat
		read(archivoLogico,libro);
	until (eof(archivoLogico) or (ISBN = libro.ISBN));
end;

var
    libro: libroReg;
    archivoLogico: archivo;
    texto: text;
    opciones: integer;
    ISBN: string[14];
begin
    assign(texto, 'libros.txt');
    assign(archivoLogico, 'libros');


    opciones:= 0;
    writeln('presione 1 para crear archivo nuevo binario:');
    writeln('presione 2 para agregar libro');
    writeln('presione 3 para modificar libro');
    readln(opciones);

    case opciones of

    	1: begin
    		reset(texto);
    		rewrite(archivoLogico);

		    while(not eof(texto)) do
		    begin
		    	cargarLibros(texto, libro);
		    	escribirLibro(archivoLogico,libro);
		    end;
		    close(archivoLogico);
    		close(texto);
		    writeln('hecho!');
    	end;

    	2: begin
    		reset(archivoLogico);
    		leerLibro(libro);
    		seek(archivoLogico,fileSize(archivoLogico));
    		write(archivoLogico,libro);
    		close(archivoLogico);
    		writeln('hecho!');
    	end;

    	3: begin
    		writeln('inserte ISBN');
    		readln(ISBN);
    		reset(archivoLogico);
    		buscarRegistro(ISBN,archivoLogico,libro);
    		leerLibro(libro);
    		write(archivoLogico,libro):
    		close(archivoLogico);
    		writeln('hecho');
    	end;
    end;
    readln();
end.
