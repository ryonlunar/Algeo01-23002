package algeo01_23002;
import algeo01_23002.types.Matrix;
import static algeo01_23002.solvers.LinearSystemSolver.Gauss;
import static algeo01_23002.solvers.LinearSystemSolver.GaussJordan;


public class Main {

    public static void main(String[] args) {
        Matrix matrix = new Matrix(4,6);
        matrix.inputMatrix();

        Matrix res = GaussJordan(matrix) ;
        res.printMatrix();
    }

}
