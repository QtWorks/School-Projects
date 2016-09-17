// Skeleton written by Joe Zachary for CS 3500, September 2013
// Read the entire skeleton carefully and completely before you
// do anything else!

// Version 1.1 (9/22/13 11:45 a.m.)

// Change log:
//  (Version 1.1) Repaired mistake in GetTokens
//  (Version 1.1) Changed specification of second constructor to
//                clarify description of how validation works

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using SpreadsheetUtilities;
using FormulaEvaluator;


namespace SpreadsheetUtilities
{
    /// <summary>
    /// Represents formulas written in standard infix notation using standard precedence
    /// rules.  The allowed symbols are non-negative numbers written using double-precision 
    /// floating-point syntax; variables that consist of a letter or underscore followed by 
    /// zero or more letters, underscores, or digits; parentheses; and the four operator 
    /// symbols +, -, *, and /.  
    /// 
    /// Spaces are significant only insofar that they delimit tokens.  For example, "xy" is
    /// a single variable, "x y" consists of two variables "x" and y; "x23" is a single variable; 
    /// and "x 23" consists of a variable "x" and a number "23".
    /// 
    /// Associated with every formula are two delegates:  a normalizer and a validator.  The
    /// normalizer is used to convert variables into a canonical form, and the validator is used
    /// to add extra restrictions on the validity of a variable (beyond the standard requirement 
    /// that it consist of a letter or underscore followed by zero or more letters, underscores,
    /// or digits.)  Their use is described in detail in the constructor and method comments.
    /// </summary>
    public class Formula
    {
        private LinkedList<string> valid_tokens;

        private HashSet<string> variables;
       
        /// <summary>
        /// Creates a Formula from a string that consists of an infix expression written as
        /// described in the class comment.  If the expression is syntactically invalid,
        /// throws a FormulaFormatException with an explanatory Message.
        /// 
        /// The associated normalizer is the identity function, and the associated validator
        /// maps every string to true.  
        /// </summary>
        public Formula(String formula) :
            this(formula, s => s, s => true)
        {
        }


        /// <summary>
        /// Creates a Formula from a string that consists of an infix expression written as
        /// described in the class comment.  If the expression is syntactically incorrect,
        /// throws a FormulaFormatException with an explanatory Message.
        /// 
        /// The associated normalizer and validator are the second and third parameters,
        /// respectively.  
        /// 
        /// If the formula contains a variable v such that normalize(v) is not a legal variable, 
        /// throws a FormulaFormatException with an explanatory message. 
        /// 
        /// If the formula contains a variable v such that isValid(normalize(v)) is false,
        /// throws a FormulaFormatException with an explanatory message.
        /// 
        /// Suppose that N is a method that converts all the letters in a string to upper case, and
        /// that V is a method that returns true only if a string consists of one letter followed
        /// by one digit.  Then:
        /// 
        /// new Formula("x2+y3", N, V) should succeed
        /// new Formula("x+y3", N, V) should throw an exception, since V(N("x")) is false
        /// new Formula("2x+y3", N, V) should throw an exception, since "2x+y3" is syntactically incorrect.
        /// </summary>
        public Formula(String formula, Func<string, string> normalize, Func<string, bool> isValid)
        {
            valid_tokens = new LinkedList<string>();
            variables = new HashSet<string>();
            if (formula == null || formula.Length < 1)
                throw new FormulaFormatException("Passed in a null or empty string. Must have at least one character");

            //open_paren_count must ALWAYS be >= close_paren_count, or exception will be thrown
            int open_paren_count = 0;
            int close_paren_count = 0;

            LinkedList<string> tokens = new LinkedList<string>(GetTokens(formula));
            //Used to check for syntax errors.
            if (tokens.Count() < 1)
                throw new FormulaFormatException("Must have at least one token in formula");
            //Check the start and end of the list for invalid characters
            StartEndCheck(tokens);
            for (LinkedListNode<string> node = tokens.First; node != null; node = node.Next)
            {
                string current = node.Value;
                string next = null;
                if(node.Next != null)
                    next = node.Next.Value;
                double num;
                //Check to see if current is a valid character
                //Use FollowCheck Methods to make sure following character is valid
                if (double.TryParse(current, out num))
                {
                    ValFollowCheck(current, next);
                    current = num.ToString();
                }
                else if (current.Equals("("))
                {
                    open_paren_count++;
                    OpFollowCheck(current, next);
                }
                else if (current.Equals(")"))
                {
                    close_paren_count++; 
                    //There can never be more closing parentheses
                    //than opening parentheses
                    if (close_paren_count > open_paren_count)
                        throw new FormulaFormatException("Found a closing parentheses without a matching opening parentheses");
                    ValFollowCheck(current, next);
                }
                else if (current.IsOperator())
                {
                    OpFollowCheck(current, next);
                }
                //Check if current is a valid variable
                else if (current.IsVariable() && isValid(normalize(current)))
                {
                    ValFollowCheck(current, next);
                    //Add normalized version of the variable to variables
                    current = normalize(current);
                    variables.Add(current);
                }
                else
                {
                    throw new FormulaFormatException("Invalid character: " + current);
                }
                valid_tokens.AddLast(current); //Current token has passed all tests, add to valid tokens
            }
            //Opening and closing parentheses must be the same amount
            if (open_paren_count != close_paren_count)
                throw new FormulaFormatException("Must have matching number of opening and closing parentheses. Found " +
                    open_paren_count + " opening parentheses and " + close_paren_count + " closing parentheses.");
        }

        /// <summary>
        /// Checks to see if there are invalid following characters after a number or a value.
        /// Can only be an operator or close parentheses
        /// </summary>
        /// <param name="current">Current Value</param>
        /// <param name="next">Following character</param>
        private void ValFollowCheck(string current, string next)
        {
            if(next!= null && !next.IsOperator() && !next.Equals(")"))
            {
                throw new FormulaFormatException("Cannot have a '" + next + "' following a '" + current + "'");
            }
        }
        /// <summary>
        /// Checks to see if there are invalid characters following an operator or an opening parentheses
        /// Invalid: +, -, *, /, )
        /// </summary>
        /// <param name="current"></param>
        /// <param name="next"></param>
        private void OpFollowCheck(string current, string next)
        {
            if (next != null && (next.IsOperator() || next.Equals(")")))
            {
                throw new FormulaFormatException("Cannot have a '" + next + "' following a '" + current + "'");
            }
        }
        /// <summary>
        /// Checks the start and end for invalid characters: +,-,/,*,) at the start or ( at the end
        /// </summary>
        /// <param name="tokens">List of tokens</param>
        private void StartEndCheck(LinkedList<string> tokens)
        {
            string start = tokens.First.Value;
            string end = tokens.Last.Value;
            if (start.IsOperator() || start.Equals(")"))
            {
                throw new FormulaFormatException("Invalid starting character: " + start);
            }
            if (end.IsOperator() || end.Equals("("))
            {
                throw new FormulaFormatException("Invalid ending character: " + end);
            }
        }


        /// <summary>
        /// Evaluates this Formula, using the lookup delegate to determine the values of
        /// variables.  When a variable symbol v needs to be determined, it should be looked up
        /// via lookup(normalize(v)). (Here, normalize is the normalizer that was passed to 
        /// the constructor.)
        /// 
        /// For example, if L("x") is 2, L("X") is 4, and N is a method that converts all the letters 
        /// in a string to upper case:
        /// 
        /// new Formula("x+7", N, s => true).Evaluate(L) is 11
        /// new Formula("x+7").Evaluate(L) is 9
        /// 
        /// Given a variable symbol as its parameter, lookup returns the variable's value 
        /// (if it has one) or throws an ArgumentException (otherwise).
        /// 
        /// If no undefined variables or divisions by zero are encountered when evaluating 
        /// this Formula, the value is returned.  Otherwise, a FormulaError is returned.  
        /// The Reason property of the FormulaError should have a meaningful explanation.
        ///
        /// This method should never throw an exception.
        /// </summary>
        public object Evaluate(Func<string, double> lookup)
        {
            try
            {
                return Toolbox.Evaluate(valid_tokens, lookup);
            }
            catch(ArgumentException e)
            {
                return new FormulaError(e.Message);
            }
        }



        /// <summary>
        /// Enumerates the normalized versions of all of the variables that occur in this 
        /// formula.  No normalization may appear more than once in the enumeration, even 
        /// if it appears more than once in this Formula.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        /// 
        /// new Formula("x+y*z", N, s => true).GetVariables() should enumerate "X", "Y", and "Z"
        /// new Formula("x+X*z", N, s => true).GetVariables() should enumerate "X" and "Z".
        /// new Formula("x+X*z").GetVariables() should enumerate "x", "X", and "z".
        /// </summary>
        public IEnumerable<String> GetVariables()
        {
            return variables;
        }

        /// <summary>
        /// Returns a string containing no spaces which, if passed to the Formula
        /// constructor, will produce a Formula f such that this.Equals(f).  All of the
        /// variables in the string should be normalized.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        /// 
        /// new Formula("x + y", N, s => true).ToString() should return "X+Y"
        /// new Formula("x + Y").ToString() should return "x+Y"
        /// </summary>
        public override string ToString()
        {
            string result = "";
            foreach(string token in valid_tokens)
                result = result + token;
            return result;
        }

        /// <summary>
        /// If obj is null or obj is not a Formula, returns false.  Otherwise, reports
        /// whether or not this Formula and obj are equal.
        /// 
        /// Two Formulae are considered equal if they consist of the same tokens in the
        /// same order.  To determine token equality, all tokens are compared as strings 
        /// except for numeric tokens, which are compared as doubles, and variable tokens,
        /// whose normalized forms are compared as strings.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        ///  
        /// new Formula("x1+y2", N, s => true).Equals(new Formula("X1  +  Y2")) is true
        /// new Formula("x1+y2").Equals(new Formula("X1+Y2")) is false
        /// new Formula("x1+y2").Equals(new Formula("y2+x1")) is false
        /// new Formula("2.0 + x7").Equals(new Formula("2.000 + x7")) is true
        /// </summary>
        public override bool Equals(object obj)
        {
            if (obj == null)
                return false;
            return this.ToString().Equals(obj.ToString());
        }

        /// <summary>
        /// Reports whether f1 == f2, using the notion of equality from the Equals method.
        /// Note that if both f1 and f2 are null, this method should return true.  If one is
        /// null and one is not, this method should return false.
        /// </summary>
        public static bool operator ==(Formula f1, Formula f2)
        {
            //Must cast as objects to avoid infinite recursion
            if ((object) f1 == null && (object) f2 == null)
                return true;
            if ((object) f1 == null ^ (object) f2 == null)
                return false;
            return f1.Equals(f2);
        }

        /// <summary>
        /// Reports whether f1 != f2, using the notion of equality from the Equals method.
        /// Note that if both f1 and f2 are null, this method should return false.  If one is
        /// null and one is not, this method should return true.
        /// </summary>
        public static bool operator !=(Formula f1, Formula f2)
        {
            return !(f1 == f2);
        }

        /// <summary>
        /// Returns a hash code for this Formula.  If f1.Equals(f2), then it must be the
        /// case that f1.GetHashCode() == f2.GetHashCode().  Ideally, the probability that two 
        /// randomly-generated unequal Formulae have the same hash code should be extremely small.
        /// 
        /// Sums all character values of string and multiplies each one by the next prime.
        /// </summary>
        public override int GetHashCode()
        {
            int result = 0;
            string tokenString = this.ToString();
            LinkedList<int> primes = new LinkedList<int>(Primes(tokenString.Length));
            LinkedListNode<int> primeNode = primes.First;
            foreach(char c in tokenString)
            {
                //Multiply char value by next prime and add product to result
                int val = (int)c;
                result += (val * primeNode.Value);
                primeNode = primeNode.Next;
            }
            return result;
        }

        /// <summary>
        /// Given an expression, enumerates the tokens that compose it.  Tokens are left paren;
        /// right paren; one of the four operator symbols; a string consisting of a letter or underscore
        /// followed by zero or more letters, digits, or underscores; a double literal; and anything that doesn't
        /// match one of those patterns.  There are no empty tokens, and no token contains white space.
        /// </summary>
        private static IEnumerable<string> GetTokens(String formula)
        {
            // Patterns for individual tokens
            String lpPattern = @"\(";
            String rpPattern = @"\)";
            String opPattern = @"[\+\-*/]";
            String varPattern = @"[a-zA-Z_](?: [a-zA-Z_]|\d)*";
            String doublePattern = @"(?: \d+\.\d* | \d*\.\d+ | \d+ ) (?: [eE][\+-]?\d+)?";
            String spacePattern = @"\s+";

            // Overall pattern
            String pattern = String.Format("({0}) | ({1}) | ({2}) | ({3}) | ({4}) | ({5})",
                                            lpPattern, rpPattern, opPattern, varPattern, doublePattern, spacePattern);

            // Enumerate matching tokens that don't consist solely of white space.
            foreach (String s in Regex.Split(formula, pattern, RegexOptions.IgnorePatternWhitespace))
            {
                if (!Regex.IsMatch(s, @"^\s*$", RegexOptions.Singleline))
                {
                    yield return s; //todo need to understand more about yield return
                }
            }
        }

        /// <summary>
        /// Returns a given number of primes, beginning with the number 2
        /// </summary>
        /// <param name="numberOfPrimes">Number of primes desired</param>
        /// <returns>IEnumerable of prime numbers</returns>
        private static IEnumerable<int> Primes(int numberOfPrimes)
        {
            yield return 2;
            int prime = 3;
            for (int primesSoFar = 1; primesSoFar <= numberOfPrimes; prime += 2)
            {
                if (!prime.IsPrime())
                    continue;
                primesSoFar++;
                yield return prime;
            }
        }
    }

    static class Toolbox
    {
        /// <summary>
        /// Method used to evaluate an expression from any IEnumerable for its integer value.
        /// Overload of previous method
        /// </summary>
        /// <param name="valid_tokens">IEnumerable containing tokens</param>
        /// <param name="lookup">Method to use to "look up" variable value</param>
        /// <returns>Integer value of expression</returns>
        public static double Evaluate(IEnumerable<string> valid_tokens, Func<string, double> lookup)
        {
            Stack<double> values = new Stack<double>();
            Stack<string> operators = new Stack<string>();

            foreach (string s in valid_tokens)
            {
                string token = s.Trim();
                if (token.Equals(""))
                    continue;
                double myDouble;
                //Check to see if the string can be parsed as an int
                if (double.TryParse(token, out myDouble))
                {
                    //if * or / is at the top of the operator stack, pop the value stack and 
                    //apply the operator to t and the popped number. 
                    if (myDouble == 0 && operators.Count() > 0 && operators.Peek().Equals("/"))
                        throw new ArgumentException("Cannot divide by zero");
                    myDouble.processDouble(values, operators);
                }
                //String can't be parsed as an int
                else
                {
                    token.processString(lookup, values, operators);
                }
            }
            if (operators.Count() == 0)
            {
                //Pop it and report as the value of the expression
                return values.Pop();
            }
            else
            {
                //Apply the operator to the two values and report the result as the
                //value of the expression.
                return performTopOperation(values, operators);
            }
        }

        

        /// <summary>
        /// Called when token cannot be parsed to a double. Used to evaluate operators and variables
        /// </summary>
        /// <param name="s">String being operated on</param>
        /// <param name="variableEvaluator">Method to look up variable value</param>
        /// <param name="values">Stack of values</param>
        /// <param name="operators">Stack of operators</param>
        public static void processString(this string s, Func<string, double> variableEvaluator, Stack<double> values, Stack<string> operators)
        {
            if (s.IsOneOfThese("+", "-"))
            {
                //if + or - is at the top of the operator stack, pop the value stack twice and the 
                //operator stack once.
                if (operators.hasOnTop("+", "-"))
                {
                    values.Push(performTopOperation(values, operators));
                }
            }
            if (s.IsOneOfThese("*", "/", "(", "+", "-"))
            {
                //Push onto the operator stack
                operators.Push(s);
            }
            else if (s.Equals(")"))
            {
                //If + or - is at the top of the operator stack, pop the value stack twice and the operator stack once.
                if (operators.hasOnTop("+", "-"))
                {
                    //Apply the popped operator to the popped numbers. Push the result onto the value stack
                    values.Push(performTopOperation(values, operators));
                }

                //Next, the top of the operator stack should be a (. Pop it
                string op = null;
                if (operators.Count() > 0)
                {
                    op = operators.Pop();
                }
                else
                {
                    throw new ArgumentException("Empty operator stack");
                }
                //Possible errors: A ( isn't found where expected
                if (op != null && !op.Equals("("))
                {
                    throw new ArgumentException("Unbalanced parentheses");
                }
                //Possible errors: The value stack contains fewer than 2 values during the first step

                //Finally, if * or / is at the top of the operator stack, pop the value stack twice and ther operator
                //stack once. Apply the popped operator to the popped numbers. Push the result onto the value stack
                if (operators.hasOnTop("*", "/"))
                {
                    values.Push(performTopOperation(values, operators));
                }
            }
            else if ((s.IsVariable()))
            {
                //Means that s is a valid variable form.
                try
                {
                    processDouble(variableEvaluator(s), values, operators);
                }
                catch (Exception e)
                {
                    throw new ArgumentException(e.Message);
                }
            }
        }


        /// <summary>
        /// Called when token is parsed to a double
        /// </summary>
        /// <param name="myDouble">Double value being evaluated</param>
        /// <param name="values">Stack of values</param>
        /// <param name="operators">Stack of operators</param>
        public static void processDouble(this double myDouble, Stack<double> values, Stack<string> operators)
        {

            if (operators.hasOnTop("*", "/"))
            {
                if (values.Count() > 0)
                {
                    //push the result onto the value stack
                    values.Push(calculate(values.Pop(), operators.Pop(), myDouble));
                }
                else
                {
                    //Possible errors, the value stack is empty - a division by zero results
                    throw new ArgumentException("Empty Value Stack");
                }
            }
            else
            {
                values.Push(myDouble);
            }
            //Otherwise, push the int onto the value stack            
        }

        /// <summary>
        /// Performs operation with top two values from value stack
        /// and top token from operator stack
        /// </summary>
        /// <param name="values">Stack of values</param>
        /// <param name="operators">Stack of operators</param>
        /// <returns>Double result of operation</returns>
        private static double performTopOperation(Stack<double> values, Stack<string> operators)
        {
            if (values.Count() < 2)
            {
                throw new ArgumentException("Fewer than two values in stack");
            }
            if (operators.Count() < 1)
            {
                throw new ArgumentException("Nothing in the operator stack");
            }
            double second = values.Pop();
            double first = values.Pop();
            //Apply the popped operator to the popped numbers. Push the result onto the value stack
            return calculate(first, operators.Pop(), second);
        }

        /// <summary>
        /// Takes in two integers and a string operator and performs the necessary operation in the same order as 
        /// the parameters. For example, if (5, "-", 4) were passed in as parameters, this method would 
        /// perform 5 - 4 and return 1.
        /// </summary>
        /// <param name="first">First double</param>
        /// <param name="op">Operation to be performed with doubles. Only supports +, -, *, /.
        /// Any other string will cause an error to be thrown</param>
        /// <param name="second">Second double</param>
        /// <returns>Result of operation on the two integers</returns>
        private static double calculate(double first, string op, double second)
        {
            if (op.Equals("+"))
            {
                return first + second;
            }
            else if (op.Equals("-"))
            {
                return first - second;
            }
            else if (op.Equals("*"))
            {
                return first * second;
            }
            else if (op.Equals("/"))
            {
                if (second == 0)
                    throw new ArgumentException("Cannot divide by 0");
                return first / second;
            }
            else
            {
                throw new ArgumentException("Invalid operator: " + op);
            }
        }

        /// <summary>
        /// This method checks to see if any of the contained strings match the string
        /// being operated on. 
        /// </summary>
        /// <param name="s">String being operated on</param>
        /// <param name="strings">Indefinite number of strings to check for</param>
        /// <returns>True if match is found, false otherwise</returns>
        public static bool IsOneOfThese(this string s, params string[] strings)
        {
            if (s != null)
            {
                for (int i = 0; i < strings.Length; i++)
                {
                    if (s.Equals(strings[i]))
                    {
                        return true;
                    }
                }
            }
            return false;
        }


        /// <summary>
        /// Checks for an indefinite number of strings to see if they match the top
        /// of the stack being operated on.
        /// </summary>
        /// <param name="stack">Stack to be operated on</param>
        /// <param name="strings">Array of strings to check for</param>
        /// <returns>True if match is found. False otherwise</returns>
        public static bool hasOnTop(this Stack<string> stack, params string[] strings)
        {
            if (stack.Count > 0)
            {
                string top = stack.Peek();
                foreach (string s in strings)
                {
                    if (top.Equals(s))
                    {
                        return true;
                    }
                }
            }
            return false;
        }

        /// <summary>
        /// Reports whether s is an operator: +, -, * or /
        /// </summary>
        public static bool IsOperator(this string s)
        {
            return s.IsOneOfThese("+", "-", "*", "/");
        }

        /// <summary>
        /// Reports whether a string can be parsed as a double
        /// </summary>
        /// <param name="s">String to be parsed</param>
        /// <returns>True if s can be parsed as double, false otherwise</returns>
        public static bool IsNumber(this string s)
        {
            double num;
            String doublePattern = @"(?: \d+\.\d* | \d*\.\d+ | \d+ ) (?: [eE][\+-]?\d+)?";
            String scientificPattern = @"[-+]?(0?|[1-9][0-9]*)(\.[0-9]*[1-9])?([eE][-+]?(0|[1-9][0-9]*))?";
            return (double.TryParse(s, out num) || Regex.IsMatch(s, doublePattern) || Regex.IsMatch(s, scientificPattern));
        }

        /// <summary>
        /// Report whether this string is a valid variable:
        /// One or more letters OR underscores,
        /// followed by 0 or more letters, underscores or digits
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        public static bool IsVariable(this string s)
        {
            return Regex.IsMatch(s, "^([a-zA-Z_][a-zA-Z0-9_]*)$");
        }
        /// <summary>
        /// Reports whether this number is a prime number
        /// </summary>
        public static bool IsPrime(this int num)
        {
            if (num <= 1)
                return false;
            if (num == 2)
                return true;
            for (int i = 3; i <= Math.Ceiling(Math.Sqrt(num)); i++)
            {
                if (num % i == 0)
                    return false;
            }
            return true;
        }
    }
    /// <summary>
    /// Used to report syntactic errors in the argument to the Formula constructor.
    /// </summary>
    public class FormulaFormatException : Exception
    {
        /// <summary>
        /// Constructs a FormulaFormatException containing the explanatory message.
        /// </summary>
        public FormulaFormatException(String message)
            : base(message)
        {
        }
    }

    /// <summary>
    /// Used as a possible return value of the Formula.Evaluate method.
    /// </summary>
    public struct FormulaError
    {
        /// <summary>
        /// Constructs a FormulaError containing the explanatory reason.
        /// </summary>
        /// <param name="reason"></param>
        public FormulaError(String reason)
            : this()
        {
            Reason = reason;
        }

        /// <summary>
        ///  The reason why this FormulaError was created.
        /// </summary>
        public string Reason { get; private set; }
    }
}

