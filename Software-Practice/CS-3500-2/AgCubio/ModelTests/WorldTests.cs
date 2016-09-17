using Microsoft.VisualStudio.TestTools.UnitTesting;
using AgCubio;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using Newtonsoft.Json;

namespace AgCubio.Tests
{
    [TestClass()]
    public class WorldTests
    {
        public const string test_data_path = @"..\..\..\Resources\test_data.txt";
        public const string removal_data_path = @"..\..\..\Resources\removal_data.txt";
        public const string player_data = "{ \"loc_x\":926.0, \"loc_y\":682.0, \"argb_color\":-65536, \"uid\":5571, \"food\":false, \"Name\":\"3500 is love\", \"Mass\":1000.0 }\n";

        public string removal_data = File.ReadAllText(removal_data_path);

        public string test_data = File.ReadAllText(test_data_path);

        [TestMethod()]
        public void PopulateWorldTest()
        {
            World world = new World();
            world.Populate(player_data);
            //Populate multiple times and make sure the size isn't changing because it's the same data
            world.Populate(test_data);
            world.Populate(test_data);
            world.Populate(test_data);
            world.Populate(test_data);
            Assert.IsTrue(world.CubesMap.Count == 48);
        }

        [TestMethod()]
        public void AddInvalidString()
        {
            World world = new World();
            world.Populate("garbage");
        }

        [TestMethod()]
        public void PlayerAttributes()
        {
            string test_data = File.ReadAllText(test_data_path);
            World world = new World();
            world.Populate(player_data);
            world.Populate(test_data);
            Assert.IsTrue(world.Player.loc_x == 926.0);
            Assert.IsTrue(world.Player.loc_y == 682.0);
            Assert.IsTrue(world.Player.argb_color == -65536);
            Assert.IsTrue(world.Player.uid == 5571);
            Assert.IsTrue(world.Player.food == false);
            Assert.IsTrue(world.Player.Name.Equals("3500 is love"));
            Assert.IsTrue(world.Player.Mass == 1000.0);
            Assert.IsTrue(world.CubesMap.Count == 48);
        }

        [TestMethod()]
        public void NullData()
        {
            World world = new World();
            world.Populate(null);
            Assert.IsTrue(world.CubesMap.Count() == 0);            
        }

        [TestMethod()]
        public void RemovalData()
        {
            World world = new World();
            world.Populate(player_data);
            world.Populate(removal_data);
            Assert.IsTrue(world.CubesMap.Count() == 43);
        }
    }
}