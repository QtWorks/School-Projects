/* A5 - A Simon Game - CS 3505
 * Elijah Grubb && Tanner Barlow
 * This controller class acts as the controller
 * and mediary of our view (mainwindow) and model (game)
 * classes
 */

#include "controller.h"
#include <iostream>
#include <QObject>
#include <QString>
#include <functional>
#include <tuple>
#include <QPair>

using namespace std;

// constructor
Controller::Controller()
{
    game = new Game();
    rounds = 0;
}

// destructor
Controller::~Controller()
{
    delete game;
}

//Update model if continuing
//Emits new pattern if round complete
//Emits you lose if incorrect
void Controller::advance(int choice){
    if(game->isSuccess(choice)){
        //game continues
        //check for end of round
        double progress = (((double) game->getPlayerPosition()
                / (double) game->getPatternSize()) * 100);
        emit addProgress(progress);
        if(game->endOfRound()){
            ++*game;
            emit newPattern(game->getPattern());
            rounds++;
        }
    }
    else{
        emit loser();
    }
}


//Creates new game, resets everything, slot that is signaled by start button
void Controller::newGame(){
    game->newGame();
    emit newPattern(game->getPattern());
    rounds = 0;
}

// public retrieval of current score
int Controller::getScore(){
    return rounds;
}

// saves the newest score to our high score "database"
void Controller::writeToFile(QString player, QDateTime time){
    QFile file(database);
    if(file.open(QIODevice::Append)){
        QTextStream stream(&file);
        stream << rounds << delimiter << player <<
                  delimiter <<time.toString() << endl;
        file.close();
    }

    //QVector<QString> highScores;
    QVector<QPair<int,QString>> highScores;

    if (file.open(QIODevice::ReadOnly))
    {
       QTextStream in(&file);
       while (!in.atEnd())
       {
          QString line = in.readLine();
          QStringList pieces = line.split('\t');
          int score = pieces[0].toInt();
          QPair<int, QString> t(score,line);
          highScores.push_back(t);
       }
       file.close();
    }

    std::sort(highScores.begin(), highScores.end(),
              [](const QPair<int,QString>& a,
                     const QPair<int,QString>& b) -> bool
                     {
                       return a.first > b.first;
                     });

    highScores.resize(10);
    QString highScoresString;
    highScoresString += "Score\tPlayer\tDate\n";
    QString temp;
    for (int i = 0; i < highScores.size(); i++) {

        temp = highScores[i].second;

        highScoresString += temp;
        highScoresString += "\n";

    }

    emit showHighScores(highScoresString);
}

// method for sorting our qvector that holds our high scores
bool Controller::biggestToSmallest(QString i, QString j) {
    return (i>j);
}
