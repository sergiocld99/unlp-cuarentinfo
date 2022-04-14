%% Limpieza de variables y consola
clear; clc;

%% Carga de la señal desde su archivo
load('eeg.mat');

%% Constantes
Fs = 256;
Ts = 1/Fs;
N = length(x);

%% Soporte
f = ((1-(1/N)*mod(N,2))*(-1/(2*Ts)):(1/N)*(1/Ts):((N-1)/N) * (1/(2*Ts)));

%% Cálculo y visualización del espectro en frecuencia
X = abs(fftshift(fft(x')));
figure; plot(f,X,'r'); 
title('Módulo del Espectro del EEG'); 
xlabel('f [Hz]'); ylabel('|X(f)|'); xticks((-130:10:130)); xlim([-130 130]);

%% Zoom vertical
ylim([0 1e5]);