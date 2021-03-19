# Apuntes de Clase 2

## Análisis Nodal
Al revisar cuáles son las incógnitas del circuito, encontramos en general que se deben hallar las corrientes y tensiones en resistores, pero debe analizarse también si al mirar conexiones serie y paralelo hay resultados que se repetirán.

Por ejemplo, si una resistor está en **paralelo** con una fuente, JUSTIFICAREMOS que el resistor estará sometido a la misma tensión que la fuente por tal motivo. También se podría justificar por 2da Ley de Kirchoff, pero resulta muy trivial (evitar esta forma).

### Polaridad de la tensión en elementos pasivos
Establecemos como **positivo** por donde ingresa la corriente, y **negativo** por donde sale. 

![Alt text]("eye/02/figura1.png")

* Importante: una fuente de corriente ESTABLECE SOLO EL SENTIDO DE I DE SU RAMA, es decir, no puedo asegurar nada de los sentidos de corrientes de los otros elementos.

* Atención: la tensión en una fuente de corriente puede llegar a quedar al revés que su polaridad (es decir, quedaría el negativo en la punta de la flecha). En este caso, la fuente recibiría energía en lugar de entregarla (CONFIRMADO 19/3 14hs)

Sin embargo, pueden aparecer problemas como en la siguiente figura, donde para R2 quedó "positivo" en ambos extremos (nodos antes definidos cuando se hizo R1 y R3). Entonces, acá es cuando se **SUPONE un sentido de la corriente**, viendo cuál de los extremos es "más positivo que el otro".  

![Alt text]("eye/02/figura2.png")
![Alt text]("eye/02/figura3.png")

En rojo quedaron los SUPUESTOS.

Vemos en la Figura 3 una suposición para R2. De esta manera, suponemos que el **potencial** en el Nodo 2 es MENOR que el potencial del Nodo 1.

## Polaridad de la tensión en elementos activos
El signo de la tensión de las fuentes queda supuesta por los signos en los nodos:
![Alt text]("eye/02/figura4.png")

Vemos que para este ejemplo, quedó supuesto que la fuente 1 entrega energía, y la fuente 2 recibe.

### Resolución
UNA VEZ HECHO TODO ESTE ANÁLISIS ESTOY EN CONDICIONES DE APLICAR LA 1RA LEY DE KIRCHOFF EN EL NODO 1 y 2.

![Alt text]("eye/02/figura5.png")

Luego voy reemplazando las corrientes por expresiones de la Ley de Ohm (salvo las que son dato, en general, solo reemplazamos las de los elementos pasivos)

![Alt text]("eye/02/figura6.png")

Dejamos las corrientes/tensiones de las fuentes de un lado de las igualdades, y los términos con incógnitas del otro lado. Reemplazamos las R por G (conductancia) para evitar la escritura de varios denominadores, de modo que el sistema matricial quede más simplificado a la vista. 

![Alt text]("eye/02/figura7.png")



## No confundir
* El sentido de la corriente en una rama con la polaridad de la fuente (pueden ser diferentes)