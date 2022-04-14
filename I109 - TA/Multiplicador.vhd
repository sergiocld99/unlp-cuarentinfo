-- Alumno: CALDERON Sergio Leandro
-- Legajo: 02285/4	 	  

-- Multiplicador para dos numeros binarios sin signo de 4 bits
entity Multiplicador is	
	
	-- Recibe 2 numeros binarios de 4 bits (A y B), el reloj (CLK) y el mando de inicio (STB)
	-- Tiene como salida un numero binario de 8 bits (Result) y una señal de "terminado" (Done)
	port(									   
		STB, CLK: in Bit; 
		A, B: in Bit_Vector(3 downto 0); 
		Result: out Bit_Vector(7 downto 0);
		Done: out Bit
	);
	
end Multiplicador;


-- Arquitectura usando el esquema de sumas sucesivas
architecture SumasSucesivas of Multiplicador is	

	-- Componente Controlador (máquina de estados)
	component Controller
		port(STB, CLK, LSB, Stop: in Bit; Init, Shift, Add, Done: out Bit);
	end component;

	-- Componente Registro de desplazamiento de N bits
	component ShiftN
		port(CLK, CLR, LD, SH, DIR: in Bit; D: in Bit_Vector; Q: out Bit_Vector);	   
	end component;

	-- Componente Sumador de 8 bits
	component Adder8
		port(A, B: in Bit_Vector(7 downto 0); Cin: in Bit; Cout: out Bit; Sum: out Bit_Vector(7 downto 0)); 
	end component;

	-- Componente Flip Flop tipo D
	component Latch8
		port(D: in Bit_Vector(7 downto 0); Clk, Pre, Clr: in Bit; Q: out Bit_Vector(7 downto 0));
	end component;


	-- Declaración de señales internas a la arquitectura					   
	signal Stop: Bit;							-- Entrada Stop del Controlador
	signal Init : Bit;							-- Salida Init del Controlador 
	signal Shift: Bit;							-- Salida Shift del Controlador
	signal Add: Bit;							-- Salida Add del Controlador 	 
	signal Q_SRA: Bit_Vector(7 downto 0);		-- Salida del componente SRA 
	signal Q_SRB: Bit_Vector(7 downto 0);		-- Salida del componente SRB  
	signal Q_ACC: Bit_Vector(7 downto 0); 		-- Salida del acumulador (ACC)
	signal Q_Adder: Bit_Vector(7 downto 0);		-- Salida del sumador (Adder) 
	signal Co_Adder: Bit;						-- Carry de salida del sumador
	signal NotClock: Bit;						-- Señal invertida de reloj	 
	signal NotClearReg: Bit;					-- Señal invertida de Clear para reg acumulador y resultado
	signal Estable: Bit;						-- Señal que indica si los FF están estabilizados

begin	   
	-- Asignación concurrente de señales																	  
	Estable <= '0', '1' after 2 ns;
	NotClock <= not CLK;
	
	-- Los registros ACC y resultado deben limpiarse si los mismos no están estabilizados al principio
	-- y también cuando la máquina se encuentra en el estado Init.
	NotClearReg <= Estable and not Init;
	
	-- Instanciación de todos los componentes, mapeo de puertos
	FSM_Controller: Controller port map (STB=>STB, CLK=>NotClock, LSB=>Q_SRA(0), Stop=>Stop, 
											Init=>Init, Shift=>Shift, Add=>Add, Done=>Done);
											
	Adder: Adder8 port map(A=>Q_ACC, B=>Q_SRB, Cin=>'0', Cout=>Co_Adder, Sum=>Q_Adder);	  
	Reg_SRA: ShiftN port map(CLK=>CLK, CLR=>'0', LD=>Init, SH=>Shift, DIR=>'0', D=>A, Q=>Q_SRA);	
	Reg_SRB: ShiftN port map(CLK=>CLK, CLR=>'0', LD=>Init, SH=>Shift, DIR=>'1', D=>B, Q=>Q_SRB);     
	Reg_ACC: Latch8 port map(D=>Q_Adder, Clk=>Add, Pre=>'1', Clr=>NotClearReg, Q=>Q_ACC);	 -- Clr y Pre son activas en bajo	 	
	Reg_Res: Latch8 port map(D=>Q_ACC, Clk=>Stop, Pre=>'1', Clr=>NotClearReg, Q=>Result);	 -- Clr y Pre son activas en bajo
	
	-- Compuerta NOR entre SRA y la máquina de estados
	Stop <= not(Q_SRA(7) or Q_SRA(6) or Q_SRA(5) or Q_SRA(4) or Q_SRA(3) or Q_SRA(2) or Q_SRA(1) or Q_SRA(0)); 
	
end;
