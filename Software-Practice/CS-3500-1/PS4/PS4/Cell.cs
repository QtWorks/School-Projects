using SpreadsheetUtilities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SS
{
    class Cell
    {
        string name;
        IEnumerable<string> dependents;
        object content; //todo need this to cover text, formula and number
        //so that when I return it, they can deal with it from there
        string text;
        Formula formula;
        double number;


        public Cell(string name)
        {
            this.name = name;
        }
        /* Might use in the future
        public Cell(string name, string contents)
        {
            this.name = name;
            this.text = contents;
        }

        public Cell(string name, string contents, IEnumerable<string> dependents)
        {
            this.name = name;
            this.text = contents;
            this.dependents = dependents;
        }*/

        public string Name
        {
            get { return name; }
            private set { name = value; }
        }

        public object Content
        {
            get { return content; }
            set { content = value; }
        }
        /* Might use in the future
        public string Text
        {
            get { return text; }
            set { text = value; }
        }

        public IEnumerable<string> Dependents
        {
            get { return dependents; }
            set { dependents = value; }
        }

        public Formula Formula
        {
            get { return formula; }
            set { formula = value; }
        }

        public double Number
        {
            get { return number; }
            set { number = value; }
        }
        */
    }
}
