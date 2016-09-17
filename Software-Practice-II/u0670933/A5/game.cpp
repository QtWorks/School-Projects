/* A5 - A Simon Game - CS 3505
 * Elijah Grubb && Tanner Barlow
 * This acts as the model for our game
 * storing all relevant data and avoiding any
 * logical decision making
 */

#include "game.h"
#include <iostream>

using namespace std;

// constructor
Game::Game()
{
    initial_pattern_size = 2;
    pattern = new QVector<int>();
    playerPosition = 0;
}

// destructor
Game::~Game()
{
    delete pattern;
}

// returns the current pattern
QVector<int> Game::getPattern(){
    return *pattern;
}

// returns the current pattern size
int Game::getPatternSize(){
    return pattern->size();
}

// returns the current player position in the pattern
int Game::getPlayerPosition(){
    return playerPosition;
}

// increments the player's position in the pattern
void Game::newGame(){
    pattern->clear();
    for(int i = 0; i < initial_pattern_size; i++){
        ++*this;
    }
}

// ++ override
Game& Game::operator ++(){
    //Generate random between 0 and 1 and add to pattern

    int next = rand() % 4;
    pattern->push_back(next);
    playerPosition = 0;
    return *this;
}

// ++ override
Game Game::operator ++(int){
    Game temp = *this;
    ++*this;
    return temp;
}

// returns whether or not they correctly guessed the next color in the pattern
bool Game::isSuccess(int choice){
    if(pattern->size() >= playerPosition){
        if(pattern->at(playerPosition) == choice){
            playerPosition++;
            return true;
        }
        else
            return false;
    }
    else{
        return false;
    }
}

// boolean that indicates if they've reached the end of the round
bool Game::endOfRound(){
    return playerPosition == pattern->size();
}
