function plotAjuste (x, y, grado, paso)
	plot(x,y,'or')
	hold on
	grid on	

	coef=polyfit(x,y,grado);
	xi=[min(x):paso:max(x)];
	yi=polyval(coef,xi);
	plot(xi,yi);
endfunction

% testeado, funciona bien :)