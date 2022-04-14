-- Alumno: CALDERON Sergio Leandro
-- Legajo: 02285/4

-- Utilización de librería para entrada/salida
use STD.textio.all;

-- Utilización del Paquete Utils (práctica 8)
use work.Utils.all;

-- Entidad de Testbench, no requiere entradas ni salidas
entity TestMultiplicadorIO is end;
	
-- Arquitectura del Testbench
architecture Interfase of TestMultiplicadorIO is	

	-- Mensajes personalizados para mostrar en consola
	constant msj_bienvenida:string :=	"Bienvenido. Este sistema es un multiplicador de números enteros.";
	constant msj_restriccion:string :=	"Unicamente se permite ingresar números entre 0 y 15";
	constant msj_pide_num1:string :=	"Por favor, ingrese el primer operando";
	constant msj_pide_num2:string := 	"Ahora, ingrese el segundo operando";
	constant msj_error_input:string := 	"Entrada inválida. Ingrese un número entre 0 y 15";
	constant msj_calculando:string :=	"Resolviendo la multiplicación ";
	constant msj_resultado:string := 	"El resultado es ";

	-- Componente sobre el cual se realizarán las pruebas
	component Multiplicador
	port(									 
		STB, CLK: in Bit; 
		A, B: in Bit_Vector(3 downto 0); 
		Result: out Bit_Vector(7 downto 0);
		Done: out Bit
	);
	end component;


	-- Señales de entrada y salida del Multiplicador
	signal A, B: Bit_Vector(3 downto 0);	
	signal Result: Bit_Vector(7 downto 0);
	signal STB, CLK, Done: Bit; 	   
	
	-- Definición de subtipo valido para entradas
	subtype entradaNatural is Natural range 15 downto 0;

begin
	-- Se instancia el multiplicador
	Multi: Multiplicador port map(STB, CLK, A, B, Result, Done);

	-- Se asigna el período del reloj
	Clock(CLK, 10 ns, 10 ns); 
	
	Stimulus : process
		-- Declaración de variables
		variable lineaOut,lineaIn:line;	-- Linea buffer para entrada y salida  
		variable exito:boolean;			-- Indicador de asignación de lectura exitoso
		variable num1,num2:integer;		-- Número entero leido por teclado	  		
		variable resultado:Natural;		-- Resultado (producto) en representación decimal	
		
	begin	
		-- Se da la bienvenida al usuario por consola	
		write(lineaOut, msj_bienvenida);
		writeline(output, lineaOut);
		
		write(lineaOut, msj_restriccion);
		writeline(output, lineaOut);		 
		
		-- Se solicita y lee el primer operando	 	
		write(lineaOut, msj_pide_num1);
		writeline(output, lineaOut);
		
		loop	 
			readline(input, lineaIn);
			read(lineaIn, num1, exito);
			-- Se verifica que sea un numero entre 0 y 15
			if (exito and num1 >= 0 and num1 < 16) then	
				A <= Convert(num1,4);
				exit;
			else		 
				write(lineaOut, msj_error_input); 
				writeline(output, lineaOut);
			end if;
		end loop;
		
		-- Se solicita y lee el segundo operando
		write(lineaOut, msj_pide_num2);
		writeline(output, lineaOut);
		
		loop	 
			readline(input, lineaIn);
			read(lineaIn, num2, exito);
			-- Se verifica que sea un numero entre 0 y 15
			if (exito and num2 >= 0 and num2 < 16) then	
				B <= Convert(num2,4);
				exit;
			else
				write(lineaOut, msj_error_input);
				writeline(output, lineaOut);
			end if;
		end loop;
		
		-- Se da comienzo a la operación  
		wait until CLK'Event and CLK = '1';
 		STB <= '1';
		wait until CLK'Event and CLK = '0';
		STB <= '0';	
				
		-- Se muestra qué operación se está realizando y se espera al resultado
		write(lineaOut, msj_calculando & integer'image(num1) & "x" & integer'image(num2));
		writeline(output, lineaOut);
		wait until Done = '1';
		
		-- Se informa el resultado
		resultado:= Convert(Result);
		write(lineaOut, msj_resultado & integer'image(resultado));
		writeline(output, lineaOut);
		
		-- Se finaliza la simulación
		wait;
	end process;

end;
