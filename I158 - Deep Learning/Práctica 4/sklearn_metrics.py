from sklearn import  metrics

Y_train = [0,1,2,3,0,1,2,3,0,1,2,3]
Y_pred  = [0,2,1,3,0,1,2,0,0,1,2,3]

aciertos = metrics.accuracy_score(Y_train,Y_pred)
print("%% accuracy = %.3f" % aciertos)

MM = metrics.confusion_matrix(Y_train,Y_pred)
print("Matriz de confusión:\n%s" % MM)

report = metrics.classification_report(Y_train,Y_pred)
print("Resultado de la clasificación:\n%s" % report)

 