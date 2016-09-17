#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include <QPainter>
#include <QImage>
#include <QMouseEvent>
#include <iostream>
#include <QRect>

using namespace std;

class Widget : public QWidget
{
public:
    Widget(QWidget *parent = 0, Qt::WindowFlags f = 0) : QWidget(parent, f), screen_image_(256, 240, QImage::Format_RGB32) {

        }

        void draw_frame(void *data) {
            // render data into screen_image_
            // I am using fill here, but in the real thing I am rendering
            // on a pixel by pixel basis
            screen_image_.fill(rand());
        }

        void start_frame() {
            // do any pre-rendering prep work that needs to be done before
            // each frame
        }

        void end_frame() {
            //update(); // force a paint event
            repaint();
        }

        void mousePressEvent(QMouseEvent *event){
            cout << event->pos().x() << endl;
            cout << event->pos().y() << endl;
        }

        void paintEvent(QPaintEvent * event) {
           const QRect paintRect = event->rect();
           QPainter painter(this);

           painter.drawImage(paintRect, screen_image_, paintRect);
           painter.drawPolyline(points,4);
            //p.drawImage(rect(), screen_image_, screen_image_.rect());

        }

        QImage screen_image_;
};

#endif // WIDGET_H
