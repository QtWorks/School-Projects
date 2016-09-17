#ifndef SPIRAL_H
#define SPIRAL_H
#include <iostream>

using namespace std;

class spiral{
    //Necessary member variables
    double center_x, center_y, x, y, degSpiral, degText, _radius;
    int count;
    float radSpiral, radText;

public:
    spiral();//default constructor
    spiral(double centerX, double centerY, double spiralAngle, double radius);//specified constructor
    //getters
    double get_text_y();
    double get_text_x();
    double get_spiral_angle();
    double get_text_angle();
    //increment operator
    spiral operator++(int); //Postfix increment operator
    friend ostream& operator<<(ostream& output, spiral s);
private://Conversion methods
    double ToDegrees(double radian);
    double ToRadians(double degree);
};
#endif
