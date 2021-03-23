program P2E1;
uses sysutils;

const
VALOR_ALTO = 9999;
N = 10;

type
fechaReg=record
    dia: 1..31;
    mes: 1..12;
    anio: 1900..2200;
end;

str30 = string[30];

licencia=record
    codEmp: integer;
    fecha: fechaReg;
    cantD: integer;
end;

empleado=record
    cod: integer;
    nom: str30;
    ap: str30;
    fechaNac: fechaReg;
    dir: str30;
    cantH: integer;
    tel: LongInt;
    diasV: integer;
end;

detalle = File of licencia;
maestro = File of empleado;

arLic = array[1..N] of licencia;
arDet = array[1..N] of detalle;

procedure leer(var archivo: detalle; var dato: licencia);

begin
    if not eof(archivo) then
       read(archivo, dato)
    else dato.codEmp:= VALOR_ALTO;
end;

procedure determinarMin(var arreglo: arLic; var indice: integer);
var
   codMin, i: integer;

begin
     indice:= 1; codMin:= arreglo[1].codEmp;
     for i:= 2 to N do begin
         if arreglo[i].codEmp < codMin then begin
            codMin:= arreglo[i].codEmp;
            indice:= i;
         end;
     end;
end;

procedure minimo(var ar_reg: arLic; var min: licencia; var ar_arch: arDet);
var
   ind: integer;

begin
     determinarMin(ar_reg, ind);
     min:= ar_reg[ind];
     leer(ar_arch[ind], ar_reg[ind]);
end;

procedure actualizarMaestro(var mae: maestro; var arDeta: arDet; var lic: arLic);
var
   archTxt: Text;
   lic_min: licencia;
   aux: integer;
   totalD: integer;
   emp: empleado;

begin
     assign(archTxt, 'informe.txt'); rewrite(archTxt);
     minimo(lic, lic_min, arDeta);
     while lic_min.codEmp <> VALOR_ALTO do begin
         totalD:= 0;
         aux:= lic_min.codEmp;
         while aux = lic_min.codEmp do begin
             minimo(lic, lic_min, arDeta);
             totalD:= totalD + lic_min.cantD;
         end;
         read(mae, emp);
         while emp.cod <> aux do
             read(mae, emp);
         if emp.diasV < totalD then begin
             with emp do writeln(archTxt, cod, ' ', nom, ' ', ap, ' ', diasV, ' ', totalD, 'hola xd');
         end
         else begin
             emp.diasV:= emp.diasV - totalD;
             seek(mae, filepos(mae)-1);
             write(mae, emp);
         end;
     end;
     close(archTxt);
end;

var
   archMaestro: maestro;
   arDetalle: arDet;
   licencias: arLic;
   i: integer;

begin
     assign(archMaestro, 'maestro1.dat'); reset(archMaestro);
     for i:=1 to N do begin
         assign(arDetalle[i], concat('detalle', IntToStr(i), '.dat'));
         reset(arDetalle[i]);
         leer(arDetalle[i], licencias[i]);
     end;
     actualizarMaestro(archMaestro, arDetalle, licencias);
     close(archMaestro);
     for i:=1 to N do close(arDetalle[i]);
     writeln('Actualizacion exitosa! Presione enter para finalizar');
     readln;
end.
