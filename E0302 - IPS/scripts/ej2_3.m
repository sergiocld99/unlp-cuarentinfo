%% Limpieza de variables y consola
clear; clc;

%% Carga del filtro
load('ej2_3_filtro.mat');

%% Caracteristicas
h = impz(FIR_BP);       % Respuesta impulsional
h = [h' zeros(1,2048)];
n = [0:1:length(h)-1];
H = fftshift(fft(h));   % Respuesta en frecuencia

%% Constantes
Fs = 256;
Ts = 1/Fs;
N = length(n);

%% Soporte
f = ((1-(1/N)*mod(N,2))*(-1/(2*Ts)):(1/N)*(1/Ts):((N-1)/N) * (1/(2*Ts)));

%% Gráfica del módulo de la respuesta en frecuencia
figure; plot(f,20.*log10(abs(H)));
title('Módulo de la respuesta en frecuencia del filtro pasabanda');
xlabel('f [Hz]'); ylabel('|H(f)| [dB]');
xticks([-50 -25 -13 -8 0 8 13 25 50]);
xlim([-50 50]);

%% Gráfica de la fase de la respuesta en frecuencia
figure; plot(f,unwrap(angle(H)) + 19);
title('Fase de la respuesta en frecuencia del filtro pasabanda');
xlabel('f [Hz]'); ylabel('Fase de H(f) [rad]');
xticks((-140:20:140));