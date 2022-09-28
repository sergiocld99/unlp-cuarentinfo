import numpy as np
from matplotlib import pyplot as plt
from grafica import *

def evaluar(FUN, x):
    if (FUN=='tansig'):
        return (2.0 / (1+np.exp(-2*x)) - 1)
    elif (FUN=='logsig'):
        return (1.0/(1+np.exp(-x)))
    else:
        return(x)
    
def evaluarDerivada(FUN,x):
    if (FUN=='tansig'):
        return (1-x**2)
    elif (FUN=='logsig'):
        #return (x*(1+np.dot(-1,x)))
        return (x*(1-x))
    else:
        return(1)
        
def entrena_Perceptron(X,T,alfa, MAX_ITE,dibuja=0, titulos=[]):
    #X es una matriz que tiene los ejemplos por fila
    nCantEjemplos = X.shape[0]  # nro. de filas
    entradas = X.shape[1]         #nro. de columnas
    
    # Inicializar la recta
    W = np.random.uniform(-0.5,0.5,[1, entradas])
    b = np.random.uniform(-0.5, 0.5)
    
    if dibuja: # graficar
        dibuPtos(1, X, T, titulos)
        ph = dibuRecta(X, W[0], b)
    
    hubo_cambio=True
    ite=0
    while (hubo_cambio and (ite<MAX_ITE)):
        hubo_cambio=False
        ite = ite + 1
        print(ite)
        # para cada ejemplo
        for i in range(nCantEjemplos):
            # Calcular la salida
            neta= W @ X[i:i+1,:].T + b
            Y = 1 * (neta>0)
    
            # Si no es correcta, corregir W y b  
            if not(Y==T[i]):
                hubo_cambio=True
                #    actualizamos los pesos W y b
                W = W + alfa * (T[i]-Y)*X[i:i+1,:]
                b = b + alfa *(T[i]-Y)
            
        if dibuja: # graficar la recta
            ph = dibuRecta(X, W[0], b, ph)
    return(W,b)

def entrena_NeuronaLineal(X,T,alfa, MAX_ITE, CotaError=0.001, dibuja=0, titulos=[]):
    #X es una matriz que tiene los ejemplos por fila
    nCantEjemplos = X.shape[0]  # nro. de filas
    entradas = X.shape[1]         #nro. de columnas
    
    # Inicializar la recta
    W = np.random.uniform(-0.5,0.5,[1, entradas])
    b = np.random.uniform(-0.5, 0.5)
    
    if dibuja: # graficar
        print("dibujando")
        dibuPtos(1, X, T, titulos)
        ph = dibuRecta(X, W[0], b)
        print("fin dibujo")
    
    ite=0
    ErrorAnt = 1000
    ErrorAVG = np.mean(((W @ X.T + b)-T)**2)
    while (ite<MAX_ITE) and (abs(ErrorAVG-ErrorAnt) > CotaError):
        # para cada ejemplo
        ErrorAnt = ErrorAVG
        for i in range(nCantEjemplos):
            # Calcular la salida
            neta= np.dot(W,X[i,:]) + b
            Y = neta
    
            gradiente_W =  (T[i]-Y) *  X[i,:] 
            gradiente_b =  (T[i]-Y) 
            #    actualizamos los pesos W y b
            W = W + alfa * gradiente_W 
            b = b + alfa * gradiente_b 
            
                        
        ErrorAVG = np.mean(((W @ X.T + b)-T)**2)    
        ite = ite + 1
        print(ite, ErrorAVG)
        
        if dibuja: # graficar la recta
            ph = dibuRecta(X, W[0], b, ph)
    return(W,b,ite)


def entrena_NeuronaNoLineal(X,T,alfa, MAX_ITE,FunO, CotaError=0.001, beta=0, dibuja=0, titulos=[]):
    #X es una matriz que tiene los ejemplos por fila
    nCantEjemplos = X.shape[0]  # nro. de filas
    entradas = X.shape[1]         #nro. de columnas
    
    # Inicializar la recta
    W = np.random.uniform(-0.5,0.5,[1, entradas])
    b = np.random.uniform(-0.5, 0.5)
    
    W_m = np.zeros([1,entradas])
    b_m = 0
    
    if dibuja: # graficar
        dibuPtos(1, X, T, titulos)
        ph = dibuRecta(X, W[0], b)
    
    ite=0
    ErrorAVG = np.mean((evaluar(FunO, W @ X.T + b)-T)**2)
    while (ite<MAX_ITE) and (ErrorAVG > CotaError):
        # para cada ejemplo
        for i in range(nCantEjemplos):
            # Calcular la salida
            neta= np.dot(W,X[i,:]) + b
            Y = evaluar(FunO, neta)
    
            gradiente_W =  (T[i]-Y) * evaluarDerivada(FunO,Y) * X[i,:] 
            gradiente_b =  (T[i]-Y) * evaluarDerivada(FunO,Y)
            #    actualizamos los pesos W y b
            W = W + alfa * gradiente_W + beta * W_m
            b = b + alfa * gradiente_b + beta * b_m
            
            W_m = alfa * gradiente_W + beta * W_m
            b_m = alfa * gradiente_b + beta * b_m
            
        ErrorAVG = np.mean((evaluar(FunO, W @ X.T + b)-T)**2)    
        ite = ite + 1
        print(ite, ErrorAVG)
        
        if dibuja: # graficar la recta
            ph = dibuRecta(X, W[0], b, ph)
    return(W,b)
        

def BPN_entrena(P,T,FunH, FunO, ocultas, alfa=0.05, MAX_ITE = 200, CotaError=0.01, beta=0):
    mezcla = np.random.permutation(len(P))
    P = P[mezcla,:]
    T = T[mezcla,:]
    
    entradas = P.shape[1]
    salidas = T.shape[1]
    
    W1 = np.random.uniform(-0.5,0.5,[ocultas, entradas])
    b1 = np.random.uniform(-0.5,0.5, [ocultas,1])
    W2 = np.random.uniform(-0.5,0.5,[salidas, ocultas])
    b2 = np.random.uniform(-0.5,0.5, [salidas,1])
    
    NetasH = W1 @ P.T + b1
    SalidasH = evaluar(FunH, NetasH)
    NetasO = W2 @ SalidasH + b2
    SalidasO = evaluar(FunO, NetasO)
    totalError = np.mean((T.T - SalidasO)**2)

    W1_m = np.zeros([ocultas, entradas])
    b1_m = np.zeros([ocultas,1])
    W2_m = np.zeros([salidas, ocultas])
    b2_m = np.zeros([salidas,1])
    
    ite = 0
    while (ite < MAX_ITE) and (totalError > CotaError):
         
        for p in range(len(P)):
            # propagar el ejemplo hacia adelante
            netasH = W1 @ P[p:p+1,:].T + b1
            salidasH = evaluar(FunH, netasH)
            netasO = W2 @ salidasH + b2
            salidasO = evaluar(FunO, netasO)
    
            # calcular los errores en ambas capas        
            ErrorSalida = T[p:p+1,:].T-salidasO
            deltaO = ErrorSalida * evaluarDerivada(FunO,salidasO)
            deltaH = evaluarDerivada(FunH,salidasH)*(W2.T @ deltaO)
    
            # corregir los todos los pesos      
            W1 = W1 + alfa * deltaH @ P[p:p+1,:] + beta * W1_m
            b1 = b1 + alfa * deltaH + beta * b1_m
            W2 = W2 + alfa * deltaO @ salidasH.T + beta * W2_m
            b2 = b2 + alfa * deltaO + beta * b2_m
            
            W1_m = alfa * deltaH @ P[p:p+1,:] + beta * W1_m
            b1_m = alfa * deltaH + beta * b1_m
            W2_m = alfa * deltaO @ salidasH.T + beta * W2_m
            b2_m = alfa * deltaO + beta * b2_m            
           
        ite = ite + 1
        
        NetasH = W1 @ P.T + b1
        SalidasH = evaluar(FunH, NetasH)
        NetasO = W2 @ SalidasH + b2
        SalidasO = evaluar(FunO, NetasO)
        totalError = np.mean((T.T - SalidasO)**2)
    
        #if (ite % 50)==0:
        print(ite, totalError)
    return(W1,b1,W2,b2,ite)

def BPN_forward(P,W1,b1,W2,b2,FunH, FunO):
    NetasH = W1 @ P.T + b1
    SalidasH = evaluar(FunH, NetasH)
    NetasO = W2 @ SalidasH + b2
    SalidasO = evaluar(FunO, NetasO)
    return(SalidasO)
    
def BPN_umbral(SalidasO, FunO):
    
    if (FunO == 'tansig'):
        salidaBinaria = 1 * (SalidasO>0)
        return(2 * salidaBinaria - 1)
       
    elif (FunO == 'logsig'):
        salidaBinaria = 1 * (SalidasO>0.5)
        
        return(salidaBinaria)
    else:
        return (SalidasO)    

def BPN_predice(P,W1,b1,W2,b2,FunH, FunO):
    
    SalidasO = BPN_forward(P,W1,b1,W2,b2,FunH, FunO)
    
    return(BPN_umbral(SalidasO,FunO)) 
    

