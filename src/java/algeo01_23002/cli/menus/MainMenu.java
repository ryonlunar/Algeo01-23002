package algeo01_23002.cli.menus;

import java.util.Scanner;
import algeo01_23002.cli.menus.submenus.*;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.*;

public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("MAIN MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Linear System Solver");
            System.out.println("2.  Interpolation");
            System.out.println("3.  Regression");
            System.out.println("4.  Matrix Transformation");
            System.out.println("5.  Matrix Operations");
            System.out.println("6.  Matrix Properties");
            System.out.println("7.  About the library");
            System.out.println("8.  Help");
            System.out.println("9.  Exit");
            System.out.print(ARROW +"  Select an option (1-9): " + RESET);

            int choice = getChoice(1, 9);
            switch(choice) {
                case 1 -> LinearSystemSolverMenu.show();
                case 2 -> InterpolationMenu.show();
                case 3 -> RegressionMenu.show();
                case 4 -> MatrixTransformationMenu.show();
                case 5 -> MatrixOperationsMenu.show();
                case 6 -> MatrixPropertiesMenu.show();
                case 7 -> AboutMenu.show();
                case 8 -> HelpMenu.show();
                case 9 -> { isRunning = false; }
            }
        }
    }
}
