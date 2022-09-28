import numpy as np
from sklearn import preprocessing, metrics, model_selection
import pandas as pd
import RN_feedforward as rn

datos = pd.read_csv("DrugY.csv")
mapeo = {"Drug": {"drugY":0, "drugX":1, "drugA":2, "drugB":3, "drugC":4}}
datos.replace(mapeo, inplace=True)

# Tomamos todas las columnas menos la última
entradas = datos.iloc[:,:-1]
# convirtiendo los atributos nominales en numericos
entradasNumericas = pd.get_dummies(entradas)
# convirtiendo la tabla en arreglo
entradas = np.array(entradasNumericas)

salidas = np.array(datos.iloc[:,-1])
nDrogas = 5

#--- CONJUNTOS DE ENTRENAMIENTO Y TESTEO ---
X_train, X_test, T_train, T_test = model_selection.train_test_split( \
        entradas, salidas, test_size=0.30, random_state=42)

T = np.zeros((len(T_train), nDrogas))
for o in range(len(T_train)):
    T[o, T_train[o]]=1


normalizarEntrada = 1  # 1 si normaliza; 0 si no

if normalizarEntrada:
    # Escala los valores entre 0 y 1
    min_max_scaler = preprocessing.MinMaxScaler()
    X_train = min_max_scaler.fit_transform(X_train)
    X_test = min_max_scaler.transform(X_test)
    
    
FunH = 'logsig'
FunO = 'tansig'

if (FunO=='tansig'):
    T = 2*T-1

alfa = 0.15
CotaError = 1.0e-09
MAX_ITE = 700
ocultas = 4

[W1,b1,W2,b2,ite] = rn.BPN_entrena(X_train,T,FunH, FunO, ocultas, alfa, MAX_ITE, CotaError)

# NetasH = W1 @ X_train.T + b1
# SalidasH = evaluar(FunH, NetasH)
# NetasO = W2 @ SalidasH + b2
# SalidasO = evaluar(FunO, NetasO)

y_predTrain = rn.BPN_predice(X_train,W1,b1,W2,b2,FunH, FunO)
y_pred = np.argmax(y_predTrain,axis=1)

report = metrics.classification_report(T_train,y_pred)
print("Confusion matrix:\n%s" % report)
MM = metrics.confusion_matrix(T_train,y_pred)
print("Confusion matrix:\n%s" % MM) 

y_predTest = rn.BPN_predice(X_test,W1,b1,W2,b2,FunH, FunO)
y_pred = np.argmax(y_predTest,axis=1)

report = metrics.classification_report(T_test,y_pred)
print("Confusion matrix TEST:\n%s" % report)
MM = metrics.confusion_matrix(T_test,y_pred)
print("Confusion matrix TEST:\n%s" % MM) 