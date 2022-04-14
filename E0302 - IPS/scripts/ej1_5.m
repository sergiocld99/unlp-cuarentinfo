%% Limpieza de variables y consola;
clear; clc;

%% Declaración de constantes
SAT = (1:1:32);       % Números de satélites
T = 1023;             % Último indice del primer periodo de señal

%% Carga de la señal incógnita (s) y extracción de 1 período
load('senial_incognita.mat');
xinc = s(1:T);

%% Comparamos la señal con la correspondiente a cada satélite
m = (-511:1:511);

for k = 1 : length(SAT)
    x = cacode(SAT(k));                 % Código Gold de satélite
    r = correlfft(x, xinc);             % Intercorrelación
    figure; stem(m,fftshift(r),'.');   
    title(strcat('Intercorrelación para Satélite:',num2str(SAT(k))));
    xlabel('m'); ylabel('r°_x_s[m]');
end
