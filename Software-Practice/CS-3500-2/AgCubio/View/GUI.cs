using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using AgCubio;
using Newtonsoft.Json;
using System.Drawing.Drawing2D;
using System.Net.Sockets;

namespace View
{
    public partial class GUI : Form
    {
        /// <summary>
        /// Brush to paint with
        /// </summary>
        private System.Drawing.SolidBrush myBrush;
        /// <summary>
        /// Logic for all cubes in world
        /// </summary>
        World world;
        /// <summary>
        /// Scale at which to paint cubes
        /// </summary>
        private int scale = 3; //todo 3 is the only one that looks good...
        /// <summary>
        /// Name of player
        /// </summary>
        string player_name;
        /// <summary>
        /// Container for network information
        /// </summary>
        private NetworkState state;
        /// <summary>
        /// Reports whether or not currently connected
        /// </summary>
        private bool connected = false;
        /// <summary>
        /// Reports whether or not player is dead
        /// </summary>
        private bool dead = false;
        /// <summary>
        /// Start time of the user's game
        /// </summary>
        private DateTime start;
        
        /// <summary>
        /// Fonts to use when painting
        /// </summary>
        Font dead_font, stats_font, player_font;

        //delegate void UpdateLabelsCallback(Label label, string text);

        
        /// <summary>
        /// Graphical User Interface for AgCubio game
        /// </summary>
        public GUI()
        {
            world = new World();
            InitializeComponent();
            DoubleBuffered = true;
            state = new NetworkState();
            dead_font = new Font("Times New Roman", 80.0f);
            stats_font = new Font("Times New Roman", 20.0f);
            player_font = new Font("Times New Roman", 20.0f);
        }

        /// <summary>
        /// Gracefully close socket if connected
        /// </summary>
        /// <param name="e"></param>
        protected override void OnClosing(System.ComponentModel.CancelEventArgs e)
        {
            if(state != null && state.workSocket != null)
                state.workSocket.Close();
        }

        /// <summary>
        /// Paint method. Constantly repaints cubes from world and messages to user
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void GUI_Paint(object sender, PaintEventArgs e)
        {
            if (dead)
            {
                //Stats to print: Final Mass, Time Alive, Largest Mass achieved
                myBrush = new System.Drawing.SolidBrush(Color.Red);
                e.Graphics.DrawString("You were eaten", dead_font, myBrush, 100, 100);
                myBrush = new System.Drawing.SolidBrush(Color.Black);
                e.Graphics.DrawString("Final Mass: " + (int)world.Player.Mass, stats_font, myBrush, 300, 250);
                TimeSpan length = DateTime.Now.Subtract(start);
                e.Graphics.DrawString("Time Alive: " + length.Hours + "h " + length.Minutes + "m " + length.Seconds + "s", stats_font, myBrush, 300, 300);
                e.Graphics.DrawString("Largest Mass Achieved: " + world.MaxMass, stats_font, myBrush, 300, 350);

                backgroundWorker2.RunWorkerAsync();
                return;
            }
            lock (world)
            {
                //Paint each cube in world
                foreach (Cube cube in world.CubesMap.Values)
                {
                    myBrush = new System.Drawing.SolidBrush(cube.CubeColor);
                    Rectangle scaled = ScaledRectangle(cube);
                    e.Graphics.FillRectangle(myBrush, scaled);
                    if (cube.IsPlayer)
                    {
                        //Painting name onto player cube
                        myBrush = new System.Drawing.SolidBrush(Color.White);
                        e.Graphics.DrawString(cube.Name, player_font, myBrush,
                            scaled, StringFormat.GenericDefault);
                    }
                }
            }
            //Paint stats onto screen
            if (connected && world != null && world.Player != null)
            {
                myBrush = new System.Drawing.SolidBrush(Color.Black);
                e.Graphics.DrawString("Food: " + world.FoodCount, stats_font, myBrush,
                    this.Width - 200, 50);
                e.Graphics.DrawString("Mass: " + (int)world.Player.Mass, stats_font, myBrush,
                    this.Width - 200, 100);
            }
            this.Invalidate();
        }

        /// <summary>
        /// Returns rectangle scaled to world to be used in painting
        /// </summary>
        /// <param name="cube">Cube from world</param>
        /// <returns>Rectangle of scaled cube</returns>
        private Rectangle ScaledRectangle(Cube cube)
        {
            lock (world)
            {
                //Get the offset from the world
                int offset_x = GetOffSet().X;
                int offset_y = GetOffSet().Y;
                //Find the new width
                int new_width = (int)cube.Width * scale;
                //Add it all together
                int x = ((int)cube.loc_x + offset_x) - (new_width / 2);
                int y = ((int)cube.loc_y + offset_y) - (new_width / 2);
                //Create the new rectangle
                return new Rectangle(x , y, new_width, new_width);
            }
        }

        /// <summary>
        /// Gets offset point for whole world
        /// </summary>
        /// <returns>Point(x,y) of coordinates</returns>
        private Point GetOffSet()
        {
            int y = ((this.Height / 2) - world.Player.TopLeft.Y);
            int x = ((this.Width / 2) - world.Player.TopLeft.X);
            return new Point(x, y);
        }

        /// <summary>
        /// Handles start button click
        /// </summary>
        private void start_button_Click(object sender, EventArgs e)
        {
            Startup();
        }

        /// <summary>
        /// Performs the same as start button if user presses enter
        /// </summary>
        private void name_text_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                Startup();
            }
        }

        /// <summary>
        /// Saves player's name and Connects to server
        /// </summary>
        private void Startup()
        {
            if(player_name == null)
                player_name = name_text.Text;
            string serverText = "";
            if (server_text.Text.Equals(""))
                serverText = "localhost";
            else
                serverText = server_text.Text;
            if(!splash_panel.IsDisposed)
                splash_panel.Dispose();
            start = DateTime.Now;
            lock (state)
            {
                NetworkClient.Connect_to_Server(state, SendPlayerName, serverText);
            }
        }

        /// <summary>
        /// Callback function to use when connecting to server
        /// </summary>
        private void SendPlayerName()
        {
            lock (state)
            {
                //todo can pass in different method because we are passing in player name from beginning

                // we're now connected
                connected = true;

                // use NetworkClient send function
                NetworkClient.Send(state.workSocket, player_name + "\n");

                // update the delegate in our state
                state.callback_function = GetUpdates;

                // get more data
                try
                {
                    NetworkClient.i_want_more_data(state);
                }
                catch (Exception)
                {
                    connected = false;
                    MessageBox.Show("Server died. Start server again to continue playing");
                }
            }
        }

        /// <summary>
        /// Receives all updates from server and performs necessary actions
        /// </summary>
        private void GetUpdates()
        {
            lock(state)
            {
                //Get complete data from update
                string temp = state.sb.ToString();
                int indexOfLastNewline = temp.LastIndexOf('\n');
                string completeData = temp.Substring(0, indexOfLastNewline + 1);
                state.sb = new StringBuilder();
                state.sb.Append(temp.Substring(indexOfLastNewline + 1));

                //Lock on world to populate with all cubes that we receive
                lock (world)
                {
                    try
                    {
                        world.Populate(completeData);
                    }
                    //If player is eaten, we set dead to true and the paint function displays the message
                    catch (PlayerEatenException)
                    {
                        dead = true;
                    }
                }
                //Always be sending move information back when we receive info
                if (connected)
                {
                    SendCommand("move");
                }
                try
                {
                    NetworkClient.i_want_more_data(state);
                }
                catch (Exception)
                {//Send message to user that the server is dead
                    connected = false;
                    MessageBox.Show("Server died. Start server again to continue playing");
                }
            }
        }

        /// <summary>
        /// Takes in "send" or "split" and sends command to network
        /// </summary>
        /// <param name="command"></param>
        private void SendCommand(string command)
        {
            try
            {
                NetworkClient.Send(state.workSocket, "(" + command + ", " + GetWorldPosition().X + ", " + GetWorldPosition().Y + ")\n");
            }
            catch (Exception)
            {
                MessageBox.Show("Invalid command: " + command);
            }
        }

        /// <summary>
        /// Performs any operations dealing with key down on a background worker
        /// </summary>
        private void GUI_KeyDown(object sender, KeyEventArgs e)
        {
            backgroundWorker1.RunWorkerAsync(e);
        }

        /// <summary>
        /// Performs split if space bar is pressed
        /// </summary>
        private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
        {
            KeyEventArgs args = (KeyEventArgs)e.Argument;
            if(args.KeyCode == Keys.Space)
            {
                if (connected)
                {
                    SendCommand("split");
                }
            }
        }

        private void backgroundWorker2_DoWork(object sender, DoWorkEventArgs e)
        {          
            DialogResult result = MessageBox.Show("Would you like to play again?", "Restart", MessageBoxButtons.YesNo);
            if (result == DialogResult.Yes)
            {
                new GUI();
            }
        }

        /// <summary>
        /// Convert current mouse position to position in world
        /// </summary>
        /// <returns></returns>
        private Point GetWorldPosition()
        {
            //Get location relative to screen
            int x = MousePosition.X - (this.Location.X + (this.Width / 2));
            int y = MousePosition.Y - (this.Location.Y + (this.Height / 2));

            //Add center of player
            x += (int)world.Player.Center.X;
            y += (int)world.Player.Center.Y;

            //Subtract player width / scale
            x -= (int)world.Player.Width / scale;
            y -= (int)world.Player.Width / scale;

            return new Point(x, y);
        }
        
        /// <summary>
        /// Allows us to paint labels on splash screen
        /// </summary>
        protected override CreateParams CreateParams
        {
            get
            {
                CreateParams cp = base.CreateParams;
                cp.ExStyle |= 0x02000000;  // Turn on WS_EX_COMPOSITED
                return cp;
            }
        }
    }
}
