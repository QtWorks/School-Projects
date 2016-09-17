using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Decompose
{
    class Program
    {
        static void Main(string[] args)
        {
            HashSet<string> dictionary1 = new HashSet<string>
            { "A", "AABC", "AA", "CDA", "D", "ACBB", "AD", "AB"};
            string s1 = "AABCDACBBADDAB";
            List<string> expected1 = new List<string>
            { "AABC", "D", "ACBB", "AD", "D", "AB" };
            Console.WriteLine("Possible Decomposition:" + PossibleDecomposition(dictionary1, s1, expected1));

            HashSet<string> dictionary2 = new HashSet<string>
            { "A", "AABC", "AA", "CDA", "D", "ACBB", "AD"};
            string s2 = "AABCDACBBADDAB";
            Console.WriteLine("Impossible Decomposition:" + ImpossibleDecomposition(dictionary2, s2));

            HashSet<string> dictionary3 = new HashSet<string>
            { "dog", "cat", "mouse"};
            string s3 = "catdogmouse";
            List<string> expected3 = new List<string>
            { "cat","dog","mouse" };
            Console.WriteLine("Possible Decomposition:" + PossibleDecomposition(dictionary3, s3,expected3));

            HashSet<string> dictionary4 = new HashSet<string>
            { "mouse", "dog"};
            string s4 = "catdogmouse";
            Console.WriteLine("Impossible Decomposition:" + ImpossibleDecomposition(dictionary4, s4));

            Console.ReadLine();
        }

        private static bool ImpossibleDecomposition(HashSet<string> dictionary, string s)
        {
            List<string> result = Decompose(s, dictionary);
            if (result != null)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        private static bool PossibleDecomposition(HashSet<string> dictionary, string s, List<string> expected)
        {
            List<string> result = Decompose(s, dictionary);
            if (result != null)
            {
                string res = String.Join("", result);
                if (res.Equals(s) && ListsEqual(expected, result))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }

        private static bool ListsEqual(List<string> expected, List<string> result)
        {
            bool listEqual = true;
            if (expected.Count == result.Count)
            {
                for (int i = 0; i < expected.Count; i++)
                {
                    if (!expected[i].Equals(result[i]))
                    {
                        listEqual = false;
                    }
                }
            }
            else
            {
                listEqual = false;
            }
            return listEqual;
        }
        /// <summary>
        /// Decomposes a string into words in list order.
        /// </summary>
        /// <returns>List of words. Null if no decomposition is possible</returns>
        public static List<string> Decompose (string s, HashSet<string> dictionary)
        {
            List<string> result = new List<string>();
            for(int i = 0; i < s.Length; i++)
            {
                string word = LongestWord("", "", s.Substring(i), dictionary);
                if (word.Length > 0)
                {
                    result.Add(word);
                }
                else //Means that no decomposition is possible
                {
                    return null;
                }
                i += word.Length - 1;
            }
            return result;
        }
        /// <summary>
        /// Returns the longest possible word (in dictionary) from beginning of string
        /// Returns empty string if none is found
        /// </summary>
        public static string LongestWord(string word, string current, string remaining, HashSet<string> dictionary)
        {
            if(remaining.Length == 0)
            {
                if (dictionary.Contains(current))
                    return current;
                else
                    return word;
            }
            if (dictionary.Contains(current))
            {
                return LongestWord(current, current + remaining[0], remaining.Substring(1), dictionary);
            }
            else
            {
                return LongestWord(word, current + remaining[0], remaining.Substring(1), dictionary);
            }
        }
    }
}
