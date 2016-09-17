using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpreadsheetUtilities;
using SS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SS
{
    /// <summary>
    /// Test spreadsheet logic
    /// </summary>
    [TestClass()]
    public class SpreadsheetTests
    {

        /// <summary>
        /// Test for exception with null name
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(InvalidNameException))]
        public void GetCellContents_NullName()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            spreadsheet.GetCellContents(null);
        }

        /// <summary>
        /// Get cell content - double content
        /// </summary>
        [TestMethod()]
        public void GetCellContentsTest_DoubleContent()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            double number = 4;
            spreadsheet.SetCellContents("B1", number);
            object content = spreadsheet.GetCellContents("B1");
            Assert.IsTrue(content.GetType().IsAssignableFrom(number.GetType()));
            Assert.AreEqual(number, (double)content);
        }
        /// <summary>
        /// Get cell content - Formula content
        /// </summary>
        public void GetCellContentsTest_FormulaContent()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            double a = 3;
            Formula b = new Formula("A1*2");
            spreadsheet.SetCellContents("A1", a);
            spreadsheet.SetCellContents("B1", b);
            object content = spreadsheet.GetCellContents("B1");
            Assert.IsTrue(content.GetType().IsAssignableFrom(b.GetType()));
            Assert.AreEqual(b, (Formula)content);
        }

        /// <summary>
        /// Get cell content - text content
        /// </summary>
        [TestMethod()]
        public void GetCellContentsTest_TextContent()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            string a = "test";
            Formula b = new Formula("A1*2");
            Formula c = new Formula("A1 + B1");
            spreadsheet.SetCellContents("A1", a);
            spreadsheet.SetCellContents("B1", b);
            spreadsheet.SetCellContents("C1", c);
            object content = spreadsheet.GetCellContents("A1");
            Assert.IsTrue(content.GetType().IsAssignableFrom(a.GetType()));
            Assert.AreEqual(a, (string)content);
        }
        
        /// <summary>
        /// Test get cell contents with a cell that does not have content
        /// </summary>
        [TestMethod()]
        public void GetCellContentsTest_NewCell()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            object content = spreadsheet.GetCellContents("A1");
            string a = "";
            Assert.IsTrue(content.GetType().IsAssignableFrom(a.GetType()));
            Assert.AreEqual(a, (string)content);
        }

        /// <summary>
        /// Test get names of all non-empty cells
        /// </summary>
        [TestMethod()]
        public void GetNamesOfAllNonemptyCellsTest()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            Formula a = new Formula("3");
            Formula b = new Formula("A1*2");
            Formula c = new Formula("A1 + B1");
            spreadsheet.SetCellContents("B1", b);
            spreadsheet.SetCellContents("C1", c);
            HashSet<string> set = new HashSet<string>(spreadsheet.GetNamesOfAllNonemptyCells());
            HashSet<string> desired = new HashSet<string> { "B1", "C1" };
            Assert.IsTrue(desired.SetEquals(set));
        }

        /// <summary>
        /// Test set contents with text content
        /// </summary>
        [TestMethod()]
        public void SetCellContentsTest_TextContent()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            Formula a = new Formula("3");
            string b = "test";
            Formula c = new Formula("A1 + B1");
            spreadsheet.SetCellContents("B1", b);
            spreadsheet.SetCellContents("C1", c);
            HashSet<string> bSet = new HashSet<string>(spreadsheet.SetCellContents("B1", b));
            HashSet<string> desired = new HashSet<string> { "B1", "C1" };
            Assert.IsTrue(desired.SetEquals(bSet));
        }

        /// <summary>
        /// Test set contents with formula content
        /// </summary>
        [TestMethod()]
        public void SetCellContentsTest_FormulaContent()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            Formula a = new Formula("3");
            Formula b = new Formula("A1*2");
            Formula c = new Formula("A1 + B1");
            spreadsheet.SetCellContents("B1", b);
            spreadsheet.SetCellContents("C1", c);
            HashSet<string> aSet = new HashSet<string>(spreadsheet.SetCellContents("A1", a));
            HashSet<string> desired = new HashSet<string> { "A1", "B1", "C1" };
            Assert.IsTrue(desired.SetEquals(aSet));

            a = new Formula("A1+B1+C1+F1+G1+H1+I1+J1+K1");
            spreadsheet.SetCellContents("D1", a);
            HashSet<string> set = new HashSet<string>(spreadsheet.SetCellContents("D1", a));
            desired = new HashSet<string> { "A1", "B1", "C1", "F1", "G1", "H1", "I1", "J1", "K1", "D1" };

        }

        /// <summary>
        /// Test set contents with double content
        /// </summary>
        [TestMethod()]
        public void SetCellContentsTest_DoubleContent()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            double a = 4;
            Formula b = new Formula("A1*2");
            Formula c = new Formula("A1 + B1");
            spreadsheet.SetCellContents("B1", b);
            spreadsheet.SetCellContents("C1", c);
            HashSet<string> aSet = new HashSet<string>(spreadsheet.SetCellContents("A1", a));
            HashSet<string> desired = new HashSet<string> { "A1", "B1", "C1" };
            Assert.IsTrue(desired.SetEquals(aSet));
        }

        /// <summary>
        /// Tests for exception with null name
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetCellContents_NullName_Formula()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            Formula content = new Formula("5");
            spreadsheet.SetCellContents(null, content);
        }

        /// <summary>
        /// Tests for exception with null name
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetCellContents_NullName_String()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            string content = "5";
            spreadsheet.SetCellContents(null, content);
        }

        /// <summary>
        /// Tests for exception with null name
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetCellContents_NullName_Double()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            double content = 5;
            spreadsheet.SetCellContents(null, content);
        }
        /// <summary>
        /// Tests for exception with bad variable
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetCellContents_BadVariable()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            double content = 5;
            spreadsheet.SetCellContents("Bad Variable", content);
        }
        /// <summary>
        /// Tests for exception with null formula
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(ArgumentNullException))]
        public void SetCellContents_NullFormula()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            Formula content = null;
            spreadsheet.SetCellContents("A1", content);
        }
        /// <summary>
        /// Tests for exception with null text
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(ArgumentNullException))]
        public void SetCellContents_NullText()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            string content = null;
            spreadsheet.SetCellContents("A1", content);
        }
        /// <summary>
        /// Tests for a circular exception
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(CircularException))]
        public void SetCellContents_CircularException()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            Formula a = new Formula("B1");
            Formula b = new Formula("C1");
            Formula c = new Formula("A1");
            spreadsheet.SetCellContents("A1", a);
            spreadsheet.SetCellContents("B1", b);
            spreadsheet.SetCellContents("C1", c);
        }
        /// <summary>
        /// Tests for a circular exception
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(CircularException))]
        public void SetCellContents_CircularException2()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            Formula a = new Formula("B1 + 7 - 9 * 4");
            Formula b = new Formula("C1 / 2");
            Formula c = new Formula("A1 * 13");
            spreadsheet.SetCellContents("A1", a);
            spreadsheet.SetCellContents("B1", b);
            spreadsheet.SetCellContents("C1", c);
        }

        /// <summary>
        /// Tests for a circular exception
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(CircularException))]
        public void SetCellContents_CircularException3()
        {
            Spreadsheet spreadsheet = new Spreadsheet();
            Formula a = new Formula("B1");
            Formula b = new Formula("A1");
            spreadsheet.SetCellContents("A1", a);
            spreadsheet.SetCellContents("B1", b);
        }
    }
}