program gen03;
uses sysutils;

var
  txt:text;
  i:integer;

begin
  assign(txt,'alumnos.txt');
  rewrite(txt);
  randomize;

  for i:=1 to 10 do begin
    writeln(txt,random(10000));
    writeln(txt,10000000+random(30000000));
    writeln(txt,concat('apellido',intToStr(i)));
    writeln(txt,concat('nombre',intToStr(i)));
    writeln(txt,concat('calle ',intToStr(i)));
    writeln(txt,1+random(5));
    writeln(txt,random(10000000));
  end;

  close(txt);
end.

