%% Esta función calcula la correlación circular entre 2 señales: x e y.          
function r = correlfft(x, y)

%% Se refleja circularmente la señal y
y_2 = [y(1) y(end:-1:2)];

%% Se aplica el conjugado a cada valor de la señal reflejada
y_2 = conj(y_2);

%% Se calcula la correlación circular
Tx = fft(x);                % TDF de "x"
Ty = fft(y_2);              % TDF de "y" conjugada y reflejada circ.
r = real(ifft(Tx.*Ty));     % Se descarta la componente imaginaria

%% [EOF]