package expressions;

import java.util.Hashtable;
import java.util.Objects;

/**
 *  {@code Variable} is the class that extends Expression to
 *  store a name, hashcode and value for the instance.
 *  @version 2022092900
 *  @author David Simmons
 */

//---------------------------------------------------------------------
// 2 - ToDo: Remove these comments. They are temporary to help
// ToDo: code the requirements.
// Add a Variable class that extends the Expression class and
// resides in its own source file. Each variable has a name and each
// name is unique among all variables.
//---------------------------------------------------------------------
public class Variable extends Expression
{
    // ToDo: Remove these comments. They are temporary to help
    // ToDo: code the requirements.
    // No javadoc needed since these are private
    // Private with no keyword makes these instance variables

    //---------------------------------------------------------------
    // **** Private without static keyword makes these instance
    //      variables ******
    //---------------------------------------------------------------
    // 2A - It needs a private instance variable to store its name
    //---------------------------------------------------------------
    private String name = "";
    //---------------------------------------------------------------
    // 2B - It needs a private instance variable to store an instance
    // of its value.
    //---------------------------------------------------------------
    private Expression value;
    //---------------------------------------------------------------
    // 2C - It needs a private instance variable to store its hash
    // code value as in our Review package.
    //---------------------------------------------------------------
    private int	variableHashcode;

    //----------------------------------------------------------------
    // 2D - It needs a private class variable to contain a collection
    // of all variables by name (consider using a HashTable).
    // ****** The static keyword makes this a class variable ******
    //----------------------------------------------------------------
    private static Hashtable<String, Expression>
            variableHashTable
            = new Hashtable<String, Expression>();


    //------------------------------------------------------------------
    // 2E - It has a private constructor that takes a String and
    // saves it as its name. Also, it calculates its hash code based
    // on its name (as in the Review package).
    //------------------------------------------------------------------

    // This happens every time the class is instantiated.
    private Variable(String name)
    {
        this.name = name;
        this.value = this;
        variableHashcode = Objects.hashCode(name);
    }

    //------------------------------------------------------------------
    // 2F - It has a public static get() method that takes a single
    // String parameter as the name of a variable and returns an
    // instance of Variable. It looks up the given name in its
    // collection. If the name is found in its collection, the
    // corresponding Variable instance is returned. If the name is not
    // found in its collection, a new instance of Variable is
    // instantiated with the given name, added to the collection and
    // returned.
    //------------------------------------------------------------------
    public static Variable get(String name){
        // Need to try and lookup the instance for the name in the
        // hashtable. If it finds it, return the stored instance
        // of variable. If it is not found, create a new instance and
        // add the name and new instance to the hashtable
        Expression variable;

        if (variableHashTable.get(name) != null){
            variable = variableHashTable.get(name);
        } else {
            variable = new Variable(name);
            variableHashTable.put(name, variable);
        }

        return (Variable) variable;
    }

    //------------------------------------------------------------------
    // 2G - It has an accessor (getter) method named
    // getExpression() which takes no parameters and returns the
    // Expression referring to the value of this Variable.
    //------------------------------------------------------------------
    public Expression getExpression(){
        return this.value;
    }

    public int  getValue()
    {
        return(value.getValue());
    }

    //------------------------------------------------------------------
    // 2J - It has a mutator (setter) method named assign()
    // which returns nothing and takes an Expression to replace
    // the current value of the Variable.
    //------------------------------------------------------------------
    public void assign(Expression expression){
        variableHashTable.put(name, expression);
        this.value = expression;
    }


    /**
     *  Compare two {@code Variable} instances
     *  @param variableObject the reference of the instance to compare
     *  to this instance
     *  @return equal or not
     */
    //------------------------------------------------------------------
    // 2K - It has an equals() method similar to our Review
    // package that determines the equality of Variables by their
    // name.
    //------------------------------------------------------------------
    public boolean equals(Object variableObject)
    {
        Variable  otherVariable;

        if (variableObject == null) {
            /*
             *  The caller didn't give us anything.
             */
            return(false);
        }

        if (this == variableObject) {
            /*
             *  They're at the same address!
             */
            return(true);
        }

        if (getClass() != variableObject.getClass()) {
            /*
             *  They're different classes.
             */
            return(false);
        }

        /*
         *  We know the otherObject must be a non-null reference to
         *  this class.
         */
        otherVariable = (Variable)variableObject;

        if (name.equals(otherVariable.name) != true) {
            /*
             *  They're different.
             */
            return(false);
        }

        return(true);
    }

    /**
     *  Hash codes are integers that represent the object with a
     *  high probability but no guarantee of uniqueness
     *  @return hash code of {@code Variable}
     */
    //------------------------------------------------------------------
    // 2L - It has a hashCode() method similar to our Review
    // package that returns the hash code of this Variable.
    //------------------------------------------------------------------
    public int hashCode()
    {
        return(variableHashcode);
    }

    /**
     *  Return our expression as a {@code String}.
     *  @return Our expression as a {@code String}.
     */
    //------------------------------------------------------------------
    // 2I - It overrides its toString() method to return its
    // name.
    //-----------------------------------------------------------------
    public String toString()
    {
        return("" + name);
    }


    /**
     *  Unit test our {@code Variable} expression.
     *  @param arg Command line arguments
     */
    //------------------------------------------------------------------
    // 2M - It has a main() method that performs a "unit test"
    // of the Variable class by get()ting variables, assign()ing
    // various values to variables and checking for correct
    // values or correct error results.
    //------------------------------------------------------------------
    public static void  main(String arg[])
    {
        Expression  expression;

        int result1 = 0;

        //Instantiate the class
        Variable Variable1 = new Variable("X");
        Variable Variable2 = new Variable("Y");
        Variable Variable3 = new Variable("Z");
        Variable Variable4 = new Variable("X");

        //Assign the value
        expression = new Integer("2");
        Variable1.assign(expression);

        expression = new Integer("3");
        Variable2.assign(expression);

        expression = new Integer("5");
        Variable3.assign(expression);

        expression = new Integer("2");
        Variable4.assign(expression);

        result1 = Variable4.getValue();

    }


}
