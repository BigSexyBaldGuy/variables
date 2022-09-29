package expressions;

/**
 *  {@code Parentheses} is the class that extends Expression to
 *  provide a parenthetical subexpression.
 *  @version 2022083100
 *  @author Richard Barton
 */
public class    Parentheses extends Expression
{
	/**
	 *  The operator used to open {@code Parentheses}.
	 */
    public static final char	openOperator		= '(';
	/**
	 *  The operator used to close {@code Parentheses}.
	 */
    public static final char	closeOperator		= ')';
	/**
	 *  The ranking in order of precedence for {@code Parentheses} --
	 *  the {@code openOperator} specifically.
	 */
    public static final int		priority			= 3;

    private Expression  subexpression;

    /**
     *  Construct an instance of {@code Parentheses} that evaluates
     *  its subexpression.
     *  @param subexpression The subexpression
     */
    public      Parentheses(Expression subexpression)
    {
        this.subexpression = subexpression;
    }

    /**
     *  Return the current value of our subexpression.
     *  @return The current value of our subexpression.
     */
    public int  getValue()
    {
        return(subexpression.getValue());
    }

    /**
     *  Return our subexpression as a {@code String}.
     *  @return Our subexpression as a {@code String}.
     */
    public String       toString()
    {
        return("" +
        	   openOperator + subexpression + closeOperator);
    }

    /**
     *  Unit test our {@code Parentheses} expression.
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
        expressionToTest = openOperator + "1 " + Addition.operator +
        						" 2" + closeOperator;

        errors = 0;

        expression = new Parentheses(new Addition(one, two));
        getValue = expression.getValue();
        System.out.println("Parenthetically adding 1 & 2: " +
                           getValue);
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
