using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Collections;

/// <summary>
/// This is an infix calculator
/// </summary>
namespace FormulaEvaluator
{
    /// <summary>
    /// Base class to evaluate expressions
    /// </summary>
    public static class Evaluator
    {
        /// <summary>
        /// This delegate takes in a variable value and returns its corresponding integer value
        /// </summary>
        /// <param name="v">Variable to be looked up</param>
        /// <returns>Integer value of variable</returns>
        public delegate int Lookup(String v);
        /// <summary>
        /// This Stack holds all double values being evaluated
        /// </summary>
        //private Stack<double> values;
        /// <summary>
        /// This Stack holds all operators being evaluated. 
        /// Supports +, -, *, -, (, )
        /// </summary>
       // private Stack<string> operators;

        /// <summary>
        /// Method used to evaluate a string expression for its integer value
        /// </summary>
        /// <param name="exp">Expression to evaluate</param>
        /// <param name="variableEvaluator">Method to use to "look up" variable value</param>
        /// <returns>Integer value of expression</returns>
        public static double Evaluate(string exp, Lookup variableEvaluator)
        {
            Stack<double> values = new Stack<double>();
            Stack<string> operators = new Stack<string>();

            string[] substrings = Regex.Split(exp, "(\\()|(\\))|(-)|(\\+)|(\\*)|(/)");

            foreach (string s in substrings)
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
                    processDouble(myDouble, values, operators);
                }
                //String can't be parsed as an int
                else
                {
                    processString(token, variableEvaluator, values, operators);
                }
            }
            if(operators.Count() == 0)
            {
                //Value stack should contain a single number
                if (values.Count() != 1)
                {
                    //Possible errors: There isn't exactly one value on the value stack
                    throw new ArgumentException("Should be exactly one value left");
                }
                //Pop it and report as the value of the expression
                return values.Pop();
            }
            else
            {
                if (operators.Count() != 1)
                {
                    //Possible errors: There isn't exactly one operator on the operator stack
                    throw new ArgumentException("Should be exactly one operator left");
                }
                //There should be exactly two values on the value stack. 
                if (values.Count() != 2)
                {
                    //Possible errors: There aren't exactly two numbers on the value stack
                    throw new ArgumentException("Should be exactly two values left");
                }
                //There should be exactly one operator on the operator stack, and it should be either + or -.
                if (!operators.hasOnTop("+", "-"))
                {
                    throw new ArgumentException("Last operator should be + or -");
                }
                //Apply the operator to the two values and report the result as the
                //value of the expression.
                return performTopOperation(values, operators);
            }
        }

        /// <summary>
        /// Method used to evaluate an expression from any IEnumerable for its integer value.
        /// Overload of previous method
        /// </summary>
        /// <param name="valid_tokens">IEnumerable containing tokens</param>
        /// <param name="lookup">Method to use to "look up" variable value</param>
        /// <returns>Integer value of expression</returns>
        public static double Evaluate(IEnumerable valid_tokens, Func<string, double> lookup)
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
        public static void processString(this string s, Lookup variableEvaluator, Stack<double> values, Stack<string> operators)
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
                if(operators.Count() > 0)
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
            else if((s.Length >= 2 && Regex.IsMatch(s, "[a-zA-Z]+[1-9]+")))
            {
                //Means that s is a valid variable form.
                try
                {
                    processDouble(variableEvaluator(s), values, operators);
                }
                catch(Exception e)
                {
                    throw new ArgumentException(e.Message);
                }
            }
            else
            {
                throw new ArgumentException("Invalid variable");
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
            return (double.TryParse(s, out num) || Regex.IsMatch(s, doublePattern) || Regex.IsMatch(s,scientificPattern));
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
}
