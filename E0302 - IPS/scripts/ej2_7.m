%% Limpieza de variables y consola
clear; clc;

%% Carga de señales
load('ecg.mat');                % electrocardiograma limpio
load('ecg_contaminada.mat');    % electrocardiograma contaminado
y = ecg_contaminada.';
x = ecg_limpia.';

%% Constantes
Fs = 500;
Ts = 1/Fs;
N = length(x);

%% Gráfica de la señal limpia
t1 = (0:Ts:Ts*(N-1));
figure; subplot(2,2,1); plot(t1,x); title('Electrocardiograma (ECG) - limpio');
xlabel('t [segundos]'); ylabel('x(t)'); axis([0 11 -0.0006 0.0008]);

%% Espectro de la señal limpia
X = fftshift(fft(x));
f = ((1-(1/N)*mod(N,2))*(-1/(2*Ts)):(1/N)*(1/Ts):((N-1)/N) * (1/(2*Ts)));
subplot(2,2,2); plot(f,abs(X)); title('Espectro del ECG - limpio'); 
xlabel('f [Hz]'); ylabel('|X(f)|'); axis([-250 250 0 0.2]);

%% Señal contaminada
N = length(y);
t = (0:Ts:Ts*(N-1));
subplot(2,2,3); plot(t,y,'r'); title('Electrocardiograma (ECG) - contaminado');
xlabel('t [segundos]'); ylabel('y(t)'); axis([0 11 -0.0006 0.0008]);

%% Espectro de la señal contaminada
Y = fftshift(fft(y));
f = ((1-(1/N)*mod(N,2))*(-1/(2*Ts)):(1/N)*(1/Ts):((N-1)/N) * (1/(2*Ts)));
subplot(2,2,4); plot(f,abs(Y),'r'); title('Espectro del ECG - contaminado'); 
xlabel('f [Hz]'); ylabel('|Y(f)|'); axis([-250 250 0 0.2]);