% devuelve una matriz
% el primer valor es el coeficiente cuadratico
% el segundo valor es el coeficiente lineal
% el tercer valor es el termino independiente

function coef = ajusteSegundoGrado (x, y)
	sumx=sum(x);
	sumx2=sum(x.^2);
	sumx3=sum(x.^3);
	sumx4=sum(x.^4);
	sumy=sum(y);
	sumxy=sum(x.*y);
	sumx2y=sum(x.^2.*y);
	n=length(x);
	m1=[sumx2 sumx n;sumx3 sumx2 sumx;sumx4 sumx3 sumx2];
	m2=[sumy;sumxy;sumx2y];
	coef=m1\m2;
endfunction

% testeado, funciona bien :)