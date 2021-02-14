package matrix;

/**
 * functional interface representing function that takes one float argument and return float
 */
interface FloatFunc {
    float func(float x);
}

/**
 * Class representing matrices, and most math methods
 */
public class Matrix {
    private final int rows;
    private final int cols;
    private float[][] data;

    /**
     * Matrix class constructor
     *
     * @param rows    represent number of rows in the matrix
     * @param columns represent number of rows in the matrix
     */
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.cols = columns;
        this.data = new float[rows][columns];
    }

    /**
     * Randomize all matrix elements in given range
     *
     * @param min range minimum value
     * @param max range maximum value
     * @return reference to this object
     */
    public Matrix randomize(int min, int max) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = (float) Math.random() * (max - min + 1) + min;
            }
        }
        return this;
    }

    /**
     * Randomize all matrix elements in range 0.0 to 1.0
     *
     * @return reference to this object
     */
    public Matrix randomize() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = (float) Math.random();
            }
        }
        return this;
    }

    /**
     * Apply function to all matrix elements
     *
     * @param f function with float as parameter and float as return value
     * @return reference to this object
     */
    public Matrix apply(FloatFunc f) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = f.func(data[i][j]);
            }
        }
        return this;
    }

    /**
     * Creates Matrix from two-dimensional array
     *
     * @param arr two-dimensional float array from which Matrix is crated
     * @return Matrix object representing array as matrix
     * @throws MatricesNotAlignedError
     */
    public static Matrix fromArray(float[][] arr) throws MatricesNotAlignedError {
        Matrix m = new Matrix(arr.length, arr[0].length);
        m.setData(arr);
        return m;
    }

    /**
     * Creates Matrix from one-dimensional array (data is stored in one row)
     *
     * @param arr one-dimensional float array from which Matrix is created
     * @return Matrix object representing array as matrix
     */
    public static Matrix fromArray(float[] arr) {
        float[][] new_arr = new float[1][arr.length];
        for (int i = 0; i < arr.length; i++) {
            new_arr[0][i] = arr[i];
        }
        Matrix res = new Matrix(new_arr.length, new_arr[0].length);
        res.safeSetData(new_arr);
        return res;
    }

    /**
     * Creates two-dimensional array from Matrix
     *
     * @return two-dimensional array
     */
    public float[][] to2DArray() {
        return data;
    }

    /**
     * If one of Matrix size is equal to 1 creates one-dimensional array with Matrix elements in it
     *
     * @return one-dimensional array
     * @throws MatricesNotAlignedError when one-dimensional array not available
     */
    public float[] to1DArray() throws MatricesNotAlignedError {
        float[] new_arr;
        if (data.length == 1) {
            new_arr = new float[data[0].length];
            for (int i = 0; i < data[0].length; i++) {
                new_arr[i] = data[0][i];
            }
        } else if (data[0].length == 1) {
            new_arr = new float[data.length];
            for (int i = 0; i < data.length; i++) {
                new_arr[i] = data[i][0];
            }
        } else {
            throw new MatricesNotAlignedError("1D array not available");
        }
        return new_arr;
    }

    /**
     * Get Matrix number of rows
     *
     * @return int
     */
    public int getSizeRows() {
        return rows;
    }

    /**
     * Get Matrix number of columns
     *
     * @return int
     */
    public int getSizeColumns() {
        return cols;
    }

    /**
     * Return element at position row, column
     *
     * @param row    int, first position
     * @param column int, second position
     * @return float
     */
    public float getData(int row, int column) {
        return data[row][column];
    }

    private void safeSetData(float[][] data) {
        this.data = data;
    }

    /**
     * Set element value at position row, column to value
     *
     * @param value  float
     * @param row    int, first position
     * @param column int, second position
     * @return reference to this object
     * @throws ArrayIndexOutOfBoundsException
     */
    public Matrix setData(float value, int row, int column) throws ArrayIndexOutOfBoundsException {
        if (row > this.rows || row < 0 || column > this.cols || column < 0) throw new ArrayIndexOutOfBoundsException();
        data[row][column] = value;
        return this;
    }

    /**
     * Set Matrix elements to d
     *
     * @param d float two-dimensional array
     * @return reference to this object
     * @throws MatricesNotAlignedError
     */
    public Matrix setData(float[][] d) throws MatricesNotAlignedError {
        if (d.length != data.length || d[0].length != data[0].length)
            throw new MatricesNotAlignedError("Dimensions not aligned");
        data = d;
        return this;
    }

    /**
     * Add a scalar to Matrix
     *
     * @param num float
     * @return reference to this object
     */
    public Matrix add(float num) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] += num;
            }
        }
        return this;
    }

    /**
     * Add matrix to this object
     *
     * @param m Matrix object
     * @return reference to this object
     * @throws MatricesNotAlignedError
     */
    public Matrix add(Matrix m) throws MatricesNotAlignedError {
        if (m.getSizeRows() != this.getSizeRows() || m.getSizeColumns() != this.getSizeColumns())
            throw new MatricesNotAlignedError("Dimensions not aligned");
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] += m.getData(i, j);
            }
        }
        return this;
    }

    /**
     * Add two matrices together
     *
     * @param a Matrix, first matrix
     * @param b Matrix, second matrix
     * @return New Matrix object
     * @throws MatricesNotAlignedError
     */
    public static Matrix add(Matrix a, Matrix b) throws MatricesNotAlignedError {

        if (a.getSizeRows() != b.getSizeRows() || b.getSizeColumns() != a.getSizeColumns())
            throw new MatricesNotAlignedError("Dimensions not aligned");

        Matrix res = new Matrix(a.getSizeRows(), a.getSizeColumns());
        for (int i = 0; i < a.getSizeRows(); i++) {
            for (int j = 0; j < a.getSizeColumns(); j++) {
                res.setData(a.getData(i, j) + b.getData(i, j), i, j);
            }
        }
        return res;
    }

    /**
     * Subtract a scalar to Matrix
     *
     * @param num float
     * @return reference to this object
     */
    public Matrix sub(float num) {
        this.add(-num);
        return this;
    }

    /**
     * Subtract matrix to this object
     *
     * @param m Matrix object
     * @return reference to this object
     * @throws MatricesNotAlignedError
     */
    public Matrix sub(Matrix m) throws MatricesNotAlignedError {
        if (m.getSizeRows() != this.getSizeRows() || m.getSizeColumns() != this.getSizeColumns())
            throw new MatricesNotAlignedError("Dimensions not aligned");
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] -= m.getData(i, j);
            }
        }
        return this;
    }

    /**
     * Subtract two matrices together
     *
     * @param a Matrix, first matrix
     * @param b Matrix, second matrix
     * @return New Matrix object
     * @throws MatricesNotAlignedError
     */
    public static Matrix sub(Matrix a, Matrix b) throws MatricesNotAlignedError {

        if (a.getSizeRows() != b.getSizeRows() || b.getSizeColumns() != a.getSizeColumns())
            throw new MatricesNotAlignedError("Dimensions not aligned");

        Matrix res = new Matrix(a.getSizeRows(), a.getSizeColumns());
        for (int i = 0; i < a.getSizeRows(); i++) {
            for (int j = 0; j < a.getSizeColumns(); j++) {
                res.setData(a.getData(i, j) - b.getData(i, j), i, j);
            }
        }
        return res;
    }

    /**
     * Multiply matrix by a scalar
     *
     * @param num float
     * @return reference to this object
     */
    public Matrix mult(float num) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] *= num;
            }
        }
        return this;
    }

    /**
     * Multiply this Matrix by m Matrix
     *
     * @param m Matrix to multiply by
     * @return reference to this object
     * @throws MatricesNotAlignedError
     * @see <a href="https://en.wikipedia.org/wiki/Hadamard_product_(matrices)">Hadamard product</a>
     */
    public Matrix mult(Matrix m) throws MatricesNotAlignedError {
        if (m.getSizeRows() != this.getSizeRows() || m.getSizeColumns() != this.getSizeColumns())
            throw new MatricesNotAlignedError("Dimensions not aligned");
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] *= m.getData(i, j);
            }
        }
        return this;
    }

    /**
     * Calculates Matrix product
     *
     * @param a first Matrix object
     * @param b second Matrix object
     * @return new Matrix obvject
     * @throws MatricesNotAlignedError
     * @see <a href="https://en.wikipedia.org/wiki/Matrix_multiplication">Matrix product</a>
     */
    public static Matrix mult(Matrix a, Matrix b) throws MatricesNotAlignedError {
        if (a.getSizeColumns() != b.getSizeRows()) throw new MatricesNotAlignedError("Dimensions not aligned");
        Matrix res = new Matrix(a.getSizeRows(), b.getSizeColumns());
        for (int i = 0; i < a.getSizeRows(); i++) {
            for (int j = 0; j < b.getSizeColumns(); j++) {
                float sum = 0;
                for (int k = 0; k < b.getSizeRows(); k++) {
                    sum += a.getData(i, k) * b.getData(k, j);
                }
                res.setData(sum, i, j);
            }
        }
        return res;
    }

    /**
     * Return transposed matrix based on this object
     *
     * @return New transposed matrix
     * @throws MatricesNotAlignedError
     * @see <a href="https://en.wikipedia.org/wiki/Transpose">Transpose Matrix</a>
     */
    public Matrix transpose() throws MatricesNotAlignedError {
        float[][] t = new float[cols][rows];
        for (int i = 0; i < cols; i++) {
            float[] d = new float[rows];
            for (int j = 0; j < rows; j++) {
                d[j] = data[j][i];
            }
            t[i] = d;
        }
        Matrix m = new Matrix(cols, rows);
        m.setData(t);
        return m;
    }

    /**
     * String representation
     *
     * @return String representing Matrix object
     */
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (float[] datum : data) {
            StringBuilder line = new StringBuilder();
            for (float v : datum) {
                line.append(v).append(" ");
            }
            res.append(line).append("\n");
        }
        return res.toString();
    }
}
