using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SpreadsheetUtilities;
using System.Text.RegularExpressions;
using SS;

namespace SS
{
    /// <summary>
    /// Organization of cells comprised of text, numbers or formulas with the ability
    /// to perform calculations based on the calculations of other cells.
    /// </summary>
    /// <author>Tanner Barlow</author>
    public class Spreadsheet : AbstractSpreadsheet
    {
        /// <summary>
        /// Dependency graph keeps track of dependencies between cells. Used to prevent
        /// circular dependencies and to let cells depend on the values in other cells
        /// </summary>
        DependencyGraph dependency_graph;
        /// <summary>
        /// Dictionary of all cells that contain some sort of content (string, double or Formula)
        /// </summary>
        Dictionary<string, Cell> non_empty_cells;

        /// <summary>
        /// Creates an empty spreadsheet
        /// </summary>
        public Spreadsheet()
        {
            dependency_graph = new DependencyGraph();
            non_empty_cells = new Dictionary<string, Cell>();
        }
        /// <summary>
        /// If name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, returns the contents (as opposed to the value) of the named cell.  The return
        /// value should be either a string, a double, or a Formula.
        /// </summary>
        public override object GetCellContents(string name)
        {
            if(name == null || !IsVariable(name))
                throw new InvalidNameException();
            Cell cell;
            if (non_empty_cells.TryGetValue(name, out cell))
                return cell.Content;
            else
                return "";        
        }
        /// <summary>
        /// Implements functionality described in AbstractSpreadsheet.cs
        /// </summary>
        public override IEnumerable<string> GetNamesOfAllNonemptyCells()
        {
            foreach(Cell cell in non_empty_cells.Values)
            {
                yield return cell.Name;
            }
        }
        /// <summary>
        /// Implements functionality described in AbstractSpreadsheet.cs
        /// </summary>
        public override ISet<string> SetCellContents(string name, Formula formula)
        {
            if (formula == null)
                throw new ArgumentNullException();
            if (name == null || !IsVariable(name))
                throw new InvalidNameException();
            HashSet<string> set = new HashSet<string>(GetCellsToRecalculate(name));
            IEnumerable<string> variables = formula.GetVariables();
            
            //Check for any circular exceptions
            foreach (string variable in variables)
            {
                if (set.Contains(variable))
                    throw new CircularException();
            }

            //Cannot perform both operations (checking for circular exceptions
            //and adding dependencies to the graph in the same foreach loop because
            //adding a dependency would change the spreadsheet. By definition
            //in the comments, spreadsheet should remained unchanged if ANY
            //circular exceptions are found. Since we don't know if there are
            //circular exceptions until we have looked at each variable, we
            //have to separate the two steps and add the dependencies AFTER
            //we have scanned for circular exceptions.
            foreach(string variable in variables)
            {
                dependency_graph.AddDependency(name, variable);
            }
            //Cell has been set. Add to NonEmpty list
            AddCellToNonEmpty(name, formula);
            return set;
        }

        /// <summary>
        /// Implements functionality described in AbstractSpreadsheet.cs
        /// </summary>
        public override ISet<string> SetCellContents(string name, string text)
        {
            if (name == null || !IsVariable(name))
                throw new InvalidNameException();
            if (text == null)
                throw new ArgumentNullException();
            if (text.Equals(""))
                return new HashSet<string>();
            AddCellToNonEmpty(name, text);
            return new HashSet<string>(GetCellsToRecalculate(name));
        }
        /// <summary>
        /// Implements functionality described in AbstractSpreadsheet.cs
        /// </summary>
        public override ISet<string> SetCellContents(string name, double number)
        {
            if (name == null || !IsVariable(name))
                throw new InvalidNameException();
            AddCellToNonEmpty(name, number);
            return new HashSet<string>(GetCellsToRecalculate(name));
        }

        /// <summary>
        /// Implements functionality described in AbstractSpreadsheet.cs
        /// </summary>
        private void AddCellToNonEmpty(string name, object o)
        {
            Cell cell;
            if (non_empty_cells.TryGetValue(name, out cell))
            {
                cell.Content = o;
            }
            else
            {
                cell = new Cell(name);
                cell.Content = o;
                non_empty_cells.Add(name, cell);
            }
        }


        /// <summary>
        /// Implements functionality described in AbstractSpreadsheet.cs
        /// </summary>
        protected override IEnumerable<string> GetDirectDependents(string name)
        {
            if (name == null)
                throw new ArgumentNullException();
            if (!IsVariable(name))
                throw new InvalidNameException();
            return dependency_graph.GetDependees(name); 
        }

        /// <summary>
        /// Implements functionality described in AbstractSpreadsheet.cs
        /// </summary>
        public bool IsVariable(string s)
        {
            return Regex.IsMatch(s, "^([a-zA-Z_][a-zA-Z0-9_]*)$");
        }
    }
}
