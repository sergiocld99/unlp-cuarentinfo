import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn import preprocessing, metrics, model_selection
from sklearn.neural_network import MLPClassifier
# from sklearn.model_selection import cross_val_score

df = pd.read_csv('../Datos/Sonar.csv')

# Tomamos todas las columnas menos la última
X = np.array(df.iloc[:, 0:-1])
Y = np.array(df.iloc[:,-1])
Y_bin = (Y=='Mine')*1

#--- CONJUNTOS DE ENTRENAMIENTO Y TESTEO ---
X_train, X_test, Y_train, Y_test = model_selection.train_test_split( \
        X,Y_bin, test_size=0.30, random_state=42)

normalizarEntrada = 1  # 1 si normaliza; 0 si no

if normalizarEntrada:
    # Escala los valores entre 0 y 1
    min_max_scaler = preprocessing.StandardScaler()
    X_train = min_max_scaler.fit_transform(X_train)
    X_test = min_max_scaler.transform(X_test)

clf = MLPClassifier(solver='sgd', learning_rate_init=0.05,
                    hidden_layer_sizes=(5,), random_state=1,
                    max_iter=2000, 
                    verbose=50,  tol=1.0e-05,
                    activation='logistic') #'tanh')

clf.fit(X_train,Y_train)

# scores = cross_val_score(clf, X_train, Y_train, cv=10, scoring='accuracy')
# print("AVG accuracy %.4f" % np.mean(scores)) 
Y_pred = clf.predict(X_train)

print("%% aciertos X_train : %.3f" % metrics.accuracy_score(Y_train, Y_pred))
report = metrics.classification_report(Y_train,Y_pred)

print("Confusion matrix:\n%s" % report)
cm = metrics.confusion_matrix(Y_train,Y_pred)
print("Confusion matrix:\n%s" % cm) 

fig=metrics.plot_confusion_matrix(clf, X_train, Y_train) 
plt.title("Matriz de confusión - Datos TRAIN") 
plt.show()
     
Y_predTest= clf.predict(X_test)
print("%% aciertos X_test : %.3f" % metrics.accuracy_score(Y_test,Y_predTest))

report = metrics.classification_report(Y_test,Y_predTest)
print("Confusion matrix:\n%s" % report)

MM = metrics.confusion_matrix(Y_test,Y_predTest)
print("Confusion matrix:\n%s" % MM) 

fig=metrics.plot_confusion_matrix(clf, X_test, Y_test)  
plt.title("Matriz de confusión - Datos TESTEO")
plt.show()

# calculate the fpr and tpr for all thresholds of the classification
# Y_test_Mina = (Y_test=='Mine')*1
# Y_predTest_Mina = (Y_predTest=='Mine')*1
yy = clf.predict_proba(X_test)
yy = yy[:,1]
fpr, tpr, threshold = metrics.roc_curve(Y_test,yy)
roc_auc = metrics.auc(fpr, tpr)

#Generamos un clasificador sin entrenar , que asignará 0 a todo
siempre_0 = [0 for _ in range(len(Y_test))]
ns_fpr, ns_tpr, _ = metrics.roc_curve(Y_test,siempre_0)


# method I: plt
plt.figure()
plt.title('Receiver Operating Characteristic')
plt.plot(fpr, tpr, 'b', label = 'AUC = %0.2f' % roc_auc)
plt.plot(ns_fpr, ns_tpr, linestyle='--', label='Sin entrenar')

plt.legend(loc = 'lower right')
plt.plot([0, 1], [0, 1],'r--')
plt.xlim([0, 1])
plt.ylim([0, 1])
plt.ylabel('True Positive Rate')
plt.xlabel('False Positive Rate')
plt.show()

# # method II: ggplot
# from ggplot import *
# df = pd.DataFrame(dict(fpr = fpr, tpr = tpr))
# ggplot(df, aes(x = 'fpr', y = 'tpr')) + geom_line() + geom_abline(linetype = 'dashed')