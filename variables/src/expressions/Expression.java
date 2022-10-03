package expressions;

import java.util.*;
import java.text.*;

/**
 *  {@code Expression} is the class that takes tokens from the parser
 *  and creates an object that can be evaluated.
 *  @version 2022081900
 *  @author Richard Barton
 */
public class    Expression
{
    private Expression  value;

    /**
     *  Constructor of an expression.
     *  @param string {@code String} containing the expression.
     *  @throws ParseException When the given parameter doesn't
     *  comply with the syntax.
     */
    public  Expression(String string)
            throws ParseException
    {
        String                  tokens[];
        Stack<String>           pendingOperations;
        Stack<Expression>       pendingOperands;

        /*
         *  Have the parser give us all the tokens.
         */
        tokens = Parse.parse(string);

        /*
         *  We need stacks to collect tokens into expressions.
         *  Expressions are in infix form as in
         *    operand operator operand.
         *  Each operand may be another expression.
         */
        pendingOperations = new Stack<>();
        pendingOperands = new Stack<>();

        /*
         *  Let's visit each token in order.
         */
        for (String nextToken : tokens) {
            if (Character.isDigit(nextToken.charAt(0)) == true) {
                /*
                 *  The parser guarantees that if the token starts
                 *  with a digit, it's all digits.  So, instantiate
                 *  an integer operand and push it on the stack to
                 *  save it until an operation tells us what to do
                 *  with it.
                 */

//                System.out.println("digit:  Pushing '" + nextToken +
//                                   "' as an operand");
                pendingOperands.push(new Integer(nextToken));
                continue;
            }

            /*
             *  We know it's not an integer.
             */

			//---------------------------------------------------------
			// 1 - ToDo: Remove these comments. They are temporary to
			//     ToDo: help code the requirements.
			// 1- Modify the Expression class to accept a token
			// beginning with a letter
			//---------------------------------------------------------
			if (Character.isAlphabetic(nextToken.charAt(0)) == true) {
				/*
				 *  The parser guarantees that if the token starts
				 *  with an alphabetic character, it must only
				 *  contain letters and numbers. So, instantiate
				 *  a variable operand and push it on the stack to
				 *  save it until an operation tells us what to do
				 *  with it.
				 */

				//------------------------------------------------------
				// (1a) - ToDo: Remove these comments. They are
				//        ToDo: temporary to help code the requirements.
				// (1a) as a variable by getting an instance of the
				// Variable class named after the token.
				//------------------------------------------------------
				Variable variable = Variable.get(nextToken);


				pendingOperands.push(variable);

				// pendingOperands.push(new Variable(nextToken));
				continue;
			}

			/*
			 *  Let's see if we can construct an expression from
			 *  what we've seen so far.
			 */
			constructOperation(nextToken, pendingOperations,
							   pendingOperands);

            /*
             *  This token becomes the next operation and all the
             *  operations we just constructed (if any) are its
             *  left-hand operand.
             */
            pendingOperations.push(nextToken);
//            System.out.println("op:  Pushing '" + nextToken +
//                               "' as an operation");
        }

        /*
         *  We've run out of tokens.  Construct whatever operations
         *  are left.
         */
		constructOperation(null, pendingOperations, pendingOperands);

        if (pendingOperations.empty() == false) {
			/*
			 *  There's an operation left!?
			 */
			throw(new ParseException("Don't know what to do with '" +
									 pendingOperations.pop() + "'",
									 0));
		}

        if (pendingOperands.empty() == true) {
			/*
			 *  There aren't any subexpressions left!?
			 */
			throw(new ParseException("Expression evaluated to nothing",
									 0));
		}

		/*
		 *  Assume the top operand is our value.
		 */
		value = pendingOperands.pop();
		if (pendingOperands.empty() == false) {
			/*
			 *  Having operands left is wrong.
			 */
			throw(new ParseException("Operands left over" +
									 " starting with \"" +
									 pendingOperands.pop() + "\"", 0));
		}
    }

    /**
     *  Constructor which does nothing for use by our subclasses.
     */
    public Expression()
    {
    }

    /*
     *  Helper method to create an expression from an operator
     *  and its operands.  The operator must be on the top of
     *  the pendingOperations stack and its operands must be
     *  at the top of the pendingOperands stack.  We're told what
     *  the next operation is to provide information for
     *  precedence. A null nextOperation means to do everything.
     */
    private void
    constructOperation(String nextOperation,
    				   Stack<String> pendingOperations,
                       Stack<Expression> pendingOperands)
                        throws ParseException
    {
		int		nextPriority;

		/*
		 *  Get the priority of the operation which follows
		 *  what's on the stack(s).
		 */
		nextPriority = priority(nextOperation);
		while (pendingOperations.empty() == false) {
			char		pendingOperator;
			String		pendingOperation;
			Expression  left;
			Expression  right;

			/*
			 *  Assume we're going to perform the operation
			 *  on the top of the stack and get its priority.
			 */
			pendingOperation = pendingOperations.pop();
			pendingOperator = pendingOperation.charAt(0);
			if ((pendingOperator != Parentheses.closeOperator) &&
				(nextPriority > priority(pendingOperation))) {
				/*
				 *  Close parentheses we'll always process.
				 *  Otherwise, if our next operation is higher
				 *  priority that the top of the stack, we're
				 *  going to wait to perform the operation on
				 *  the stack.
				 */
				pendingOperations.push(pendingOperation);
				break;
			}

			left = null;
			right = null;
			if (pendingOperator == Addition.operator) {
				if (pendingOperands.empty() == true) {
					throw(new ParseException("Missing right" +
											 " operand for " +
											 pendingOperation, 0));
				}
				right = pendingOperands.pop();

				if (pendingOperands.empty() == true) {
					throw(new ParseException("Missing left" +
											 " operand for " +
											 pendingOperation, 0));
				}
				left = pendingOperands.pop();

				pendingOperands.push(new Addition(left, right));
			} else if (pendingOperator == Subtraction.operator) {
				if (pendingOperands.empty() == true) {
					throw(new ParseException("Missing right" +
											 " operand for " +
											 pendingOperation, 0));
				}
				right = pendingOperands.pop();

				if (pendingOperands.empty() == true) {
					throw(new ParseException("Missing left" +
											 " operand for " +
											 pendingOperation, 0));
				}
				left = pendingOperands.pop();

				pendingOperands.push(new Subtraction(left, right));
			} else if (pendingOperator == Multiplication.operator) {
				if (pendingOperands.empty() == true) {
					throw(new ParseException("Missing right" +
											 " operand for " +
											 pendingOperation, 0));
				}
				right = pendingOperands.pop();

				if (pendingOperands.empty() == true) {
					throw(new ParseException("Missing left" +
											 " operand for " +
											 pendingOperation, 0));
				}
				left = pendingOperands.pop();

				pendingOperands.push(new Multiplication(left, right));
			} else if (pendingOperator == Division.operator) {
				if (pendingOperands.empty() == true) {
					throw(new ParseException("Missing right" +
											 " operand for " +
											 pendingOperation, 0));
				}
				right = pendingOperands.pop();

				if (pendingOperands.empty() == true) {
					throw(new ParseException("Missing left" +
											 " operand for " +
											 pendingOperation, 0));
				}
				left = pendingOperands.pop();

				pendingOperands.push(new Division(left, right));
			} else if (pendingOperator == Parentheses.openOperator) {
				pendingOperations.push(pendingOperation);
				return;
			} else if (pendingOperator == Parentheses.closeOperator) {
				if (pendingOperands.empty() == true) {
					throw(new ParseException("Missing parenthetical" +
											 " subexpression", 0));
				}
				left = pendingOperands.pop();

				pendingOperands.push(new Parentheses(left));

				if ((pendingOperations.empty() == true) ||
					(pendingOperations.pop().charAt(0) !=
								Parentheses.openOperator)) {
					/*
					 *  We just handled the parenthetical
					 *  expression.  So, the next operator on
					 *  the stack should be the open parentheses.
					 *  But, it's not.
					 */
					throw(new ParseException("Where's the '" +
											 Parentheses.
											 	openOperator + "'?",
											 0));
				}
			} else {
				throw(new ParseException("Don't understand" +
										 " operation '" +
										 pendingOperation + "'", 0));
			}
		}
    }

    /*
     *  Helper method to lookup the priority of a given operation.
     */
    private int	priority(String operation)
    {
		int		returnValue;
		char	givenOperator;

		returnValue = -1;
		if (operation == null) {
			return(returnValue);
		}

		givenOperator = operation.charAt(0);
		if (givenOperator == Addition.operator) {
			returnValue = Addition.priority;
		} else if (givenOperator == Subtraction.operator) {
			returnValue = Subtraction.priority;
		} else if (givenOperator == Multiplication.operator) {
			returnValue = Multiplication.priority;
		} else if (givenOperator == Division.operator) {
			returnValue = Division.priority;
		} else if (givenOperator == Parentheses.openOperator) {
			returnValue = Parentheses.priority;
		}

		return(returnValue);
	}

    /**
     *  Evaluate the expression and return its value.
     *  @return Integer which represents the current value of
     *  the expression.
     */
    public int getValue()
    {
        if (value != null) {
            return(value.getValue());
        }

        return(0);
    }

    /**
     *  Return a {@code String} representation of the expression.
     *  @return {@code String} which represents the expression.
     */
    public String       toString()
    {
        if (value != null) {
            return("" + value);
        }

        return(null);
    }

    /**
     *  Unit test our {@code Expression} constructor.
     *  @param arg Command line arguments
     */
    public static void  main(String arg[])
    {
        int             errors;
        int             whichTest;
        /*
         *  The items in this array are expressions to evaluate.
         *  Each corresponds to the list of results in the
         *  following at the same index.
         */
        String          toTest[]    = {
					"6 + 5",
					"X + 5",
					"X + 7",
                };
        /*
         *  The items in this array are the results of evaluating the
         *  corresponding expressions at the same index in the
         *  previous array.  A -99 value means we expect an exception.
         */
        int             results[]   = {
					11,
					93,
					95,
                };


        if (toTest.length != results.length) {
            /*
             *  Someone changed the expressions to evaluate or
             *  results without changing the other.
             */
            System.out.println("*** ERROR *** " +
                               toTest.length +
                               " expressions to evaluate and " +
                               results.length +
                               " results");
            System.exit(1);
        }

        /*
         *  Go through each test.
         */
        errors = 0;
        for (whichTest = 0; (whichTest < toTest.length);
             ++whichTest) {
            int         getValue;
            String      toString;
            Expression  expression;

            System.out.println("Trying to evaluate:\n" +
                               "  \"" + toTest[whichTest] +
                               "\"");

            try {
                expression = new Expression(toTest[whichTest]);
            } catch (Exception exception) {
                /*
                 *  Got an exception.
                 */
                if ((exception instanceof ParseException) != true) {
                    /*
                     *  It's the wrong exception.
                     */
                    System.out.println("*** ERROR ***");
                    ++errors;
                } else if (results[whichTest] != -99) {
                    /*
                     *  We weren't expecting this Parse Exception.
                     */
                    System.out.println("*** ERROR *** at position " +
                                       ((ParseException)exception).
                                                getErrorOffset());
                    ++errors;
                }
                exception.printStackTrace(System.out);
                continue;
            }

            getValue = expression.getValue();
            System.out.println("Evaluates to: " + getValue);

            if (getValue != results[whichTest]) {
                System.out.println("*** ERROR *** getValue()" +
                                   " returns " + getValue +
                                   " should be " + results[whichTest]);
                ++errors;
            }

            toString = "" + expression;
            if (toString.equals(toTest[whichTest]) == false) {
                System.out.println("*** ERROR *** toString()" +
                                   " returns " + toString +
                                   " should be " + toTest[whichTest]);
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
