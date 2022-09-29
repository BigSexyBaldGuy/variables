package expressions;

import java.util.*;
import java.text.*;

/**
 *  {@code Parse} is the class that breaks a string into tokens.
 *  @version 2022081600
 *  @author Richard Barton
 */
public final class  Parse
{
    /*
     *  Make sure noone can instantiate this class.
     */
    private Parse()
    {
    }

    /**
     *  Given a {@code String}, break it into tokens and return
     *  them in an array.
     *  @param string String to parse.
     *  @return Array of tokens.
     *  @throws ParseException When an error during parsing
     *  is encountered.
     */
    public static String[]      parse(String string)
                throws ParseException
    {
        int                     i;
        int                     collected;
        StringBuffer            buffer;
        ArrayList<String>       returnValue;

        if ((string == null) || (string.length() <= 0)) {
            /*
             *  No string to parse.
             */
            throw(new ParseException("Empty string to parse", 0));
        }

        /*
         *  Need a place to store the tokens.
         */
        returnValue = new ArrayList<>();

        /*
         *  Gonna count the characters in the token and need
         *  a place to accumulate the token.  Then go through
         *  each character in the string.
         */
        collected = 0;
        buffer = new StringBuffer();
        for (i = 0; (i < string.length()); ++i) {
            char        thisChar;

            /*
             *  Get this character.
             */
            thisChar = string.charAt(i);
            if (Character.isWhitespace(thisChar) == true) {
                /*
                 *  It's whitespace.  So, whatever we've collected
                 *  so far is our newest token.
                 */
                if (collected > 0) {
                    /*
                     *  Add our newest token to our list and prepare
                     *  to collect the next token.
                     */
                    returnValue.add(buffer.toString());
                    buffer = new StringBuffer();
                    collected = 0;
                }
                continue;
            }

            if (Character.isDigit(thisChar) == true) {
                /*
                 *  We have a digit.
                 */
                if ((collected > 0) &&
                    (Character.isLetterOrDigit(buffer.charAt(0)) !=
                                        true)) {
                    /*
                     *  We have a digit but this token started out
                     *  with something other than a letter or digit.
                     *  Don't know what this could be.
                     */
                    throw(new ParseException("Didn't expect a" +
                                             " digit at position " +
                                             i, i));
                }

                /*
                 *  Add this digit to our token.
                 */
                buffer.append(thisChar);
                ++collected;
                continue;
            }

            if (Character.isLetter(thisChar) == true) {
                /*
                 *  We have a letter.
                 */
                if ((collected > 0) &&
                    (Character.isLetter(buffer.charAt(0)) != true)) {
                    /*
                     *  We have a letter but this token started out
                     *  with something other than a letter. Don't
                     *  know what this could be.
                     */
                    throw(new ParseException("Didn't expect a" +
                                             " letter at position " +
                                             i, i));
                }

                /*
                 *  Add this letter to our token.
                 */
                buffer.append(thisChar);
                ++collected;
                continue;
            }

            /*
             *  We got something that's not whitespace, a digit
             *  or a letter.
             */
            if (collected > 0) {
                /*
                 *  Whatever we have collected up to this character
                 *  treat as our newest token and prepare to
                 *  collect the next token.
                 */
                returnValue.add(buffer.toString());
                buffer = new StringBuffer();
                collected = 0;
            }
            /*
             *  Add this character to our token.
             */
            returnValue.add("" + thisChar);
        }

        if (collected > 0) {
            /*
             *  We're at the end of the string.  Wrap up our
             *  last token.
             */
            returnValue.add(buffer.toString());
        }

        if (returnValue.isEmpty() == true) {
            throw(new ParseException("No tokens", 0));

        }

        return(returnValue.toArray(new String[0]));
    }

    /**
     *  Unit test our parser.
     *  @param arg Command line arguments
     */
    public static void  main(String arg[])
    {
        int     errors;
        int     whichTest;
        /*
         *  The items in this array are strings to parse.
         *  Each corresponds to the list of results in the
         *  following at the same index.
         */
        String  toParse[]   = {
            "",
            " ",
            "fred123wilma456",
            "123fred456wilma",
            "123.fred.456.wilma",
            "123 fred 456 wilma",
            "a.1b,2c!3d*4e&",
            "a.1 b,2 c!3 d*4 e&",
            "1 + 2 = 3",
            "  1  +  2  =  3  ",
            "\n 1\n +\n 2\n =\n 3\n ",
            "\t 1\t +\t 2\t =\t 3\t ",
            "12 + 34 = 46",
            "george + jane = judy",
            "george+jane=judy",
            "george1+jane2=judy3",
            "george12+jane34=judy56",
            "geo12rge+ja34ne=ju56dy",
                          };
        /*
         *  The items in this array are tokens from parsing the
         *  corresponding string at the same index in the previous
         *  array.  A null value means we expect an exception.
         */
        String  parseResults[][]    = {
            {null},
            {null},
            {"fred123wilma456"},
            {null},
            {"123", ".", "fred", ".", "456", ".", "wilma"},
            {"123", "fred", "456", "wilma"},
            {null},
            {"a", ".", "1", "b", ",", "2", "c", "!", "3", "d", "*",
             "4", "e", "&"},
            {"1", "+", "2", "=", "3"},
            {"1", "+", "2", "=", "3"},
            {"1", "+", "2", "=", "3"},
            {"1", "+", "2", "=", "3"},
            {"12", "+", "34", "=", "46"},
            {"george", "+", "jane", "=", "judy"},
            {"george", "+", "jane", "=", "judy"},
            {"george1", "+", "jane2", "=", "judy3"},
            {"george12", "+", "jane34", "=", "judy56"},
            {"geo12rge", "+", "ja34ne", "=", "ju56dy"},
                                  };

        if (toParse.length != parseResults.length) {
            /*
             *  Someone changed the strings to parse or
             *  parse results without changing the other.
             */
            System.out.println("*** ERROR *** " +
                               toParse.length +
                               " strings to parse and " +
                               parseResults.length +
                               " results");
            System.exit(1);
        }

        /*
         *  Go through each test.
         */
        errors = 0;
        for (whichTest = 0; (whichTest < toParse.length);
             ++whichTest) {
            int     whichToken;
            String  tokens[];
            String  theseResults[];

            System.out.println("Trying to parse:\n" +
                               "  \"" + toParse[whichTest] +
                               "\"");
            /*
             *  Just want the correct results without having to
             *  index the test.
             */
            theseResults = parseResults[whichTest];
            try {
                /*
                 *  Do the parsing.
                 */
                tokens = parse(toParse[whichTest]);
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
                } else if (theseResults[0] != null) {
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

            /*
             *  See if each token is correct.
             */
            for (whichToken = 0; (whichToken < theseResults.length);
                 ++whichToken) {
                if (whichToken >= tokens.length) {
                    /*
                     *  The parser didn't give us enough tokens.
                     */
                    System.out.println("*** ERROR *** Missing token:" +
                                       " \"" +
                                       theseResults[whichToken] +
                                       "\"");
                    ++errors;
                    continue;
                }

                if (tokens[whichToken].
                        equals(theseResults[whichToken]) != true) {
                    /*
                     *  The parser's token doesn't match.
                     */
                    System.out.println("*** ERROR ***" +
                                       " Tokens don't match:\n  \"" +
                                       tokens[whichToken] +
                                       "\" != \"" +
                                       theseResults[whichToken] +
                                       "\"");
                    ++errors;
                }
            }

            for (; (whichToken < tokens.length); ++whichToken) {
                /*
                 *  The parser gave us more tokens than we
                 *  were expecting.
                 */
                System.out.println("*** ERROR *** Extra token:" +
                                   " \"" + tokens[whichToken] +
                                   "\"");
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
