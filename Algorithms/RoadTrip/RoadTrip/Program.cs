using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Diagnostics;
using System.Threading;

namespace RoadTrip
{
    class Program
    {
        static int milesPerDay = 400;
        static int startSize = 10;
        static int endSize = 25;
        static int interval = 1;
        private static Random rand;

        public static void Main()
        {
            //RunTests();
            RunDynamicTests();
            Console.ReadLine();
        }

        public static void Time(Func<int, int> Size, Func<int[], int, int> RodMethod)
        {
            rand = new Random(0);
            Stopwatch timer = new Stopwatch();
            for (int i = 0; i < 20; i++)
            {
                int size = Size(i);
                int[] values = RandomArray(size + 1, 10 * size);
                Array.Sort(values);
                values[0] = 0;
                long previousTicks = timer.ElapsedTicks;
                timer.Reset();
                timer.Start();
                int profit = RodMethod(values, values.Length - 1);
                timer.Stop();
                Console.WriteLine(String.Format("Size = {0}, profit = {1}, {2}",
                    size,
                    profit,
                    TimeReport(timer, previousTicks)));
            }
        }

        public static string TimeReport(Stopwatch timer, long previousTicks)
        {
            double elapsedTime = (1.0 * timer.ElapsedTicks) / Stopwatch.Frequency;
            double previousTime = (1.0 * previousTicks) / Stopwatch.Frequency;
            string ratio = (previousTime == 0) ? "" : (elapsedTime / previousTime).ToString("F2");
            return String.Format("elapsed = {0} sec, ratio = {1}", elapsedTime.ToString("E2"), ratio);
        }

        private static int[] RandomArray(int size, int maxValue)
        {
            int[] result = new int[size];
            for (int i = 0; i < size; i++)
            {
                result[i] = rand.Next(maxValue) + 1;
            }
            return result;
        }

        public static void RunTests()
        {
            Console.WriteLine("Normal");
            for (int i = startSize; i <= endSize; i += interval)
            {
                TimingTest(i, false);
            }
        }

        public static void RunDynamicTests()
        {
            Console.WriteLine("Dynamic");
            for (int i = 2000; i <= 4000; i += 100)
            {
                TimingTest(i, true);
            }
        }

        public static void BasicTest(bool dynamic)
        {
            int[] hotel = new int[6];
            hotel[0] = 0;
            hotel[1] = 380;
            hotel[2] = 400;
            hotel[3] = 410; 
            hotel[4] = 790;
            hotel[5] = 805;

            int expected = 25;
            int penalty;
            if (dynamic)
            {
                Console.WriteLine("Dynamic");
                Dictionary<int, int> cache = new Dictionary<int, int>();
                penalty = DynamicMinimumPenalty(hotel, 0, cache);
            }
            else
            {
                Console.WriteLine("Normal");
                penalty = MinimumPenalty(hotel, 0);
            }
            if (penalty == expected)
            {
                Console.WriteLine("Got it!");
                Console.WriteLine(penalty);
            }
            else
            {
                Console.WriteLine("Booo");
                Console.WriteLine("Expected: " + expected);
                Console.WriteLine("Actual: " + penalty);
            }

        }

        public static void TimingTest(int size, bool dynamic)
        {
            int[] hotel = new int[size];
            for(int i = 0; i < size; i++)
            {
                hotel[i] = i * milesPerDay;
            }
            Stopwatch timer = new Stopwatch();
            Console.WriteLine("Size: " + size);
            int penalty = -1;
            if (dynamic)
            {
                Dictionary<int, int> cache = new Dictionary<int, int>();
                timer.Start();
                penalty = DynamicMinimumPenalty(hotel, 0, cache);
                timer.Start();
            }
            else
            {
                timer.Start();
                penalty = MinimumPenalty(hotel);
                timer.Stop();
            }
            Console.WriteLine("Elapsed: " + timer.Elapsed);
        }

        // Returns the minimum penalty that it is possible to incur when driving from the hotel 
        // that is hotel[i] miles from New York to the final hotel, which is hotel[hotel.Length-1] 
        // miles from New York.  Assume that 0 <= i < hotel.Length and that
        // 0 = hotel[0] < hotel[1] < hotel[2] < ... < hotel[hotel.Length-1]
        public static int MinimumPenalty(int[] hotel, int i)
        {
            if (i >= hotel.Length - 1 || i < 0)
                return 0;
            int[] mins = new int[hotel.Length - 1 - i];
            for(int j = hotel.Length - 1; j > i; j--)
            {
                int distance = hotel[j] - hotel[i];
                mins[j - (i + 1)] = (int)Math.Pow((milesPerDay - distance), 2) +
                    MinimumPenalty(hotel, j);
            }
            return mins.Min();            
        }

        // Returns the minimum penalty that it is possible to incur when driving from the first hotel 
        // (which is in New York) to the final hotel (which is in San Francisco).  The mileage from New York
        // to each of the hotels on the route is given by the hotel array, where 
        // 0 = hotel[0] < hotel[1] < hotel[2] < ... < hotel[hotel.Length-1]
        public static int MinimumPenalty(int[] hotel)
        {
            return MinimumPenalty(hotel, 0);
        }

        // Returns the minimum penalty that it is possible to incur when driving from the hotel 
        // that is hotel[i] miles from New York to the final hotel, which is hotel[hotel.Length-1] 
        // miles from New York.  Assume that 0 <= i < hotel.Length and that
        // 0 = hotel[0] < hotel[1] < hotel[2] < ... < hotel[hotel.Length-1].  
        public static int DynamicMinimumPenalty(int[] hotel, int i, Dictionary<int, int> cache)
        {
            if (i >= hotel.Length - 1 || i < 0)
                return 0;
            int[] mins = new int[hotel.Length - 1 - i];
            for (int j = hotel.Length - 1; j > i; j--)
            {
                int distance = hotel[j] - hotel[i];
                int val = (int)Math.Pow((milesPerDay - distance), 2);
                if (cache.ContainsKey(j))
                {
                    val += cache[j];
                }
                else
                {
                    val += DynamicMinimumPenalty(hotel, j, cache);
                }
                mins[j - (i + 1)] = val;
            }
            cache[i] = mins.Min();
            return mins.Min();
        }

        // Returns the minimum penalty that it is possible to incur when driving from the first hotel 
        // (which is in New York) to the final hotel (which is in San Francisco).  The mileage from New York
        // to each of the hotels on the route is given by the hotel array, where 
        // 0 = hotel[0] < hotel[1] < hotel[2] < ... < hotel[hotel.Length-1]
        public static int DynamicMinimumPenalty(int[] hotel)
        {
            Dictionary<int, int> cache = new Dictionary<int, int>();
            return DynamicMinimumPenalty(hotel, 0, cache);
        }
    }
}
