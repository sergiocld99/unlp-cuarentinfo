function coef = ajusteLineal (x, y)
	sumx=sum(x);
	sumx2=sum(x.^2);
	sumy=sum(y);
	sumxy=sum(x.*y);
	n=length(x);
	m1=[n sumx;sumx sumx2];
	m2=[sumy;sumxy];
	coef=m1\m2;
endfunction