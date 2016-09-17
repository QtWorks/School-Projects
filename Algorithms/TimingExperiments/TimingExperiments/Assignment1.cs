using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace TimingExperiments
{
    /// <summary>
    /// Timing experiment to test the average lookup time for a balanced binary search tree (SortedSet<int>)
    /// </summary>
    static class Assignment1
    {
        //Number of loops to run to warm up code
        static int warmup = 1000000;
        //One second duration for tests
        static int DURATION = 1000;
        /// <summary>
        /// Main function
        /// </summary>
        static void Main(string[] args)
        {
            int intervals = 11;
            //Hold the results of all tests to write to file
            string[] lines = new string[intervals];
            //Initial size is 2^10
            int size = 1024;
            //Table headers for results
            Console.WriteLine("SIZE\t" + "MILISECONDS");
            //Double the size at each interval, run the experiment and print the results
            for (int i = 0; i < intervals; i++, size *= 2)
            {
                double result = Experiment1(size);
                string s = ((i+1) + ".\t" + size + "\t" + result);
                lines[i] = s;
                Console.WriteLine(s);
            }
            //Write results to file
            System.IO.File.WriteAllLines(@"C:\Users\Tanner\Desktop\TimingOutput.txt", lines);
            //Alert the user through the console that the test has completed
            Console.WriteLine("Test Completed");
            Console.ReadLine();
        }
        /// <summary>
        /// Runs timing experiment with SortedSet of specified size
        /// </summary>
        /// <param name="size">Size of SortedSet</param>
        static double Experiment1(int size)
        {
            //Warm mup code
            for (int i = 0; i < warmup; i++) { }
            //Get new set
            SortedSet<int> set = GetNewSet(size);
            Stopwatch sw = new Stopwatch();
            double elapsed = 0;
            long repetitions = 1;
            //Double amount of repetitions until elapsed time is greater than one second
            do
            {
                repetitions *= 2;
                //Reset stopwatch back to zero
                sw.Restart();
                for (int i = 0; i < repetitions; i++)
                {
                    for (int value = 0; value < size; value++)
                    {
                        //Search set for value
                        set.Contains(value);
                    }
                }
                sw.Stop();
                elapsed = msecs(sw);
            } while (elapsed < DURATION);
            //Get average time for a repetition
            double totalAverage = elapsed / repetitions;

            //Account for overhead
            sw = new Stopwatch();
            elapsed = 0;
            repetitions = 1;
            //Double amount of repetitions until elapsed time is greater than one second
            do
            {
                repetitions *= 2;
                sw.Restart();
                for (int i = 0; i < repetitions; i++)
                {
                    for (int value = 0; value < size; value++)
                    {
                        //set.Contains(value);
                    }
                }
                sw.Stop();
                elapsed = msecs(sw);
            } while (elapsed < DURATION);
            double overheadAverage = elapsed / repetitions;
            //Console.WriteLine("Total average: " + totalAverage / size);
            //Console.WriteLine("Overhead average: " + overheadAverage / size);
            return (totalAverage - overheadAverage) / size;
        }
        /// <summary>
        /// Takes in stopwatch and returns the number of milliseconds elapsed
        /// </summary>
        /// <param name="sw">Stopwatch object</param>
        /// <returns>Number of milliseconds elapsed</returns>
        private static double msecs(Stopwatch sw)
        {
            return (((double)sw.ElapsedTicks) / Stopwatch.Frequency) * 1000;
        }

        /// <summary>
        /// Searches the Sorted set for specified value
        /// </summary>
        /// <param name="set"></param>
        /// <param name="value"></param>
        private static void SetSearch(SortedSet<int> set, int value, ref int hits)
        {
            if (set.Contains(value))
                hits++;
        }
        /// <summary>
        /// Returns a new sorted set of specified size
        /// Contains all integers from 0 to size, entered in a random order
        /// </summary>
        /// <param name="size">Size of set</param>
        /// <returns>Resulting set</returns>
        private static SortedSet<int> GetNewSet(int size)
        {
            //Create new set
            SortedSet<int> set = new SortedSet<int>();
            Random rand = new Random(size);
            //populate set with 'size' integers in random order
            while(set.Count < size)
            {
                set.Add(rand.Next(size));
            }
            return set;
        }
    }
}
