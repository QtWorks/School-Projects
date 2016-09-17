/****************************************************************************
** Meta object code from reading C++ file 'mainwindow.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.5.1)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../../A5/mainwindow.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#include <QtCore/QVector>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'mainwindow.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.5.1. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
struct qt_meta_stringdata_MainWindow_t {
    QByteArrayData data[29];
    char stringdata0[371];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_MainWindow_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_MainWindow_t qt_meta_stringdata_MainWindow = {
    {
QT_MOC_LITERAL(0, 0, 10), // "MainWindow"
QT_MOC_LITERAL(1, 11, 8), // "newClick"
QT_MOC_LITERAL(2, 20, 0), // ""
QT_MOC_LITERAL(3, 21, 6), // "choice"
QT_MOC_LITERAL(4, 28, 7), // "newGame"
QT_MOC_LITERAL(5, 36, 15), // "newScoreToStore"
QT_MOC_LITERAL(6, 52, 6), // "player"
QT_MOC_LITERAL(7, 59, 4), // "time"
QT_MOC_LITERAL(8, 64, 20), // "on_redButton_clicked"
QT_MOC_LITERAL(9, 85, 21), // "on_blueButton_clicked"
QT_MOC_LITERAL(10, 107, 22), // "on_startButton_clicked"
QT_MOC_LITERAL(11, 130, 22), // "on_greenButton_clicked"
QT_MOC_LITERAL(12, 153, 23), // "on_yellowButton_clicked"
QT_MOC_LITERAL(13, 177, 7), // "youLose"
QT_MOC_LITERAL(14, 185, 11), // "addProgress"
QT_MOC_LITERAL(15, 197, 8), // "progress"
QT_MOC_LITERAL(16, 206, 11), // "flashButton"
QT_MOC_LITERAL(17, 218, 6), // "button"
QT_MOC_LITERAL(18, 225, 14), // "ResetRedButton"
QT_MOC_LITERAL(19, 240, 15), // "ResetBlueButton"
QT_MOC_LITERAL(20, 256, 16), // "ResetGreenButton"
QT_MOC_LITERAL(21, 273, 17), // "ResetYellowButton"
QT_MOC_LITERAL(22, 291, 11), // "PlayPattern"
QT_MOC_LITERAL(23, 303, 12), // "QVector<int>"
QT_MOC_LITERAL(24, 316, 7), // "pattern"
QT_MOC_LITERAL(25, 324, 9), // "FlashNext"
QT_MOC_LITERAL(26, 334, 10), // "showDialog"
QT_MOC_LITERAL(27, 345, 14), // "showHighScores"
QT_MOC_LITERAL(28, 360, 10) // "highScores"

    },
    "MainWindow\0newClick\0\0choice\0newGame\0"
    "newScoreToStore\0player\0time\0"
    "on_redButton_clicked\0on_blueButton_clicked\0"
    "on_startButton_clicked\0on_greenButton_clicked\0"
    "on_yellowButton_clicked\0youLose\0"
    "addProgress\0progress\0flashButton\0"
    "button\0ResetRedButton\0ResetBlueButton\0"
    "ResetGreenButton\0ResetYellowButton\0"
    "PlayPattern\0QVector<int>\0pattern\0"
    "FlashNext\0showDialog\0showHighScores\0"
    "highScores"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_MainWindow[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
      19,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       3,       // signalCount

 // signals: name, argc, parameters, tag, flags
       1,    1,  109,    2, 0x06 /* Public */,
       4,    0,  112,    2, 0x06 /* Public */,
       5,    2,  113,    2, 0x06 /* Public */,

 // slots: name, argc, parameters, tag, flags
       8,    0,  118,    2, 0x08 /* Private */,
       9,    0,  119,    2, 0x08 /* Private */,
      10,    0,  120,    2, 0x08 /* Private */,
      11,    0,  121,    2, 0x08 /* Private */,
      12,    0,  122,    2, 0x08 /* Private */,
      13,    0,  123,    2, 0x0a /* Public */,
      14,    1,  124,    2, 0x0a /* Public */,
      16,    1,  127,    2, 0x0a /* Public */,
      18,    0,  130,    2, 0x0a /* Public */,
      19,    0,  131,    2, 0x0a /* Public */,
      20,    0,  132,    2, 0x0a /* Public */,
      21,    0,  133,    2, 0x0a /* Public */,
      22,    1,  134,    2, 0x0a /* Public */,
      25,    0,  137,    2, 0x0a /* Public */,
      26,    0,  138,    2, 0x0a /* Public */,
      27,    1,  139,    2, 0x0a /* Public */,

 // signals: parameters
    QMetaType::Void, QMetaType::Int,    3,
    QMetaType::Void,
    QMetaType::Void, QMetaType::QString, QMetaType::QDateTime,    6,    7,

 // slots: parameters
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void, QMetaType::Double,   15,
    QMetaType::Void, QMetaType::Int,   17,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void, 0x80000000 | 23,   24,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void, QMetaType::QString,   28,

       0        // eod
};

void MainWindow::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        MainWindow *_t = static_cast<MainWindow *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: _t->newClick((*reinterpret_cast< int(*)>(_a[1]))); break;
        case 1: _t->newGame(); break;
        case 2: _t->newScoreToStore((*reinterpret_cast< QString(*)>(_a[1])),(*reinterpret_cast< QDateTime(*)>(_a[2]))); break;
        case 3: _t->on_redButton_clicked(); break;
        case 4: _t->on_blueButton_clicked(); break;
        case 5: _t->on_startButton_clicked(); break;
        case 6: _t->on_greenButton_clicked(); break;
        case 7: _t->on_yellowButton_clicked(); break;
        case 8: _t->youLose(); break;
        case 9: _t->addProgress((*reinterpret_cast< double(*)>(_a[1]))); break;
        case 10: _t->flashButton((*reinterpret_cast< int(*)>(_a[1]))); break;
        case 11: _t->ResetRedButton(); break;
        case 12: _t->ResetBlueButton(); break;
        case 13: _t->ResetGreenButton(); break;
        case 14: _t->ResetYellowButton(); break;
        case 15: _t->PlayPattern((*reinterpret_cast< QVector<int>(*)>(_a[1]))); break;
        case 16: _t->FlashNext(); break;
        case 17: _t->showDialog(); break;
        case 18: _t->showHighScores((*reinterpret_cast< QString(*)>(_a[1]))); break;
        default: ;
        }
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        switch (_id) {
        default: *reinterpret_cast<int*>(_a[0]) = -1; break;
        case 15:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 0:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< QVector<int> >(); break;
            }
            break;
        }
    } else if (_c == QMetaObject::IndexOfMethod) {
        int *result = reinterpret_cast<int *>(_a[0]);
        void **func = reinterpret_cast<void **>(_a[1]);
        {
            typedef void (MainWindow::*_t)(int );
            if (*reinterpret_cast<_t *>(func) == static_cast<_t>(&MainWindow::newClick)) {
                *result = 0;
            }
        }
        {
            typedef void (MainWindow::*_t)();
            if (*reinterpret_cast<_t *>(func) == static_cast<_t>(&MainWindow::newGame)) {
                *result = 1;
            }
        }
        {
            typedef void (MainWindow::*_t)(QString , QDateTime );
            if (*reinterpret_cast<_t *>(func) == static_cast<_t>(&MainWindow::newScoreToStore)) {
                *result = 2;
            }
        }
    }
}

const QMetaObject MainWindow::staticMetaObject = {
    { &QMainWindow::staticMetaObject, qt_meta_stringdata_MainWindow.data,
      qt_meta_data_MainWindow,  qt_static_metacall, Q_NULLPTR, Q_NULLPTR}
};


const QMetaObject *MainWindow::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *MainWindow::qt_metacast(const char *_clname)
{
    if (!_clname) return Q_NULLPTR;
    if (!strcmp(_clname, qt_meta_stringdata_MainWindow.stringdata0))
        return static_cast<void*>(const_cast< MainWindow*>(this));
    return QMainWindow::qt_metacast(_clname);
}

int MainWindow::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QMainWindow::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 19)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 19;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 19)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 19;
    }
    return _id;
}

// SIGNAL 0
void MainWindow::newClick(int _t1)
{
    void *_a[] = { Q_NULLPTR, const_cast<void*>(reinterpret_cast<const void*>(&_t1)) };
    QMetaObject::activate(this, &staticMetaObject, 0, _a);
}

// SIGNAL 1
void MainWindow::newGame()
{
    QMetaObject::activate(this, &staticMetaObject, 1, Q_NULLPTR);
}

// SIGNAL 2
void MainWindow::newScoreToStore(QString _t1, QDateTime _t2)
{
    void *_a[] = { Q_NULLPTR, const_cast<void*>(reinterpret_cast<const void*>(&_t1)), const_cast<void*>(reinterpret_cast<const void*>(&_t2)) };
    QMetaObject::activate(this, &staticMetaObject, 2, _a);
}
QT_END_MOC_NAMESPACE
