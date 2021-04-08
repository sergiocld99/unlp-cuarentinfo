function ajustePotencial (x, y)
	lx=log(x);
	ly=log(y);
	sumlx=sum(lx)
	suml2x=sum(lx.^2)
	sumly=sum(ly)
	sumlxly=sum(lx.*ly)
	n=length(x);
	m1=[n sumlx;sumlx suml2x];
	m2=[sumly;sumlxly];
	coefaux=m1\m2;
	a0=coefaux(1)
	a1=coefaux(2)
	A=exp(a0)
	B=a1
endfunction