// Skeleton implementation written by Joe Zachary for CS 3500, September 2013.
// Version 1.1 (Fixed error in comment for RemoveDependency.)

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

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
    ///
    /// For example, suppose DG = {("a", "b"), ("a", "c"), ("b", "d"), ("d", "d")}
    ///     dependents("a") = {"b", "c"}
    ///     dependents("b") = {"d"}
    ///     dependents("c") = {}
    ///     dependents("d") = {"d"}
    ///     dependees("a") = {}
    ///     dependees("b") = {"a"}
    ///     dependees("c") = {"a"}
    ///     dependees("d") = {"b", "d"}
    /// </summary>
    public class DependencyGraph
    {
        private Dictionary<string, List<string>> dependents;
        private Dictionary<string, List<string>> dependees;
        private int Count;

        /// <summary>
        /// Creates an empty DependencyGraph.
        /// </summary>
        public DependencyGraph()
        {
            dependees = new Dictionary<string, List<string>>();
            dependents = new Dictionary<string, List<string>>();
            Count = 0;
        }


        /// <summary>
        /// The number of ordered pairs in the DependencyGraph.
        /// </summary>
        public int Size
        {
            get { return Count; }
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
                List<string> value;
                if (dependees.TryGetValue(s, out value))
                {
                    return value.Count();
                }
                else
                {
                    return 0;
                }
            }
        }


        /// <summary>
        /// Reports whether dependents(s) is non-empty.
        /// </summary>
        public bool HasDependents(string s)
        {
            return TestContains(s, dependents);
        }


        /// <summary>
        /// Reports whether dependees(s) is non-empty.
        /// </summary>
        public bool HasDependees(string s)
        {
            return TestContains(s, dependees);
        }


        /// <summary>
        /// Enumerates dependents(s).
        /// </summary>
        public IEnumerable<string> GetDependents(string s)
        {
            return GetIEnumerable(s, dependents);
        }

        /// <summary>
        /// Enumerates dependees(s).
        /// </summary>
        public IEnumerable<string> GetDependees(string s)
        {
            return GetIEnumerable(s, dependees);
        }


        /// <summary>
        /// <para>Adds the ordered pair (s,t), if it doesn't exist</para>
        /// 
        /// <para>This should be thought of as:</para>   
        /// 
        ///   s depends on t
        ///
        /// </summary>
        /// <param name="s"> s cannot be evaluated until t is</param>
        /// <param name="t"> t must be evaluated first.  S depends on T</param>
        public void AddDependency(string s, string t)
        {
            bool success = false;
            if (dependents.ContainsKey(s))
            {
                if (!dependents[s].Contains(t))
                {
                    dependents[s].Add(t);
                    success = true;
                }
            }
            else
            {
                dependents[s] = new List<string> { t };
                success = true;
            }
            if (dependees.ContainsKey(t))
            {
                if (!dependees[t].Contains(s))
                {
                    dependees[t].Add(s);
                }
            }
            else
            {
                dependees[t] = new List<string> { s };
            }

            if (success)
                Count++;
        }


        /// <summary>
        /// Removes the ordered pair (s,t), if it exists
        /// </summary>
        /// <param name="s"></param>
        /// <param name="t"></param>
        public void RemoveDependency(string s, string t)
        {
            if (dependents.ContainsKey(s))
            {
                if (dependents[s].Contains(t))
                {
                    dependents[s].Remove(t);
                    Count--;
                }
            }
            if (dependees.ContainsKey(t))
            {
                if (dependees[t].Contains(s))
                {
                    dependees[t].Remove(s);
                }
            }
        }


        /// <summary>
        /// Removes all existing ordered pairs of the form (s,r).  Then, for each
        /// t in newDependents, adds the ordered pair (s,t).
        /// </summary>
        public void ReplaceDependents(string s, IEnumerable<string> newDependents)
        {
            ReplaceDictionaries(s, newDependents, dependents, dependees);
        }


        /// <summary>
        /// Removes all existing ordered pairs of the form (r,s).  Then, for each 
        /// t in newDependees, adds the ordered pair (t,s).
        /// </summary>
        public void ReplaceDependees(string s, IEnumerable<string> newDependees)
        {
            ReplaceDictionaries(s, newDependees, dependees, dependents);
        }

        /// <summary>
        /// Takes in a string and it's replacements and replaces it's dependendents/dependees 
        /// from the left dictionary in the form (s,t) and from the right dictionary in the form (t,s)
        /// </summary>
        /// <param name="s">String key to remove current dependents/dependees</param>
        /// <param name="replacements">List of replacements</param>
        /// <param name="left">To replace in the form (s,t)</param>
        /// <param name="right">To replace in the form (t,s)</param>
        private void ReplaceDictionaries(string s, IEnumerable<string> replacements, Dictionary<string, List<string>> left, Dictionary<string, List<string>> right)
        {
            List<string> newList = new List<string>();
            foreach (string i in replacements)
                newList.Add(i);

            if (left.ContainsKey(s))
            {
                left[s] = newList;
            }
            else
            {
                left.Add(s, newList);
            }

            foreach (KeyValuePair<string, List<string>> entry in right)
            {
                if (entry.Value.Contains(s))
                {
                    entry.Value.Remove(s);
                }
            }

            foreach (string newDep in replacements)
            {
                if (right.ContainsKey(newDep))
                {
                    if (!right[newDep].Contains(s))
                    {
                        right[newDep].Add(s);
                    }
                }
                else
                {
                    right[newDep] = new List<string> { s };
                }
            }
        }

        /// <summary>
        /// Tests whether the key s in the Dictionary testDic has any value in its resulting List/<string/>
        /// </summary>
        /// <param name="s">Test Key</param>
        /// <param name="testDic">List to check</param>
        /// <returns></returns>
        private bool TestContains(string s, Dictionary<string, List<string>> testDic)
        {
            if (testDic.ContainsKey(s) && testDic[s].Count > 0)
                return true;
            else
                return false;
        }

        /// <summary>
        /// Takes in a string and dictionary and returns an List of the resulting value to the s key
        /// </summary>
        /// <param name="s">Key to check</param>
        /// <param name="testDic">Dictionary to pass key</param>
        /// <returns></returns>
        private List<string> GetIEnumerable(string s, Dictionary<string, List<string>> testDic)
        {
            List<string> value = new List<string>();
            if (testDic.TryGetValue(s, out value))
                return value;

            return new List<string>();
        }
    }
}