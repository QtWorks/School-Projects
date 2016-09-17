/* A5 - A Simon Game - CS3505
 * Elijah Grubb && Tanner Barlow
 * This functions as our view for our simon game
 */

#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "QDir"
#include "controller.h"
#include "QTimer"
#include <QDateTime>
#include <iostream>
#include <QThread>
#include <QInputDialog>
#include <QMessageBox>

// Constructor
MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    QDir dir;
    controller = new Controller();

    // connect all the signals and slots for communication between
    // our view and our controller together
    connect(this, &MainWindow::newClick, controller, &Controller::advance);
    connect(this, &MainWindow::newClick, this, &MainWindow::flashButton);
    connect(this, &MainWindow::newGame, controller, &Controller::newGame);
    connect(this, &MainWindow::newScoreToStore, controller, &Controller::writeToFile);
    connect(controller, &Controller::newPattern, this, &MainWindow::PlayPattern);
    connect(controller, &Controller::loser, this, &MainWindow::youLose);
    connect(controller, &Controller::addProgress, this, &MainWindow::addProgress);
    connect(controller, &Controller::showHighScores, this, &MainWindow::showHighScores);

    ui->progressBar->setValue(0);

    buttonsEnabled(false);
}

// destructor
MainWindow::~MainWindow()
{
    delete ui;
    delete controller;
}

// triggers when the red button is clicked
void MainWindow::on_redButton_clicked()
{
    //triggers advance in controller.cpp
    emit newClick(0);

}

// triggers when the blue button is clicked
void MainWindow::on_blueButton_clicked()
{
    emit newClick(1);
}

// triggers when the green button is clicked
void MainWindow::on_greenButton_clicked()
{
    emit newClick(2);
}

// triggers when the yellow button is clicked
void MainWindow::on_yellowButton_clicked()
{
    emit newClick(3);
}

// called when user loses. Triggers the
// end of the game
void MainWindow::youLose(){

    buttonsEnabled(false);

    ui->redButton->setStyleSheet(blackStyleSheet);
    ui->greenButton->setStyleSheet(blackStyleSheet);
    ui->blueButton->setStyleSheet(blackStyleSheet);
    ui->yellowButton->setStyleSheet(blackStyleSheet);

    ui->redButton->setText("YOU");
    ui->greenButton->setText("ARE");
    ui->blueButton->setText("A");
    ui->yellowButton->setText("LOSER");

    ui->startButton->setText("Start");

    QTimer::singleShot(1000,this,SLOT(showDialog()));

}

// deals with end of game high score saving
void MainWindow::showDialog(){
    char message[100];
    strcpy(message,"You passed ");
    strcat(message,std::to_string(controller->getScore()).c_str());
    strcat(message," rounds.\nWould you like to record your score?");
    QMessageBox::StandardButton reply;
    reply = QMessageBox::question(this, "Score",message,
                                  QMessageBox::Yes|QMessageBox::No);
    if(reply == QMessageBox::Yes){
        bool ok;

        QString user = QInputDialog::getText(this, tr(message),
                                             tr("Name:"), QLineEdit::Normal,
                                             QDir::home().dirName(), &ok);

        emit newScoreToStore(user, QDateTime::currentDateTime());
    }else{
        char message2[100];
        strcpy(message2,"Oh come on... ");
        strcat(message2,std::to_string(controller->getScore()).c_str());
        strcat(message2," is not that bad. Are you sure you don't want to save it?");
        QMessageBox::StandardButton reply;
        reply = QMessageBox::question(this, "Score",message2,
                                      QMessageBox::Yes|QMessageBox::No);
        if(reply == QMessageBox::Yes){
            emit newScoreToStore("Stupid",QDateTime::currentDateTime());
            QMessageBox msgBox;
            msgBox.setText("I stored it anyway under the name \"Stupid\"\n#Gotcha #LeetHackerz #SimonSays");
            msgBox.exec();
        }
        else if(reply == QMessageBox::No){
            bool ok;
            QString user = QInputDialog::getText(this, tr(message),
                                                 tr("Name:"), QLineEdit::Normal,
                                                 QDir::home().dirName(), &ok);
            emit newScoreToStore(user, QDateTime::currentDateTime());
        }
    }
}

// displays the high scores as a popup as provided by the
// signal from the controller
void MainWindow::showHighScores(QString highScores){
    QMessageBox::information(0, "High Scores", highScores);
}

// slot that updates the progress bar in our simon game
// based on the current percentage from the timer
void MainWindow::addProgress(double progress){
    ui->progressBar->setValue(progress);
}

// flashes the button indicated by the input number
void MainWindow::flashButton(int button){
    if(button == 0){
        ui->redButton->setStyleSheet(redStyleSheet);
        QTimer::singleShot(buttonInterval,this,SLOT(ResetRedButton()));
    }
    else if(button == 1){
        ui->blueButton->setStyleSheet(blueStyleSheet);
        QTimer::singleShot(buttonInterval, this, SLOT(ResetBlueButton()));
    }
    else if(button == 2){
        ui->greenButton->setStyleSheet(greenStyleSheet);
        QTimer::singleShot(buttonInterval, this, SLOT(ResetGreenButton()));
    }
    else if(button == 3){
        ui->yellowButton->setStyleSheet(yellowStyleSheet);
        QTimer::singleShot(buttonInterval, this, SLOT(ResetYellowButton()));
    }
}

// sets the red button back to a normal background
void MainWindow::ResetRedButton(){
    ui->redButton->setStyleSheet(defaultStyleSheet);
}

// sets the blue button back to a normal background
void MainWindow::ResetBlueButton(){
    ui->blueButton->setStyleSheet(defaultStyleSheet);
}

// sets the green button back to a normal background
void MainWindow::ResetGreenButton(){
    ui->greenButton->setStyleSheet(defaultStyleSheet);
}

// sets the yellow button back to a normal background
void MainWindow::ResetYellowButton(){
    ui->yellowButton->setStyleSheet(defaultStyleSheet);
}

// enables all the playing buttons on the screen
void MainWindow::buttonsEnabled(bool enable){
    ui->blueButton->setEnabled(enable);
    ui->redButton->setEnabled(enable);
    ui->greenButton->setEnabled(enable);
    ui->yellowButton->setEnabled(enable);
}

// slot that plays out the pattern provided by the
// signal from the controller
void MainWindow::PlayPattern(QVector<int> pattern){
    buttonsEnabled(false);
    currentPattern = pattern;
    if((buttonInterval - speedup) >= minimumInterval){
        buttonInterval -= speedup;
    }
    if((betweenButtons - speedup) >= minimumBetween){
        buttonInterval -= speedup;
    }
    playbackIndex = 0;
    QTimer::singleShot(roundWait, this, SLOT(FlashNext()));
}

// flashes the next button in the current pattern
void MainWindow::FlashNext(){
    if(playbackIndex < currentPattern.size()){
        flashButton(currentPattern[playbackIndex]);
        playbackIndex++;
        QTimer::singleShot(betweenButtons, this, SLOT(FlashNext()));
    }
    else{
        //last playback has happened. set value to 0
        ui->progressBar->setValue(0);
        buttonsEnabled(true);
        QString round = QString::number(controller->getScore()+1);
        ui->roundLabel->setText("Round\n" + round);
    }
}

// triggers when the start button is clicked
void MainWindow::on_startButton_clicked()
{
    if(ui->startButton->text().startsWith("Start")){
        ui->startButton->setText("Restart");
    }
    else{
        ui->startButton->setText("Start");
    }
    buttonInterval = originalInterval;
    betweenButtons = originalBetween;
    speedup = originalSpeedup;
    buttonsEnabled(false);
    emit newGame();    
    resetButtons();
    ui->progressBar->setValue(0);
    ui->roundLabel->setText("Round\n1");
}

// resets all the buttons on the screen
// back to their default states
void MainWindow::resetButtons(){
    ui->redButton->setText("");
    ui->greenButton->setText("");
    ui->blueButton->setText("");
    ui->yellowButton->setText("");

    ui->redButton->setStyleSheet(defaultStyleSheet);
    ui->greenButton->setStyleSheet(defaultStyleSheet);
    ui->blueButton->setStyleSheet(defaultStyleSheet);
    ui->yellowButton->setStyleSheet(defaultStyleSheet);
}






