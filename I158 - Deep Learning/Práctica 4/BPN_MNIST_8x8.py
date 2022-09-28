import pandas as pd
import numpy as np
import grafica as gr
import matplotlib.pyplot as plt
from sklearn import preprocessing, metrics

import RN_feedforward as rn


df_train = pd.read_csv('../Datos/optdigits_train.csv')
X_train = np.array(df_train.iloc[:,:-1]).astype(float)
Y_train = np.array(df_train.iloc[:,-1])

df_test = pd.read_csv('../Datos/optdigits_test.csv')
X_test = np.array(df_test.iloc[:,:-1]).astype(float)
Y_test = np.array(df_test.iloc[:,-1])

#-- la red tendrá una salida para cada dígito
salidas = 10

Y_trainB = np.zeros((len(Y_train), salidas))
for o in range(len(Y_train)):
    Y_trainB[o, Y_train[o]]=1
    
Y_testB = np.zeros((len(Y_test), salidas))
for o in range(len(Y_test)):
    Y_testB[o, Y_test[o]]=1
    
normalizarEntrada = 1  # 1 si normaliza; 0 si no

if normalizarEntrada:
    # Escala los valores entre 0 y 1
    min_max_scaler = preprocessing.MinMaxScaler(feature_range=(-1,1))
    X_train = min_max_scaler.fit_transform(X_train)
    X_test = min_max_scaler.transform(X_test)


entradas = X_train.shape[1]
ocultas = 15
salidas = Y_trainB.shape[1]

W1 = np.random.uniform(-0.5,0.5,[ocultas, entradas])
b1 = np.random.uniform(-0.5,0.5, [ocultas,1])
W2 = np.random.uniform(-0.5,0.5,[salidas, ocultas])
b2 = np.random.uniform(-0.5,0.5, [salidas,1])

#=====  Calcular el error actual =====
FunH = 'logsig'
FunO = 'logsig'
if (FunO == 'tansig'):
    Y_trainB = 2 * Y_trainB - 1
    Y_testB = 2 * Y_testB - 1
    
# --- Calcular la rta.de la red para TODOS los ejemplos ---
# NetasH = W1 @ X_train.T + b1
# SalidasH = gr.evaluar(FunH, NetasH)
# NetasO = W2 @ SalidasH + b2
# SalidasO = gr.evaluar(FunO, NetasO)

# -- calcular el error --
SalidasO = rn.BPN_forward(X_train,W1,b1,W2,b2,FunH, FunO) # sin FunO la salida es lineal
AVGError = np.mean((Y_trainB.T - SalidasO)**2)

alfa = 0.01
CotaError = 1.0e-15
MAX_ITERA = 200
ite = 0
errorAnt = 1

errorTrain = []
errorTrain_aprox = []
errorTest  = []
okTest = []
okTrain = []

while ( abs(AVGError-errorAnt) > CotaError ) and ( ite < MAX_ITERA ):
    errorAnt = AVGError
    sumaAVG = np.zeros((salidas,1))
    for p in range(len(X_train)):   #para cada ejemplo
        # propagar el ejemplo hacia adelante
        netasH = W1 @ X_train[p:p+1,:].T + b1
        salidasH = gr.evaluar(FunH, netasH)
        netasO = W2 @ salidasH + b2
        salidasO = gr.evaluar(FunO, netasO)

        # calcular los errores en ambas capas        
        ErrorSalida = Y_trainB[p:p+1,:].T-salidasO
        deltaO = ErrorSalida * gr.evaluarDerivada(FunO,salidasO)
        deltaH = gr.evaluarDerivada(FunH,salidasH)*(W2.T @ deltaO)

        # corregir todos los pesos      
        W1 = W1 + alfa * deltaH @ X_train[p:p+1,:] 
        b1 = b1 + alfa * deltaH 
        W2 = W2 + alfa * deltaO @ salidasH.T 
        b2 = b2 + alfa * deltaO 
        
        sumaAVG = sumaAVG + ErrorSalida**2
        
    #-- Error sobre los datos de entrenamiento aproximada ---    
    AVGError = sum(sumaAVG / salidas)  
    errorTrain_aprox.append(AVGError)

    SalidasO = rn.BPN_forward(X_train, W1, b1, W2, b2, FunH, FunO)
    avgError = np.mean((Y_trainB.T - SalidasO)**2)
    errorTrain.append(avgError)
    Y_predB = rn.BPN_umbral(SalidasO, FunO)
    Y_pred = np.argmax(Y_predB,axis=0)
    
    #Y_pred = np.argmax(rn.BPN_predice(X_train, W1, b1, W2, b2, FunH, FunO),axis=0)
    okTrain.append(metrics.accuracy_score(Y_train, Y_pred))
    
    # -- calcular el error sobre los ejemplos de testeo ---
    SalidasO = rn.BPN_forward(X_test, W1, b1, W2, b2, FunH, FunO)
    AVGErrorTest = np.mean((Y_testB.T - SalidasO)**2)
    errorTest.append(AVGErrorTest)
    Y_predTest = np.argmax(rn.BPN_umbral(SalidasO, FunO),axis=0)
    okTest.append(metrics.accuracy_score(Y_test, Y_predTest))
        
    ite = ite + 1
    print("ite = %3d   error = %.8f" % (ite, abs(AVGError-errorAnt)))
 
# -- Recalcular AVGError --
y_pred = rn.BPN_predice(X_train, W1, b1, W2, b2, FunH, FunO)    
Y_pred = np.argmax(y_pred,axis=0)
#metrics.accuracy_score(y_test,evaluar(FUN,pred_test))

print("%% aciertos X_train : %.3f" % metrics.accuracy_score(Y_train,Y_pred))
        
report = metrics.classification_report(Y_train,Y_pred)
print("Confusion matrix:\n%s" % report)
MM = metrics.confusion_matrix(Y_train,Y_pred)
print("Confusion matrix:\n%s" % MM) 

#--- aplicando la red a los datos de testeo ---
y_predTest = rn.BPN_predice(X_test, W1, b1, W2, b2, FunH, FunO)      
Y_predTest = np.argmax(y_predTest,axis=0)

print("%% aciertos X_test : %.3f" % metrics.accuracy_score(Y_test,Y_predTest))

report = metrics.classification_report(Y_test,Y_predTest)
print("Confusion matrix:\n%s" % report)
MM = metrics.confusion_matrix(Y_test,Y_predTest)
print("Confusion matrix:\n%s" % MM) 

plt.figure()
plt.plot(np.arange(0,ite), errorTrain, "-b", label="Train")
plt.plot(np.arange(0,ite), errorTest, "-r", label="Test")
plt.legend(loc="upper right")
plt.show()

plt.figure()
plt.plot(np.arange(0,ite), okTrain, "-b", label="Train")
plt.plot(np.arange(0,ite), okTest, "-r", label="Test")
#plt.legend(loc="upper left")
plt.legend(loc="lower right")
# plt.ylim(0.0,1.0)
plt.show()

