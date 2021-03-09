program ejer03;

const
  PALABRA_FIN = 'zzz';

procedure escribirDinosarios(var miArchivo: text);
var
  input: string[30];
begin
  readln(input);
  while (input <> PALABRA_FIN) do begin
    writeln(miArchivo, input);
    readln(input);
  end;
end;

var
  archivo: text;
  nombreArch: string[30];

begin
  // Crear archivo de texto
  writeln('Ingrese el nombre del archivo a crear:');
  readln(nombreArch);
  assign(archivo, nombreArch);
  rewrite(archivo);

  // Ir leyendo (y escribiendo) hasta la palabra de corte
  writeln('Ingrese tipos de dinosaurios de Sudamerica:');
  escribirDinosaurios(archivo);

  // Cerrar archivo
  close(archivo);

  // Terminar
  writeln('Fin del programa');
  readln;
end.

