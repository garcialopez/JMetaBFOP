# Framework Java del TS-MBFOA

El **TS-MBFOA** es un algoritmo basado en el forrajeo de bacterias y funciona para resolver problemas de optimización global.

En la codificación de este **framework** desarrollado en Java se aborda un algoritmo metaheurístico de inteligencia colectiva,
que gracias a la buena calibración o ajuste de sus parámetros, funciona para encontrar
una o un conjunto de soluciones a **problemas de optimización** de diversas áreas de
estudio. Esta **metaheurística** esta basada en el forrajeo de bacterias. En la codificación del framework se
incluyen problemas de optimización de Ingeniería Mecánica y Química y el conjunto de problemas del **CEC 2006**.

Esta nueva herramienta tiene funcionalidades como: 

- Poder elegir y probar uno de los problemas de optimización integrados
- Poder calibrar los parámetros propios del algoritmo
- Permitir al usuario ingresar su propio problema a optimizar
- Analizar los resultados y las estadísticas básicas (mejor, media, mediana, desviación estándar, peor valor, tasa de factibilidad, tasa de éxito y rendimiento exitoso)

Los problemas de optimización que se pueden resolver son expresiones matemáticas que deben de cumplir con lo siguiente:

- Función objetivo
- Variables de diseño
- Restricciones
- Rango de variables
- Mejor valor conocido

**Un ejemplo de problemas de optimización es el siguiente:**

![image](https://user-images.githubusercontent.com/52833089/155066384-74753153-a297-40f4-9eab-2e0c77b1e1ef.png)

#Comencemos a codificar

1. Para poder usar este framework necesita descargar el mismo en su versión lanzada:

- [JMetaBFOP_v1.0.zip](https://github.com/garcialopez/JMetaBFOP/files/9856951/JMetaBFOP_v1.0.zip "Descargar")



2. Para comenzar a utilzar la herramienta se necesita crear un nuevo proyecto en **Java** (Java Application), para este caso utilizaremos el IDE NetBeans 8.0.2.

![image](https://user-images.githubusercontent.com/52833089/155067744-13311c67-3fde-43a6-8f8b-b7d53a652402.png)

3. Posteriormente, se necesita incluir el archivo dentro de las librerías del proyecto.

![image](https://user-images.githubusercontent.com/52833089/155068150-cc88d612-ba05-4cd5-b3f5-1fd04c3fc5a4.png)

4. Finalmente, ya puedes comenzar a codificar...

### Conociendo el framework

El framework utiliza un evaluador de expresiones matemáticas gratuito para poder evaluar las expresiones matemáticas. Para ello, se utiliza mXparser por ser una herramienta muy potente con grandes características. Este lo puedes encontrar aquí <http://mathparser.org>.

El framework tiene tres clases el cual son los pilares para realizar una nueva ejecución de un problema integrado o insertados. Estas son las siguientes: 

|Clase|Métodos|Utilidad|
|--------------------|----------------------------|--------------------|
|     **Problem**    | setNameProblem<br />setFunction<br />setOrderVariables<br />setObj<br />setBestKnownValue<br />detectConstraints<br /> ...    | Nombre<br />función matemática<br />orden de variables<br />objetivo (min o max)<br />mejor valor conocido<br />restricciones<br />...  |
|   **Configurator** |    setSb<br />setStepSize<br />setNc<br />setScalingFactor<br />setBacteriaReproduce<br />setRepcycle<br />setEvaluations<br /> | cantidad de bacterias<br />amaño de paso<br />número de ciclos quimiotáxicos<br />factor de escalamiento<br />número de bacterias<br />frecuencia de reproducción<br />número de evaluaciones |
|   **RunTsmbfoa**   |    run    | Incia el algoritmo |

Para comenzar a usar este framework, dentro del metodo `Main` se creara un objeto de la Clase **Problem**:

~~~
package ts_mbfofoa_run;

public class Main {
    public static void main(String[] args) {  
    
             Problem problem;       
             
    }          
}
~~~

Para poder asignarle un problema a dicho objeto creado, se puede utilizar los métodos de la tabla anterior o bien asignar un problema de optimización de los integrados el cual, puede ser uno de los siguientes: 

- PressureVessel
- ProcessSynthesisMINLP
- TensionCompressionSpring
- DesignReinforcedConcreteBeam
- QuadraticallyConstrainedQuadraticProgram
- G**01**_CEC2006 al G**13**_CEC2006
- G**15**_CEC2006, G**17**_CEC2006, G**18**_CEC2006
- G**21**_CEC2006 al G**24**_CEC2006

Para ello se debe de hacer lo siguiente:

~~~
  problem = new TensionCompressionSpring();
~~~

Para calibrar los parametros propios del algoritmo se debe de usar los métodos de la tabla anterior o bien usar la configuración recomendada:

~~~
  Configurator configurator = problem.getRecommendedSetting();
~~~

Tambien se establece el número de ejecuciones independiente para la ejecución del algoritmo:

~~~
  problem.setExecutions(30);
~~~

Finalmente, se hace la ejecución del algoritmo:

~~~
  RunTsmbfoa stst = new RunTsmbfoa();
  stst.run(problem, configurator, true, true); 
~~~

Nuestro código final queda de la siguiente manera:

![image](https://user-images.githubusercontent.com/52833089/155073092-9e50300b-6999-4765-bc36-16668962ee66.png)

AL final de la ejecución se muestra el resultado obtenido para el problema de optimización seleccionado o insertado:

![image](https://user-images.githubusercontent.com/52833089/155073374-aeda1d23-6e85-4792-9bf3-58b67516f822.png)

De igual manera muestra las estadísticas básicas: 
![image](https://user-images.githubusercontent.com/52833089/155073481-261c2400-02be-49fc-bdf0-a5d34769050d.png)


Puede visualizar los resultados completos en el siguiente archivo:
[Resultados TS-MBFOA.txt](https://github.com/garcialopez/frameworkTSMBFOA/files/8114044/Resultados.TS-MBFOA.txt)












