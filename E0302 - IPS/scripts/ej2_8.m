%% Limpieza de variables y consola
clear; clc;

%% Carga del filtro
load('ej2_8_filtro.mat');

%% Caracteristicas
h = impz(FIR_LP);       % Respuesta impulsional
h = [h' zeros(1,2048)];
n = [0:1:length(h)-1];
H = fftshift(fft(h));   % Respuesta en frecuencia

%% Constantes
Fs = 500;
Ts = 1/Fs;
N = length(n);

%% Soporte
f = ((1-(1/N)*mod(N,2))*(-1/(2*Ts)):(1/N)*(1/Ts):((N-1)/N) * (1/(2*Ts)));

%% Gráfica del módulo de la respuesta en frecuencia
figure; plot(f,20*log10(abs(H)));
title('Módulo de la respuesta en frecuencia del filtro pasabajos');
xlabel('f [Hz]'); ylabel('|H(f)| [dB]');

%% Gráfica de la fase de la respuesta en frecuencia
figure; plot(f,unwrap(angle(H)) + 125.36);
title('Fase de la respuesta en frecuencia del filtro pasabajos');
xlabel('f [Hz]'); ylabel('Fase de H(f) [rad]');