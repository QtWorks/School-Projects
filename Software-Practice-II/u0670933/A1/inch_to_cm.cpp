#include <iostream>
#include <iomanip>
using namespace std;

//Takes in a centimeters value and an inch value
//to return a useful message to the user
void niceOutput(double cm, double inch){
  cout << setprecision(2) << fixed;
  cout << cm << endl;
}
//Converts inch value to centimeter value
double convertInchToCm(double inch){
  return inch * 2.54;
}
//Main function
int main(){
  double inch;
  cout << "Give me a number in inches" << endl;
  while(cin >> inch){
    niceOutput(convertInchToCm(inch), inch);
  }
}
