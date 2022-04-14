-- Alumno: CALDERON Sergio Leandro
-- Legajo: 02285/4

-- Utilización del Paquete Utils (práctica 8)
use work.Utils.all;

-- Entidad de Testbench, no requiere entradas ni salidas
entity TestMultiplicador is end;
	
-- Arquitectura del Testbench
architecture Driver of TestMultiplicador is	

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
	
	-- Señal auxiliar para verificar cada operación
	signal resultado: NATURAL := 0;

begin
	-- Se instancia el multiplicador
	Multi: Multiplicador port map(STB, CLK, A, B, Result, Done);

	-- Se asigna el período del reloj
	Clock(CLK,5 ns,5 ns); 
	
	Stimulus : process
	begin		
		
		-- Se realiza un test exhaustivo (todas las combinaciones de A y B)
		-- Por cada valor de A
		for i in 0 to 15 loop				   			
			-- Se convierte el primer operando a binario
			A <= Convert(i, 4);				
			
			-- Por cada valor de B
			for j in 0 to 15 loop
				-- Se convierte el segundo operando a binario
				B <= Convert(j, 4);	
				
				-- Se da comienzo a la operación  
				wait until CLK'Event and CLK = '1';
 				STB <= '1';
				wait until CLK'Event and CLK = '0';
				STB <= '0';	
				
				-- Se espera al fin de la operación	
				wait until Done = '1';	 
			
				-- Se verifica el resultado obtenido
				assert Result = Convert(resultado, 8) report "Resultado incorrecto" severity FAILURE;
				wait for 5 ns;
				
				-- Se actualiza el resultado esperado
				resultado <= resultado + i;
				
			end loop;
			
			-- Se reinicia el resultado	   
			resultado <= 0;
			
		end loop;
		
		-- Terminar simulación (usando esta configuración tarda aprox 28500 ns)
		wait;
	end process;

end;
