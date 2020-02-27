

# Práctica 2

## Coordinación y tiempos

- Probad qué pasa si los Atletas no esperan a las órdenes de "preparados" y "listos", y empiezan a correr en cuanto pueden
	- En distintos despliegues: debido a que los tiempos de espera de los atletas son altos (entre 9 y 11 s) para una máquina moderna, no se nota mucha diferencia entre despliegues, aunque sí para correr no esperan ningún tiempo, se apreciaría como al repetir el proceso varias veces, los atletas que "corren" en la misma máquina en la que se encuentra el servidor tienen una clara ventaja por tener un menor retraso en las comunicaciones.
	- Reflexionado y/o probad con diferentes medidas de tiempos: En vez de reflexionar para cada situación, realizaremos una reflexión global sobre este aspecto: Como todas las medidas de tiempo las toma el propio servidor, no se da ningún problema de referencias de tiempo, como se daria si los atletas tomarán por ellos mismos las mediciones. Esto se debe a que no podemos comparar las referencias de tiempo tomadas en dos máquinas distintas, porque los relojes de los que toman la diferencia pueden tener una pequeña deriva (aunque sea de ms) que altera el resultado. En el caso de necesitar tomar las medidas de tiempo en distintos equipos, tendremos que sincronizar los relojes, lo cual como hemos visto en clase es un proceso extremadamente complejo y que no aporta la precisión deseada. 

## Análisis

### ¿Qué posibles fallos encuentras en el sistema que has implementado?
- Relativos a los tiempos: 
	- si dos atletas llegan a la vez, uno tendrá que esperar a que el otro guarde su tiempo en el HashMap que maneja esta información.
	- claramente, la carrera no esta igualada, porque los atletas que corren en el ordenador donde se encuentra el servidor, tienen una ventaja de ms, debido a que el retardo de transmisión en las peticiones al servicio de carrera, es minimo en comparacion con los atletas que se encuentran en otros equipos.
- Relativos a la coordinación: 
	- a la hora de invocar los atletas entre varias máquinas, si no se ejecuta primero la aplicación de la máquina que realizara el reiniciar y se esperan unos pocos segundos (sobre 2 o 3) para que a esta le dé tiempo a enviar el mensaje, los atletas ejecutados por otras máquinas se quedarán colgados debido a que cabe la posibilidad de que alguno de ellos llegue a preparados y listos antes de que se haga la petición al endpoint /reinicio.
- Relativos a posibles fallos de proceso: 
	- si un proceso se cae, el sistema entero deja de funcionar debido a que directamente ningún hilo pasaría del endpoint de preparados.

### ¿Se te ocurren mejoras posibles para el sistema?
- establecer un timeout de petición en el proceso que se encarga de solicitar los resultados, y en el caso de que no hayan hayan terminado todos los hilos (ya sea porque un proceso haya fallado o por alguna otra razón), se vuelvan a solicitar los resultados en otro endpoint (distinto de resultados), que nos permita obtener estos, aunque estén incompletos.
- añadir un endpoint en la clase Carrera100, en el que cada nodo que aporte corredores, se registre al principio. Aquí indicará el número de atletas, de forma que este, no esté limitado a el valor establecido en una constante en la clase Carrera100.
- que en el endpoint descrito justo antes, la clase Carrera100 asigne un ID de atleta a cada atleta que se registre. Con este ID se identificó a cada atleta dentro de la clase Carrera100, haciendo que se pueda mantener el estado de este dentro de dicha clase.
- elaborar un POJO Atleta, en el que almacenemos información como el ID de atleta, dorsal y tiempos para evitar manejar esta información en un Map en la clase Carrera100.
- devolver los resultados del procesamiento del servicio en un formato estructurado como XML o JSON en vez de como texto plano