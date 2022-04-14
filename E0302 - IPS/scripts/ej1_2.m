%% Limpieza de variables y consola
clear; clc;

%% Generación de señales
x = cacode(12);             % Código Gold correspondiente al satélite 12
y = randi([0,1],size(x));   % Señal de ruido (valores 0 y 1)
y(y==0) = -1;               % "y" toma 2 valores posibles: -1 y 1

%% Cálculo de correlaciones
r_xx = correlfft(x,x);      % Autocorrelación de "x"
r_yy = correlfft(y,y);      % Autocorrelación de "y"
r_xy = correlfft(x,y);      % Intercorrelación de "x" e "y"

%% Soportes (eje de abscisas)
n = (0:1:length(x)-1);
m = (-511:1:511);

%% Visualización de las señales
figure; subplot(2,1,1); stem(n,x,'.'); xticks((0:128:1024)); xlim([0 1024]);
title('Señal tipo código Gold del Satélite 12'); xlabel('n'); ylabel('x[n]');

subplot(2,1,2); stem(n,y,'.'); xticks((0:128:1024)); xlim([0 1024]);
title('Señal de ruido'); xlabel('n'); ylabel('y[n]');

%% Visualización de autocorrelaciones
figure; subplot(2,1,1); stem(m,fftshift(r_xx),'.'); xticks((-512:128:512)); xlim([-512 512]);
title('Autocorrelación de x'); xlabel('m'); ylabel('r°_x_x[m]');

subplot(2,1,2); stem(m,fftshift(r_yy),'.'); xticks((-512:128:512)); xlim([-512 512]);
title('Autocorrelación de y'); xlabel('m'); ylabel('r°_y_y[m]');

%% Visualización de la intercorrelación
figure; stem(m,fftshift(r_xy),'.'); xticks((-512:128:512)); xlim([-512 512]);
title('Intercorrelación de x e y'); xlabel('m'); ylabel('r°_x_y[m]');
