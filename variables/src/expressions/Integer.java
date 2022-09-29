package expressions;

/**
 *  {@code Integer} is the class that extends {@code Expression} to
 *  provide an integer constant value.
 *  @version 2022081900
 *  @author Richard Barton
 */
public class    Integer extends Expression
{
    private int value;

    /**
     *  Construct an {@code Integer} instance with the value
     *  given as the parameter.
     *  @param value The value of this instance.
     */
    public      Integer(String value)
    {
        this.value = java.lang.Integer.parseInt(value);
    }

    /**
     *  Return our value.
     *  @return The value we were given when instantiated.
     */
    public int  getValue()
    {
        return(value);
    }

    /**
     *  Return our value as a {@code String}.
     *  @return The value we were given when instantiated as a
     *  {@code String}.
     */
    public String       toString()
    {
        return("" + value);
    }

    /**
     *  Unit test our {@code Integer} expression.
     *  @param arg Command line arguments
     */
    public static void  main(String arg[])
    {
        int             errors;
        String          valuesToTest[]  = {
                                    "1234",
                                };

        errors = 0;
        for (String which : valuesToTest) {
            int         getValue;
            int         parsedValue;
            String      toString;
            Expression  expression;

            expression = new Integer(which);
            getValue = expression.getValue();
            System.out.println(which + ": " + getValue);

            parsedValue = java.lang.Integer.parseInt(which);
            if (getValue != parsedValue) {
                System.out.println("*** ERROR *** getValue()" +
                                   " returns " + getValue +
                                   " should be " + parsedValue);
                ++errors;
            }

            toString = "" + expression;
            if (toString.equals(which) == false) {
                System.out.println("*** ERROR *** toString()" +
                                   " returns " + toString +
                                   " should be " + which);
                ++errors;
            }
        }

        if (errors > 0) {
            System.out.println("\nUNIT TEST FAILED with " +
                               errors + " errors!");
            System.exit(1);
        }
    }
}
