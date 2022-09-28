import numpy as np
import pandas as pd
import grafica as gr
from sklearn import preprocessing, metrics, model_selection

df = pd.read_csv('../Datos/Iris.csv')

X = np.array(df.iloc[:, 0:4])
nEj = X.shape[0]

nomClases = pd.unique(df['class'])
#-- la red tendrá una salida para cada tipo de flor
salidas = len(nomClases)

#-- la salida debe ser numérica --
clase = df['class']
Y=np.zeros(nEj)
for s in range(nEj):
    Y[s]=np.argwhere(nomClases == clase[s])
Y = Y.astype(int)    

#--- CONJUNTOS DE ENTRENAMIENTO Y TESTEO ---
X_train, X_test, Y_train, Y_test = model_selection.train_test_split( \
        X, Y, test_size=0.30) #, random_state=42)

Y_trainB = np.zeros((len(Y_train), salidas))
for o in range(len(Y_train)):
    Y_trainB[o, Y_train[o]]=1

normalizarEntrada = 1  # 1 si normaliza; 0 si no

if normalizarEntrada:
    # Escala los valores entre 0 y 1
    min_max_scaler = preprocessing.MinMaxScaler()
    X_train = min_max_scaler.fit_transform(X_train)
    X_test = min_max_scaler.transform(X_test)


entradas = X_train.shape[1]
ocultas = 2
salidas = Y_trainB.shape[1]

W1 = np.random.uniform(-0.5,0.5,[ocultas, entradas])
b1 = np.random.uniform(-0.5,0.5, [ocultas,1])
W2 = np.random.uniform(-0.5,0.5,[salidas, ocultas])
b2 = np.random.uniform(-0.5,0.5, [salidas,1])

#=====  Calcular el error actual =====
FunH = 'logsig'
FunO = 'tansig'
if (FunO == 'tansig'):
    Y_trainB = 2 * Y_trainB - 1

# --- Calcular la rta.de la red para TODOS los ejemplos ---
NetasH = W1 @ X_train.T + b1

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
AVGError = np.mean((Y_trainB.T - SalidasO)**2)

alfa = 0.1
CotaError = 1.0e-15
MAX_ITERA = 800
ite = 0
errorAnt = 1
while ( abs(AVGError-errorAnt) > CotaError ) and ( ite < MAX_ITERA ):
    errorAnt = AVGError
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
       
    # Recalcular AVGError
    NetasH = W1 @ X_train.T + b1
    SalidasH = gr.evaluar(FunH, NetasH)
    NetasO = W2 @ SalidasH + b2
    SalidasO = gr.evaluar(FunO, NetasO)
    AVGError = np.mean((Y_trainB.T - SalidasO)**2)
    
    ite = ite + 1
    print("ite = %3d   error = %.8f" % (ite, abs(AVGError-errorAnt)))
  
if (FunO == 'tansig'):    
    y_pred = 2*((SalidasO>0) * 1)-1
    
if (FunO == 'logsig'):    
    y_pred = (SalidasO>0.5) * 1
    
Y_pred = np.argmax(y_pred,axis=0)
#metrics.accuracy_score(y_test,evaluar(FUN,pred_test))

print("%% aciertos X_train : %.3f" % metrics.accuracy_score(Y_train,Y_pred))
        
report = metrics.classification_report(Y_train,Y_pred)
print("Confusion matrix:\n%s" % report)
MM = metrics.confusion_matrix(Y_train,Y_pred)
print("Confusion matrix:\n%s" % MM) 

#--- aplicando la red a los datos de testeo ---
NetasH = W1 @ X_test.T + b1
SalidasH = gr.evaluar(FunH, NetasH)
NetasO = W2 @ SalidasH + b2
SalidasO = gr.evaluar(FunO, NetasO)

if (FunO == 'tansig'):    
    y_predTest = 2*((SalidasO>0) * 1)-1
    
if (FunO == 'logsig'):    
    y_predTest = (SalidasO>0.5) * 1
    
Y_predTest = np.argmax(y_predTest,axis=0)

print("%% aciertos X_test : %.3f" % metrics.accuracy_score(Y_test,Y_predTest))

report = metrics.classification_report(Y_test,Y_predTest)
print("Confusion matrix:\n%s" % report)
MM = metrics.confusion_matrix(Y_test,Y_predTest)
print("Confusion matrix:\n%s" % MM) 


