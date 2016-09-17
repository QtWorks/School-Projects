#include <stdlib.h>
#include <iostream>
#include <stdio.h>
#include <string>
#include <math.h>
#include "spiral.h"
#include "HaruPDF.h"

using namespace std;

int main(int argc, char **argv)
{
    HaruPDF haru;
	//Initialize PDF
	haru.createPDF();
	//Initialize center of PDF, initial radius and angle
	double centerx = 210;
	double centery = 300;
	double spiralAngle = 180;
	double initial_radius = 80;

	//Initialize spiral object
	spiral s (centerx, centery, spiralAngle, initial_radius);
	if(argc != 2){
		cout << "Please give one and only one message";
		return 0;
	}
	//Get message from argument
	std::string message = argv[1];

	for(unsigned int i = 0; i < message.length(); i++){
		s++;
		char c = message[i];
		haru.addNextLetter(c, s.get_text_x(), s.get_text_y(), s.get_spiral_angle());
	}

	haru.savePDF();

}
