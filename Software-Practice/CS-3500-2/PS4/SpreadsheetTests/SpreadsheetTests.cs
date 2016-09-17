// Implementation written by Elijah Grubb, u0894728, October 2015
// for CS 3500

using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SS;
using SpreadsheetUtilities;
using System.Collections.Generic;

namespace SpreadsheetTests
{
    /// <summary>
    /// Class used to test the Spreadsheet class
    /// </summary>
    [TestClass]
    public class SpreadsheetTests
    {
        /// <summary>
        /// Makes sure passing null to getcellcontents throws an exception
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestNullStringGetCellContentsThrowsException()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.GetCellContents(null);
        }

        /// <summary>
        /// Make sure there's an error if a number is the first character
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestStringBadNameNumberStartGetCellContentsThrowsException()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.GetCellContents("9test");
        }

        /// <summary>
        /// Makes sure there's an error if the input formula is null
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(ArgumentNullException))]
        public void TestSetCellContentsNullFormulaThrowsException()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            Formula test = null;
            sheet.SetCellContents("test", test);
        }

        /// <summary>
        /// Makes sure there's an error if the cell name starts with a number
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetCellContentsBadNameThrowsException()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            Formula test = new Formula("a5 + b4");
            sheet.SetCellContents("9test", test);
        }

        /// <summary>
        /// Makes sure there's an error if the cell name starts with a number with text
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetCellContentsBadNameThrowsExceptionDouble()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("9test", "test");
        }

        /// <summary>
        /// Makes sure there's an error if the cell name is null
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetCellContentsNullNameThrowsException()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            Formula test = new Formula("a5 + b4");
            sheet.SetCellContents(null, test);
        }

        /// <summary>
        /// Makes sure there's an error if the cell name is null
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetCellContentsNullTextThrowsException()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("test", (string) null);
        }

        /// <summary>
        /// Makes sure there's an error if the cell name has a special character
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestStringBadNameSpecialCharacterGetCellContentsThrowsException()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.GetCellContents("_xy%");
        }

        /// <summary>
        /// Makes sure there's an error if the requested cell doesn't exist
        /// </summary>
        [TestMethod]
        public void TestStringCellDoesntExistGetCellContentsThrowsException()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            Assert.AreEqual("", sheet.GetCellContents("test"));
        }

        /// <summary>
        /// Makes sure that different capitalizations are different cells
        /// </summary>
        [TestMethod]
        public void TestStringCellDoesntExistCapsGetCellContentsThrowsException()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("test", "test");
            Assert.AreEqual("", sheet.GetCellContents("Test"));
        }

        /// <summary>
        /// Makes sure that the correct cell contents are returning when
        /// inputing a string
        /// </summary>
        [TestMethod]
        public void TestGetCellContentsBasicFunctionalityStringInput()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("test", "test");
            Assert.AreEqual("test", (string)sheet.GetCellContents("test"));
        }

        /// <summary>
        /// Makes sure that the correct cell contents are returning when
        /// inputing a Formula
        /// </summary>
        [TestMethod]
        public void TestGetCellContentsBasicFunctionalityFormulaInput()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("test", new Formula("a5 + b4"));
            Assert.AreEqual(true, new Formula("a5 + b4").Equals((Formula) sheet.GetCellContents("test")));
        }

        /// <summary>
        /// Makes sure that the correct cell contents are returning when
        /// inputing a double
        /// </summary>
        [TestMethod]
        public void TestGetCellContentsBasicFunctionalityDoubleInput()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("test", 5.5);
            Assert.AreEqual(5.5, (double)sheet.GetCellContents("test"));
        }

        /// <summary>
        /// Makes sure that the GetNamesOfAllNonemptyCells method is returning properly
        /// </summary>
        [TestMethod]
        public void TestGetNamesOfAllNonemptyCellsBasic()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("test1", "don't matter");
            sheet.SetCellContents("test2", "don't matter");
            sheet.SetCellContents("test3", "don't matter");
            sheet.SetCellContents("test4", "don't matter");
            sheet.SetCellContents("ItIsEmpty", "");
            List<string> answer = new List<string> { "test1", "test2", "test3", "test4" };
            List<string> result = new List<string>(sheet.GetNamesOfAllNonemptyCells());
            result.Sort();

            for (int i = 0; i < answer.Count; i++)
            {
                Assert.AreEqual(answer[i], result[i]);
            }
        }

        /// <summary>
        /// Test that when adding a double the setcells method is returns the set correctly
        /// </summary>
        [TestMethod]
        public void TestSetCellsReturningCorrectlyDouble()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("b2", new Formula("a1 + 5"));
            sheet.SetCellContents("c3", new Formula("b1 + a1"));
            sheet.SetCellContents("d4", new Formula("c3 * 2"));

            List<string> answer = new List<string> { "a1", "b2", "c3", "d4" };
            ISet<string> result = (HashSet<string>)sheet.SetCellContents("a1", 5.5);
            List<string> listResult = new List<string>(result);
            listResult.Sort();

            for (int i = 0; i < answer.Count; i++)
            {
                Assert.AreEqual(answer[i], listResult[i]);
            }

        }

        /// <summary>
        /// Test that when adding a formula the setcells method is returns the set correctly
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// set {A1, B1, C1} is returned.
        /// </summary>
        [TestMethod]
        public void TestSetCellsReturningCorrectlyFormula()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("B1", new Formula("A1 * 2"));
            sheet.SetCellContents("C1", new Formula("B1 + A1"));

            List<string> answer = new List<string> { "A1", "B1", "C1"};
            ISet<string> result = (HashSet<string>)sheet.SetCellContents("A1", new Formula("d7 - 2"));
            List<string> listResult = new List<string>(result);
            listResult.Sort();

            for (int i = 0; i < answer.Count; i++)
            {
                Assert.AreEqual(answer[i], listResult[i]);
            }

        }

        /// <summary>
        /// Test that when adding text the setcells method is returns the set correctly
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// set {A1, B1, C1} is returned.
        /// </summary>
        [TestMethod]
        public void TestSetCellsReturningCorrectlyText()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("B1", new Formula("A1 * 2"));
            sheet.SetCellContents("C1", new Formula("B1 + A1"));

            List<string> answer = new List<string> { "A1", "B1", "C1" };
            ISet<string> result = (HashSet<string>)sheet.SetCellContents("A1", "test");
            List<string> listResult = new List<string>(result);
            listResult.Sort();

            for (int i = 0; i < answer.Count; i++)
            {
                Assert.AreEqual(answer[i], listResult[i]);
            }

        }

        /// <summary>
        /// Makes sure there's an error if the input formula is null
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetCellContentsNullNameThrowsExceptionDouble()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents(null, 5);
        }

        /// <summary>
        /// Makes sure old dependecies are removed when resetting a 
        /// cell with a double
        /// </summary>
        [TestMethod]
        public void TestSetCellContentRemovesDependentsDouble()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("a1", new Formula("b2 + c3"));
            sheet.SetCellContents("c3", 5);

            sheet.SetCellContents("a1", 5);

            try
            {
                sheet.SetCellContents("b2", new Formula("a1"));
            }
            catch(CircularException e)
            {
                throw new AssertFailedException(e.Message);
            }
        }

        /// <summary>
        /// Makes sure old dependecies are removed when resetting a 
        /// cell with text
        /// </summary>
        [TestMethod]
        public void TestSetCellContentRemovesDependentsText()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("a1", new Formula("b2 + c3"));
            sheet.SetCellContents("c3", 5);

            sheet.SetCellContents("a1", "test");

            try
            {
                sheet.SetCellContents("b2", new Formula("a1"));
            }
            catch (CircularException e)
            {
                throw new AssertFailedException(e.Message);
            }
        }

        /// <summary>
        /// Makes sure old dependecies are removed when resetting a 
        /// cell with a new formula
        /// </summary>
        [TestMethod]
        public void TestSetCellContentRemovesDependentsFormula()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("a1", new Formula("b2 + c3"));
            sheet.SetCellContents("c3", 5);

            sheet.SetCellContents("a1", new Formula("d4"));

            try
            {
                sheet.SetCellContents("b2", new Formula("a1"));
            }
            catch (CircularException e)
            {
                throw new AssertFailedException(e.Message);
            }
        }

        /// <summary>
        /// Make sure that when you create cells with a circular motion that 
        /// you throw a circular exception!!
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(CircularException))]
        public void TestCircularException()
        {
            AbstractSpreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("a1", new Formula("b2 * 2"));
            sheet.SetCellContents("b2", new Formula("c3 * 7"));
            sheet.SetCellContents("c3", new Formula("d4 - 6"));
            sheet.SetCellContents("d4", new Formula("a1 + 5"));
        }
    }
}
