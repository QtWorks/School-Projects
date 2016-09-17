/********************************************************************************
** Form generated from reading UI file 'mainwindow.ui'
**
** Created by: Qt User Interface Compiler version 5.5.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINWINDOW_H
#define UI_MAINWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QProgressBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QWidget *centralWidget;
    QPushButton *redButton;
    QPushButton *blueButton;
    QPushButton *startButton;
    QProgressBar *progressBar;
    QPushButton *greenButton;
    QPushButton *yellowButton;
    QLabel *roundLabel;
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *MainWindow)
    {
        if (MainWindow->objectName().isEmpty())
            MainWindow->setObjectName(QStringLiteral("MainWindow"));
        MainWindow->resize(953, 900);
        centralWidget = new QWidget(MainWindow);
        centralWidget->setObjectName(QStringLiteral("centralWidget"));
        redButton = new QPushButton(centralWidget);
        redButton->setObjectName(QStringLiteral("redButton"));
        redButton->setGeometry(QRect(150, 90, 321, 251));
        QFont font;
        font.setPointSize(20);
        font.setBold(true);
        font.setWeight(75);
        redButton->setFont(font);
        blueButton = new QPushButton(centralWidget);
        blueButton->setObjectName(QStringLiteral("blueButton"));
        blueButton->setGeometry(QRect(150, 350, 321, 251));
        blueButton->setFont(font);
        startButton = new QPushButton(centralWidget);
        startButton->setObjectName(QStringLiteral("startButton"));
        startButton->setGeometry(QRect(350, 640, 231, 101));
        progressBar = new QProgressBar(centralWidget);
        progressBar->setObjectName(QStringLiteral("progressBar"));
        progressBar->setGeometry(QRect(10, 30, 931, 23));
        progressBar->setValue(24);
        greenButton = new QPushButton(centralWidget);
        greenButton->setObjectName(QStringLiteral("greenButton"));
        greenButton->setGeometry(QRect(480, 90, 321, 251));
        greenButton->setFont(font);
        yellowButton = new QPushButton(centralWidget);
        yellowButton->setObjectName(QStringLiteral("yellowButton"));
        yellowButton->setGeometry(QRect(480, 350, 321, 251));
        yellowButton->setFont(font);
        roundLabel = new QLabel(centralWidget);
        roundLabel->setObjectName(QStringLiteral("roundLabel"));
        roundLabel->setGeometry(QRect(20, 160, 111, 91));
        QFont font1;
        font1.setPointSize(14);
        roundLabel->setFont(font1);
        roundLabel->setAlignment(Qt::AlignCenter);
        MainWindow->setCentralWidget(centralWidget);
        menuBar = new QMenuBar(MainWindow);
        menuBar->setObjectName(QStringLiteral("menuBar"));
        menuBar->setGeometry(QRect(0, 0, 953, 26));
        MainWindow->setMenuBar(menuBar);
        mainToolBar = new QToolBar(MainWindow);
        mainToolBar->setObjectName(QStringLiteral("mainToolBar"));
        MainWindow->addToolBar(Qt::TopToolBarArea, mainToolBar);
        statusBar = new QStatusBar(MainWindow);
        statusBar->setObjectName(QStringLiteral("statusBar"));
        MainWindow->setStatusBar(statusBar);

        retranslateUi(MainWindow);

        QMetaObject::connectSlotsByName(MainWindow);
    } // setupUi

    void retranslateUi(QMainWindow *MainWindow)
    {
        MainWindow->setWindowTitle(QApplication::translate("MainWindow", "MainWindow", 0));
        redButton->setText(QString());
        blueButton->setText(QString());
        startButton->setText(QApplication::translate("MainWindow", "Start", 0));
        greenButton->setText(QString());
        yellowButton->setText(QString());
        roundLabel->setText(QApplication::translate("MainWindow", "Round\n"
"0", 0));
    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
