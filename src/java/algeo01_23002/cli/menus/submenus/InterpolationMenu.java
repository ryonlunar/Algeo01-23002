package algeo01_23002.cli.menus.submenus;

import algeo01_23002.mathmodels.Interpolation;
import algeo01_23002.types.*;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.*;

public class InterpolationMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("INTERPOLATION MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Polynomial Interpolation");
            System.out.println("2.  Bicubic Spline Interpolation");
            System.out.println("3.  Back to main menu");
            System.out.print(ARROW +"  Select an option (1-3): " + RESET);

            int choice = getChoice(1, 3);
            switch(choice) {
                case 1 -> polynomialInterpolationDriver();
                case 2 -> bicubicSplineInterpolationDriver();
                case 3 -> {isRunning = false;}
            }
        }
    }

    private static void polynomialInterpolationDriver() {
        System.out.print("\n" + ARROW + "  Enter number of points: ");
        int n_points = getChoice(1, 100);

        Matrix augmented = new Matrix(n_points, 2);
        Matrix x_points = new Matrix(1, n_points);
        Matrix y_points = new Matrix(1, n_points);

        inputMatrixChoiceDriver(augmented);

        // Input for X
        for(int i = 0; i < n_points; i++) {
            x_points.setData(0, i, augmented.getData(i, 0));
        }

        // Input for Y
        for(int i = 0; i < n_points; i++) {
            y_points.setData(0, i, augmented.getData(i, 1));
        }

        System.out.println("\nFitting the data...");
        PolynomialResult result;
        try {
            result = Interpolation.polynomialInterpolation(x_points, y_points);
            result.printEquation();
        } catch (Exception e) {
            System.out.println(YELLOW + "\nInterpolation can't be performed " + RESET);
            return;
        }

        while(true) {
            System.out.print("\n" + ARROW + "  Enter x value for point to be interpolated : ");
            double inp = getDouble();
            double res = result.evaluate(inp);
            System.out.println(YELLOW + "\nResult: " + RESET);
            System.out.println(res);
            System.out.print("\n" + ARROW + "  Do you wish to continue (1 for continue/0 to stop): ");
            int choice = getChoice(0, 1);
            if(choice == 0) {
                break;
            }
        }

        System.out.println();
    }

    private static void bicubicSplineInterpolationDriver() {
        System.out.print("\n" + ARROW + "  Matrix should be 4 x 4: ");

        Matrix matrix = new Matrix(4, 4);

        inputMatrixChoiceDriver(matrix);

        Matrix input = new Matrix(16, 1);

        int inputRows = 0;
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                input.setData(inputRows, 0, matrix.getData(i,j));
                inputRows++;
            }
        }

        System.out.print("\n" + ARROW + "  Enter x value for point to be interpolated : ");
        double x = getDouble();
        System.out.print("\n" + ARROW + "  Enter y value for point to be interpolated : ");
        double y = getDouble();

        System.out.print("\nFitting the data...");
        double result =0;
        try{
            Matrix Xinverse = Interpolation.getXInverseBicubicSpline();
            result = Interpolation.bicubicSplineInterpolation(input, x, y, Xinverse);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(YELLOW + "\nInterpolation can't be performed " + RESET);
        }

        System.out.println(YELLOW + "\nResult: " + RESET);
        System.out.println(result);

        System.out.println();
    }
}
