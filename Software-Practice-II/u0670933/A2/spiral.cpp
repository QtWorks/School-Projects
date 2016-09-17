#include <iostream>
#include <math.h>
#include "spiral.h"

using namespace std;

spiral::spiral(){

}

spiral::spiral(double centerX, double centerY, double spiralAngle, double radius){
    //Initialize center of pdf
    center_x = centerX;
    center_y = centerY;
    //Starting point is center
    x = centerX;
    y = centerY;
    //Starting angle
    degSpiral = spiralAngle;
    radSpiral = ToRadians(degSpiral);
    //Starting angle of text is perpendicular
    radText = ToRadians(degSpiral - 90);
    //Initialize starting radius
    _radius = radius;
    //Initizlize count for inverse relationship between angle and radius
    count = 1;
}


double spiral::get_text_x(){
    return x;
}

double spiral::get_text_y(){
    return y;
}

double spiral::get_spiral_angle(){
    return degSpiral;
}

double spiral::get_text_angle(){
    return degText;
}

spiral spiral::operator++(int){
    //Decrease degrees by 10
    degSpiral -= 10;
    _radius += 1;
    //Convert degrees to radians to use in formula
    radSpiral = ToRadians(degSpiral);
    x = center_x + cos(radSpiral) * _radius;
    y = center_y + sin(radSpiral) * _radius;
    //Increment count by 1
    count++;
    return *this;
}

ostream& operator<<(ostream& output, spiral s){
    output << "x: " << s.x << " y: " << s.y << " spiral degree: " << s.degSpiral << " text degree: " << s.degText;
	return output;
}
//Convert radians to degrees
double spiral::ToDegrees(double radian){
    return radian / (M_PI) * 180;
}
//Convert degrees to radians
double spiral::ToRadians(double degree){
    return degree / 180 * (M_PI);
}
