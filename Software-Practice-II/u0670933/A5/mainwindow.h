/* A5 - A Simon Game - CS3505
 * Elijah Grubb && Tanner Barlow
 * This functions as our view for our simon game
 */

#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "controller.h"
#include <QtMultimedia/QMediaPlayer>


using namespace std;

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    // Constructor
    explicit MainWindow(QWidget *parent = 0);
    // destructor
    ~MainWindow();

private slots:

    // triggers when the red button is clicked
    void on_redButton_clicked();

    // triggers when the blue button is clicked
    void on_blueButton_clicked();

    // triggers when the start button is clicked
    void on_startButton_clicked();

    // triggers when the green button is clicked
    void on_greenButton_clicked();

    // triggers when the yellow button is clicked
    void on_yellowButton_clicked();

public slots:

    // called when user loses. Triggers the
    // end of the game
    void youLose();

    // slot that updates the progress bar in our simon game
    // based on the current percentage from the timer
    void addProgress(double progress);

    // flashes the button indicated by the input number
    void flashButton(int button);

    // sets the red button back to a normal background
    void ResetRedButton();

    // sets the blue button back to a normal background
    void ResetBlueButton();

    // sets the green button back to a normal background
    void ResetGreenButton();

    // sets the yellow button back to a normal background
    void ResetYellowButton();

    // slot that plays out the pattern provided by the
    // signal from the controller
    void PlayPattern(QVector<int> pattern);

    // flashes the next button in the current pattern
    void FlashNext();

    // deals with end of game high score saving
    void showDialog();

    // displays the high scores as a popup as provided by the
    // signal from the controller
    void showHighScores(QString highScores);

signals:
    // signals a new button has been clicked
    void newClick(int choice);

    // signals that the start/restart button has been clicked
    void newGame();

    // signals that there's a new score we need to store
    void newScoreToStore(QString player, QDateTime time);

private:
    // our qt ui
    Ui::MainWindow *ui;
    int playbackIndex = 0;

    // current patter we're flashing
    QVector<int> currentPattern;

    // timing constants
    const int roundWait = 1000;
    const int originalInterval = 300;
    const int originalBetween = 600;
    const int originalSpeedup = 10;
    const int minimumBetween = 250;
    const int minimumInterval = 150;

    int buttonInterval, betweenButtons, speedup;

    // background stylesheet constants for buttons
    const QString defaultStyleSheet = "background-color:none";
    const QString redStyleSheet = "background-color:red";
    const QString blueStyleSheet = "background-color:blue";
    const QString greenStyleSheet = "background-color:green";
    const QString yellowStyleSheet = "background-color:yellow";
    const QString blackStyleSheet = "background-color:black;color:white";

    // our controller
    Controller* controller;

    // resets all the buttons on the screen
    // back to their default states
    void resetButtons();

    // enables all the playing buttons on the screen
    void buttonsEnabled(bool enable);
};

#endif // MAINWINDOW_H
