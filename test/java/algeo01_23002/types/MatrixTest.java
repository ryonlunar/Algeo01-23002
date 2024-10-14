package algeo01_23002.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;


import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {
    private Matrix matrix;

    @BeforeEach
    public void setUp() {
        matrix = new Matrix(2,3);
        matrix.setAllData(new double[][] {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        });

    }

    //  Input / Output Test
    @Test
    public void testInputMatrix() {
        // Simulate user input
        String input = "1.0 2.0 3.0\n4.0 5.0 6.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        matrix.inputMatrix();

        double[][] expectedData = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };

        assertArrayEquals(expectedData, getMatrixData(matrix));
    }

    @Test
    public void testPrintMatrix() {
//        5// Capture the output of printMatrix
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        matrix.printMatrix(); // Call the printMatrix method

        // Reset the standard output
        System.setOut(originalOut);
        String actualOutput = outputStream.toString().replace("\r\n", "\n").replace("\r", "\n");
        // Check the printed output
        String expectedOutput = "1.0 2.0 3.0 \n4.0 5.0 6.0 \n"; // Output based on your matrix
        assertEquals(expectedOutput, actualOutput);
    }

    //  Operation Test
    @Test
    public void testAddMatrix() {
        matrix = matrix.add(matrix);
        double[][] expectedData = {
                {2.0, 4.0, 6.0},
                {8.0, 10.0, 12.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix));

    }
    @Test
    public void testSubtractMatrix() {

        matrix = matrix.subtract(matrix);
        double[][] expectedData = {
                {0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix));
    }
    @Test
    public void testMultiplyByMatrixMatrix() {
        String input1 = "1.0 2.0 3.0\n4.0 5.0 6.0\n";
        System.setIn(new ByteArrayInputStream(input1.getBytes()));
        Matrix matrix1 = new Matrix(2, 3);
        matrix1.inputMatrix();

        String input2 = "7.0 8.0\n9.0 10.0\n11.0 12.0\n";
        System.setIn(new ByteArrayInputStream(input2.getBytes()));
        Matrix matrix2 = new Matrix(3, 2);
        matrix2.inputMatrix();


        double[][] expected = {
                {58, 64},
                {139, 154}
        };

        Matrix result = matrix1.multiplyByMatrix(matrix2);

        assertArrayEquals(expected, result.getAllData(), "Matrix multiplication result is incorrect.");
    }
    @Test
    public void testMultiplyByScalarMatrix() {

        matrix = matrix.multiplyByScalar(3);
        double[][] expectedData = {
                {3.0, 6.0, 9.0},
                {12.0, 15.0, 18.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix));
    }
    @Test
    public void testTransposeMatrix() {

        matrix = matrix.transpose(); // Test mutable for transpose
        matrix = matrix.add(matrix);

        double[][] expectedData = {
                {2.0,8.0},
                {4.0,10.0},
                {6.0,12.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix));
    }

    @Test
    public void testInputMatrixFromFile(){

        Matrix matrix1 = new Matrix(3,3);
        matrix1.inputMatrixFromFile("test/resources/FileTest.txt");

        double[][] expectedData = {
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };

        assertArrayEquals(expectedData, matrix1.getAllData(), "Parsed matrix is incorrect");
    }

    @Test
    // testGetDeterminantWithCofactor
    public void testGetDeterminantWithCofactor(){

        Matrix matrix1 = new Matrix(3,3);
        matrix1.inputMatrixFromFile("test/resources/FileTest.txt");

        double determinant = matrix1.getDeterminantWithCofactor();

        double expectedValue = 0;

        assertEquals(expectedValue,determinant);
    }

    // Test getAdjoint
    @Test
    public void testGetAdjoint(){
        Matrix matrix1 = new Matrix(3,3);
        double[][] initialData = {
                {1.0,2.0,3.0},
                {4.0,5.0,6.0},
                {7.0,8.0,9.0}
        };

        matrix1.setAllData(initialData);

        double[][] expectedData = {
                {-3.0,6.0,-3.0},
                {6.0,-12.0,6.0},
                {-3.0,6.0,-3.0}
        };

        Matrix adjoint = matrix1.getAdjoint();

        assertArrayEquals(expectedData, adjoint.getAllData(), "Adjoint matrix is incorrect");

    }

    // Test getInverseWithAdjoint
    @Test
    public void testGetInverseWithAdjoint(){
        Matrix matrix1 = new Matrix(3,3);

        double[][] initialData = {
                {1.0, 8.0, 5.0},
                {1.0, 9.0, 6.0},
                {1.0, 3.0, 5.0}
        };

        matrix1.setAllData(initialData);

        double[][] expectedData = {
                {5.4, -5.0, 0.6},
                {0.2, 0.0, -0.2},
                {-1.2, 1.0, 0.2}
        };

        Matrix result = matrix1.getInverseWithAdjoint();
        result.printMatrix();

        assertArrayEquals(expectedData, result.getAllData(), "Inverse matrix is incorrect");
    }

    // Test getInverseWithRowReduced
    @Test
    public void testGetInverseWithRowReduced(){
        Matrix matrix1 = new Matrix(3,3);

        double[][] initialData = {
                {1.0, 8.0, 5.0},
                {1.0, 9.0, 6.0},
                {1.0, 3.0, 5.0}
        };

        matrix1.setAllData(initialData);

        double[][] expectedData = {
                {5.4, -5.0, 0.6},
                {0.2, 0.0, -0.2},
                {-1.2, 1.0, 0.2}
        };

        Matrix result = matrix1.getInverseWithRowReduction();
        result.printMatrix();

        assertArrayEquals(expectedData, result.getAllData(), "Inverse matrix is incorrect");
    }


    // Test multiplyRowByScalar
    @Test
    public void testMultiplyRowByScalar() {

        matrix = matrix.multiplyRowByScalar(0, 2);

        double[][] expectedData = {
                {2.0, 4.0, 6.0},
                {4.0, 5.0, 6.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix), "Result of multiplying row by scalar is incorrect.");
    }

    // Test multiplyColumnByScalar
    @Test
    public void testMultiplyColByScalar() {

        matrix = matrix.multiplyColByScalar(1, 3);

        double[][] expectedData = {
                {1.0, 6.0, 3.0},
                {4.0, 15.0, 6.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix), "Result of multiplying column by scalar is incorrect.");
    }

    // Test swapRow
    @Test
    public void testSwapRow() {

        matrix = matrix.swapRow(0, 1);

        double[][] expectedData = {
                {4.0, 5.0, 6.0},
                {1.0, 2.0, 3.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix), "Result of swapping rows is incorrect.");
    }

    // Test swapCol
    @Test
    public void testSwapCol() {

        matrix = matrix.swapCol(0, 2);

        double[][] expectedData = {
                {3.0, 2.0, 1.0},
                {6.0, 5.0, 4.0}
        };
        assertArrayEquals(expectedData, getMatrixData(matrix), "Result of swapping columns is incorrect.");
    }

    // Test getDeterminantWithRowReduction
    @Test
    public void testGetDeterminantWithRowReduction(){
        Matrix matrix1 = new Matrix(4,4);
        double[][] initialData = {
                {3.0,6.0,9.0,3.0},
                {-1.0,0.0,1.0,0},
                {1.0,3.0,2.0,-1},
                {-1.0,-2.0,-2.0,1}
        };

        matrix1.setAllData(initialData);
        double determinant = matrix1.getDeterminantWithRowReduction();

        double expectedValue = -21;
        double tolerance = 0.002;
        assertEquals(expectedValue,determinant, tolerance);
    }

    // Test getRowEchelonForm
    @Test
    public void testGetRowEchelonForm(){
        Matrix matrix1 = new Matrix(3,4);
        double[][] initialData = {
                {1.0,1.0,2.0,4.0},
                {2.0,-1.0,1.0,2.0},
                {1.0,2.0,3.0,7.0}
        };

        matrix1.setAllData(initialData);
        Matrix result = matrix1.getRowEchelonForm();
        //result.printMatrix();

        double[][] expectedValue = {
                {1.0,1.0,2.0,4.0},
                {0.0,1.0,1.0,2.0},
                {0.0,0.0,0.0,1.0}
        };

        assertArrayEquals(expectedValue,result.getAllData());
    }

    // Test getRowEchelonForm
    @Test
    public void testGetReducedRowEchelonForm(){
        Matrix matrix1 = new Matrix(3,4);
        double[][] initialData = {
                {2.0,3.0,-1.0,5.0},
                {4.0,4.0,-3.0,3.0},
                {-2.0,3.0,-1.0,1.0}
        };

        matrix1.setAllData(initialData);
        Matrix result = matrix1.getReducedRowEchelonForm();
        //result.printMatrix();

        double[][] expectedValue = {
                {1.0,0.0,0.0,1.0},
                {0.0,1.0,0.0,2.0},
                {0.0,0.0,1.0,3.0}
        };

        assertArrayEquals(expectedValue,result.getAllData());
    }

    //  Helper
    private double[][] getMatrixData(Matrix matrix) {
    try {
        Field field = Matrix.class.getDeclaredField("data");
        field.setAccessible(true); // Make the private field accessible
        return (double[][]) field.get(matrix);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

}
