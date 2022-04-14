%% Limpieza de variables y consola
clear; clc;

%% Generación de las señales
x = cacode(3);
y = cacode(14);

%% Soporte (eje de abscisas)
m = (-511:1:511);

%% Cálculo de correlaciones
r_xx = correlfft(x,x);
r_yy = correlfft(y,y);
r_xy = correlfft(x,y);

%% Visualización de autocorrelaciones
figure; subplot(2,1,1);stem(m, fftshift(r_xx), '.'); xticks((-512:128:512)); xlim([-512 512]);
title('Autocorrelación - Satélite 3'); xlabel('m'); ylabel('r°_x_x[m]');

subplot(2,1,2); stem(m, fftshift(r_yy), '.'); xticks((-512:128:512)); xlim([-512 512]);
title('Autocorrelación - Satélite 14'); xlabel('m'); ylabel('r°_y_y[m]');

%% Intercorrelación de x e y
figure; stem(m, fftshift(r_xy), '.'); xticks((-512:128:512)); xlim([-512 512]);
title('Intercorrelación de los señales'); xlabel('m'); ylabel('r°_x_y[m]');