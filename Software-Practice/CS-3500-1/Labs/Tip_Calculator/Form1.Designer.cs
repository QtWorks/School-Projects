namespace Tip_Calculator
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.billAmount = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.tipAmount = new System.Windows.Forms.TextBox();
            this.button1 = new System.Windows.Forms.Button();
            this.poorService = new System.Windows.Forms.RadioButton();
            this.fairService = new System.Windows.Forms.RadioButton();
            this.goodService = new System.Windows.Forms.RadioButton();
            this.incredibleService = new System.Windows.Forms.RadioButton();
            this.totalAmount = new System.Windows.Forms.TextBox();
            this.tipAmountLabel = new System.Windows.Forms.Label();
            this.totalLabel = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // billAmount
            // 
            this.billAmount.Location = new System.Drawing.Point(195, 33);
            this.billAmount.Name = "billAmount";
            this.billAmount.Size = new System.Drawing.Size(293, 22);
            this.billAmount.TabIndex = 0;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(31, 33);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(100, 17);
            this.label1.TabIndex = 1;
            this.label1.Text = "Enter Total Bill";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // tipAmount
            // 
            this.tipAmount.Location = new System.Drawing.Point(195, 63);
            this.tipAmount.Name = "tipAmount";
            this.tipAmount.Size = new System.Drawing.Size(293, 22);
            this.tipAmount.TabIndex = 2;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(195, 133);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(128, 34);
            this.button1.TabIndex = 3;
            this.button1.Text = "Compute Tip";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // poorService
            // 
            this.poorService.AutoSize = true;
            this.poorService.Location = new System.Drawing.Point(34, 220);
            this.poorService.Name = "poorService";
            this.poorService.Size = new System.Drawing.Size(59, 21);
            this.poorService.TabIndex = 4;
            this.poorService.TabStop = true;
            this.poorService.Text = "Poor";
            this.poorService.UseVisualStyleBackColor = true;
            this.poorService.CheckedChanged += new System.EventHandler(this.radioButton1_CheckedChanged);
            // 
            // fairService
            // 
            this.fairService.AutoSize = true;
            this.fairService.Location = new System.Drawing.Point(168, 220);
            this.fairService.Name = "fairService";
            this.fairService.Size = new System.Drawing.Size(53, 21);
            this.fairService.TabIndex = 5;
            this.fairService.TabStop = true;
            this.fairService.Text = "Fair";
            this.fairService.UseVisualStyleBackColor = true;
            this.fairService.CheckedChanged += new System.EventHandler(this.fairService_CheckedChanged);
            // 
            // goodService
            // 
            this.goodService.AutoSize = true;
            this.goodService.Location = new System.Drawing.Point(277, 220);
            this.goodService.Name = "goodService";
            this.goodService.Size = new System.Drawing.Size(64, 21);
            this.goodService.TabIndex = 6;
            this.goodService.TabStop = true;
            this.goodService.Text = "Good";
            this.goodService.UseVisualStyleBackColor = true;
            this.goodService.CheckedChanged += new System.EventHandler(this.goodService_CheckedChanged);
            // 
            // incredibleService
            // 
            this.incredibleService.AutoSize = true;
            this.incredibleService.Location = new System.Drawing.Point(398, 220);
            this.incredibleService.Name = "incredibleService";
            this.incredibleService.Size = new System.Drawing.Size(90, 21);
            this.incredibleService.TabIndex = 7;
            this.incredibleService.TabStop = true;
            this.incredibleService.Text = "Incredible";
            this.incredibleService.UseVisualStyleBackColor = true;
            this.incredibleService.CheckedChanged += new System.EventHandler(this.incredibleService_CheckedChanged);
            // 
            // totalAmount
            // 
            this.totalAmount.Location = new System.Drawing.Point(195, 92);
            this.totalAmount.Name = "totalAmount";
            this.totalAmount.Size = new System.Drawing.Size(293, 22);
            this.totalAmount.TabIndex = 8;
            this.totalAmount.TextChanged += new System.EventHandler(this.textBox1_TextChanged);
            // 
            // tipAmountLabel
            // 
            this.tipAmountLabel.AutoSize = true;
            this.tipAmountLabel.Location = new System.Drawing.Point(34, 67);
            this.tipAmountLabel.Name = "tipAmountLabel";
            this.tipAmountLabel.Size = new System.Drawing.Size(80, 17);
            this.tipAmountLabel.TabIndex = 9;
            this.tipAmountLabel.Text = "Tip Amount";
            // 
            // totalLabel
            // 
            this.totalLabel.AutoSize = true;
            this.totalLabel.Location = new System.Drawing.Point(37, 96);
            this.totalLabel.Name = "totalLabel";
            this.totalLabel.Size = new System.Drawing.Size(92, 17);
            this.totalLabel.TabIndex = 10;
            this.totalLabel.Text = "Total Amount";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(520, 253);
            this.Controls.Add(this.totalLabel);
            this.Controls.Add(this.tipAmountLabel);
            this.Controls.Add(this.totalAmount);
            this.Controls.Add(this.incredibleService);
            this.Controls.Add(this.goodService);
            this.Controls.Add(this.fairService);
            this.Controls.Add(this.poorService);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.tipAmount);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.billAmount);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox billAmount;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox tipAmount;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.RadioButton poorService;
        private System.Windows.Forms.RadioButton fairService;
        private System.Windows.Forms.RadioButton goodService;
        private System.Windows.Forms.RadioButton incredibleService;
        private System.Windows.Forms.TextBox totalAmount;
        private System.Windows.Forms.Label tipAmountLabel;
        private System.Windows.Forms.Label totalLabel;
    }
}

