function ajusteExponencial (x, y, paso)
	ly=log(y);
	sumx=sum(x)
	sumx2=sum(x.^2)
	sumly=sum(ly)
	sumxly=sum(x.*ly)
	n=length(x);
	m1=[n sumx;sumx sumx2];
	m2=[sumly;sumxly];
	coefaux=m1\m2;
	a0=coefaux(1)
	a1=coefaux(2)
	A=exp(a0)
	B=a1

	% funcion de ajuste como mensaje
	printf("Funcion de ajuste: %f * e^(%f x)\n", A, B)

	% vamo a dibujar
	clf
	grid on
	plot(x,y,'or')
	hold on	

	xi=[x(1):paso:x(length(x))];
	yi=A.*exp(B.*xi);
	plot(xi,yi);	
endfunction