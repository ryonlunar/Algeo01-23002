package algeo01_23002.cli.menus.submenus;

import algeo01_23002.mathmodels.Regression;
import algeo01_23002.types.*;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.*;
import static algeo01_23002.cli.Utilities.printMatrixWithBorder;

public class RegressionMenu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("REGRESSION MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Multiple Linear Regression");
            System.out.println("2.  Multiple Quadratic Regression");
            System.out.println("3.  Back to main menu");
            System.out.print(ARROW +"  Select an option (1-3): " + RESET);

            int choice = getChoice(1, 3);
            switch(choice) {
                case 1 -> multipleLinearRegressionDriver();
                case 2 -> multipleQuadraticRegressionDriver();
                case 3 -> { isRunning = false; }
            }
        }
    }

    private static void multipleLinearRegressionDriver() {
        System.out.print("\n" + ARROW + "  Enter number of rows: ");
        int rows = getChoice(1, 100);
        System.out.print("\n" + ARROW + "  Enter number of cols: ");
        int cols = getChoice(1, 100);

        int choice;
        double estimated = 0;
        Matrix matrix = new Matrix(rows, cols);
        Matrix estimateVal = new Matrix(1, cols-1);

        inputMatrixChoiceDriver(matrix);

        System.out.print("\nFitting the data...");
        LinearSystemSolution result = Regression.multipleLinearRegression(matrix);

        // Print Data
        if(result instanceof UniqueSolution) {
            System.out.println(YELLOW + "\nResult: " + RESET);
            LinearRegressionResult regressionResult = new LinearRegressionResult(((UniqueSolution) result).getSolution());
            regressionResult.printEquation();

            System.out.println("Do you want to estimate value (1 (yes) / 0 (no))?");
            choice = getChoice(0, 1);
            if (choice == 1) {
                while (true){
                    System.out.println("Input All X Variabels to Estimate Result");
                    inputMatrixDriver(estimateVal);
                    estimated = regressionResult.estimate(estimateVal);
                    System.out.println(YELLOW + "Result: " + RESET + estimated);
                    System.out.println("Do you wish to continue? (1 (continue)/ 0 (stop))");
                    choice = getChoice(0, 1);
                    if (choice == 0) {
                        break;
                    }
                }
            }
        }
        else if(result instanceof ParametricSolution) {
            System.out.println(YELLOW + "\nParametric solution found " + RESET);
            System.out.println(printParametricLinearRegression((ParametricSolution) result));
        }
        else {
            System.out.println(YELLOW + "\nRegression can't be performed " + RESET);
        }

        // Save Data
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no))");
        choice = getChoice(0,1);
        if(choice == 1 && result instanceof UniqueSolution) {
            Matrix solution = ((UniqueSolution) result).getSolution();
            saveUniqueLinearRegressionResultToFile(solution);
        }
        else if(choice == 1 && result instanceof ParametricSolution) {
            saveParametricLinearRegressionToFile((ParametricSolution) result);
        }

        System.out.println();
    }

    private static void multipleQuadraticRegressionDriver() {
        System.out.print("\n" + ARROW + "  Enter number of rows: ");
        int rows = getChoice(1, 100);
        System.out.print("\n" + ARROW + "  Enter number of cols: ");
        int cols = getChoice(1, 100);

        int choice;
        double estimated = 0;
        Matrix matrix = new Matrix(rows, cols);
        Matrix estimateVal = new Matrix(1, cols-1);

        inputMatrixChoiceDriver(matrix);

        System.out.print("\nFitting the data...");
        LinearSystemSolution result = Regression.multipleQuadraticRegression(matrix);

        // Print Data
        if(result instanceof UniqueSolution) {
            Matrix solution = ((UniqueSolution) result).getSolution();
            System.out.println(YELLOW + "\nResult: " + RESET);
            QuadraticRegressionResult regressionResult = new QuadraticRegressionResult(((UniqueSolution) result).getSolution());
            regressionResult.printEquation();

            System.out.println("Do you want to estimate value (1 (yes) / 0 (no))?");
            choice = getChoice(0, 1);
            if (choice == 1) {
                while (true){
                    System.out.println("Input All X Variabels to Estimate Result");
                    inputMatrixDriver(estimateVal);
                    estimated = regressionResult.estimate(estimateVal);
                    System.out.println(YELLOW + "Result: " + RESET + estimated);
                    System.out.println("Do you wish to continue? (1 (continue)/ 0 (stop))");
                    choice = getChoice(0, 1);
                    if (choice == 0) {
                        break;
                    }
                }
            }

        } else if(result instanceof ParametricSolution) {
            System.out.println(YELLOW + "\nParametric solution found " + RESET);
            System.out.println(printParametricQuadraticRegression((ParametricSolution) result));
        }
        else {
            System.out.println(YELLOW + "\nRegression can't be performed " + RESET);
        }

        // Save Data
        System.out.println("Do you want to save it to file? (1 (yes) / 0 (no))");
        choice = getChoice(0,1);
        if(choice == 1 && result instanceof UniqueSolution) {
            Matrix solution = ((UniqueSolution) result).getSolution();
            saveUniqueQuadraticRegressionToFile(solution);
        }
        else if(choice == 1 && result instanceof ParametricSolution) {
            saveParametricQuadraticRegressionToFile((ParametricSolution) result);
        }
        System.out.println();
    }

}
