// Skeleton written by Joe Zachary for CS 3500, September 2013
// Read the entire skeleton carefully and completely before you
// do anything else!

// Version 1.1 (9/22/13 11:45 a.m.)

// Change log:
//  (Version 1.1) Repaired mistake in GetTokens
//  (Version 1.1) Changed specification of second constructor to
//                clarify description of how validation works
//
// Implementation written by Elijah Grubb, u0894728, September 2015
// for CS 3500

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
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

        private string FormulaToEvaluate;

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
            LinkedList<string> temp = new LinkedList<string>(GetTokens(formula));

            if (temp.Count == 0)
                throw new FormulaFormatException("Please enter at least one token");

            string result = "";
            LinkedListNode<string> currentNode = temp.First;

            if (!currentNode.Value.isLeftParen() && !currentNode.Value.IsDouble() && !currentNode.Value.IsVariable())
                throw new FormulaFormatException("First token must be an opening parenthesis, a number or a variable");

            int leftParenCount = 0;
            int rightParenCount = 0;

            while (currentNode != null)
            {
                if (currentNode.Next != null && !currentNode.Next.Value.isRightParen() && !currentNode.Next.Value.isLeftParen() && !currentNode.Next.Value.IsDouble() && !currentNode.Next.Value.IsVariable() && !currentNode.Next.Value.isOperator())
                    throw new FormulaFormatException("The only valid tokens are are (, ), +, -, *, /, variables, and floating-point numbers");

                if (currentNode.Value.isRightParen())
                {
                    rightParenCount++;
                    if (rightParenCount > leftParenCount)
                        throw new FormulaFormatException("More closing Parenthensis then opening");
                }
                else if (currentNode.Value.isLeftParen())
                {
                    leftParenCount++;
                }

                if (currentNode.Value.IsVariable())
                {
                    currentNode.Value = normalize(currentNode.Value);
                    if (!isValid(currentNode.Value))
                        throw new FormulaFormatException("Not a valid variable");
                }

                if (currentNode.Value.IsDouble())
                {
                    double num = Double.Parse(currentNode.Value);
                    currentNode.Value = "" + num;
                }

                if ((currentNode.Value.isLeftParen() || currentNode.Value.isOperator()) && currentNode.Next != null)
                {
                    if (!currentNode.Next.Value.isLeftParen() && !currentNode.Next.Value.IsDouble() && !currentNode.Next.Value.IsVariable())
                        throw new FormulaFormatException("An opening parenthesis or an operator must be followed by an opening parenthasis, a number or a variable");
                }

                if ((currentNode.Value.IsDouble() || currentNode.Value.IsVariable() || currentNode.Value.isRightParen()) && currentNode.Next != null)
                {
                    if (!currentNode.Next.Value.isOperator() && !currentNode.Next.Value.isRightParen())
                        throw new FormulaFormatException("A number, variable or closing parenthesis must be followed by an operator or a closing parenthesis");
                }

                result += currentNode.Value;

                // move on to the next one
                currentNode = currentNode.Next;
            }

            currentNode = temp.Last;

            if (!currentNode.Value.isRightParen() && !currentNode.Value.IsDouble() && !currentNode.Value.IsVariable())
                throw new FormulaFormatException("First token must be an closing parenthesis, a number or a variable");

            if (rightParenCount != leftParenCount)
                throw new FormulaFormatException("Parenthesis are unbalenced");

            FormulaToEvaluate = result;
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
            double result;
            try
            {
                result = Evaluator.Evaluate(FormulaToEvaluate, lookup);
            }
            catch (ArgumentException e)
            {
                if (e.Message.Equals("Cannot divide by zero"))
                    return new FormulaError("Cannot divide by zero");
                else
                    return new FormulaError("Undefined Variable");
            }

            return result;
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
            List<string> tokens = new List<string>(GetTokens(FormulaToEvaluate));
            List<string> variables = new List<string>();
            foreach (string s in tokens)
            {
                if (s.IsVariable() && !variables.Contains(s))
                    variables.Add(s);
            }

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
            return FormulaToEvaluate;
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
            if (obj == null || !(obj is Formula))
                return false;
            else
                return this.ToString().Equals(((Formula)obj).ToString());
        }

        /// <summary>
        /// Reports whether f1 == f2, using the notion of equality from the Equals method.
        /// Note that if both f1 and f2 are null, this method should return true.  If one is
        /// null and one is not, this method should return false.
        /// </summary>
        public static bool operator ==(Formula f1, Formula f2)
        {
            if ((object) f1 == null && (object) f2 == null)
                return true;
            else if (((object) f1 == null && (object) f2 != null) || ((object) f1 != null && (object) f2 == null))
                return false;
            else
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
        /// </summary>
        public override int GetHashCode()
        {
            int result = 0;
            List<int> primes = GeneratePrimes(FormulaToEvaluate.Length);
            for (int i = 0; i < FormulaToEvaluate.Length; i++)
            {
                result += FormulaToEvaluate[i] * primes[i];
            }
            return result;
        }

        /// <summary>
        /// Generates a list of n primes
        /// </summary>
        /// <param name="n">number of primes to generate</param>
        /// <returns></returns>
        private List<int> GeneratePrimes(int n)
        {
            List<int> primes = new List<int>();
            primes.Add(2);
            int nextPrime = 3;
            while (primes.Count < n)
            {
                int sqrt = (int)Math.Sqrt(nextPrime);
                bool isPrime = true;
                for (int i = 0; (int)primes[i] <= sqrt; i++)
                {
                    if (nextPrime % primes[i] == 0)
                    {
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime)
                {
                    primes.Add(nextPrime);
                }
                nextPrime += 2;
            }
            return primes;
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
                    yield return s;
                }
            }

        }
    }
    
    /// <summary>
    /// Class designed to house my custom extensions for this project
    /// </summary>
    public static class CustomExtensions
    {
        /// <summary>
        /// Takes in a string and returns whether or not that string is a valid variable
        /// </summary>
        /// <param name="s">String to be evaluated</param>
        /// <returns>True if the input string is a valid variable</returns>
        public static bool IsVariable(this string s)
        {
            return Regex.IsMatch(s, "^([a-zA-Z_][a-zA-Z0-9_]*)$");
        }

        /// <summary>
        /// Returns whether or not the referenced string is a double
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        public static bool IsDouble(this string s)
        {
            double value;
            return Double.TryParse(s, out value);
        }

        /// <summary>
        /// Returns whether or not the referenced string is a left parenthesis
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        public static bool isLeftParen(this string s)
        {
            return Regex.IsMatch(s, "\\(");
        }

        /// <summary>
        /// Returns whether or not the referenced string is a right parenthesis
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        public static bool isRightParen(this string s)
        {
            return Regex.IsMatch(s, "\\)");
        }

        /// <summary>
        /// Returns whether or not the referenced string is an operator
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        public static bool isOperator(this string s)
        {
            return Regex.IsMatch(s, "[\\+\\-*/]");
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

