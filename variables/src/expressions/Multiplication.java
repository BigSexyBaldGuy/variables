package expressions;

/**
 *  {@code Multiplication} is the class that extends Expression to
 *  provide a product of two other expressions.
 *  @version 2022082600
 *  @author Richard Barton
 */
public class    Multiplication extends Expression
{
	/**
	 *  The operator used for {@code Multiplication}.
	 */
    public static final char	operator	= '*';
	/**
	 *  The ranking in order of precedence for {@code Multiplication}.
	 */
    public static final int		priority	= 2;

    private Expression  multiplicand;
    private Expression  multiplier;

    /**
     *  Construct an instance of {@code Multiplication} that evaluates
     *  to the product of its parameters.
     *  @param multiplicand The left-hand operand of the product.
     *  @param multiplier The right-hand operand of the product.
     */
    public      Multiplication(Expression multiplicand,
                               Expression multiplier)
    {
        this.multiplicand = multiplicand;
        this.multiplier = multiplier;
    }

    /**
     *  Return the current product of our operands.
     *  @return The current product of our operands.
     */
    public int  getValue()
    {
        return(multiplicand.getValue() * multiplier.getValue());
    }

    /**
     *  Return our expression as a {@code String}.
     *  @return Our expression as a {@code String}.
     */
    public String       toString()
    {
        return(multiplicand + " " + operator + " " + multiplier);
    }

    /**
     *  Unit test our {@code Multiplication} expression.
     *  @param arg Command line arguments
     */
    public static void  main(String arg[])
    {
        int         errors;
        Integer     two;
        Integer     three;
        int         getValue;
        int         parsedValue;
        String      toString;
        Expression  expression;
        String		expressionToTest;

        two = new Integer("2");
        three = new Integer("3");
        expressionToTest = "2 " + operator + " 3";

        errors = 0;

        expression = new Multiplication(two, three);
        getValue = expression.getValue();
        System.out.println("Multiplying 2 & 3: " + getValue);
        if (getValue != 6) {
            System.out.println("*** ERROR *** getValue()" +
                               " returns " + getValue +
                               " should be " + 6);
            ++errors;
        }

        toString = "" + expression;
        if (toString.equals(expressionToTest) == false) {
            System.out.println("*** ERROR *** toString()" +
                               " returns " + toString +
                               " should be " + expressionToTest);
            ++errors;
        }

        if (errors > 0) {
            System.out.println("\nUNIT TEST FAILED with " +
                               errors + " errors!");
            System.exit(1);
        }
    }
}
