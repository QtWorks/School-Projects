// Skeleton implementation written by Joe Zachary for CS 3500, September 2013.
// Version 1.1 (Fixed error in comment for RemoveDependency.)

using SpreadsheetUtilities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

/// <summary>
/// Utilities for Spreadsheet application
/// </summary>
namespace SpreadsheetUtilities
{

    /// <summary>
    /// (s1,t1) is an ordered pair of strings
    /// s1 depends on t1 --> t1 must be evaluated before s1
    /// 
    /// A DependencyGraph can be modeled as a set of ordered pairs of strings.  Two ordered pairs
    /// (s1,t1) and (s2,t2) are considered equal if and only if s1 equals s2 and t1 equals t2.
    /// (Recall that sets never contain duplicates.  If an attempt is made to add an element to a 
    /// set, and the element is already in the set, the set remains unchanged.)
    /// 
    /// Given a DependencyGraph DG:
    /// 
    ///    (1) If s is a string, the set of all strings t such that (s,t) is in DG is called dependents(s).
    ///        
    ///    (2) If s is a string, the set of all strings t such that (t,s) is in DG is called dependees(s).
    //
    // For example, suppose DG = {("a", "b"), ("a", "c"), ("b", "d"), ("d", "d")}
    //     dependents("a") = {"b", "c"}
    //     dependents("b") = {"d"}
    //     dependents("c") = {}
    //     dependents("d") = {"d"}
    //     dependees("a") = {}
    //     dependees("b") = {"a"}
    //     dependees("c") = {"a"}
    //     dependees("d") = {"b", "d"}
    /// </summary>
    /// 
    public class DependencyGraph
    {
        /// <summary>
        /// Dictionary of all dependents, indexed by string
        /// </summary>
        private Dictionary<string, LinkedList<string>> dependents;

        /// <summary>
        /// Dictionary of all dependees, indexed by string
        /// </summary>
        private Dictionary<string, LinkedList<string>> dependees;

        /// <summary>
        /// Number of ordered pairs in dependency graph
        /// </summary>
        private int size;

        /// <summary>
        /// Creates an empty DependencyGraph.
        /// </summary>
        public DependencyGraph()
        {
            dependents = new Dictionary<string, LinkedList<string>>();
            dependees = new Dictionary<string, LinkedList<string>>();
        }


        /// <summary>
        /// The number of ordered pairs in the DependencyGraph.
        /// </summary>
        public int Size
        {
            get { return size; }
        }


        /// <summary>
        /// The size of dependees(s).
        /// This property is an example of an indexer.  If dg is a DependencyGraph, you would
        /// invoke it like this:
        /// dg["a"]
        /// It should return the size of dependees("a")
        /// </summary>
        public int this[string s]
        {
            get {
                LinkedList<string> list = null;
                dependees.TryGetValue(s, out list);
                if (list != null) return list.Count();
                else return 0;
            }
        }


        /// <summary>
        /// Reports whether dependents(s) is non-empty.
        /// </summary>
        public bool HasDependents(string s)
        {
            LinkedList<string> list = null;
            dependents.TryGetValue(s, out list);
            return (list != null && list.Count() > 0);
        }


        /// <summary>
        /// Reports whether dependees(s) is non-empty.
        /// </summary>
        public bool HasDependees(string s)
        {
            LinkedList<string> list = null;
            dependees.TryGetValue(s, out list);
            return (list != null && list.Count() > 0);
        }


        /// <summary>
        /// Enumerates dependents(s).
        /// </summary>
        public IEnumerable<string> GetDependents(string s)
        {
            return GetList(dependents, s);
        }

        /// <summary>
        /// Returns the List at item s in Dictionary
        /// </summary>
        /// <param name="d">Dictionary to be used (dependees or dependents)</param>
        /// <param name="s">String value to look for</param>
        /// <returns>List value at value s. If s is not found, returns an empty LinkedList</returns>
        private IEnumerable<string> GetList(Dictionary<string, LinkedList<string>> d, string s)
        {
            LinkedList<string> list;
            if (d.TryGetValue(s, out list))
                return list;
            return new LinkedList<string>();
        }

        /// <summary>
        /// Enumerates dependees(s).
        /// </summary>
        public IEnumerable<string> GetDependees(string s)
        {
            return GetList(dependees, s);
        }


        /// <summary>
        /// <para>Adds the ordered pair (s,t), if it doesn't exist</para>
        /// 
        /// <para>This should be thought of as:</para>   
        /// 
        ///   s depends on t
        ///   
        ///   dependent(s) = t
        ///   dependee(t) = s
        ///
        /// </summary>
        /// <param name="s"> s cannot be evaluated until t is</param>
        /// <param name="t"> t must be evaluated first.  S depends on T</param>
        public void AddDependency(string s, string t)
        {
            if(AddToDictionary(dependents, s, t))
                size++; //Even though we are keeping track of two dictionaries, 
                        //Only increment size ONCE per ordered pair added
            AddToDictionary(dependees, t, s);
        }

        /// <summary>
        /// Add item to list contained at dictionary value
        /// </summary>
        /// <param name="dictionary">Dictionary to add to</param>
        /// <param name="key">Dictionary key at which to add value</param>
        /// <param name="value">Value to add at dictionary key</param>
        /// <returns>True if dictionary was changed. False otherwise</returns>
        private bool AddToDictionary(Dictionary<string, LinkedList<string>> dictionary, string key, string value)
        {
            LinkedList<string> valList;
            //Check to see if dictionary has key, put output in valList
            if (dictionary.TryGetValue(key, out valList))
            {
                //As long as the list doesn't already contain the value, add it
                if (!valList.Contains(value))
                {
                    valList.AddLast(value);
                    return true; //Dictionary was changed
                }
                return false; //Dictionary was not changed
            }
            else
            {
                //Key not found. Need to create a new list and add it to dictionary
                valList = new LinkedList<string>();
                valList.AddLast(value);
                dictionary.Add(key, valList);
                return true;
            }
        }


        /// <summary>
        /// Removes the ordered pair (s,t), if it exists
        /// </summary>
        /// <param name="s"></param>
        /// <param name="t"></param>
        public void RemoveDependency(string s, string t)
        {
            if (RemoveDepItem(dependents, s, t))
            {
                //Decrement size if dictionary was changed
                size--;
                RemoveDepItem(dependees, t, s);
            }
        }

        /// <summary>
        /// Removes all dependents from any given dependee
        /// </summary>
        /// <param name="s">Dependee to be operated on</param>
        public void RemoveAllDependents(string s)
        {
            LinkedList<string> list;
            //If is is found, remove all dependencies in that list
            dependents.TryGetValue(s, out list);
            if (list != null)
            {
                //Remove all items in list from dependencies
                while (list.Count() > 0)
                {
                    string first = list.First();
                    RemoveDependency(s, first);
                }
            }
        }

        /// <summary>
        /// Removes all dependees from any given dependent
        /// </summary>
        /// <param name="s">Dependent to be operated on</param>
        private void RemoveAllDependees(string s)
        {
            LinkedList<string> list;
            //If s is found, remove all dependencies in that list
            if (dependees.TryGetValue(s, out list))
            {
                //Remove all items in list from ordered pairs
                while (list.Count() > 0)
                {
                    string item = list.First();
                    list.Remove(item);
                    RemoveDependency(item, s);
                }
            }
        }

        /// <summary>
        /// Removes all existing ordered pairs of the form (s,r).  Then, for each
        /// t in newDependents, adds the ordered pair (s,t).
        /// </summary>
        public void ReplaceDependents(string s, IEnumerable<string> newDependents)
        {
            RemoveAllDependents(s);
            foreach (string t in newDependents)
                AddDependency(s, t);
        }

        /// <summary>
        /// Removes all existing ordered pairs of the form (r,s).  Then, for each 
        /// t in newDependees, adds the ordered pair (t,s).
        /// </summary>
        public void ReplaceDependees(string s, IEnumerable<string> newDependees)
        {
            RemoveAllDependees(s);
            foreach (string t in newDependees)
                AddDependency(t, s);
        }

        /// <summary>
        /// Removes an item from the list at given dictionary key
        /// </summary>
        /// <param name="d">The dictionary to remove the value from</param>
        /// <param name="key">Key in dictionary under which the item to be removed may be found</param>
        /// <param name="removeValue">The value to be removed</param>
        /// <returns>True if dictionary was altered. False otherwise</returns>
        public bool RemoveDepItem(Dictionary<string, LinkedList<string>> d, string key, string removeValue)
        {
            LinkedList<string> valList;
            if (d.TryGetValue(key, out valList))
            {
                if (valList.Contains(removeValue))
                {
                    valList.Remove(removeValue);
                    return true;
                }
            }
            return false;
        }

        /// <summary>
        /// Used to get string of all ordered pairs in the Dependency Graph
        /// </summary>
        /// <returns>String of all ordered pairs in the Dependency Graph separated by new line</returns>
        override
        public string ToString()
        {
            string result = "";
            LinkedList<string> itemList;
            foreach(var item in dependents)
            {
                itemList = item.Value;
                foreach(string s in itemList)
                    result = result + "(" + item.Key + "," + s + ")" + "\n";
            }
            return result;
        }

        /// <summary>
        /// Used to find the value in the dependency graph that is most depended on
        /// </summary>
        /// <returns>String of value that is most depended on</returns>
        public string MostDependedOn()
        {
            LinkedList<string> list;
            string result = "";
            int max = -1;
            foreach(var item in dependees)
            {
                list = item.Value;
                if (max < 0)
                {
                    max = list.Count();
                    result = item.Key;
                }
                if (list.Count() > max)
                {
                    max = list.Count();
                    result = item.Key;
                }
            }
            return result;
        }

        /// <summary>
        /// Used to find the value in the dependency graph that has most dependents
        /// </summary>
        /// <returns>String of value that has most dependents</returns>
        public string MostDependents()
        {
            LinkedList<string> list;
            string result = "";
            int max = -1;
            foreach (var item in dependents)
            {
                list = item.Value;
                if (max < 0)
                {
                    max = list.Count();
                    result = item.Key;
                }
                if (list.Count() > max)
                {
                    max = list.Count();
                    result = item.Key;
                }
            }
            return result;
        }

        /// <summary>
        /// Reports whether dependency graph contains the ordered pair
        /// </summary>
        /// <param name="s">First value of ordered pair</param>
        /// <param name="t">Second value of ordered pair</param>
        /// <returns>True if ordered pair is found in graph, false otherwise</returns>
        public bool Contains(string s, string t)
        {
            LinkedList<string> list;
            if (dependents.TryGetValue(s, out list) && list.Contains(t))
                return true;
            return false;
        }
    }
}