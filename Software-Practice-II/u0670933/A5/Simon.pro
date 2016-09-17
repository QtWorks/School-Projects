#-------------------------------------------------
#
# Project created by QtCreator 2016-02-25T13:44:51
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = Simon
TEMPLATE = app
CONFIG += c++11


SOURCES += main.cpp\
        mainwindow.cpp \
    game.cpp \
    controller.cpp

HEADERS  += mainwindow.h \
    game.h \
    controller.h

FORMS    += mainwindow.ui
