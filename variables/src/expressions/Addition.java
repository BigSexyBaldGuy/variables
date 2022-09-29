package expressions;

/**
 *  {@code Addition} is the class that extends Expression to
 *  provide a sum of two other expressions.
 *  @version 2022081900
 *  @author Richard Barton
 */
public class    Addition extends Expression
{
	/**
	 *  The operator used for {@code Addition}.
	 */
    public static final char	operator	= '+';
	/**
	 *  The ranking in order of precedence for {@code Addition}.
	 */
    public static final int		priority	= 1;

    private Expression  augend;
    private Expression  addend;

    /**
     *  Construct an instance of {@code Addition} that evaluates
     *  to the sum of its parameters.
     *  @param augend The left-hand operand of the sum.
     *  @param addend The right-hand operand of the sum.
     */
    public      Addition(Expression augend, Expression addend)
    {
        this.augend = augend;
        this.addend = addend;
    }

    /**
     *  Return the current sum of our operands.
     *  @return The current sum of our operands.
     */
    public int  getValue()
    {
        return(augend.getValue() + addend.getValue());
    }

    /**
     *  Return our expression as a {@code String}.
     *  @return Our expression as a {@code String}.
     */
    public String       toString()
    {
        return(augend + " " + operator + " " + addend);
    }

    /**
     *  Unit test our {@code Addition} expression.
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
        expressionToTest = "1 " + operator + " 2";

        errors = 0;

        expression = new Addition(one, two);
        getValue = expression.getValue();
        System.out.println("Adding 1 & 2: " + getValue);
        if (getValue != 3) {
            System.out.println("*** ERROR *** getValue()" +
                               " returns " + getValue +
                               " should be " + 3);
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
