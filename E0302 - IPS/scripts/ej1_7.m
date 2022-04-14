%% Limpiamos variables y figuras anteriores
clear
clc

%% Declaración de constantes
SAT = [2 8 16];       % Números de los satélites a comparar

%% Cargamos la señal incógnita (s)
load('senial_incognita.mat');

%% Agregamos ruido
xincn = s+7*randn(size(s));

%% Comparamos la señal con ruido con la correspondiente a cada satélite
m = (-2557:1:2557);

for k = 1 : length(SAT)
    % Generación del código de satélite (de 5 periodos)
    x = cacode(SAT(k));
    x = repmat(x,1,5);
    % Cálculo de la intercorrelación con la señal incógnita
    r = correlfft(x, xincn);
    % Se genera la imagen del resultado para el satélite actual
    figure; stem(m, fftshift(r),'.'); title(strcat('Intercorrelación para Satélite:',num2str(SAT(k))));
    xlabel('m'); ylabel('r°_x_s[m]');
end