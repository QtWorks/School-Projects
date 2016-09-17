using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using CustomNetworking;

namespace AgCubio
{
    /// <summary>
    /// The state of our network and it's different
    /// needed applications
    /// </summary>
    public class NetworkState
    {
        /// <summary>
        /// Client socket.
        /// </summary>
        /// <returns></returns>
        public Socket workSocket = null;
        /// <summary>
        /// Size of receive buffer.
        /// </summary>
        public const int BufferSize = 1024;
        /// <summary>
        /// Receive buffer.
        /// </summary>
        public byte[] buffer = new byte[BufferSize];
        /// <summary>
        /// Received data string.
        /// </summary>
        public StringBuilder sb = new StringBuilder();
        /// <summary>
        /// Callback function
        /// </summary>
        public delegate void Callback();

        /// <summary>
        /// Function that we call at various times to access our GUI
        /// </summary>
        public Callback callback_function;
    }

    /// <summary>
    /// Our static class containing the different methods
    /// for accessing data on our socket
    /// </summary>
    public static class NetworkClient
    {
        /// <summary>
        /// The port number for the remote device.
        /// </summary>
        private const int port = 11000;
        
        /// <summary>
        /// Called by View to initialize the connection of our server
        /// </summary>
        /// <param name="state">State our current GUI is using</param>
        /// <param name="function">Our callback function</param>
        /// <param name="hostname">The host our user wants to connect to</param>
        public static void Connect_to_Server(NetworkState state, NetworkState.Callback function, string hostname)
        {
            // Connect to a remote device.
            try
            {
                // Establish the remote endpoint for the socket.
                IPHostEntry ipHostInfo = Dns.GetHostEntry(hostname);
                IPAddress ipAddress = ipHostInfo.AddressList[0];
                IPEndPoint remoteEP = new IPEndPoint(ipAddress, port);

                // Create a TCP/IP socket.
                Socket client = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

                state.workSocket = client;
                state.callback_function = function;

                // Connect to the remote endpoint.
                client.BeginConnect(remoteEP, new AsyncCallback(Connected_to_Server), state);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        /// <summary>
        /// Finish the connecting to our server
        /// and callback to the GUI to proceed in our program
        /// </summary>
        /// <param name="state_in_an_ar_object"></param>
        private static void Connected_to_Server(IAsyncResult state_in_an_ar_object)
        {
            try
            {
                // Retrieve the socket from the state object.
                NetworkState state = (NetworkState)state_in_an_ar_object.AsyncState;

                // Complete the connection.
                state.workSocket.EndConnect(state_in_an_ar_object);

                Console.WriteLine("Socket connected to {0}", state.workSocket.RemoteEndPoint.ToString());
                
                // callback to our GUI
                state.callback_function();

                // Write the response to the console.
                Console.WriteLine("Response received : {0}", state.sb.ToString());
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }     
        }

        /// <summary>
        /// Method called after we've begun receiving new data
        /// from the server
        /// </summary>
        /// <param name="state_in_an_ar_object"></param>
        private static void ReceiveCallback(IAsyncResult state_in_an_ar_object)
        {
            try
            {
                // Retrieve the state object and the client socket 
                // from the asynchronous state object.
                NetworkState state = (NetworkState)state_in_an_ar_object.AsyncState;
                Socket client = state.workSocket;

                // Read data from the remote device.
                int bytesRead = client.EndReceive(state_in_an_ar_object);

                if (bytesRead > 0)
                {
                    // Add the new data onto our stringbuilder
                    state.sb.Append(Encoding.ASCII.GetString(state.buffer, 0, bytesRead));

                    // Go back for more
                    state.callback_function();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        /// <summary>
        /// Call with the current state containing active socket
        /// in order to request more data for AgCubio
        /// </summary>
        /// <param name="state"></param>
        public static void i_want_more_data(NetworkState state)
        {
            try
            {
                // Begin receiving the data from the remote device.
                state.workSocket.BeginReceive(state.buffer, 0, NetworkState.BufferSize, 0,
                    new AsyncCallback(ReceiveCallback), state);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        /// <summary>
        /// Send data to the indicated socket
        /// </summary>
        /// <param name="socket">Socket to pass data down</param>
        /// <param name="data">Data to send</param>
        public static void Send(Socket socket, String data)
        {
            // Convert the string data to byte data using ASCII encoding.
            byte[] byteData = Encoding.ASCII.GetBytes(data);

            // Begin sending the data to the remote device.
            socket.BeginSend(byteData, 0, byteData.Length, 0,
                new AsyncCallback(SendCallback), socket);
        }

        /// <summary>
        /// Callback method called by send after sending is complete
        /// Checks that all the data was sent
        /// </summary>
        /// <param name="ar">Socket</param>
        private static void SendCallback(IAsyncResult ar)
        {
            try
            {
                // Retrieve the socket from the state object.
                Socket client = (Socket)ar.AsyncState;

                // Complete sending the data to the remote device.
                int bytesSent = client.EndSend(ar);
            }
            catch (Exception e)
            {
                // Most likely this means the server is dead
                Console.WriteLine(e.ToString());
            }
        }
    }
}
