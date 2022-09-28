import pandas as pd
import numpy as np
from sklearn import preprocessing, metrics
import RN_feedforward as rn

#--- Leer los ejemplos de entrenamiento y testeo
df_train = pd.read_csv('../Datos/optdigits_train.csv')
X_train = np.array(df_train.iloc[:,:-1]).astype(float)
Y_train = np.array(df_train.iloc[:,-1])

df_test = pd.read_csv('../Datos/optdigits_test.csv')
X_test = np.array(df_test.iloc[:,:-1]).astype(float)
Y_test = np.array(df_test.iloc[:,-1])

# Escala los valores entre 0 y 1
min_max_scaler = preprocessing.MinMaxScaler() 
X_train = min_max_scaler.fit_transform(X_train)
X_test = min_max_scaler.transform(X_test)

#-- la red tendrá una salida para cada dígito
salidas = 10

Y_trainB = np.zeros((len(Y_train), salidas))
for o in range(len(Y_train)):
    Y_trainB[o, Y_train[o]]=1
    
Y_testB = np.zeros((len(Y_test), salidas))
for o in range(len(Y_test)):
    Y_testB[o, Y_test[o]]=1
    
FunH = 'tansig'
FunO = 'tansig'

if (FunO == 'tansig'):
    Y_trainB = 2 * Y_trainB - 1
    Y_testB = 2 * Y_testB - 1
    
alfa = 0.001
CotaError = 1.0e-15
MAX_ITE = 200
ite = 0
ocultas = 15

[W1,b1,W2,b2,ite]=rn.BPN_entrena(X_train,Y_trainB,FunH, FunO, ocultas, alfa, MAX_ITE, CotaError)
     
#-----------------------------------------------------
#--- aplicando la red a los datos de entrenamiento ---
#-----------------------------------------------------
y_pred = rn.BPN_predice(X_train, W1, b1, W2, b2, FunH, FunO)    
Y_pred = np.argmax(y_pred,axis=0)

print("ite = %d  %% aciertos X_train : %.3f" % (ite, metrics.accuracy_score(Y_train,Y_pred)))
        
report = metrics.classification_report(Y_train,Y_pred)
print("Resultado de la clasificación - ENTRENAMIENTO:\n%s" % report)

MM = metrics.confusion_matrix(Y_train,Y_pred)
print("Matriz de confusión TRAIN:\n%s" % MM) 

#----------------------------------------------
#--- aplicando la red a los datos de testeo ---
#----------------------------------------------
y_predTest = rn.BPN_predice(X_test, W1, b1, W2, b2, FunH, FunO)      
Y_predTest = np.argmax(y_predTest,axis=0)

print("%% aciertos X_test : %.3f" % metrics.accuracy_score(Y_test,Y_predTest))

report = metrics.classification_report(Y_test,Y_predTest)
print("Resultado de la clasificación - TESTEO:\n%s" % report)
MM = metrics.confusion_matrix(Y_test,Y_predTest)
print("Matriz de confusión TEST:\n%s" % MM) 



