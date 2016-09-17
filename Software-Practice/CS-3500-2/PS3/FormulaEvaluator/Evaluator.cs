// Implementation written by Elijah Grubb, u0894728, September 2015
// for CS 3500

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace FormulaEvaluator
{
    /// <summary>
    /// Class Library for evaluating standard inflix notation
    /// and returning the result
    /// </summary>
    public static class Evaluator
    {
        /// <summary>
        /// Takes in an expression and returns the result
        /// using standard inflix notation using a Func instead of Delegate
        /// </summary>
        /// <param name="exp">Expression to be evaluated</param>
        /// <param name="variableEvaluator">Func to be used to lookup variables</param>
        /// <returns></returns>
        public static double Evaluate(String exp, Func<string,double> variableEvaluator)
        {
            // get rid of whitespaces
            exp = Regex.Replace(exp, @"\s+", "");
            // use provided regex to split
            string[] substrings = Regex.Split(exp, "(\\()|(\\))|(-)|(\\+)|(\\*)|(/)");

            // my two stacks to store everything
            Stack<string> operatorStack = new Stack<string>();
            Stack<double> valueStack = new Stack<double>();

            for (int i = 0; i < substrings.Length; i++)
            {
                double value;
                // it's a number
                if (double.TryParse(substrings[i], out value))
                {
                    if (operatorStack.Count() > 0 && (operatorStack.Peek().Equals("/") || operatorStack.Peek().Equals("*")))
                    {
                        if (valueStack.Count == 0)
                        {
                            throw new ArgumentException();
                        }
                        valueStack.Push(Calculate(valueStack.Pop(), operatorStack.Pop(), value));
                    }
                    else
                    {
                        valueStack.Push(value);
                    }
                }
                // It's a variable
                else if (substrings[i].Length >= 2 && Regex.IsMatch(substrings[i], "^([a-zA-Z_][a-zA-Z0-9_]*)$"))
                {
                    double variableValue = variableEvaluator(substrings[i]);
                    if (operatorStack.Count() > 0 && (operatorStack.Peek().Equals("/") || operatorStack.Peek().Equals("*")))
                    {
                        if (valueStack.Count == 0)
                            throw new ArgumentException();

                        valueStack.Push(Calculate(valueStack.Pop(), operatorStack.Pop(), variableValue));
                    }
                    else
                    {
                        valueStack.Push(variableValue);
                    }
                }
                // It's either a + or -
                else if (substrings[i].Equals("+") || substrings[i].Equals("-"))
                {
                    if (operatorStack.Count() > 0 && (operatorStack.Peek().Equals("+") || operatorStack.Peek().Equals("-")))
                    {
                        double last = valueStack.Pop();
                        double first = valueStack.Pop();
                        valueStack.Push(Calculate(first, operatorStack.Pop(), last));
                    }
                    operatorStack.Push(substrings[i]);
                }
                // It's either a * or /
                else if (substrings[i].Equals("*") || substrings[i].Equals("/"))
                {
                    operatorStack.Push(substrings[i]);
                }
                // It's a (
                else if (substrings[i].Equals("("))
                {
                    operatorStack.Push(substrings[i]);
                }
                // It's a )
                else if (substrings[i].Equals(")"))
                {
                    if (operatorStack.Count() > 0 && (operatorStack.Peek().Equals("+") || operatorStack.Peek().Equals("-")))
                    {
                        double last = valueStack.Pop();
                        double first = valueStack.Pop();
                        valueStack.Push(Calculate(first, operatorStack.Pop(), last));
                    }

                    if (operatorStack.Count() > 0 && operatorStack.Peek().Equals("("))
                        operatorStack.Pop();
                    else
                        throw new ArgumentException("No matching opening parthesis");

                    if (operatorStack.Count() > 0 && (operatorStack.Peek().Equals("*") || operatorStack.Peek().Equals("/")))
                    {
                        double last = valueStack.Pop();
                        double first = valueStack.Pop();
                        valueStack.Push(Calculate(first, operatorStack.Pop(), last));
                    }
                }
                // weird case of empty strings falling through
                else if (substrings[i].Equals(""))
                {
                    // Do nothing
                }
                // Only other possibility is it's an invalid character
                else
                {
                    throw new ArgumentException();
                }
            }

            if (operatorStack.Count() == 0)
            {
                if (valueStack.Count() != 1)
                    throw new ArgumentException();

                return valueStack.Pop();
            }
            else
            {
                if (operatorStack.Count() != 1 || valueStack.Count() != 2 || !(operatorStack.Peek().Equals("+") || operatorStack.Peek().Equals("-")))
                    throw new ArgumentException();

                double last = valueStack.Pop();
                double first = valueStack.Pop();
                return Calculate(first, operatorStack.Pop(), last);
            }
        }

        /// <summary>
        /// Private method for calculating simple arithmatic
        /// </summary>
        /// <param name="first">First Number</param>
        /// <param name="op">Operation to be Performed</param>
        /// <param name="last">Second Number</param>
        /// <returns></returns>
        private static double Calculate(double first, String op, double last)
        {
            if (op.Equals("*"))
            {
                return first * last;
            }
            else if (op.Equals("/"))
            {
                if (last == 0)
                    throw new ArgumentException("Cannot divide by zero");
                return first / last;
            }
            else if (op.Equals("+"))
            {
                return first + last;
            }
            else if (op.Equals("-"))
            {
                return first - last;
            }
            else
            {
                throw new ArgumentException();
            }
        }
    }
}