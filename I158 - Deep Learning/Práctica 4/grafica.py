import numpy as np
from matplotlib import pyplot as plt
import mpl_toolkits.mplot3d.axes3d as p3  
from matplotlib import gridspec
import math

def dibuPtos(nroFig, entradas, salida, titulos):
    if (entradas.shape[1]==2):
        plt.figure(nroFig)
        
        plt.axis([min(entradas[:,0])-0.05, max(entradas[:,0])+0.05,min(entradas[:,1])-0.05, max(entradas[:,1])+0.05])
        plt.setp(plt.gca(), autoscale_on=False)
        #plt.hold(True)
        
        clases=np.unique(salida)
        if len(clases)==2:
            plt.plot(entradas[salida==min(clases),0], entradas[salida==min(clases),1], 'bo')
            plt.plot(entradas[salida==max(clases),0], entradas[salida==max(clases),1], 'ro')
        else:
            plt.plot(entradas[:,0], entradas[:,1], 'ro')
        if (len(titulos)==2):
            plt.xlabel(titulos[0])
            plt.ylabel(titulos[1])
        
def dibuRecta(entradas, W, b, ph=0):
    if (entradas.shape[1]==2):
        if (ph!=0):
            for p in ph:
                p.remove()
        
        X = np.array([min(entradas[:,0]), max(entradas[:,0])])
        Y = (-1)*(W[0]/W[1])*X - (b/W[1])
        ph = plt.plot(X,np.squeeze(np.asarray(Y)),color="green", linewidth=3.0)
        plt.draw()
        plt.pause(.001)
        return(ph)

def dibu2Rectas(entradas, W, b, ph=0):
    if (entradas.shape[1]==2):
        n = 2
        if (ph!=0):
            for r in range(n):
                for p in ph[r]:  #borramos la recta r
                    p.remove()
        X = np.array([min(entradas[:,0]), max(entradas[:,0])])
     
        ph = []
        for r in range(n):
            Y = (-1)*(W[r,0]/W[r,1])*X - (b[r]/W[r,1])
            ph.append(plt.plot(X,np.squeeze(np.asarray(Y))))
                        
        plt.show()
        plt.pause(0.00000001)
        return(ph)
            
    
def dibuPtosColor(nroFig, entradas, nroColor, titulos, centros, ph=0):
    if (entradas.shape[1]==2):
        if (ph!=0):
            for p in ph:
                p.remove()
        plt.figure(nroFig)
        minE = np.min(entradas,axis=0)
        minC = np.min(centros,axis=0) 
        maxE = np.max(entradas,axis=0)
        maxC = np.max(centros,axis=0) 
        
        plt.axis([min(minE[0], minC[0])-0.05, max(maxE[0], maxC[0])+0.05,min(minE[1], minC[1])-0.05, max(maxE[1], maxC[1])+0.05])
        plt.setp(plt.gca(), autoscale_on=False)
        #plt.hold(True)
        
        colores = "bkgcy"
        N = len(colores)
        
        for c in range(N):
            (plt.plot(entradas[nroColor==c,0], entradas[nroColor==c,1], 
                      color=colores[c], marker='o', linestyle="",markersize=3))
            
        ph = plt.plot(centros[:,0], centros[:,1], 'r*', markersize=12)    
                
        plt.xlabel(titulos[0])
        plt.ylabel(titulos[1])
        plt.draw()
        plt.pause(.001)
        return(ph)
        
def calcularFuncion(nroFuncion=1):
    if (nroFuncion==1):
        # Paraboloide 3x^2+y^2 entre -2 y 2
        X = np.linspace(-2,2,num=20)
        Y = np.linspace(-2,2,num=20)
        X, Y = np.meshgrid(X,Y)
        Z = 3 * X**2 +  Y**2
        etiquetas = ['X', 'Y', 'z=3x^2+y^2']
        
    elif (nroFuncion==2):
        X = np.linspace(-2,2,num=20)
        Y = np.linspace(-2,2,num=20)
        X, Y = np.meshgrid(X,Y)
        Z = (X**2 * Y * math.pi)/3 
        etiquetas = ['Radio', 'Altura', 'Volumen']
        
    elif (nroFuncion==3):
        # z = -3/(x^2 + y^2 + 1)
        X = np.linspace(-2.5,2.5,num=20)
        Y = np.linspace(-2.5,2.5,num=20)
        X, Y = np.meshgrid(X,Y)
        Z = (-3) / (X**2 +  Y**2 + 1)  
        etiquetas = ['X', 'Y', 'z=-3/(x^2 + y^2 + 1)']
        
    else:    
        # Error Cuadrático
        X = np.linspace(-11,9,num=20)
        Y = np.linspace(-8,12,num=20)
        X, Y = np.meshgrid(X,Y)
        Z = (1/3)*((3-2*Y-X)**2+(1-Y-X)**2+(-3+Y-X)**2);
        etiquetas = ['w0', 'w1', 'Error'];
         
    return([X,Y,Z,etiquetas])
        
def graficoGradiente(nroFuncion=1):
    [X,Y,Z,etiquetas] = calcularFuncion(nroFuncion)

    fig = plt.figure(figsize=plt.figaspect(0.5))
    #fig=plt.figure(figsize=(7, 3))
    #gs = gridspec.GridSpec(6,11)
    
    ax = fig.add_subplot(1, 2, 1, projection='3d')
    #ax = fig.add_subplot(gs[:,0:6], projection='3d')
    ax.plot_wireframe(X,Y,Z)
    ax.set_xlabel('radio')
    ax.set_ylabel('altura')
    ax.set_zlabel('volumen');
    
    ax1 = fig.add_subplot(1, 2, 2)
    #ax = fig.add_subplot(gs[1:4,8:11])
    pr, ph = np.gradient(Z,0.05,0.05)
    ax1.contour(X,Y,Z,20)
    ax1.quiver(X,Y,pr,ph)
    ax1.set_xlabel('radio')
    ax1.set_ylabel('altura')
    xPos = ax1.get_xlim()
    yPos = ax1.get_ylim()
    aux = plt.text((xPos[1]+xPos[0])*0.5,(yPos[1]+yPos[0])*0.5,'*CLICK AQUI*', horizontalalignment='center',fontsize = 9);
    
    plt.show()
    coord = plt.ginput(1);
    aux.remove()

    return([coord[0][0], coord[0][1], [ax, ax1]])    

def graficarPaso(PtoAnt, PtoAct, h):
    ax = h[0]
    ax1 = h[1]
    
    XL = ax.get_xlim()
    YL = ax.get_ylim()
    ZL = ax.get_zlim()
    
    menores = [XL[0], YL[0], ZL[0]]
    mayores = [XL[1], YL[1], ZL[1]]
    
    PtoAct1 = np.max(np.matrix([PtoAct,  menores]),axis=0).tolist()[0]
    PtoAct1 = np.min(np.matrix([PtoAct1, mayores]),axis=0).tolist()[0]
    
    PtoAnt1 = np.max(np.matrix([PtoAnt,  menores]),axis=0).tolist()[0]
    PtoAnt1 = np.min(np.matrix([PtoAnt1, mayores]),axis=0).tolist()[0]
        
    ax.plot3D([PtoAnt1[0],PtoAct1[0]],[PtoAnt1[1],PtoAct1[1]],[PtoAnt1[2],PtoAct1[2]],color='r',lw=1, ls='-', marker='o', markersize=2)
    
    XL = ax1.get_xlim()
    YL = ax1.get_ylim()
    
    menores = [XL[0], YL[0]]
    mayores = [XL[1], YL[1]]
        
    PtoAct2 = np.max(np.matrix([PtoAct[:2],  menores]),axis=0).tolist()[0]
    PtoAct2 = np.min(np.matrix([PtoAct2[:2], mayores]),axis=0).tolist()[0]
    
    PtoAnt2 = np.max(np.matrix([PtoAnt[:2],  menores]),axis=0).tolist()[0]
    PtoAnt2 = np.min(np.matrix([PtoAnt2[:2], mayores]),axis=0).tolist()[0]
    
    ax1.plot([PtoAnt2[0],PtoAct2[0]],[PtoAnt2[1],PtoAct2[1]],color='r',lw=1, ls='-', marker='o', markersize=2)
    
    
# ===== Neurona no lineal =====
def evaluar(FUN, x):
    if (FUN=='tansig'):
        return (2.0 / (1+np.exp(np.dot(-2,x))) - 1)
    elif (FUN=='logsig'):
        return (1.0/(1+np.exp(np.dot(-1,x))))
    elif (FUN=='relu'):
        return (x*((x>0)*1))
    else:
        return(x)
    
def evaluarDerivada(FUN,x):
    if (FUN=='tansig'):
        return (1-x**2)
    elif (FUN=='logsig'):
        #return (x*(1+np.dot(-1,x)))
        return (x*(1-x))
    elif (FUN=='relu'):
        return ((x>0)*1)
    else:
        return(1)
    
def graficarFuncionActivacion(ptos, T2, W, b, FUN, ph=0, h=0):
    if (len(np.unique(T2))!=2) or (len(np.shape(T2))>1) or (len(np.shape(ptos))!=2):
        print('ERROR en los parámetros de entrada') 
    else:
        clases = np.unique(T2)
        
        if (h==0):
            fig = plt.figure(figsize=plt.figaspect(0.5))
        
            ax = fig.add_subplot(1, 2, 1)
            ax1 = fig.add_subplot(1, 2, 2, projection='3d')
        else:
            ax = h[0]
            ax1= h[1]
            ph2D = ph[0]
            ph3D = ph[1]

        ax.set_xlim(-2,4)
        ax.set_ylim(-1,6)
        
        ax.plot(ptos[T2==min(clases),0], ptos[T2==min(clases),1], 'bo')
        ax.plot(ptos[T2==max(clases),0], ptos[T2==max(clases),1], 'ro')
                
        ax.set_xlabel('X')
        ax.set_ylabel('Y')
        
        if (ph!=0):
            for p in ph2D:
                p.remove()
            
                
                
        x = np.array([min(ptos[:,0]), max(ptos[:,0])])
        y = (-1)*(W[0]/W[1])*x - (b/W[1])
        ph2D = ax.plot(x,np.squeeze(np.asarray(y)))
        #        return(ph)

        if (ph!=0):
            ax1.collections.remove(ph3D)        #ax1 = fig.add_subplot(1, 2, 2, projection='3d')
        X = np.linspace(-2,4,num=10)
        Y = np.linspace(-1,5,num=10)
        X, Y = np.meshgrid(X,Y)
        neta =  W[0]*X + W[1]*Y + b 
        Z = evaluar(FUN,neta)   #(2.0 / (1+np.exp(-2*neta))) - 1
        
        ph3D = ax1.plot_wireframe(X,Y,Z)
        ax1.set_xlabel('X')
        ax1.set_ylabel('Y')
        
        #ax1.plot3D(ptos[T2==-1,0], ptos[T2==-1,1], (-1)*np.ones(sum(T2==-1)),color='r',lw=1, ls='', marker='o', markersize=4)
        #ax1.plot3D(ptos[T2==1,0], ptos[T2==1,1], np.ones(sum(T2==-1)),color='b',lw=1, ls='', marker='o', markersize=4)
        ax1.plot3D(ptos[:,0], ptos[:,1], T2,color='r',lw=1, ls='', marker='o', markersize=4)        
    
        #plt.draw()
        plt.pause(.001)
        
        return([ph2D, ph3D] , [ax, ax1])
    
