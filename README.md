# **JMetaBFOP**

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

![image](https://user-images.githubusercontent.com/52833089/235054239-08d66c78-376e-4428-bc53-c728266f9cac.png)


## **Download and Configure JMetaBFOP in NetBeans**

### Step 1: Download the JMetaBFOP release

1. Download the release: [JMetaBFOPv1.0.1](https://github.com/garcialopez/JMetaBFOP/releases/download/framework/JMetaBFOP_v1.0.1.zip)
2. Click on the link to download the ZIP file of the release.
3. Save the ZIP file to a location of your choice on your computer.

### Step 2: Extract the ZIP file

1. Navigate to the location where you saved the downloaded ZIP file.
2. Right-click on the ZIP file and select "Extract Here" or "Unzip Here". This will create an uncompressed folder with the release name.

### Step 3: Create a new project in NetBeans

1. Open NetBeans and select **File** from the menu bar.
2. Select **New Project** from the dropdown menu.
3. In the **New Project** window, select **Java** in the category and **Java Application** under projects.
4. Click **Next**.

![1](https://github.com/garcialopez/JMetaBFOP/assets/52833089/e18f19a5-ad0c-494f-a6fa-cdd4ec61549c)

### Step 4: Configure the project

1. In the project configuration window, specify a name and location for your project.
2. Click **Finish**.

### Step 5: Add the JAR files to the project

1. In the NetBeans project window, right-click on the **Libraries** folder within your project and select **Add JAR/Folder**.
2. Navigate to the uncompressed folder of the JMetaBFOP release.
3. Select the **JMetaBFOP_v1.0.1.jar** file and click **Open**. This JAR file contains the JMetaBFOP framework.
4. Repeat the previous steps to add the **MathParser.org-mXparser.jar** file, which is located in the same uncompressed folder. This JAR file is a necessary dependency for solving user-defined CNOPs.

![2](https://github.com/garcialopez/JMetaBFOP/assets/52833089/5edf88af-00be-476d-8200-86ec3e511b55)

### Step 6: Verify the configuration

1. Ensure that the JAR files have been successfully added to the **Libraries** folder of the project.
2. Verify that the JAR files appear in the **Libraries** section within the NetBeans project window.

You have now downloaded the JMetaBFOP release and loaded the JAR files into your Java project in NetBeans! You can start using the framework to solve CNOPs and leverage the capabilities of the MathParser.org-mXparser library to define your own CNOPs.

JMetaBFOP utilizes a free mathematical expression evaluator called mXparser. This tool offers great features and allows for efficient evaluation of mathematical expressions. You can find more information about mXparser on its official website: http://mathparser.org.

## About JMetaBFOP

The framework has three classes that are the pillars for performing a new run of an integrated or inserted problem. These are as follows:

| Class    |
|----------|
| CNOP     |
| TSMBFOA  |
| Constraints |


# **Getting Started with JMetaBFOP**

To use JMetaBFOP to solve CNOP (Constrained Numerical Optimization Problems) problems, you need to follow these steps:

## Step 1: Import the necessary classes

Make sure to import the required classes from JMetaBFOP:

```java
import com.garcialopez.cnops.*;
import com.garcialopez.metaheuristic.tsmbfoa.TSMBFOA;
import com.garcialopez.optimizationmodel.CNOP;
```

## Step 2: Create an instance of CNOP

Inside the main method, create an object of the `CNOP` class using one of the built-in implementations. For example:

```java
//Create an instance of CNOP
CNOP cnop = new TensionCompressionSpring();
```
## Step 3: Create an instance of TSMBFOA

After creating the CNOP object, you can create an instance of the `TSMBFOA` class and pass the CNOP object and `true` as arguments. This will enable displaying execution information on the console.

```java
// Create an instance of TSMBFOA
TSMBFOA tsmbfoa = new TSMBFOA(cnop, true);
```

## Step 4: Execute the algorithm

To execute the algorithm, simply call the `run()` method on the TSMBFOA object.

```java
// Run the algorithm
tsmbfoa.run();
```

**Great!** Now you have completed the initial steps to use JMetaBFOP. The complete code looks like this:

```java
import com.garcialopez.cnops.*;
import com.garcialopez.metaheuristic.tsmbfoa.TSMBFOA;
import com.garcialopez.optimizationmodel.CNOP;

public class ExampleJMetaBFOP {

    public static void main(String[] args) {
        //Create an instance of CNOP
        CNOP cnop = new TensionCompressionSpring();
        
        // Create an instance of TSMBFOA
        TSMBFOA tsmbfoa = new TSMBFOA(cnop, true);
        
        // Run the algorithm
        tsmbfoa.run();
    }
}
```

From here, you can continue configuring the parameters, executing the algorithm, and analyzing the results according to your needs.

Based on the above, the console results would be as follows:

**Results of each generation:**

| # | x1 | x2 | x3 | FO | SVR |
| --- | --- | --- | --- | --- | --- |
| 1 | 0.051708539528959534 | 0.3571847938513473 | 11.261734071828137 | 0.01266536820426279 | 0.0 |
| 2 | 0.051676889753363014 | 0.3564226191704028 | 11.3063773520769 | 0.012665368405583374 | 0.0 |
| 3 | 0.05165730914324691 | 0.3559516235468301 | 11.334131670095449 | 0.01266541069690316 | 0.0 |
| 4 | 0.05173013895414509 | 0.357704322953273 | 11.231488883251343 | 0.012665437465644774 | 0.0 |
| 5 | 0.051731152547499326 | 0.35772835647753365 | 11.230119780166316 | 0.012665474130654525 | 0.0 |
| 6 | 0.051749150661890085 | 0.3581626818090529 | 11.204897362501681 | 0.012665484732798228 | 0.0 |
| 7 | 0.05169581795769177 | 0.3568780883779779 | 11.279792818292963 | 0.012665490286198131 | 0.0 |
| 8 | 0.05163179738412301 | 0.35534166599784683 | 11.370342172267488 | 0.012665523456655696 | 0.0 |
| 9 | 0.0517353453543781 | 0.3578288070228064 | 11.224313889650416 | 0.012665523778309545 | 0.0 |
| 10 | 0.0516462332237205 | 0.3556862990073761 | 11.34992250893015 | 0.01266552468031105 | 0.0 |
| ... | ... | ... | ... | ... | ... | ... |
| 48 | 0.05173391552008679 | 0.3578798346177322 | 11.221496661543737 | 0.012665601493420716 | 0.0 |
| 49 | 0.05173908928373005 | 0.357928760009021 | 11.21768430813936 | 0.012665617601847234 | 0.0 |
| 50 | 0.05169388810219874 | 0.3567367431254337 | 11.282911034206164 | 0.012665648300017164 | 0.0 |

**Statistics:**

| Metric              | Value                    |
|---------------------|--------------------------|
| Best Value          | 0.01266536820426279      |
| Mean Value          | 0.01666204625118927      |
| Median              | 0.012665670830097016     |
| Standard Deviation  | 0.027974428535010795     |
| Worst Value         | 0.21248304599162915      |
| Feasible Rate       | 100%                    |
| Success Rate        | 100%                    |
| Success Performance | 3603.0                   |
| Time                | 0                        |

## The integrated CNOPs available for you to test are:

| Class                            | Class                            | Class                           |
|--------------------------------------|--------------------------------------|-------------------------------------|
| DesignReinforcedConcreteBeam         | G10_CEC2006                          | G21_CEC2006                         |
| G01_CEC2006                          | G11_CEC2006                          | G22_CEC2006                         |
| G02_CEC2006                          | G12_CEC2006                          | G23_CEC2006                         |
| G03_CEC2006                          | G13_CEC2006                          | G24_CEC2006                         |
| G04_CEC2006                          | G15_CEC2006                          | PressureVessel                      |
| G05_CEC2006                          | G17_CEC2006                          | ProcessSynthesisKocis98             |
| G06_CEC2006                          | G18_CEC2006                          | ProcessSynthesisYuan88              |
| G07_CEC2006                          |                                      | QuadraticallyConstrainedQuadraticProgram |
| G08_CEC2006                          |                                      | TensionCompressionSpring             |
| G09_CEC2006                          |                                      |                                     |

# **Customization and Calibration of the Algorithm**

1. **Population size**: Set the size of the population in the algorithm.

```java
tsmbfoa.setSb(14);
```

2. **Number of chemotactic cycles**: Specify the number of chemotactic cycles in the algorithm.

```java
tsmbfoa.setNc(7);
```

3. **Step size**: Set the step size for the chemotaxis process.

```java
tsmbfoa.setStepSize(0.0005);
```

4. **Scaling factor**: Adjust the scaling factor for the algorithm.

```java
tsmbfoa.setScalingFactor(1.95);
```

5. **Number of bacteria to reproduce**: Specify the number of bacteria to reproduce in each generation.

```java
tsmbfoa.setBacteriaReproduce(1);
```

6. **Reproduction frequency**: Set the frequency of reproduction in the algorithm.

```java
tsmbfoa.setRepcycle(100);
```

7. **Number of evaluations**: Define the maximum number of function evaluations allowed.

```java
tsmbfoa.setEvaluations(20000);
```

8. **Number of independent algorithm iterations**: Set the number of independent algorithm iterations to perform.

```java
tsmbfoa.setExecutions(30);
```

### 1. You can adjust these parameters according to your needs and experiment with different values to achieve better results.

### 2. You now have the option to execute the customized algorithm with the calibrated parameters. Remember to use the `run()` method on the tsmbfoa object to start the execution.

### 3. Explore and enjoy the optimization process with JMetaBFOP!.

### 4. Remember that you can analyze the obtained results and adjust the parameters again to further improve the algorithm's performance.

The code with the configuration would be as follows:

```java
import com.garcialopez.cnops.*;
import com.garcialopez.metaheuristic.tsmbfoa.TSMBFOA;
import com.garcialopez.optimizationmodel.CNOP;

public class ExampleJMetaBFOP {

    public static void main(String[] args) {
        CNOP cnop = new TensionCompressionSpring();
        
        TSMBFOA tsmbfoa = new TSMBFOA(cnop, true);
        
        tsmbfoa.setSb(14);
        tsmbfoa.setNc(7);
        tsmbfoa.setStepSize(0.0005);        
        tsmbfoa.setScalingFactor(1.95);
        tsmbfoa.setBacteriaReproduce(1);
        tsmbfoa.setRepcycle(100);
        tsmbfoa.setEvaluations(20000);
        tsmbfoa.setExecutions(30);
        
        tsmbfoa.run();
    }
}
```

The results obtained now are from 30 independent algorithm executions, as configured by the number of algorithm executions.

You can analyze the results of each execution to assess the algorithm's performance and evaluate its convergence and optimization quality.

# **Customizing your own CNOP**

## If your CNOP is not integrated, you can input it yourself by following the code below.

Instead of instantiating an integrated CNOP, you need to create the following:

```java
CNOP cnop = new CNOP();
```

Then, set the CNOP information such as name, type, best-known value, objective function, variable order, and variable ranges.

```java
cnop.setNameProblem("Tension/compression spring");
cnop.setBestKnownValue(0.012681);
cnop.setType(CNOP.MINIMIZATION);
cnop.setFunction("(N + 2) * D * d^2");
cnop.setOrderVariables("d;D;N");
cnop.setVariableRange("(0.05, 2);(0.25, 1.3);(2, 15)");
```

Import the Constraints class:

```java
import com.garcialopez.optimizationmodel.Constraints;
```

Create a Constraints object:


```java
Constraints constraints = new Constraints();
```

Use the `add` method to add the constraints:

```java
constraints.add("1-(D^3*N)/(71785*d^4) <= 0");
constraints.add("((4*(D^2)-d*D)/(12566*(D*(d^3)-(d^4)))) + (1.0/(5108*(d^2)))-1 <= 0");
constraints.add("1.0-(140.45*d/((D^2)*N)) <= 0");
constraints.add("((D+d)/1.5)-1 <= 0");
```

Set the constraints for the CNOP:

```java
cnop.setConstraints(constraints);
```

Finally, parse the CNOP:

```java
cnop.parserCNOP();
```

Then, pass it to TSMBFOA:

```java
TSMBFOA tsmbfoa = new TSMBFOA(cnop, true);
```

Here is the complete code with the customization of your own CNOP:


```java
import com.garcialopez.metaheuristic.tsmbfoa.TSMBFOA;
import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

public class ExampleJMetaBFOP {

    public static void main(String[] args) {
        CNOP cnop = new CNOP();

        cnop.setNameProblem("Tension/compression spring");
        cnop.setBestKnownValue(0.012681);
        cnop.setType(CNOP.MINIMIZATION);
        cnop.setFunction("(N + 2) * D * d^2");
        cnop.setOrderVariables("d;D;N");
        cnop.setVariableRange("(0.05, 2);(0.25, 1.3);(2, 15)");

        Constraints constraints = new Constraints();
        constraints.add("1-(D^3*N)/(71785*d^4) <= 0");
        constraints.add("((4*(D^2)-d*D)/(12566*(D*(d^3)-(d^4)))) + (1.0/(5108*(d^2)))-1 <= 0");
        constraints.add("1.0-(140.45*d/((D^2)*N)) <= 0");
        constraints.add("((D+d)/1.5)-1 <= 0");
        cnop.setConstraints(constraints);

        cnop.parserCNOP();

        TSMBFOA tsmbfoa = new TSMBFOA(cnop, true);

        tsmbfoa.setSb(14);
        tsmbfoa.setNc(7);
        tsmbfoa.setStepSize(0.0005);
        tsmbfoa.setScalingFactor(1.95);
        tsmbfoa.setBacteriaReproduce(1);
        tsmbfoa.setRepcycle(100);
        tsmbfoa.setEvaluations(20000);
        tsmbfoa.setExecutions(30);

        tsmbfoa.run();

    }
}
```

#### *JMetaBFOP offers numerous advantages that make it a powerful tool for solving Constrained Numerical Optimization Problems (CNOPs). Its straightforward usage makes it accessible to both beginners and experienced users. The ability to easily customize the algorithm's parameters allows for fine-tuning and optimization according to specific problem requirements. Additionally, the flexibility to include both integrated CNOPs and personalized CNOPs provides a wide range of options for problem-solving. With JMetaBFOP, users can confidently explore and tackle CNOPs with efficiency and ease.*
