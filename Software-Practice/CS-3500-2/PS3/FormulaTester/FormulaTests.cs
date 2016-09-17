// Implementation written by Elijah Grubb, u0894728, September 2015
// for CS 3500

using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpreadsheetUtilities;
using System.Collections.Generic;

namespace FormulaTester
{
    /// <summary>
    /// Class for testing the SpreadsheetUtilities.Formula class and its methods
    /// </summary>
    [TestClass]
    public class FormulaTests
    {
        /// <summary>
        /// Test Normalize function that returns the uppercase of its input
        /// </summary>
        /// <param name="s">variable to be uppercased</param>
        /// <returns>Uppercase version of variable</returns>
        private string NormalizeCapitalize(string s)
        {
            return s.ToUpper();
        }

        /// <summary>
        /// Tests .Equals works when something's been normalized
        /// </summary>
        [TestMethod]
        public void TestDotEqualsSameNormalize()
        {
            Assert.AreEqual(true, new Formula("x1+y2", NormalizeCapitalize, s => true).Equals(new Formula("X1  +  Y2")));
        }

        /// <summary>
        /// Test that there is a difference in two things that haven't been capitalized
        /// </summary>
        [TestMethod]
        public void TestDotEqualsDiffCapitalization()
        {
            Assert.AreEqual(false, new Formula("x1+y2").Equals(new Formula("X1+Y2")));
        }

        /// <summary>
        /// Test that they aren't equal if they're in a different order
        /// </summary>
        [TestMethod]
        public void TestDotEqualsDiffOrder()
        {
            Assert.AreEqual(false, new Formula("x1+y2").Equals(new Formula("y2+x1")));
        }

        /// <summary>
        /// Test they're equal if the double has a different number of zeroes at the end
        /// </summary>
        [TestMethod]
        public void TestDotEqualsSameEndingZeroes()
        {
            Assert.AreEqual(true, new Formula("2.0 + x7").Equals(new Formula("2.000 + x7")));
        }

        /// <summary>
        /// Tests that if the input is null dotEquals should return false
        /// </summary>
        [TestMethod]
        public void TestDotEqualsParameterIsNull()
        {
            Formula nullForm = null;
            Assert.AreEqual(false, new Formula("1").Equals(nullForm));
        }

        /// <summary>
        /// Tests that .Equals returns false for objects other then formulas
        /// </summary>
        [TestMethod]
        public void TesDotEqualsItsNotFormula()
        {
            Object test = new List<string>();
            Assert.AreEqual(false, new Formula("0").Equals(test));
        }

        /// <summary>
        /// Test they're equal after normalization using ==
        /// </summary>
        [TestMethod]
        public void TestEqualsEqualsSimpleNormalize()
        {
            Assert.AreEqual(true, new Formula("x1+y2", NormalizeCapitalize, s => true)==(new Formula("X1  +  Y2")));
        }

        /// <summary>
        /// Test they're properly different capitalized v. uncapitalized using ==
        /// </summary>
        [TestMethod]
        public void TestEqualsEqualsFalseCapitalization()
        {
            Assert.AreEqual(false, new Formula("x1+y2")==(new Formula("X1+Y2")));
        }

        /// <summary>
        /// Test they're different when not in the same order using ==
        /// </summary>
        [TestMethod]
        public void TestEqualsEqualsFalseOrder()
        {
            Assert.AreEqual(false, new Formula("x1+y2")==(new Formula("y2+x1")));
        }

        /// <summary>
        /// Test they're the same no matter the number of zeroes at the end of a double using ==
        /// </summary>
        [TestMethod]
        public void TestEqualsEqualsTrueEndingZeroes()
        {
            Assert.AreEqual(true, new Formula("2.0 + x7")==(new Formula("2.000 + x7")));
        }

        /// <summary>
        /// Test if both are null that == returns true
        /// </summary>
        [TestMethod]
        public void TestEqualsEqualsBothNull()
        {
            Formula nullOne = null;
            Formula nullTwo = null;
            Assert.AreEqual(true, nullOne == nullTwo);
        }

        /// <summary>
        /// Test if only the first is null that == returns false
        /// </summary>
        [TestMethod]
        public void TestEqualsEqualsFirstNull()
        {
            Formula nullOne = null;
            Assert.AreEqual(false, nullOne == new Formula("doesntmatter"));
        }

        /// <summary>
        /// Test if only the second is null that == returns false
        /// </summary>
        [TestMethod]
        public void TestEqualsEqualsSecondNull()
        {
            Formula nullTwo = null;
            Assert.AreEqual(false, new Formula("doesntmatter") == nullTwo);
        }

        /// <summary>
        /// Test that != is false when they are the same after normalization
        /// </summary>
        [TestMethod]
        public void TestNotEqualsSameNormalized()
        {
            Assert.AreEqual(false, new Formula("x1+y2", NormalizeCapitalize, s => true) != (new Formula("X1  +  Y2")));
        }

        /// <summary>
        /// Test that != is true when they are different due to capitalization
        /// </summary>
        [TestMethod]
        public void TestNotEqualsDiffCapitalized()
        {
            Assert.AreEqual(true, new Formula("x1+y2") != (new Formula("X1+Y2")));
        }

        /// <summary>
        /// Test that != is true when they are different due to the order of tokens
        /// </summary>
        [TestMethod]
        public void TestNotEqualsDiffOrder()
        {
            Assert.AreEqual(true, new Formula("x1+y2") != (new Formula("y2+x1")));
        }

        /// <summary>
        /// Test that != is false when they are the same after ignoring excessive zeroes
        /// </summary>
        [TestMethod]
        public void TestNotEqualsSameNumberOfZeroes()
        {
            Assert.AreEqual(false, new Formula("2.0 + x7") != (new Formula("2.000 + x7")));
        }

        /// <summary>
        /// Test != returns false when they are both null
        /// </summary>
        [TestMethod]
        public void TestNotEqualsBothNull()
        {
            Formula nullOne = null;
            Formula nullTwo = null;
            Assert.AreEqual(false, nullOne != nullTwo);
        }

        /// <summary>
        /// Test != returns true when only the first one is null
        /// </summary>
        [TestMethod]
        public void TestNotEqualsFirstNull()
        {
            Formula nullOne = null;
            Assert.AreEqual(true, nullOne != new Formula("doesntmatter"));
        }

        /// <summary>
        /// Test != returns true when only the second one is null
        /// </summary>
        [TestMethod]
        public void TestNotEqualsSecondNull()
        {
            Formula nullTwo = null;
            Assert.AreEqual(true, new Formula("doesntmatter") != nullTwo);
        }

        /// <summary>
        /// Test that two hashcodes are the same if they are after being normalized
        /// </summary>
        [TestMethod]
        public void TestGetHashCodeSameNormalized()
        {
            Assert.AreEqual(true, new Formula("x1+y2", NormalizeCapitalize, s => true).GetHashCode() == (new Formula("X1  +  Y2")).GetHashCode());
        }

        /// <summary>
        /// Test that two hashcodes are different if they have different capitalization
        /// </summary>
        [TestMethod]
        public void TestGetHashCodeDifferentCapitalization()
        {
            Assert.AreEqual(false, new Formula("x1+y2").GetHashCode() == (new Formula("X1+Y2")).GetHashCode());
        }

        /// <summary>
        /// Test that two hashcodes are different if the order is different
        /// </summary>
        [TestMethod]
        public void TestGetHashCodeDifferentOrder()
        {
            Assert.AreEqual(false, new Formula("x1+y2").GetHashCode() == (new Formula("y2+x1")).GetHashCode());
        }

        /// <summary>
        /// Test that two hashcodes are the same regardless of number of zeroes after a double
        /// </summary>
        [TestMethod]
        public void TestGetHashCodeSameEndingZeroes()
        {
            Assert.AreEqual(true, new Formula("2.0 + x7").GetHashCode() == (new Formula("2.000 + x7")).GetHashCode());
        }

        /// <summary>
        /// Test that the proper variables are being returned
        /// </summary>
        [TestMethod]
        public void TestGetVariablesSimple()
        {
            List<string> correct = new List<string> { "x1", "y2", "z5" };
            List<string> test = new List<string>(new Formula("x1+y2*z5").GetVariables());
            for (int i = 0; i < correct.Count; i++)
            {
                Assert.AreEqual(true, correct[i].Equals(test[i]));
            }
        }

        /// <summary>
        /// Test that the proper variables are being after being normalized
        /// </summary>
        [TestMethod]
        public void TestGetVariablesNormalized()
        {
            List<string> correct = new List<string> { "X1", "Y2", "Z5" };
            List<string> test = new List<string>(new Formula("x1+y2*z5", NormalizeCapitalize, s => true).GetVariables());
            for (int i = 0; i < correct.Count; i++)
            {
                Assert.AreEqual(true, correct[i].Equals(test[i]));
            }
        }

        /// <summary>
        /// dictionary for lookup function
        /// </summary>
        public static Dictionary<string, int> variableDictionary = new Dictionary<string, int>();

        /// <summary>
        /// Lookup function using a dictionary
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        public static double lookupDictionary(String s)
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
            Assert.AreEqual((double)0, new Formula("0").Evaluate(s => 5));
        }

        [TestMethod]
        public void TestAddition()
        {
            Assert.AreEqual((double)7, new Formula("1 + 6").Evaluate(s => 5));
            Assert.AreEqual((double)124, new Formula("  57   +   67      ").Evaluate(s => 5));
        }

        [TestMethod]
        public void TestSubtraction()
        {
            Assert.AreEqual((double)7, new Formula("10 - 3").Evaluate(s => 5));
        }

        [TestMethod]
        public void TestMultiplication()
        {
            Assert.AreEqual((double)9, new Formula("3 * 3").Evaluate(s => 5));
        }

        [TestMethod]
        public void TestDivision()
        {
            Assert.AreEqual((double)4, new Formula("8 / 2").Evaluate(s => 5));
        }

        [TestMethod]
        public void TestComplex()
        {
            Assert.AreEqual((double)7, new Formula("(2 + 5) * 7 / (9 - 2)").Evaluate(s => 5));
            Assert.AreEqual((double)10, new Formula("(3+7)").Evaluate(s => 5));
        }

        [TestMethod]
        public void TestVariables()
        {
            variableDictionary.Add("a1", 2);
            variableDictionary.Add("b2", 3);
            variableDictionary.Add("c3", 5);
            variableDictionary.Add("d4", -7);

            Assert.AreEqual((double)10, new Formula("a1 + b2 + c3").Evaluate(lookupDictionary));
            Assert.AreEqual((double)-33, new Formula("d4 * c3 + a1").Evaluate(lookupDictionary));
        }

        /// <summary>
        /// Tests that an exception is thrown when it doesn't exist in the dictionary based function
        /// </summary>
        [TestMethod]
        public void TestDictionaryException()
        {
            Assert.AreEqual(new FormulaError("Undefined Variable").Reason, ((FormulaError)new Formula("2+X1").Evaluate(lookupDictionary)).Reason);

        }

        [TestMethod]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestBadCharacters()
        {
            new Formula("5 % 6");
        }

        [TestMethod]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestBadCharactersExtended()
        {
            new Formula("!!(#@JSDFKLJIA)(#");
        }

        [TestMethod]
        public void TestLongVariable()
        {
            Assert.AreEqual((double)5, new Formula("invalidVariable + 5").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test1()
        {
            Assert.AreEqual((double)5, new Formula("5").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test2()
        {
            Assert.AreEqual((double)13, new Formula("X5").Evaluate(s => 13));
        }

        [TestMethod()]
        public void Test3()
        {
            Assert.AreEqual((double)8, new Formula("5+3").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test4()
        {
            Assert.AreEqual((double)8, new Formula("18-10").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test5()
        {
            Assert.AreEqual((double)8, new Formula("2*4").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test6()
        {
            Assert.AreEqual((double)8, new Formula("16/2").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test7()
        {
            Assert.AreEqual((double)6, new Formula("2+X1").Evaluate(s => 4));
        }

        [TestMethod()]
        public void Test8()
        {
            Assert.AreEqual(new FormulaError("Undefined Variable").Reason, ((FormulaError)new Formula("2+X1").Evaluate(s => { throw new ArgumentException("Unknown variable"); })).Reason);
        }

        [TestMethod()]
        public void Test9()
        {
            Assert.AreEqual((double)15, new Formula("2*6+3").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test10()
        {
            Assert.AreEqual((double)20, new Formula("2+6*3").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test11()
        {
            Assert.AreEqual((double)24, new Formula("(2+6)*3").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test12()
        {
            Assert.AreEqual((double)16, new Formula("2*(3+5)").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test13()
        {
            Assert.AreEqual((double)10, new Formula("2+(3+5)").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test14()
        {
            Assert.AreEqual((double)50, new Formula("2+(3+5*9)").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test15()
        {
            Assert.AreEqual((double)26, new Formula("2+3*(3+5)").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test16()
        {
            Assert.AreEqual((double)194, new Formula("2+3*5+(3+4*8)*5+2").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test17()
        {
            Assert.AreEqual(new FormulaError("Cannot divide by zero").Reason,((FormulaError) new Formula("5/0").Evaluate(s => 0)).Reason);
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test18()
        {
            new Formula("+").Evaluate(s => 0);
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test19()
        {
            new Formula("2+5+").Evaluate(s => 0);
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test20()
        {
            new Formula("2+5*7)").Evaluate(s => 0);
        }

        [TestMethod()]
        public void Test21()
        {
            Assert.AreEqual((double)0, new Formula("xx").Evaluate(s => 0));
        }

        [TestMethod()]
        public void Test22()
        {
            Assert.AreEqual((double)5, new Formula("5+xx").Evaluate(s => 0));
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test23()
        {
            new Formula("5+7+(5)8").Evaluate(s => 0);
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test24()
        {
            new Formula("").Evaluate(s => 0);
        }

        [TestMethod()]
        public void Test25()
        {
            Assert.AreEqual((double)-12, new Formula("y1*3-8/2+4*(8-9*2)/2*x7").Evaluate(s => (s == "x7") ? 1 : 4));
        }

        [TestMethod()]
        public void Test26()
        {
            Assert.AreEqual((double)6, new Formula("x1+(x2+(x3+(x4+(x5+x6))))").Evaluate(s => 1));
        }

        [TestMethod()]
        public void Test27()
        {
            Assert.AreEqual((double)12, new Formula("((((x1+x2)+x3)+x4)+x5)+x6").Evaluate(s => 2));
        }

        [TestMethod()]
        public void Test28()
        {
            Assert.AreEqual((double)0, new Formula("a4-a4*a4/a4").Evaluate(s => 3));
        }

        /// <summary>
        /// Nothing is valid
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestIsValidFalse()
        {
            new Formula("5 + _arroba", s=>s,s=>false).Evaluate(s => 0);
        }

        /// <summary>
        /// Test that an operator can't follow an opening parenthesis
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestOperatorAfterLeftParenFail()
        {
            new Formula("(+5+7)").Evaluate(s => 0);
        }

        /// <summary>
        /// Test that odd characters throw a format exception
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestCarrotFail()
        {
            new Formula("(5)+^").Evaluate(s => 0);
        }

        /// <summary>
        /// Test that a format exception is thrown when there's too many right parens
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestMoreRightParens()
        {
            new Formula("((7 + 2) * 8").Evaluate(s => 0);
        }
    }
}
