package algeo01_23002.solvers;
import algeo01_23002.types.LinearSystemSolution;
import algeo01_23002.types.Matrix;
import algeo01_23002.types.ParametricSolution;
import algeo01_23002.types.UniqueSolution;

public class LinearSystemSolver {
    private boolean isAllZero(double[] row){
        int len = row.length;
        for (int i = 0; i < len; i++){
            if (row[i] != 0) return false;
        }
        return true;
    }
    private double[] substractArray (double[] arr1, double[] arr2){
        double[] res = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++){
            res[i] = arr1[i] - arr2[i];
        }
        return res;
    }

    private double[] multArrayWithConst (double[] arr1, double k){
        double[] res = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++){
            res[i] = arr1[i] * k;
        }
        return res;
    }

    private boolean isNULL(double[] row){
        int len = row.length;
        if (row[0] != -999999999){
            return false;
        }
        for (int i = 1; i < len; i++){
            if (row[i] != 0) return false;
        }
        return true;
    }

    public LinearSystemSolution gaussianElimination (Matrix matrix){
        int rows = matrix.getRowsCount();
        int cols = matrix.getColsCount();

        matrix.getRowEchelonForm();
        double[][] data = matrix.getAllData();

        boolean isManySolutions = (isAllZero(data[rows - 1]) && rows < cols) || rows < cols - 1;
        //if last row contains all zero but the rows
        // then there are many solutions


        if (!isManySolutions){ //if there is only one solution

            double[][] result = new double[1][cols-1]; //initialize array to save the result

            result[0][cols-2] = data[rows-1][cols-1]; //data of last row and lat column for last variable
            for (int row = cols-2 ; row >= 0; row--){ //backward elimination
                result[0][row] = data[row][cols - 1];
                for (int col = cols-2; col>row; col--){
                    result[0][row] -= data[row][col]*result[0][col];
                }
            }

            Matrix resultMatrixUniqueSolution = new Matrix(1,cols-1);
            resultMatrixUniqueSolution.setAllData(result);

            return new UniqueSolution(resultMatrixUniqueSolution);

        } else { // if there are many solutions

            String[][] resultParametric;
            //get the index of last row that not all zero and count how many zero rows
            int countZeroRows = 0;
            int idxlastRowNotZero = rows-1; // this is used as starting row in backward elimination
            for (int row = rows-1; row >= 0; row--){
                if (isAllZero(data[row])){
                    countZeroRows++;
                } else {
                    idxlastRowNotZero = row;
                    break;
                }
            }

            //count how many parameter is needed
            int countParameter = (rows - 1) - countZeroRows;

            //make result array
            //index 0 of result array is used to store constant and the rest is used to store the coefficient of parameter
            double[][] result = new double[cols-1][countParameter+1];

            //initiate the result array with null mark (-999999999 for constant, and 0 for parameter's coefficient)
            for(int i=0; i<cols-1; i++){
                result[i][0] = -999999999;
            }
            for (int i=0; i<cols-1; i++){
                for(int j=1; j<=countParameter; j++){
                    result[i][j] = 0;
                }
            }


            int parameter = 1; //initiate parameter index

            //iterate through all rows starting from last row that not all zero
            for (int row = idxlastRowNotZero; row >= 0; row--){
                //find leading one index
                int indexOfLeadingOne = cols;
                for(int col=0; col < cols; col++){
                    if (data[row][col] == 1){
                        indexOfLeadingOne = col;
                        break;
                    }
                }

                //find solution by iterating through cols in a certain row
                result[indexOfLeadingOne][0] = data[row][cols-1];
                for (int col = indexOfLeadingOne+1; col < cols-1; col++){

                    if (isNULL(result[col])) { //if the result of a variable is null, assign parameter
                        result[col][0] = 0;
                        result[col][parameter] = 1;
                        parameter++;
                    }
                    result[indexOfLeadingOne] = substractArray(result[indexOfLeadingOne], multArrayWithConst(result[col], data[row][col]));
                }

            }

            //move the result to resultParametric that has type String
            resultParametric = new String[cols-1][countParameter+1];

            //for constant, just move result to resultParametric
            for (int i=0; i<resultParametric.length; i++){
                resultParametric[i][0] = String.valueOf(result[i][0]);
            }

            //for parametric coefficient, append result with a character first, then assign it to resultParametric
            for (int i=0; i<resultParametric.length; i++) {
                int ascii = 113+countParameter;
                for (int j = 1; j < resultParametric[0].length; j++) {
                    resultParametric[i][j] = String.valueOf(result[i][j]) + String.valueOf((char) ascii); //append result with ascii character
                    ascii--;
                }

            }
            return new ParametricSolution(resultParametric);
        }
    }

    public LinearSystemSolution gaussJordanElimination(Matrix matrix) {
        int rows = matrix.getRowsCount();
        int cols = matrix.getColsCount();

        matrix.getRowEchelonForm();
        double[][] data = matrix.getAllData();

        boolean isManySolutions = (isAllZero(data[rows - 1]) && rows < cols) || rows < cols - 1;
        //if last row contains all zero but the rows
        // then there are many solutions


        if (!isManySolutions){ //if there is only one solution

            double[][] result = new double[1][cols-1]; //initialize array to save the result

            for(int row=0; row<rows; row++){
                result[0][row] = data[row][cols-1];
            }

            //move the result to resultParametric that has type String
            Matrix resultMatrixUniqueSolution = new Matrix(1, cols-1);
            resultMatrixUniqueSolution.setAllData(result);

            return new UniqueSolution(resultMatrixUniqueSolution);


        } else { // if there are many solutions

            String[][] resultParametric;
            //get the index of last row that not all zero and count how many zero rows
            int countZeroRows = 0;
            int idxlastRowNotZero = rows-1; // this is used as starting row in backward elimination
            for (int row = rows-1; row >= 0; row--){
                if (isAllZero(data[row])){
                    countZeroRows++;
                } else {
                    idxlastRowNotZero = row;
                    break;
                }
            }

            //count how many parameter is needed
            int countParameter = (rows - 1) - countZeroRows;

            //make result array
            //index 0 of result array is used to store constant and the rest is used to store the coefficient of parameter
            double[][] result = new double[cols-1][countParameter+1];

            //initiate the result array with null mark (-999999999 for constant, and 0 for parameter's coefficient)
            for(int i=0; i<cols-1; i++){
                result[i][0] = -999999999;
            }
            for (int i=0; i<cols-1; i++){
                for(int j=1; j<=countParameter; j++){
                    result[i][j] = 0;
                }
            }


            int parameter = 1; //initiate parameter index

            //iterate through all rows starting from last row that not all zero
            for (int row = idxlastRowNotZero; row >= 0; row--){
                //find leading one index
                int indexOfLeadingOne = cols;
                for(int col=0; col < cols; col++){
                    if (data[row][col] == 1){
                        indexOfLeadingOne = col;
                        break;
                    }
                }

                //find solution by iterating through cols in a certain row
                result[indexOfLeadingOne][0] = data[row][cols-1];
                for (int col = indexOfLeadingOne+1; col < cols-1; col++){

                    if (isNULL(result[col])) { //if the result of a variable is null, assign parameter
                        result[col][0] = 0;
                        result[col][parameter] = 1;
                        parameter++;
                    }
                    result[indexOfLeadingOne] = substractArray(result[indexOfLeadingOne], multArrayWithConst(result[col], data[row][col]));
                }

            }

            //move the result to resultParametric that has type String
            resultParametric = new String[cols-1][countParameter+1];

            //for constant, just move result to resultParametric
            for (int i=0; i<resultParametric.length; i++){
                resultParametric[i][0] = String.valueOf(result[i][0]);
            }

            //for parametric coefficient, append result with a character first, then assign it to resultParametric
            for (int i=0; i<resultParametric.length; i++) {
                int ascii = 113+countParameter;
                for (int j = 1; j < resultParametric[0].length; j++) {
                    resultParametric[i][j] = String.valueOf(result[i][j]) + String.valueOf((char) ascii); //append result with ascii character
                    ascii--;
                }

            }
            return new ParametricSolution(resultParametric);
        }
    }

    public LinearSystemSolution cramersRule(Matrix matrix, Matrix constant){

        // Cramer's rule can be used IF MATRIX is SQUARE and CONSTANT have same LENGTH as MATRIX
        if(!(matrix.isSquare() && matrix.getRowsCount() == constant.getColsCount())){
            throw new IllegalArgumentException("creamersRule() : Solution could not be calculated (dimension incompatible)");
        }
        double actualDeterminant = matrix.getDeterminantWithCofactor(); // Can be change with rowReduction methode
        if(actualDeterminant == 0){
            throw new IllegalArgumentException("cramersRule() : Solution could not be calculated");
        }
        double tempDeterminant;
        Matrix solutions = new Matrix(1, matrix.getColsCount());

        for(int i = 0; i < constant.getColsCount(); i++){
            Matrix temp = matrix.getCopy();
            for(int j = 0; j < matrix.getColsCount(); j++){
                temp.getAllData()[j][i] = constant.getAllData()[0][j]; // fill the columns with constant
            }
            tempDeterminant = temp.getDeterminantWithCofactor();
            solutions.getAllData()[0][i] = tempDeterminant / actualDeterminant;
        }
        return new UniqueSolution(solutions.transpose());
    }

    public LinearSystemSolution inverseMethod(Matrix matrix){
        Matrix equationVariables = new Matrix(matrix.getRowsCount(), matrix.getColsCount() - 1);
        if(!equationVariables.isSquare()){
            throw new IllegalArgumentException("inverseMethod() : Solution could not be calculated (wrong dimension for inverse method)");
        }
        Matrix equationResult = new Matrix(matrix.getRowsCount(), 1);

        // Fill the equation matrix
        for(int i = 0; i < matrix.getRowsCount(); i++){
            for(int j = 0; j < matrix.getColsCount(); j++){
                if(j == matrix.getColsCount() - 1){
                    equationResult.setData(i, 0, matrix.getData(i, j));
                } else {
                    equationVariables.setData(i, j, matrix.getData(i, j));
                }
            }
        }
        Matrix result = equationVariables.getInverseWithRowReduction().multiplyByMatrix(equationResult);

        return new UniqueSolution(result);
    }
}
