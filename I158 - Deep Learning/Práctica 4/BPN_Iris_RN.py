import numpy as np
import pandas as pd
import grafica as gr
import RN_feedforward as rn
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
        X, Y, test_size=0.30, random_state=42)

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

#=====  Calcular el error actual =====
FunH = 'logsig'
FunO = 'tansig'
if (FunO == 'tansig'):
    Y_trainB = 2 * Y_trainB - 1

alfa = 0.1
CotaError = 1.0e-15
MAX_ITE = 800
ite = 0

(W1,b1,W2,b2,ite) = rn.BPN_entrena(X_train,Y_trainB,FunH, FunO, ocultas, alfa, MAX_ITE, CotaError)
    
y_pred = rn.BPN_predice(X_train,W1,b1,W2,b2,FunH, FunO)    
Y_pred = np.argmax(y_pred,axis=1)
#metrics.accuracy_score(y_test,evaluar(FUN,pred_test))

print("%% aciertos X_train : %.3f" % metrics.accuracy_score(Y_train,Y_pred.T))

# report = metrics.classification_report(Y_train,Y_pred)
# print("Confusion matrix:\n%s" % report)
# MM = metrics.confusion_matrix(Y_train,Y_pred)
# print("Confusion matrix:\n%s" % MM) 
        
#--- aplicando la red a los datos de testeo ---
y_predTest = rn.BPN_predice(X_test,W1,b1,W2,b2,FunH, FunO)    
Y_predTest = np.argmax(y_predTest,axis=1)

print("%% aciertos X_test : %.3f" % metrics.accuracy_score(Y_test,Y_predTest.T))

# report = metrics.classification_report(Y_test,Y_predTest)
# print("Confusion matrix:\n%s" % report)
# MM = metrics.confusion_matrix(Y_test,Y_predTest)
# print("Confusion matrix:\n%s" % MM) 


