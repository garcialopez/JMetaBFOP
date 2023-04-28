# JMetaBFOP

The **TS-MBFOA** is an algorithm based on bacterial foraging, and works to solve global optimization problems.

This Java-based **framework** implements a collective intelligence metaheuristic algorithm that, thanks to its well-calibrated parameters, is effective in finding one or multiple solutions to optimization problems from various fields of study. This metaheuristic is based on bacterial foraging. The framework includes optimization problems from **Mechanical** and **Chemical Engineering**, as well as the set of problems from **CEC 2006**.

This new tool has features such as:

- Ability to choose and test one of the integrated optimization problems.
- Ability to calibrate the algorithm's own parameters.
- Allowing the user to input their own optimization problem.
- Analyzing the results and basic statistics (best, mean, median, standard deviation, worst value, feasibility rate, success rate, and successful performance).

The optimization problems that can be solved are mathematical expressions that must comply with the following:

- Objective function.
- Design variables.
- Constraints.
- Variable range.
- Known best value.

**An example of an optimization problem is the following:**

![image](https://user-images.githubusercontent.com/52833089/155066384-74753153-a297-40f4-9eab-2e0c77b1e1ef.png)

#Let's start coding

1. To use this framework, you need to download the released version:

- [JMetaBFOP_v1.0.zip](https://github.com/garcialopez/JMetaBFOP/releases/tag/FRAMEWORK "Descargar")

2. To begin using the tool, you need to create a new project in **Java** (Java Application). In this case, we will be using the NetBeans 8.0.2 IDE.

![image](https://user-images.githubusercontent.com/52833089/155067744-13311c67-3fde-43a6-8f8b-b7d53a652402.png)

3. Later on, you need to include the file within the project's libraries.

![image](https://user-images.githubusercontent.com/52833089/155068150-cc88d612-ba05-4cd5-b3f5-1fd04c3fc5a4.png)

4. Finally, you can start coding...

### Knowing the framework

The framework uses a free mathematical expression evaluator to evaluate mathematical expressions. To do this, mXparser is used as a very powerful tool with great features. You can find it here: <http://mathparser.org>.

The framework has three classes that are the pillars for performing a new run of an integrated or inserted problem. These are as follows:

|Class|Methods|Utility|
|--------------------|----------------------------|--------------------|
|     **Problem**    | setNameProblem<br />setFunction<br />setOrderVariables<br />setObj<br />setBestKnownValue<br />detectConstraints<br /> ...    | Nombre<br />función matemática<br />orden de variables<br />objetivo (min o max)<br />mejor valor conocido<br />restricciones<br />...  |
|   **Configurator** |    setSb<br />setStepSize<br />setNc<br />setScalingFactor<br />setBacteriaReproduce<br />setRepcycle<br />setEvaluations<br /> | cantidad de bacterias<br />amaño de paso<br />número de ciclos quimiotáxicos<br />factor de escalamiento<br />número de bacterias<br />frecuencia de reproducción<br />número de evaluaciones |
|   **RunTsmbfoa**   |    run    | Incia el algoritmo |

To start using this framework, inside the `Main` method an object of the **Problem** class will be created:

~~~
package ts_mbfofoa_run;

public class Main {
    public static void main(String[] args) {  
    
             Problem problem;       
             
    }          
}
~~~

To assign a problem to this created object, you can use the methods from the previous table or assign one of the integrated optimization problems, which can be one of the following:

- PressureVessel
- ProcessSynthesisMINLP
- TensionCompressionSpring
- DesignReinforcedConcreteBeam
- QuadraticallyConstrainedQuadraticProgram
- G**01**_CEC2006 al G**13**_CEC2006
- G**15**_CEC2006, G**17**_CEC2006, G**18**_CEC2006
- G**21**_CEC2006 al G**24**_CEC2006

To do so, you must do the following:

~~~
  problem = new TensionCompressionSpring();
~~~

To calibrate the algorithm's own parameters, you must use the methods from the previous table or use the recommended configuration:

~~~
  Configurator configurator = problem.getRecommendedSetting();
~~~

Also, the number of independent runs for the algorithm's execution is established:

~~~
  problem.setExecutions(30);
~~~

Finally, the algorithm is executed:

~~~
  RunTsmbfoa stst = new RunTsmbfoa();
  stst.run(problem, configurator, true, true); 
~~~

Our final code looks like the following:

![image](https://user-images.githubusercontent.com/52833089/155073092-9e50300b-6999-4765-bc36-16668962ee66.png)

At the end of the execution, the obtained result for the selected or inserted optimization problem is shown:

![image](https://user-images.githubusercontent.com/52833089/155073374-aeda1d23-6e85-4792-9bf3-58b67516f822.png)

Similarly, it shows the basic statistics:
![image](https://user-images.githubusercontent.com/52833089/155073481-261c2400-02be-49fc-bdf0-a5d34769050d.png)


You can view the complete results in the following file:
[Resultados TS-MBFOA.txt](https://github.com/garcialopez/frameworkTSMBFOA/files/8114044/Resultados.TS-MBFOA.txt)












