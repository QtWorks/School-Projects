using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Tip_Calculator
{
    public partial class Form1 : Form
    {
        private string service;
        double percentage;
        public Form1()
        {
            InitializeComponent();
            percentage = .1;
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            setResult();
        }

        private void tb_KeyDown(object sender, KeyEventArgs e)
        {
            if(e.KeyCode  == Keys.Enter)
            {
                setResult();
            }
        } 

        private void setResult()
        {
            double bill;
            double.TryParse(billAmount.Text, out bill);
            double tip = bill * percentage;
            tipAmount.Text = "" + tip;
            totalAmount.Text = "" + (bill + tip);
        }

        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {
            percentage = .05;
        }

        private void fairService_CheckedChanged(object sender, EventArgs e)
        {
            percentage = .15;
        }

        private void goodService_CheckedChanged(object sender, EventArgs e)
        {
            percentage = .20;

        }

        private void incredibleService_CheckedChanged(object sender, EventArgs e)
        {
            percentage = 5000;
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }
    }
}
