using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NameRandomizer
{
    class Program
    {
        static void Main(string[] args)
        {
            string[] names = new string[] { "Atul Sharma","Bobby King",
            "Conan Zhang", "Elijah Lloyd", "Jacob Kramer", "Jing Guo",
            "Johnny Le", "Josh Callahan", "Keeton Hodgson", "Marlon Granda-Duarte",
            "Nate Wilkinson"};
            Random r = new Random();
            int iterations = r.Next(50, 100);
            bool repeat = true;
            while (repeat)
            {
                for (int i = 0; i < iterations; i++)
                {
                    int index = r.Next(0, names.Length - 1);
                    Console.WriteLine(names[index]);
                }
                repeat = Console.ReadLine().ToLower().Equals("y");
            }
            
            Console.ReadLine();
        }
    }
}
