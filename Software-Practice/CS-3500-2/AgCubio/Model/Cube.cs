﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;
using Newtonsoft.Json;

namespace AgCubio
{
    /// <summary>
    /// This class represents each cube on the screen
    /// of our program
    /// </summary>
    [JsonObject(MemberSerialization.OptIn)]
    public class Cube
    {
        //Json Attributes

        /// <summary>
        /// Each cube has a unique id generated by the server
        /// </summary>
        [JsonProperty]
        public int uid { get; set; }

        /// <summary>
        /// X coordinate of top left of cube
        /// </summary>
        [JsonProperty]
        public double loc_x { get; set; }

        /// <summary>
        /// Y coordinate of top left of cube
        /// </summary>
        [JsonProperty]
        public double loc_y { get; set; }

        /// <summary>
        /// Color of cube
        /// </summary>
        [JsonProperty]
        public int argb_color { get; set; }

        /// <summary>
        /// If cube is a player cube, it will have a player name
        /// </summary>
        [JsonProperty]
        public string Name { get; set; }

        /// <summary>
        /// Size of square
        /// </summary>
        [JsonProperty]
        public double Mass { get; set; }

        /// <summary>
        /// True if cube is a "food" cube. False otherwise
        /// </summary>
        [JsonProperty]
        public bool food { get; set; }

        /// <summary>
        /// Used to identify all cubes that belong to the player
        /// </summary>
        [JsonProperty]
        public int team_id { get; set; }

        //Generated by JSon attributes
        
        /// <summary>
        /// True if cube is a "player" cube (aka not food). False otherwise
        /// </summary>
        public bool IsPlayer
        {
            get
            {
                return !food;
            }
            private set
            {
                food = !value;
            }
        }

        /// <summary>
        /// Returns the Color of the cube as an actual Color
        /// object. Not just a representative integer
        /// </summary>
        public Color CubeColor
        {
            get
            {
                return Color.FromArgb(argb_color);
            }
            private set
            {
                CubeColor = value;
                argb_color = CubeColor.ToArgb();
            }
        }

        /// <summary>
        /// Length of one of the sides of the cube
        /// </summary>
        public double Width
        {
            get
            {
                return Math.Sqrt(this.Mass);
            }
            set
            {
                Mass = (value * value);
            }
        }

        /// <summary>
        /// Y coordinate of top of cube
        /// </summary>
        public double Top
        {
            get
            {
                return this.loc_y - Width/2;
            }
            private set { }
        }

        /// <summary>
        /// X coordinate of left side of cube
        /// </summary>
        public double Left
        {
            get
            {
                return this.loc_x - Width/2;
            }
            private set { }
        }

        /// <summary>
        /// X coordinate of right side of cube
        /// </summary>
        public double Right
        {
            get
            {
                return this.loc_x + Width/2;
            }
            private set { }
        }

        /// <summary>
        /// Y coordinate of bottom of cube
        /// </summary>
        public double Bottom
        {
            get
            {
                return this.loc_y + Width/2;
            }
            private set { }
        }

        /// <summary>
        /// Calculates the center x and y coords of the 
        /// cube based on the coords of the top/left corner
        /// </summary>
        public Point Center
        {
            get
            {
                return new Point((int) (Left + (Width / 2)), (int)( Top + (Width / 2)));
            }
        }

        /// <summary>
        /// Returns the x and y coords of the 
        /// top right corner of the cube
        /// </summary>
        public Point TopRight
        {
            get
            {
                return new Point((int) Right, (int) Top);
            }
        }

        /// <summary>
        /// Returns the x and y coords of the 
        /// top left corner of the cube
        /// </summary>
        public Point TopLeft
        {
            get
            {
                return new Point((int)Left, (int)Top);
            }
        }

        /// <summary>
        /// Returns the x and y coords of the 
        /// bottom right corner of the cube
        /// </summary>
        public Point BottomRight
        {
            get
            {
                return new Point((int)Right, (int)Bottom);
            }
        }

        /// <summary>
        /// Returns the x and y coords of the 
        /// bottom left corner of the cube
        /// </summary>
        public Point BottomLeft
        {
            get
            {
                return new Point((int)Left, (int)Bottom);
            }
        }

        /// <summary>
        /// Returns in order TopLeft, TopRight, BottomLeft, BottomRight
        /// </summary>
        public Point[] PointArray
        {
            get
            {
                return new Point[] { TopLeft, TopRight, BottomLeft };
            }
        }

        /// <summary>
        /// Returns a generated rectangle object
        /// based on our Cube
        /// </summary>
        public Rectangle Rectangle
        {
            get
            {
                return new Rectangle((int)loc_x, (int)loc_y, (int)Width, (int)Width);
            }
            private set { }
        }

        /// <summary>
        /// Returns whether or not the input cube is a team member of
        /// the existing cube object
        /// </summary>
        /// <param name="cube"></param>
        /// <returns></returns>
        public bool IsTeamMember(Cube cube)
        {
            return cube.IsPlayer && (this.team_id == cube.team_id) && (this.uid != cube.uid);
        }
        /// <summary>
        /// Returns whether a cube is overlapping another cube
        /// </summary>
        /// <param name="cube"></param>
        /// <returns></returns>
        public bool IsOverlapping(Cube cube)
        {
            if (this.Top > cube.loc_y ||
                this.Bottom < cube.loc_y ||
                this.Left < cube.loc_x ||
                this.Right > cube.loc_x)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        /// <summary>
        /// Comparator for the mass of a cube
        /// </summary>
        /// <param name="cube"></param>
        /// <returns>-1 if "this cube" is LESS THAN passed in cube
        /// 1 if "this cube" is MORE THAN passed in cube
        /// 0 if mass is equal</returns>
        public int CompareMass(Cube cube)
        {
            if(this.Mass < cube.Mass)
            {
                return -1;
            }
            else if(this.Mass > cube.Mass)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }

        /// <summary>
        /// Default constructor for Json serialization 
        /// </summary>
        public Cube()
        {
        }
    }
}
