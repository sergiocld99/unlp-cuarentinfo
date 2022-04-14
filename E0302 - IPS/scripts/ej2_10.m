%% Limpieza de variables y consola
clear; clc;

%% carga de señales
load('ecg.mat');                % carga del electrocardiograma limpio
load('ecg_contaminada.mat');    % carga del ECG contaminado

%% señales
y = ecg_contaminada.';
x = ecg_limpia.';

%% variables
Fs = 500;
Ts = 1/Fs;
N = length(y);
t = (0:Ts:Ts*(N-1));

%% carga de filtros
load('ej2_8_filtro.mat');
load('ej2_9_filtro.mat');

%% filtramos la señal contaminada
z = filter(FIR_LP,y');
z = filter(FIR_BS,z);

%% mostramos los resultados en el dominio del tiempo
figure; subplot(3,1,1); plot(t,y,'r'); title('ECG contaminado, sin filtrar');
xlabel('t [segundos]'); ylabel('y(t)');

subplot(3,1,2); plot(t,z,'black'); title('ECG filtrado');
xlabel('t [segundos]'); ylabel('z(t)');

subplot(3,1,3); plot(t,x(1:length(z))); title('ECG limpio (comparativa)');
xlabel('t [segundos]'); ylabel('x(t)');

%% mostramos los resultados en el dominio de la frecuencia
f = ((1-(1/N)*mod(N,2))*(-1/(2*Ts)):(1/N)*(1/Ts):((N-1)/N) * (1/(2*Ts)));
figure; subplot(3,1,1); plot(f,abs(fftshift(fft(y))),'r'); title('Espectro del ECG, sin filtrar'); 
xlabel('f [Hz]'); ylabel('|Y(f)|');

subplot(3,1,2); plot(f,abs(fftshift(fft(z))),'black'); title('Espectro del ECG filtrado'); 
xlabel('f [Hz]'); ylabel('|Z(f)|');

N = length(x);
f = ((1-(1/N)*mod(N,2))*(-1/(2*Ts)):(1/N)*(1/Ts):((N-1)/N) * (1/(2*Ts)));
subplot(3,1,3); plot(f,abs(fftshift(fft(x)))); title('Espectro en frecuencia del ECG limpio'); 
xlabel('f [Hz]'); ylabel('|X(f)|');