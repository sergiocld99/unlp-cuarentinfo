import numpy as np
import pandas as pd
import grafica as gr
from sklearn import metrics

X = np.array([ [-1, -1], [-1, 1], [1, -1], [1, 1]])
Y = np.array([-1, 1, 1, -1]).reshape(len(X),1)

entradas = X.shape[1]
ocultas = 2
salidas = Y.shape[1]

W1 = np.random.uniform(-0.5,0.5,[ocultas, entradas])
b1 = np.random.uniform(-0.5,0.5, [ocultas,1])
W2 = np.random.uniform(-0.5,0.5,[salidas, ocultas])
b2 = np.random.uniform(-0.5,0.5, [salidas,1])

gr.dibuPtos(1, X, Y.T[0], ['x1', 'x2'])
ph = gr.dibu2Rectas(X, W1, b1.T[0])

#=====  Calcular el error actual =====
FunH = 'logsig'
FunO = 'tansig'

# --- Calcular la rta.de la red para TODOS los ejemplos ---
NetasH = W1 @ X.T + b1

# cantEj = P.shape[0]
# netasH = np.zeros([ocultas, cantEj])
# for i in range(cantEj):
#     for o in range(ocultas):
#         netasH[o,i]=b1[o]
#         for e in range(entradas):
#             netasH[o,i] = netasH[o,i] + W1[o,e]*P[i,e]
            
SalidasH = gr.evaluar(FunH, NetasH)
NetasO = W2 @ SalidasH + b2
SalidasO = gr.evaluar(FunO, NetasO)

# -- calcular el error --
AVGError = np.mean((Y.T - SalidasO)**2)

alfa = 0.15
CotaError = 1.0e-15
MAX_ITERA = 1500
ite = 0
errorAnt = 1
while ( abs(AVGError-errorAnt) > CotaError ) and ( ite < MAX_ITERA ):
    errorAnt = AVGError
    for p in range(len(X)):   #para cada ejemplo
        # propagar el ejemplo hacia adelante
        netasH = W1 @ X[p:p+1,:].T + b1
        salidasH = gr.evaluar(FunH, netasH)
        netasO = W2 @ salidasH + b2
        salidasO = gr.evaluar(FunO, netasO)

        # calcular los errores en ambas capas        
        ErrorSalida = Y[p:p+1,:].T-salidasO
        deltaO = ErrorSalida * gr.evaluarDerivada(FunO,salidasO)
        deltaH = gr.evaluarDerivada(FunH,salidasH)*(W2.T @ deltaO)

        # corregir todos los pesos      
        W1 = W1 + alfa * deltaH @ X[p:p+1,:] 
        b1 = b1 + alfa * deltaH 
        W2 = W2 + alfa * deltaO @ salidasH.T 
        b2 = b2 + alfa * deltaO 
        
        
    # Recalcular AVGError
    NetasH = W1 @ X.T + b1
    SalidasH = gr.evaluar(FunH, NetasH)
    NetasO = W2 @ SalidasH + b2
    SalidasO = gr.evaluar(FunO, NetasO)
    AVGError = np.mean((Y.T - SalidasO)**2)
    
    ite = ite + 1
    print("ite = %3d   error = %.5f" % (ite, abs(AVGError-errorAnt)))
    # Graficar las rectas
    if (ite % 10) ==0:
        ph = gr.dibu2Rectas(X, W1, b1.T[0], ph)
     
y_pred = 2*((SalidasO>0) * 1)-1

#metrics.accuracy_score(y_test,evaluar(FUN,pred_test))

report = metrics.classification_report(Y.reshape(1,-1)[0],y_pred[0])
print("Confusion matrix:\n%s" % report)
MM = metrics.confusion_matrix(Y.reshape(1,-1)[0],y_pred[0])
print("Confusion matrix:\n%s" % MM) 
        



