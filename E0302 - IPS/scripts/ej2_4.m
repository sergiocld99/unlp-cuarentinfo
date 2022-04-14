%% Limpieza de variables y consola
clear; clc;

%% Carga de la señal y filtro
load('eeg.mat');
load('ej2_3_filtro.mat');

%% Constantes
Fs = 256;
Ts = 1/Fs;
N = length(x);

%% Soportes
t = (0:Ts:Ts*(N-1));
f = ((1-(1/N)*mod(N,2))*(-1/(2*Ts)):(1/N)*(1/Ts):((N-1)/N) * (1/(2*Ts)));

%% Aplicación del filtro
y = filter(FIR_BP,x);
Y = fftshift(fft(y));               % espectro

%% Gráficas
figure; subplot(2,1,1); plot(t,y); title('Electroencefalograma filtrado (EEG)');
xlabel('t [segundos]'); ylabel('y(t)'); xticks((0:5:85)); xlim([0 85]);

subplot(2,1,2); plot(f,abs(Y),'r'); title('Espectro del EEG filtrado');
xlabel('f [Hz]'); ylabel('|Y(f)|'); xticks((-130:10:130)); xlim([-130 130]);