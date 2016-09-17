// Implementation written by Elijah Grubb, u0894728, October 2015
// for CS 3500

using SS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SpreadsheetUtilities;
using System.Text.RegularExpressions;

namespace SS
{
    /// <summary>
    /// Represents the state of a simple spreadsheet.  A 
    /// spreadsheet consists of an infinite number of named cells.
    /// 
    /// A string is a valid cell name if and only if:
    ///   (1) its first character is an underscore or a letter
    ///   (2) its remaining characters (if any) are underscores and/or letters and/or digits
    /// Note that this is the same as the definition of valid variable from the PS3 Formula class.
    /// 
    /// For example, "x", "_", "x2", "y_15", and "___" are all valid cell  names, but
    /// "25", "2x", and "&" are not.  Cell names are case sensitive, so "x" and "X" are
    /// different cell names.
    /// 
    /// A spreadsheet contains a cell corresponding to every possible cell name.  (This
    /// means that a spreadsheet contains an infinite number of cells.)  In addition to 
    /// a name, each cell has a contents and a value.  The distinction is important.
    /// 
    /// The contents of a cell can be (1) a string, (2) a double, or (3) a Formula.  If the
    /// contents is an empty string, we say that the cell is empty.  (By analogy, the contents
    /// of a cell in Excel is what is displayed on the editing line when the cell is selected.)
    /// 
    /// In a new spreadsheet, the contents of every cell is the empty string.
    ///  
    /// The value of a cell can be (1) a string, (2) a double, or (3) a FormulaError.  
    /// (By analogy, the value of an Excel cell is what is displayed in that cell's position
    /// in the grid.)
    /// 
    /// If a cell's contents is a string, its value is that string.
    /// 
    /// If a cell's contents is a double, its value is that double.
    /// 
    /// If a cell's contents is a Formula, its value is either a double or a FormulaError,
    /// as reported by the Evaluate method of the Formula class.  The value of a Formula,
    /// of course, can depend on the values of variables.  The value of a variable is the 
    /// value of the spreadsheet cell it names (if that cell's value is a double) or 
    /// is undefined (otherwise).
    /// 
    /// Spreadsheets are never allowed to contain a combination of Formulas that establish
    /// a circular dependency.  A circular dependency exists when a cell depends on itself.
    /// For example, suppose that A1 contains B1*2, B1 contains C1*2, and C1 contains A1*2.
    /// A1 depends on B1, which depends on C1, which depends on A1.  That's a circular
    /// dependency.
    /// </summary>
    public class Spreadsheet : AbstractSpreadsheet
    {
        /// <summary>
        /// Dictionary for holding our Cells
        /// 
        /// The name of the cell acts as a key since all names are unique and
        /// each key points to a coresponding cell object
        /// </summary>
        private Dictionary<string, Cell> CellDictionary;

        /// <summary>
        /// Dependency graph for our spreadsheet to keep track of who depends on
        /// what
        /// </summary>
        private DependencyGraph Graph;

        /// <summary>
        /// Constructor to initialize member variables
        /// </summary>
        public Spreadsheet()
        {
            CellDictionary = new Dictionary<string, Cell>();
            Graph = new DependencyGraph();
        }
        
        /// <summary>
        /// If name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, returns the contents (as opposed to the value) of the named cell.  The return
        /// value should be either a string, a double, or a Formula.
        /// </summary>
        public override object GetCellContents(string name)
        {
            if (name == null || !Regex.IsMatch(name, "^([a-zA-Z_][a-zA-Z0-9_]*)$"))
            {
                throw new InvalidNameException();
            }
            // if cell hasn't been initialized, then it's contents is just an empty string
            else if (!CellDictionary.ContainsKey(name))
            {
                return "";
            }
            else
            {
                return CellDictionary[name].Contents;
            }
        }

        /// <summary>
        /// Enumerates the names of all the non-empty cells in the spreadsheet.
        /// </summary>
        public override IEnumerable<string> GetNamesOfAllNonemptyCells()
        {
            foreach (KeyValuePair<string, Cell> c in CellDictionary)
            {
                if (c.Value != null && c.Value.Contents != null && !c.Value.Contents.Equals(""))
                {
                    yield return c.Value.Name;
                }
                else
                {
                    continue;
                }
            }
        }

        /// <summary>
        /// If the formula parameter is null, throws an ArgumentNullException.
        /// 
        /// Otherwise, if name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, if changing the contents of the named cell to be the formula would cause a 
        /// circular dependency, throws a CircularException.  (No change is made to the spreadsheet.)
        /// 
        /// Otherwise, the contents of the named cell becomes formula.  The method returns a
        /// Set consisting of name plus the names of all other cells whose value depends,
        /// directly or indirectly, on the named cell.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// set {A1, B1, C1} is returned.
        /// </summary>
        public override ISet<string> SetCellContents(string name, Formula formula)
        {
            if (formula == null)
            {
                throw new ArgumentNullException("Formula cannot be null");
            }
            if (NameIsInvalid(name))
            {
                throw new InvalidNameException();
            }

            List<string> variables = new List<string>(formula.GetVariables());

            GetCellsToRecalculate(new HashSet<string>(variables));

            if (Graph.HasDependents(name))
            {
                Graph.ReplaceDependents(name, new List<string>());
            }

            foreach (string s in variables)
            {
                Graph.AddDependency(name, s);
            }

            CellDictionary[name] = new Cell(name, formula);

            return new HashSet<string>(GetCellsToRecalculate(name));
        }

        /// <summary>
        /// Returns true if name is null or an invalid cell name
        /// Else returns false
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
        private bool NameIsInvalid(string name)
        {
            if (name == null || !Regex.IsMatch(name, "^([a-zA-Z_][a-zA-Z0-9_]*)$"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        /// <summary>
        /// If text is null, throws an ArgumentNullException.
        /// 
        /// Otherwise, if name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, the contents of the named cell becomes text.  The method returns a
        /// set consisting of name plus the names of all other cells whose value depends, 
        /// directly or indirectly, on the named cell.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// set {A1, B1, C1} is returned.
        /// </summary>
        public override ISet<string> SetCellContents(string name, string text)
        {
            if (NameIsInvalid(name) || text == null)
            {
                throw new InvalidNameException();
            }

            if (Graph.HasDependents(name))
            {
                Graph.ReplaceDependents(name, new List<string>());
            }

            CellDictionary[name] = new Cell(name, text);

            return new HashSet<string>(GetCellsToRecalculate(name));
        }

        /// <summary>
        /// If name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, the contents of the named cell becomes number.  The method returns a
        /// set consisting of name plus the names of all other cells whose value depends, 
        /// directly or indirectly, on the named cell.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// set {A1, B1, C1} is returned.
        /// </summary>
        public override ISet<string> SetCellContents(string name, double number)
        {
            if (NameIsInvalid(name))
            {
                throw new InvalidNameException();
            }

            if (Graph.HasDependents(name))
            {
                Graph.ReplaceDependents(name, new List<string>());
            }

            CellDictionary[name] = new Cell(name,number);

            return new HashSet<string>(GetCellsToRecalculate(name));
        }

        /// <summary>
        /// If name is null, throws an ArgumentNullException.
        /// 
        /// Otherwise, if name isn't a valid cell name, throws an InvalidNameException.
        /// 
        /// Otherwise, returns an enumeration, without duplicates, of the names of all cells whose
        /// values depend directly on the value of the named cell.  In other words, returns
        /// an enumeration, without duplicates, of the names of all cells that contain
        /// formulas containing name.
        /// 
        /// For example, suppose that
        /// A1 contains 3
        /// B1 contains the formula A1 * A1
        /// C1 contains the formula B1 + A1
        /// D1 contains the formula B1 - C1
        /// The direct dependents of A1 are B1 and C1
        /// </summary>
        protected override IEnumerable<string> GetDirectDependents(string name)
        {
            return Graph.GetDependees(name);
        }

        /// <summary>
        /// Cell class to function as an object to represent each of our individual
        /// cells in our spreadsheet.
        /// 
        /// A Cell consists of two defining traits. It's name (which is basically
        /// its entire identity) and the contents of the Cell.
        /// 
        /// A Cell can also give you its value, however this is calculated by
        /// what its contents are
        /// </summary>
        private class Cell
        {
            /// <summary>
            /// String that contains the unique key to this cell: its name
            /// This is what the cell is called and what differentiates it from all
            /// other cells
            /// </summary>
            public string Name{ get; }
            /// <summary>
            /// Contents of the cell. However, not its computed value.
            /// </summary>
            public object Contents { get; set; }

            /// <summary>
            /// Constructer to setup basic Cell object
            /// </summary>
            /// <param name="name"></param>
            /// <param name="contents"></param>
            public Cell(string name, object contents)
            {
                this.Name = name;
                this.Contents = contents;
            }
        }
    }
}
