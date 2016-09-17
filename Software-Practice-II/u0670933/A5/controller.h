/* A5 - A Simon Game - CS 3505
 * Elijah Grubb && Tanner Barlow
 * This controller class acts as the controller
 * and mediary of our view (mainwindow) and model (game)
 * classes
 */

#ifndef CONTROLLER_H
#define CONTROLLER_H
#include "game.h"
#include <QObject>
#include <QDateTime>
#include <QFile>
#include <QTextStream>


class Controller : public QObject
{
    Q_OBJECT

public:
    // constructor
    Controller();

    // destructor
    ~Controller();

    // public retrieval of current score
    int getScore();
public slots:
    //Update model if continuing
    //Emits new pattern if round complete
    //Emits you lose if incorrect
    void advance(int choice);

    //Creates new game, resets everything, slot that is signaled by start button
    void newGame();

    // saves the newest score to our high score "database"
    void writeToFile(QString player, QDateTime time);
signals:
    // signals that there's a new pattern for the user
    void newPattern(QVector<int> pattern);
    // signals that this user is a LOSER
    void loser();
    // signals the latest progress bar setting
    void addProgress(double progress);
    // signals the latest top 10 high scores
    void showHighScores(QString highScores);

private:
    // our game object
    Game* game;
    // number of rounds the user has survived
    int rounds;
    // location of database
    QString database = "../A5/scores.txt";
    // test delimeter
    char delimiter = '\t';
    // method for sorting our qvector that holds our high scores
    bool biggestToSmallest (QString i, QString j);
};

#endif // CONTROLLER_H
