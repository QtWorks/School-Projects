using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FormulaEvaluatorTest
{
    class Tester
    {
        static Dictionary<string, int> d;
        static void Main(string[] args)
        {
            d = new Dictionary<string, int>();
            d.Add("a", 1);
            d.Add("b", 2);
            d.Add("c", 3);
            d.Add("d", 4);
            d.Add("e", 5);
            d.Add("f", 6);
            d.Add("x6", 7);

            test(1, 7, "3+4");
            test(2, 12, "3*4");
            test(3, 0, "3/4");
            test(4, -1, "3-4");
            test(5, 7, "3 + 4");
            test(6, 15, "3 + 4 * 3");
            test(7, 21, "(3 + 4) * 3");
            test(8, 42, "(3 + 4) * 3 * 2");
            test(9, 168, "(3 + 4) * 3 * (2 + 6)");
            test(10, 0, "(3 + 4) * (3 / 0)");
            test(11, 27, "(2 + 3) * 5 + 2");
            test(12, 1, "a");
            test(12, 2, "b");
            test(12, 3, "a + b");
            test(13, 7, "(2+5)*7/(9-2)");
            test(14, 7, "3.5 * 2");
            test(15, 28, "a + b + c + d + e + f + x6");
            test(16, -44, "(a + 3) * (b - 7) + (8 * x6) / 10 * 19 - 28");
            test(17, 7, "x6");
            test(18, -8, "((c * 9) - 4 * 7) * 8");
            testException(19, "+");
            testException(20, "-6");
            testException(21, "(6+2");
            testException(22, "6+2)");
            testException(23, "badVariable");
            testException(24, "62 - 2 + h");

            Console.ReadLine();
        }

        private static void testException(int testNumber, string exp)
        {
            string passFail = "";
            try
            {
                double result = FormulaEvaluator.Evaluator.Evaluate(exp, look);
                passFail = "FAIL\tException not thrown for:\t" + exp;
            }
            catch(ArgumentException e)
            {
                passFail = "PASS\tException thrown for:\t" + exp;
            }
            Console.WriteLine(passFail);
        }

        private static void test(int testNumber, int expected, string exp)
        {
            string passFail = "";
            double result = FormulaEvaluator.Evaluator.Evaluate(exp, look);
            if(result != expected)
            {
                passFail = "FAIL\tExpected: " + expected + "\tActual: " + result;
            }
            else
            {
                passFail = "PASS\t" + exp + "=" + expected;
            }
            Console.WriteLine("" + testNumber + ".\t" + passFail);
        }

        private static int look(string key)
        {
            int val;
            if(d.TryGetValue(key, out val))
            {
                return val;
            }
            else
            {
                throw new ArgumentException("Couldn't find " + key);
            }
        }
    }
}