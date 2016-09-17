using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using FormulaEvaluator;
using System.Collections.Generic;

namespace FormulaEvaluatorUnitTest
{
    [TestClass]
    public class FormulaEvaluatorTests
    {

        public static Dictionary<string, int> variableDictionary = new Dictionary<string, int>();

        public static int lookupConsistent(String s)
        {
            return 5;
        }

        public static int lookupDictionary(String s)
        {
            int variableSolution;
            if (variableDictionary.TryGetValue(s, out variableSolution))
            {
                return variableSolution;
            }
            else
            {
                throw new ArgumentException();
            }
        }

        [TestMethod]
        public void TestSimple()
        {
            Assert.AreEqual(0, Evaluator.Evaluate("0", lookupConsistent));
        }

        [TestMethod]
        public void TestAddition()
        {
            Assert.AreEqual(7, Evaluator.Evaluate("1 + 6", lookupConsistent));
            Assert.AreEqual(124, Evaluator.Evaluate("  57   +   67      ", lookupConsistent));
        }

        [TestMethod]
        public void TestSubtraction()
        {
            Assert.AreEqual(7, Evaluator.Evaluate("10 - 3", lookupConsistent));
        }

        [TestMethod]
        public void TestMultiplication()
        {
            Assert.AreEqual(9, Evaluator.Evaluate("3 * 3", lookupConsistent));
        }

        [TestMethod]
        public void TestDivision()
        {
            Assert.AreEqual(4, Evaluator.Evaluate("8 / 2", lookupConsistent));
        }

        [TestMethod]
        public void TestComplex()
        {
            Assert.AreEqual(7, Evaluator.Evaluate("(2 + 5) * 7 / (9 - 2)", lookupConsistent));
            Assert.AreEqual(10, Evaluator.Evaluate("(3+7)", lookupConsistent));
        }

        [TestMethod]
        public void TestVariables()
        {
            variableDictionary.Add("a1", 2);
            variableDictionary.Add("b2", 3);
            variableDictionary.Add("c3", 5);
            variableDictionary.Add("d4", -7);

            Assert.AreEqual(10, Evaluator.Evaluate("a1 + b2 + c3", lookupDictionary));
            Assert.AreEqual(-33, Evaluator.Evaluate("d4 * c3 + a1", lookupDictionary));
        }

        [TestMethod]
        public void TestTruncating()
        {
            Assert.AreEqual(2, Evaluator.Evaluate("5/2", lookupConsistent));
            Assert.AreEqual(16, Evaluator.Evaluate("((7*5)/3)*44/31", lookupConsistent));
            Assert.AreEqual(4, Evaluator.Evaluate("9 / 2", lookupConsistent));
        }

        [TestMethod]
        public void TestExceptions()
        {
            testAnException("5 % 6");
            testAnException("invalidVariable + 5");
            testAnException("!!(#@JSDFKLJIA)(#");
        }

        private void testAnException(String s)
        {
            try
            {
                Evaluator.Evaluate(s, lookupConsistent);
                throw new AssertFailedException();
            }
            catch (ArgumentException e)
            {

            }
        }

        [TestMethod()]
        public void Test1()
        {
            Assert.AreEqual(5, Evaluator.Evaluate("5", s => 0));
        }

        [TestMethod()]
        public void Test2()
        {
            Assert.AreEqual(13, Evaluator.Evaluate("X5", s => 13));
        }

        [TestMethod()]
        public void Test3()
        {
            Assert.AreEqual(8, Evaluator.Evaluate("5+3", s => 0));
        }

        [TestMethod()]
        public void Test4()
        {
            Assert.AreEqual(8, Evaluator.Evaluate("18-10", s => 0));
        }

        [TestMethod()]
        public void Test5()
        {
            Assert.AreEqual(8, Evaluator.Evaluate("2*4", s => 0));
        }

        [TestMethod()]
        public void Test6()
        {
            Assert.AreEqual(8, Evaluator.Evaluate("16/2", s => 0));
        }

        [TestMethod()]
        public void Test7()
        {
            Assert.AreEqual(6, Evaluator.Evaluate("2+X1", s => 4));
        }

        [TestMethod()]
        [ExpectedException(typeof(ArgumentException))]
        public void Test8()
        {
            Evaluator.Evaluate("2+X1", s => { throw new ArgumentException("Unknown variable"); });
        }

        [TestMethod()]
        public void Test9()
        {
            Assert.AreEqual(15, Evaluator.Evaluate("2*6+3", s => 0));
        }

        [TestMethod()]
        public void Test10()
        {
            Assert.AreEqual(20, Evaluator.Evaluate("2+6*3", s => 0));
        }

        [TestMethod()]
        public void Test11()
        {
            Assert.AreEqual(24, Evaluator.Evaluate("(2+6)*3", s => 0));
        }

        [TestMethod()]
        public void Test12()
        {
            Assert.AreEqual(16, Evaluator.Evaluate("2*(3+5)", s => 0));
        }

        [TestMethod()]
        public void Test13()
        {
            Assert.AreEqual(10, Evaluator.Evaluate("2+(3+5)", s => 0));
        }

        [TestMethod()]
        public void Test14()
        {
            Assert.AreEqual(50, Evaluator.Evaluate("2+(3+5*9)", s => 0));
        }

        [TestMethod()]
        public void Test15()
        {
            Assert.AreEqual(26, Evaluator.Evaluate("2+3*(3+5)", s => 0));
        }

        [TestMethod()]
        public void Test16()
        {
            Assert.AreEqual(194, Evaluator.Evaluate("2+3*5+(3+4*8)*5+2", s => 0));
        }

        [TestMethod()]
        [ExpectedException(typeof(ArgumentException))]
        public void Test17()
        {
            Evaluator.Evaluate("5/0", s => 0);
        }

        [TestMethod()]
        [ExpectedException(typeof(ArgumentException))]
        public void Test18()
        {
            Evaluator.Evaluate("+", s => 0);
        }

        [TestMethod()]
        [ExpectedException(typeof(ArgumentException))]
        public void Test19()
        {
            Evaluator.Evaluate("2+5+", s => 0);
        }

        [TestMethod()]
        [ExpectedException(typeof(ArgumentException))]
        public void Test20()
        {
            Evaluator.Evaluate("2+5*7)", s => 0);
        }

        [TestMethod()]
        [ExpectedException(typeof(ArgumentException))]
        public void Test21()
        {
            Evaluator.Evaluate("xx", s => 0);
        }

        [TestMethod()]
        [ExpectedException(typeof(ArgumentException))]
        public void Test22()
        {
            Evaluator.Evaluate("5+xx", s => 0);
        }

        [TestMethod()]
        [ExpectedException(typeof(ArgumentException))]
        public void Test23()
        {
            Evaluator.Evaluate("5+7+(5)8", s => 0);
        }

        [TestMethod()]
        [ExpectedException(typeof(ArgumentException))]
        public void Test24()
        {
            Evaluator.Evaluate("", s => 0);
        }

        [TestMethod()]
        public void Test25()
        {
            Assert.AreEqual(-12, Evaluator.Evaluate("y1*3-8/2+4*(8-9*2)/2*x7", s => (s == "x7") ? 1 : 4));
        }

        [TestMethod()]
        public void Test26()
        {
            Assert.AreEqual(6, Evaluator.Evaluate("x1+(x2+(x3+(x4+(x5+x6))))", s => 1));
        }

        [TestMethod()]
        public void Test27()
        {
            Assert.AreEqual(12, Evaluator.Evaluate("((((x1+x2)+x3)+x4)+x5)+x6", s => 2));
        }

        [TestMethod()]
        public void Test28()
        {
            Assert.AreEqual(0, Evaluator.Evaluate("a4-a4*a4/a4", s => 3));
        }
    }
}
