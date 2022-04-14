%% Limpieza de variables y consola
clear; clc;

%% Carga de la señal limpia
load('ecg.mat');
x = ecg_limpia;

%% Constantes
Fs = 500;
Ts = 1/Fs;
N = length(x);

%% Soporte
t = (0:Ts:Ts*(N-1));
plot(t,x); title('Electrocardiograma (ECG)');
xlabel('t [segundos]'); ylabel('x(t)'); xticks((0:12));