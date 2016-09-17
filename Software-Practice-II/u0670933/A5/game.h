/* A5 - A Simon Game - CS 3505
 * Elijah Grubb && Tanner Barlow
 * This acts as the model for our game
 * storing all relevant data and avoiding any
 * logical decision making
 */

#ifndef GAME_H
#define GAME_H
#include "QVector"

class Game
{
public:
    // controller
    Game();
    // destructor
    ~Game();

    // returns the current pattern
    QVector<int> getPattern();

    // returns the current pattern size
    int getPatternSize();

    // returns the current player position in the pattern
    int getPlayerPosition();

    // increments the player's position in the pattern
    void incrementPlayerPosition();

    // ++ overrides
    Game& operator++();
    Game operator++(int);

    // returns whether or not they correctly guessed the next color in the pattern
    bool isSuccess(int choice);

    // boolean that indicates if they've reached the end of the round
    bool endOfRound();

    // resets our model to create a new game
    void newGame();
private:
    // how big the pattern is at the start of a new game
    int initial_pattern_size;
    // current pattern
    QVector<int>* pattern;
    // current player position in guessing the pattern
    int playerPosition;
};

#endif // GAME_H
