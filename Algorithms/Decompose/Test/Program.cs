using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Test
{
    class Program
    {
        static void Main(string[] args)
        {
        }
        /// <summary>
        /// Decomposes a string into words in list order.
        /// </summary>
        /// <returns>List of words. Null if no decomposition is possible</returns>
        public static List<string> Decompose(string s, HashSet<string> dictionary)
        {
            List<string> result = new List<string>();
            for (int i = 0; i < s.Length; i++)
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
            if (remaining.Length == 0)
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
