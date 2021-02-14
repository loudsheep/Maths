package matrix;

/**
 * Exception class representing basic matrices error
 */
public class MatricesNotAlignedError extends Exception {
    private final String detail;

    /**
     * Constructor
     *
     * @param d Message displayed when exception has occurred
     */
    MatricesNotAlignedError(String d) {
        detail = ": " + d;
    }

    /**
     * Constructor
     */
    MatricesNotAlignedError() {
        detail = "";
    }

    /**
     * String representation of exception class
     *
     * @return string
     */
    public String toString() {
        return "MatricesNotAlignedError " + detail;
    }
}
