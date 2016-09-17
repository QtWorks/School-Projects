LIBHARU = ./libharu
CC = g++
CFLAGS = -Wall -std=c++11

spiralPDF: spiralPDF.cpp spiral.o HaruPDF.o
	$(CC) $(CFLAGS) -o spiralPDF spiralPDF.cpp spiral.o HaruPDF.o -O2 -L $(LIBHARU)/src/ -lhpdfs -I $(LIBHARU)/include/ -lm -lz -lstdc++

spiralPDF.cpp:
	$(CC) $(CFLAGS) -c spiralPDF.cpp

spiral.o:
	$(CC) $(CFLAGS) -c spiral.cpp spiral.h

HaruPDF.o:
	$(CC) $(CFLAGS) -c HaruPDF.cpp -O2 -L $(LIBHARU)/src/ -lhpdfs -I $(LIBHARU)/include/ -lm -lz -lstdc++

clean:
	rm spiralPDF *.o

test:
	./spiralPDF
