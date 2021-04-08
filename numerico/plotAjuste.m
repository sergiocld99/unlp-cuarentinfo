function plotAjuste (x, y, grado, paso)
	clf
	grid on
	plot(x,y,'or')
	hold on	

	coef=polyfit(x,y,grado);
	xi=[x(1):paso:x(length(x))];
	yi=polyval(coef,xi);
	plot(xi,yi);
endfunction

% testeado, funciona bien :)