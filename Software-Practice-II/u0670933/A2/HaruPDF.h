#ifndef HARUPDF_H
#define HARUPDF_H
#include "hpdf.h"
#include <string>

class HaruPDF{
    HPDF_Doc  pdf;
    HPDF_Page page;
    char fname[256];
    HPDF_Font font;

public:
    void createPDF(); //Initializes a blank pdf
    void addNextLetter(char letter, double x, double y, double textAngle); //Adds a letter at a specific position and angle to the pdf
    void savePDF();//saves the pdf to spiralPDF.pdf
};
#endif
