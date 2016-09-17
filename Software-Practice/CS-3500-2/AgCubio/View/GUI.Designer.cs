namespace View
{
    /// <summary>
    /// Graphical user interface for AgCubio game
    /// </summary>
    partial class GUI
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
            this.splash_panel = new System.Windows.Forms.Panel();
            this.Bottom_Label = new System.Windows.Forms.Label();
            this.Middle_Label = new System.Windows.Forms.Label();
            this.Top_Label = new System.Windows.Forms.Label();
            this.start_button = new System.Windows.Forms.Button();
            this.server_text = new System.Windows.Forms.TextBox();
            this.name_text = new System.Windows.Forms.TextBox();
            this.server_label = new System.Windows.Forms.Label();
            this.name_label = new System.Windows.Forms.Label();
            this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
            this.backgroundWorker2 = new System.ComponentModel.BackgroundWorker();
            this.splash_panel.SuspendLayout();
            this.SuspendLayout();
            // 
            // splash_panel
            // 
            this.splash_panel.Controls.Add(this.Bottom_Label);
            this.splash_panel.Controls.Add(this.Middle_Label);
            this.splash_panel.Controls.Add(this.Top_Label);
            this.splash_panel.Controls.Add(this.start_button);
            this.splash_panel.Controls.Add(this.server_text);
            this.splash_panel.Controls.Add(this.name_text);
            this.splash_panel.Controls.Add(this.server_label);
            this.splash_panel.Controls.Add(this.name_label);
            this.splash_panel.Location = new System.Drawing.Point(13, 77);
            this.splash_panel.Name = "splash_panel";
            this.splash_panel.Size = new System.Drawing.Size(1243, 601);
            this.splash_panel.TabIndex = 0;
            // 
            // Bottom_Label
            // 
            this.Bottom_Label.AutoSize = true;
            this.Bottom_Label.Font = new System.Drawing.Font("Segoe UI Symbol", 48F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Bottom_Label.ForeColor = System.Drawing.Color.Green;
            this.Bottom_Label.Location = new System.Drawing.Point(43, 248);
            this.Bottom_Label.Name = "Bottom_Label";
            this.Bottom_Label.Size = new System.Drawing.Size(383, 106);
            this.Bottom_Label.TabIndex = 7;
            this.Bottom_Label.Text = "AgCubio!";
            // 
            // Middle_Label
            // 
            this.Middle_Label.AutoSize = true;
            this.Middle_Label.Font = new System.Drawing.Font("Segoe UI Symbol", 48F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Middle_Label.ForeColor = System.Drawing.Color.Green;
            this.Middle_Label.Location = new System.Drawing.Point(172, 123);
            this.Middle_Label.Name = "Middle_Label";
            this.Middle_Label.Size = new System.Drawing.Size(121, 106);
            this.Middle_Label.TabIndex = 6;
            this.Middle_Label.Text = "to";
            // 
            // Top_Label
            // 
            this.Top_Label.AutoSize = true;
            this.Top_Label.Font = new System.Drawing.Font("Segoe UI Symbol", 48F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Top_Label.ForeColor = System.Drawing.Color.Green;
            this.Top_Label.Location = new System.Drawing.Point(43, 9);
            this.Top_Label.Name = "Top_Label";
            this.Top_Label.Size = new System.Drawing.Size(383, 106);
            this.Top_Label.TabIndex = 5;
            this.Top_Label.Text = "Welcome";
            // 
            // start_button
            // 
            this.start_button.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.start_button.Font = new System.Drawing.Font("Segoe UI Symbol", 36F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.start_button.ForeColor = System.Drawing.Color.Blue;
            this.start_button.Location = new System.Drawing.Point(681, 261);
            this.start_button.Name = "start_button";
            this.start_button.Size = new System.Drawing.Size(532, 97);
            this.start_button.TabIndex = 4;
            this.start_button.Text = "Start";
            this.start_button.UseVisualStyleBackColor = true;
            this.start_button.Click += new System.EventHandler(this.start_button_Click);
            // 
            // server_text
            // 
            this.server_text.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.server_text.Font = new System.Drawing.Font("Segoe UI Symbol", 36F, System.Drawing.FontStyle.Bold);
            this.server_text.ForeColor = System.Drawing.Color.Red;
            this.server_text.Location = new System.Drawing.Point(885, 144);
            this.server_text.Name = "server_text";
            this.server_text.Size = new System.Drawing.Size(328, 87);
            this.server_text.TabIndex = 3;
            this.server_text.Text = "localhost";
            // 
            // name_text
            // 
            this.name_text.AcceptsReturn = true;
            this.name_text.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.name_text.Font = new System.Drawing.Font("Segoe UI Symbol", 36F, System.Drawing.FontStyle.Bold);
            this.name_text.ForeColor = System.Drawing.Color.Red;
            this.name_text.Location = new System.Drawing.Point(885, 30);
            this.name_text.Name = "name_text";
            this.name_text.Size = new System.Drawing.Size(328, 87);
            this.name_text.TabIndex = 2;
            this.name_text.KeyDown += new System.Windows.Forms.KeyEventHandler(this.name_text_KeyDown);
            // 
            // server_label
            // 
            this.server_label.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.server_label.AutoSize = true;
            this.server_label.Font = new System.Drawing.Font("Segoe UI Symbol", 36F, System.Drawing.FontStyle.Bold);
            this.server_label.Location = new System.Drawing.Point(659, 144);
            this.server_label.Name = "server_label";
            this.server_label.Size = new System.Drawing.Size(220, 81);
            this.server_label.TabIndex = 1;
            this.server_label.Text = "Server:";
            // 
            // name_label
            // 
            this.name_label.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.name_label.AutoSize = true;
            this.name_label.Font = new System.Drawing.Font("Segoe UI Symbol", 36F, System.Drawing.FontStyle.Bold);
            this.name_label.Location = new System.Drawing.Point(659, 30);
            this.name_label.Name = "name_label";
            this.name_label.Size = new System.Drawing.Size(212, 81);
            this.name_label.TabIndex = 0;
            this.name_label.Text = "Name:";
            // 
            // backgroundWorker1
            // 
            this.backgroundWorker1.DoWork += new System.ComponentModel.DoWorkEventHandler(this.backgroundWorker1_DoWork);
            // 
            // backgroundWorker2
            // 
            this.backgroundWorker2.DoWork += new System.ComponentModel.DoWorkEventHandler(this.backgroundWorker2_DoWork);
            // 
            // GUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1268, 690);
            this.Controls.Add(this.splash_panel);
            this.Name = "GUI";
            this.Text = "Form1";
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.GUI_Paint);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.GUI_KeyDown);
            this.splash_panel.ResumeLayout(false);
            this.splash_panel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion
        private System.Windows.Forms.Panel splash_panel;
        private System.Windows.Forms.Button start_button;
        private System.Windows.Forms.TextBox server_text;
        private System.Windows.Forms.TextBox name_text;
        private System.Windows.Forms.Label server_label;
        private System.Windows.Forms.Label name_label;
        private System.ComponentModel.BackgroundWorker backgroundWorker1;
        private System.Windows.Forms.Label Bottom_Label;
        private System.Windows.Forms.Label Middle_Label;
        private System.Windows.Forms.Label Top_Label;
        private System.ComponentModel.BackgroundWorker backgroundWorker2;
    }
}

