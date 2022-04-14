%% Limpiamos variables y figuras anteriores
clear
clc

%% Declaración de constantes
T = 1023;             % Último indice del primer periodo de señal
SAT = [2 8 16];       % Números de satélites

%% Cargamos la señal incógnita (s)
load('senial_incognita.mat');

%% Tomamos 1 periodo de la señal y agregamos ruido
xinc = s(1:T);
xincn = xinc+7*randn(size(xinc));

%% Graficamos ambas señales
subplot(2,1,1); stem(xinc,'.'); title('Señal incógnita');
xlabel('n'); ylabel('xinc[n]'); xticks((0:128:1024)); axis([0 1024 -25 25]);

subplot(2,1,2); stem(xincn,'.'); title('Señal incógnita con ruido adicionado');
xlabel('n'); ylabel('xincn[n]'); xticks((0:128:1024)); axis([0 1024 -25 25]);

%% Comparamos la señal con ruido con la correspondiente a cada satélite
m = (-511:1:511);

for k = 1 : length(SAT)
    % Generación del código de satélite
    x = cacode(SAT(k));
    % Cálculo de la intercorrelación con la señal incógnita
    r = correlfft(x, xincn);
    % Se genera la imagen del resultado para el satélite actual
    figure; stem(m,fftshift(r),'.'); title(strcat('Intercorrelación para Satélite:',num2str(SAT(k))));
    xlabel('m'); ylabel('r°_x_s[m]');
end