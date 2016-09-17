#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <iostream>
#include <math.h>
#include "hpdf.h"
#include "HaruPDF.h"

using namespace std;

void HaruPDF::createPDF(){

    pdf = HPDF_New (NULL, NULL);
    /* add a new page object. */
    page = HPDF_AddPage (pdf);
    HPDF_Page_SetSize (page, HPDF_PAGE_SIZE_A5, HPDF_PAGE_PORTRAIT);
//    print_grid  (pdf, page);
    font = HPDF_GetFont (pdf, "Helvetica", NULL);
    HPDF_Page_SetTextLeading (page, 20);
    HPDF_Page_SetGrayStroke (page, 0);

    HPDF_Page_BeginText (page);
    font = HPDF_GetFont (pdf, "Courier-Bold", NULL);
    HPDF_Page_SetFontAndSize (page, font, 30);
}

void HaruPDF::savePDF(){
    HPDF_Page_EndText (page);

    /* save the document to a file */
    HPDF_SaveToFile (pdf, "spiralPDF.pdf");

    /* clean up */
    HPDF_Free (pdf);
}

void HaruPDF::addNextLetter(char letter, double x, double y, double spiralAngle){
    char buf[2];

    float textRad;
	
    textRad = (spiralAngle - 90) / 180 * M_PI;
    HPDF_Page_SetTextMatrix(page, cos(textRad), sin(textRad), -sin(textRad), cos(textRad), x, y);

    // C-style strings are null-terminated. The last character must a 0.
    buf[0] = letter;
    buf[1] = 0;
    HPDF_Page_ShowText (page, buf);
}
