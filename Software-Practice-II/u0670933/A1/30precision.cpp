#include <iostream>
#include <iomanip>
using namespace std;

//Main function
int main(){

  double dub = 0.3;
  float floater = 0.3;
  long double ld = 0.3;
  int p = 10;
  while(p <= 30){
    cout << "PRECISION LEVEL " << p << endl;
    cout << setprecision(p) << fixed;
    cout << "Double: " << dub << endl;
    cout << "Float: " << floater << endl;
    cout << "Long Double: " << ld << endl;
    p++;
  }  
}
