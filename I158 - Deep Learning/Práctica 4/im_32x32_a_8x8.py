import numpy as np
import matplotlib.pyplot as plt

#-- lee una fuigura de 32x 32 y la reduce a 8x8 --
imagen = plt.imread('../Datos/seis.png')
plt.figure()
plt.imshow(imagen)

imMini = np.zeros((8,8))
for i in range(8):
    for j in range(8):
        imMini[i,j]=16-np.sum(imagen[4*i:4*i+4, 4*j:4*j+4, 0])
plt.figure()        
plt.imshow(imMini, cmap=plt.cm.gray_r)