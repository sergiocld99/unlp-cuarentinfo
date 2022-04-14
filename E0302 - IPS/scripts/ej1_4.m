%% Limpieza de variables y consola;
clear; clc;

%% Declaración de constantes
K = 200;                % Lugares a desplazar hacia la derecha

%% Generación de las señales
x = cacode(14);         % Señal generada del Satélite 14
y = circshift(x,K);     % Señal desplazada del Satélite 14

%% Soporte
m = (-511:1:511);

%% Intercorrelación
r_yx = correlfft(y,x);
figure; stem(m, fftshift(r_yx), '.'); 
title('Intercorrelación de la señal de Satélite 14 y su versión desplazada');
xlabel('m'); ylabel('r°_y_x[m]');