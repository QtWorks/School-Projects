using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgCubio
{

    /// <summary>
    /// The World is responsible for storing the current state of the game, 
    /// including the status and location of every object in the game.
    /// The world represents the "state" of the simulation. This class is 
    /// responsible for tracking at least the following data: the world Width 
    /// and Height (please use read only 'constants'), all the cubes in the 
    /// game. You may of course store additional information.
    /// </summary>
    public class World
    {

        /// <summary>
        /// boolean that signifies whether we're generated our very first player or not
        /// </summary>
        private bool first_player;

        /// <summary>
        /// Cube representing the current player of the client
        /// </summary>
        public Cube Player { get; private set; }

        /// <summary>
        /// All the current cubes in our player's team
        /// </summary>
        private Dictionary<int, Cube> Team;

        /// <summary>
        /// The current amount of food in the world
        /// </summary>
        public int FoodCount { get; set; }
        /// <summary>
        /// Maximum mass achieved
        /// </summary>
        public double MaxMass { get; set; }

        /// <summary>
        /// Dictionary of all cubes in world
        /// </summary>
        Dictionary<int, Cube> Cubes;

        /// <summary>
        /// Initializes necessary properties and creates a whole new world
        /// </summary>
        public World()
        {
            Cubes = new Dictionary<int, Cube>();
            Team = new Dictionary<int, Cube>();
            first_player = true;
        }

        /// <summary>
        /// Adds cubes if mass is greater than 0, removes them otherwise. Adds team members to team if necessary
        /// </summary>
        /// <param name="cube"></param>
        public void ProcessCube(Cube cube)
        {
            if (cube.Mass == 0)
                Cubes.Remove(cube.uid);
            else
                Cubes[cube.uid] = cube;
            
            // is it a player cube or a food cube?
            if (cube.IsPlayer)
            {
                // it's a member of our team
                if (cube.team_id == Player.uid || cube.uid == Player.uid)
                {
                    if (cube.Mass > 0)
                    {
                        if (cube.Mass > MaxMass)
                            MaxMass = cube.Mass;
                        if (!Team.ContainsKey(cube.uid))
                        {
                            Team.Add(cube.uid, cube);
                        }
                        if (cube.uid == Player.uid)
                        {
                            Player = cube;
                        }
                    }
                    else
                    {
                        if (Team.ContainsKey(cube.uid))
                        {
                            Team.Remove(cube.uid);
                        }
                        if (Team.Count == 0)
                        {
                            throw new PlayerEatenException();
                        }
                        else
                        {
                            // Our original player was eaten so we need to replace it
                            if (cube.uid == Player.uid)
                            {
                                foreach (Cube s in Team.Values)
                                {
                                    Player = s;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            // it's a food cube
            else
            {
                if (cube.Mass > 0)
                {
                    FoodCount++;
                }
                else
                {
                    FoodCount--;
                }
            }
        }

        /// <summary>
        /// Dictionary containing all cubes in world
        /// </summary>
        public Dictionary<int, Cube> CubesMap
        {
            get
            {
                return Cubes;
            }
            private set { }
        }

        /// <summary>
        /// Takes in string of Json, parses it and adds cubes to world
        /// </summary>
        /// <param name="data"></param>
        public void Populate(string data)
        {
            if (data == null)
                return;
            string[] lines = data.Split('\n');
            try
            {
                if (first_player)
                {
                    if (lines == null || lines.Length < 1)
                        return;
                    Cube cube = JsonConvert.DeserializeObject<Cube>(lines[0]);
                    Player = cube;
                    MaxMass = Player.Mass;
                    Team.Add(cube.uid, cube);
                    first_player = false;
                }
                else
                {
                    foreach (string s in lines)
                    {
                        if (s == null || !s.StartsWith("{"))
                            continue;
                        Cube cube = JsonConvert.DeserializeObject<Cube>(s);
                        ProcessCube(cube);
                    }
                }
            }
            catch (JsonReaderException)
            {
                Console.WriteLine("Json error found in string: " + data);
            }
            
        }
    }
    /// <summary>
    /// Exception to be thrown when player is eaten
    /// </summary>
    public class PlayerEatenException : Exception {}
}
