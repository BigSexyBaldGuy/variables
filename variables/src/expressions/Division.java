package expressions;

/**
 *  {@code Division} is the class that extends Expression to
 *  provide a quotient of two other expressions.
 *  @version 2022082900
 *  @author Richard Barton
 */
public class    Division extends Expression
{
	/**
	 *  The operator used for {@code Division}.
	 */
    public static final char	operator	= '/';
	/**
	 *  The ranking in order of precedence for {@code Division}.
	 */
    public static final int		priority	= 2;

    private Expression  dividend;
    private Expression  divisor;

    /**
     *  Construct an instance of {@code Division} that evaluates
     *  to the difference of its parameters.
     *  @param dividend The left-hand operand of the quotient.
     *  @param divisor The right-hand operand of the quotient.
     */
    public      Division(Expression dividend, Expression divisor)
    {
        this.dividend = dividend;
        this.divisor = divisor;
    }

    /**
     *  Return the current quotient of our operands.
     *  @return The current quotient of our operands.
     */
    public int  getValue()
    {
        return(dividend.getValue() / divisor.getValue());
    }

    /**
     *  Return our expression as a {@code String}.
     *  @return Our expression as a {@code String}.
     */
    public String       toString()
    {
        return(dividend + " " + operator + " " + divisor);
    }

    /**
     *  Unit test our {@code Division} expression.
     *  @param arg Command line arguments
     */
    public static void  main(String arg[])
    {
        int         errors;
        Integer     seven;
        Integer     three;
        int         getValue;
        int         parsedValue;
        String      toString;
        Expression  expression;
        String		expressionToTest;

        seven = new Integer("7");
        three = new Integer("3");
        expressionToTest = "7 " + operator + " 3";

        errors = 0;

        expression = new Division(seven, three);
        getValue = expression.getValue();
        System.out.println("Dividing 3 into 7: " + getValue);
        if (getValue != 2) {
            System.out.println("*** ERROR *** getValue()" +
                               " returns " + getValue +
                               " should be " + 2);
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
