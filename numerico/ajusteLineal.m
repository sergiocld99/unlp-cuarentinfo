% Devuelve los coeficientes del polinomio para usarlo luego con polyval
% Es decir, el primer valor es la pendiente y el segundo es el termino independiente
% Cuidado, porque eso ultimo difiere del orden visto en la teoria a puño y letra

function coef = ajusteLineal (x, y)
	sumx=sum(x);
	sumx2=sum(x.^2);
	sumy=sum(y);
	sumxy=sum(x.*y);
	n=length(x);
	m1=[sumx n;sumx2 sumx];
	m2=[sumy;sumxy];
	coef=m1\m2;
endfunction

% Testeado y funcionando bien :)