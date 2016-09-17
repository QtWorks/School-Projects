using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpreadsheetUtilities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpreadsheetUtilities.Tests
{
    [TestClass()]
    public class FormulaTests
    {

        [TestMethod()]
        public void PublicFormulaTest()
        {
            Formula f1 = new Formula("5");
            f1 = new Formula("(5+6)");
            f1 = new Formula("(5+x6)");
            f1 = new Formula("(5      +      6)");
            f1 = new Formula("(5      +      x6)");
            f1 = new Formula("(5+6)*8-z2+78");
            f1 = new Formula("123456789123456789");
            f1 = new Formula(".00000000000000005");
            f1 = new Formula("(5+6)");

        }

        /*[TestMethod()]
        public void PublicFormulaTest1()
        {
            Assert.Fail();
        }

        [TestMethod()]
        public void PublicEvaluateTest()
        {
            Assert.Fail();
        }*/


        /// <summary>
        /// Verify that all variables evaluate to lambda input
        /// </summary>
        [TestMethod()]
        public void PublicVariableTest()
        {
            Formula f1 = new Formula("x6 * 7");
            Assert.IsTrue(14 == (double) f1.Evaluate(s => 2));

            Formula f2 = new Formula("x * 7");
            Assert.IsTrue(21 == (double)f2.Evaluate(s => 3));

            Formula f3 = new Formula("z173482934875_____3452");
            Assert.IsTrue(2 == (double)f3.Evaluate(s => 2));

            Formula f4 = new Formula("_variable * 10");
            Assert.IsTrue(20 == (double)f4.Evaluate(s => 2));

            Formula f5 = new Formula("x6 * x17");
            Assert.IsTrue(4 == (double)f5.Evaluate(s => 2));
        }

        /// <summary>
        /// Verify that no valid variables throw an exception
        /// </summary>
        [TestMethod()]
        public void PublicValidVariablesTest()
        {
            Formula f1 = new Formula("_6 + 5");
            f1 = new Formula("x");
            f1 = new Formula("x98794875927592837498237498");
            f1 = new Formula("x__________5");
            f1 = new Formula("x_7_8_9_10_____12343_____");
            f1 = new Formula("________________________");
            f1 = new Formula("xaakfjkdjfklskdjfklsj");
        }

        /// <summary>
        /// Verify that invalid variable thorws exception
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicInvalidVariablesTest1()
        {
            Formula f1 = new Formula("6x");
        }
        /// <summary>
        /// Verify that invalid variable thorws exception
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicInvalidVariablesTest2()
        {
            Formula f1 = new Formula("?");
            
        }
        /// <summary>
        /// Verify that invalid variable thorws exception
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicInvalidVariablesTest3()
        {
            Formula f1 = new Formula("*&^%^&*&^%");
            
        }
        /// <summary>
        /// Verify that invalid variable thorws exception
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicInvalidVariablesTest4()
        {
            Formula f1 = new Formula("I_Am_B@d");
        }
        /// <summary>
        /// Verify that invalid variable thorws exception
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicInvalidVariablesTest5()
        {
            Formula f1 = new Formula("&");
        }

        /// <summary>
        /// Verify that GetVariables behaves accordingly
        /// </summary>
        [TestMethod()]
        public void PublicGetVariablesTest()
        {
            HashSet<string> variables = new HashSet<string>() { "x1", "y1", "z1" };
            Formula f1 = new Formula("x1 + y1 / z1");
            Assert.IsTrue(variables.SetEquals(f1.GetVariables()));
            variables = new HashSet<string>() { "x1", "z1" };
            Formula f2 = new Formula("x1 + 6 / z1");
            Assert.IsTrue(variables.SetEquals(f2.GetVariables()));

        }

        /// <summary>
        /// Make sure we are getting a formula error with divide by zero
        /// </summary>
        [TestMethod()]
        public void PublicFormulaErrorTest()
        {
            Formula f1 = new Formula("5/0");
            FormulaError error = new FormulaError();
            FormulaError fError = (FormulaError)f1.Evaluate(s => 0);
            Assert.IsTrue(f1.Evaluate(s=>0).GetType().IsAssignableFrom(error.GetType()));
            Assert.IsTrue(fError.Reason.Equals("Cannot divide by zero"));

        }

        /// <summary>
        /// Make sure we are getting a formula error with divide by zero
        /// </summary>
        [TestMethod()]
        public void PublicFormulaErrorTest2()
        {
            Formula f1 = new Formula("x5/0");
            FormulaError error = new FormulaError();
            FormulaError fError = (FormulaError)f1.Evaluate(s => 2);
            Assert.IsTrue(f1.Evaluate(s => 0).GetType().IsAssignableFrom(error.GetType()));
            Assert.IsTrue(fError.Reason.Equals("Cannot divide by zero"));
        }

        /// <summary>
        /// Make sure strings are evaluating to the same things where necessary.
        /// Make sure ToString is removing spaces
        /// </summary>
        [TestMethod()]
        public void PublicToStringTest()
        {
            Formula f1 = new Formula("x1 + y1");
            Assert.AreEqual("x1+y1", f1.ToString());
            Assert.AreNotEqual("x1 + y1", f1.ToString());

            Formula f2 = new Formula("X1 / Y1");
            Assert.AreEqual("X1/Y1", f2.ToString());

            Formula f3 = new Formula("x1 * Y1 / Z1");
            //Make last letter lower case
            Assert.AreNotEqual("x1*Y1/z1", f3.ToString());
        }
        /// <summary>
        /// Make sure space and variables are not a factor for equality where necessary
        /// </summary>
        [TestMethod()]
        public void PublicEqualsOperatorTest()
        {
            Formula f1 = new Formula("a6 + b7");
            Formula f2 = new Formula("a6+b7");
            Formula f3 = new Formula("a7 + b8", s => s.ToUpper(), s => true);
            Formula f4 = new Formula("A7+B8");
            Formula f5 = null;
            Formula f6 = null;

            Assert.AreEqual(f1, f2);
            Assert.IsTrue(f1 == f2);
            Assert.IsFalse(f1 != f2);

            Assert.AreEqual(f3, f4);
            Assert.IsTrue(f3 == f4);
            Assert.IsFalse(f3 != f4);

            Assert.AreNotEqual(f1, f3);
            Assert.IsTrue(f1 != f3);
            Assert.IsFalse(f1 == f3);

            Assert.IsTrue(f5 == f6);
            Assert.IsFalse(f5 == f4);

            Assert.IsFalse(f1.Equals(f5));
        }

        /// <summary>
        /// Make sure that floats are recognized as the same numbers
        /// </summary>
        [TestMethod()]
        public void PublicEqualsFloatTest()
        {
            Formula f1 = new Formula("2.0");
            Formula f2 = new Formula("2.0000");
            Formula f3 = new Formula("2.5");

            Assert.IsTrue(f1.Equals(f2));
            Assert.IsTrue(f1 == f2);
            Assert.IsFalse(f1.Equals(f3));
            Assert.IsTrue(f1 != f3);

            Assert.IsTrue(new Formula("2.0 + x7").Equals(new Formula("2.000 + x7")));

            Assert.IsTrue(new Formula("x1+y2", s => s.ToUpper(), s => true).Equals(new Formula("X1  +  Y2")));
            Assert.IsFalse(new Formula("x1+y2").Equals(new Formula("X1+Y2")));
            Assert.IsFalse(new Formula("x1+y2").Equals(new Formula("y2+x1")));
        }

        /// <summary>
        /// Test very small numbers
        /// </summary>
        [TestMethod()]
        public void PublicPrecisionTest()
        {
            Formula f1 = new Formula("(0.0000000000000000000001 + 0.0000000000000000000003)");
            Assert.AreEqual(0.0000000000000000000004, f1.Evaluate(s => 0));
            f1 = new Formula("5e-5 * 10");
            Assert.AreEqual(0.0005, f1.Evaluate(s => 0));
        }

        /// <summary>
        /// Test that zero is valid
        /// </summary>
        [TestMethod()]
        public void PublicZeroTest()
        {
            Formula f1 = new Formula("0");
            Assert.IsTrue(0 == (int) (double) f1.Evaluate(s => 0));

        }

        /// <summary>
        /// Hash code should be equal with formulas that are considered equal. Should be unequal otherwise
        /// </summary>
        [TestMethod()]
        public void PublicGetHashCodeTest()
        {
            Formula f1 = new Formula("6 + 7");
            Formula f2 = new Formula("6+7");
            Formula f3 = new Formula("x6 + 3");
            Formula f4 = new Formula("(x6 + 9) * t5 + x17 - 12");
            Formula f5 = new Formula("(x6+9)*t5+x17-12");
            Formula f6 = new Formula("6 + 8");

            Assert.AreEqual(f1, f2);
            Assert.AreEqual(f1.GetHashCode(), f2.GetHashCode());
            Assert.AreNotEqual(f1, f3);
            Assert.AreNotEqual(f1.GetHashCode(), f3.GetHashCode());
            Assert.AreEqual(f4, f5);
            Assert.AreEqual(f4.GetHashCode(), f5.GetHashCode());
            Assert.AreNotEqual(f1.GetHashCode(), f6.GetHashCode());
        }

        /// <summary>
        /// No tokens
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicNoTokens()
        {
            Formula f1 = new Formula("");
        }

        /// <summary>
        /// No characters
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicNoTokensSpace()
        {
            Formula f1 = new Formula(" ");
        }

        /// <summary>
        /// Invalid closing paren
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicRightParen1()
        {
            Formula f1 = new Formula(")");
        }

        /// <summary>
        /// Closing paren before 
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicRightParen2()
        {
            Formula f1 = new Formula("x + y) - 7");
        }

        /// <summary>
        /// Invalid operator following parentheses
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicParen3()
        {
            Formula f1 = new Formula("(6 + 9) - (+9)");
        }

        /// <summary>
        /// Extra trailing parentheses
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicRightParen4()
        {
            Formula f1 = new Formula("(5 + 3) - 8 * (6 + 5))");
        }

        /// <summary>
        /// Unbalanced parentheses
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicBalancedParen1()
        {
            Formula f1 = new Formula("(((((((((((((((((((()))))))))))))))))))))");
        }
        /// <summary>
        /// Missing closing parentheses
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicBalancedParen2()
        {
            Formula f1 = new Formula("(6 + 7) * (8 + 6");
        }
        /// <summary>
        /// Invalid starting character
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicStartingToken1()
        {
            Formula f1 = new Formula("+6");
        }
        /// <summary>
        /// Invalid starting character
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicStartingToken2()
        {
            Formula f1 = new Formula(")x + 6");
        }

        /// <summary>
        /// Invalid starting character
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicStartingToken3()
        {
            Formula f1 = new Formula(")");
        }
        /// <summary>
        /// Invalid ending operator
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicEndingToken1()
        {
            Formula f1 = new Formula("6+5+4+");
        }

        /// <summary>
        /// Invalid following parentheses
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicEndingToken2()
        {
            Formula f1 = new Formula("6+5+4*(");
        }

        /// <summary>
        /// Invalid following operator
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicParenFollow1()
        {
            Formula f1 = new Formula("(*6)");
        }

        /// <summary>
        /// Invalid following operator
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicParenFollow2()
        {
            Formula f1 = new Formula("(-6)");
        }

        /// <summary>
        /// Invalid following operator
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicOperatorFollow1()
        {
            Formula f1 = new Formula("6+-6");
        }

        /// <summary>
        /// Invalid following parentheses
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicOperatorFollow2()
        {
            Formula f1 = new Formula("(6+)");
        }

        /// <summary>
        /// Invalid following number
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicNumberFollow1()
        {
            Formula f1 = new Formula("3 5");
        }

        /// <summary>
        /// Invalid following number
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicNumberFollow2()
        {
            Formula f1 = new Formula("a1 5");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicNumberFollow3()
        {
            Formula f1 = new Formula("5 a2");
        }

        /// <summary>
        /// Invalid following variable
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicNumberFollow4()
        {
            Formula f1 = new Formula("a2 x6");
        }

        /// <summary>
        /// Invalid following number
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicNumberFollow5()
        {
            Formula f1 = new Formula("(6 + 7) 6");
        }

        /// <summary>
        /// Invalid following variable
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicNumberFollow6()
        {
            Formula f1 = new Formula("(6 + 7) x6");
        }

        /// <summary>
        /// Invalid variable
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicInvalidVariable2()
        {
            Formula f1 = new Formula("2x+y3");
        }

        /// <summary>
        /// Check for null string formula
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicNullFormula()
        {
            Formula f1 = new Formula(null);
        }

        /// <summary>
        /// Check for empty string formula
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void PublicEmptyStringFormula()
        {
            Formula f1 = new Formula("");
        }
    }
}