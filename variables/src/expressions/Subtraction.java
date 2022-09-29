package expressions;

/**
 *  {@code Subtraction} is the class that extends Expression to
 *  provide a difference of two other expressions.
 *  @version 2022082600
 *  @author Richard Barton
 */
public class    Subtraction extends Expression
{
	/**
	 *  The operator used for {@code Subtraction}.
	 */
    public static final char	operator	= '-';
	/**
	 *  The ranking in order of precedence for {@code Subtraction}.
	 */
    public static final int		priority	= 1;

    private Expression  minuend;
    private Expression  subtrahend;

    /**
     *  Construct an instance of {@code Subtraction} that evaluates
     *  to the difference of its parameters.
     *  @param minuend The left-hand operand of the difference.
     *  @param subtrahend The right-hand operand of the difference.
     */
    public      Subtraction(Expression minuend, Expression subtrahend)
    {
        this.minuend = minuend;
        this.subtrahend = subtrahend;
    }

    /**
     *  Return the current difference of our operands.
     *  @return The current difference of our operands.
     */
    public int  getValue()
    {
        return(minuend.getValue() - subtrahend.getValue());
    }

    /**
     *  Return our expression as a {@code String}.
     *  @return Our expression as a {@code String}.
     */
    public String       toString()
    {
        return(minuend + " " + operator + " " + subtrahend);
    }

    /**
     *  Unit test our {@code Subtraction} expression.
     *  @param arg Command line arguments
     */
    public static void  main(String arg[])
    {
        int         errors;
        Integer     one;
        Integer     two;
        int         getValue;
        int         parsedValue;
        String      toString;
        Expression  expression;
        String		expressionToTest;

        one = new Integer("1");
        two = new Integer("2");
        expressionToTest = "2 " + operator + " 1";

        errors = 0;

        expression = new Subtraction(two, one);
        getValue = expression.getValue();
        System.out.println("Subtracting 1 from 2: " + getValue);
        if (getValue != 1) {
            System.out.println("*** ERROR *** getValue()" +
                               " returns " + getValue +
                               " should be " + 1);
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
