/****************************************************************************
** Meta object code from reading C++ file 'controller.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.5.1)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../../A5/controller.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#include <QtCore/QVector>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'controller.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.5.1. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
struct qt_meta_stringdata_Controller_t {
    QByteArrayData data[16];
    char stringdata0[144];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_Controller_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_Controller_t qt_meta_stringdata_Controller = {
    {
QT_MOC_LITERAL(0, 0, 10), // "Controller"
QT_MOC_LITERAL(1, 11, 10), // "newPattern"
QT_MOC_LITERAL(2, 22, 0), // ""
QT_MOC_LITERAL(3, 23, 12), // "QVector<int>"
QT_MOC_LITERAL(4, 36, 7), // "pattern"
QT_MOC_LITERAL(5, 44, 5), // "loser"
QT_MOC_LITERAL(6, 50, 11), // "addProgress"
QT_MOC_LITERAL(7, 62, 8), // "progress"
QT_MOC_LITERAL(8, 71, 14), // "showHighScores"
QT_MOC_LITERAL(9, 86, 10), // "highScores"
QT_MOC_LITERAL(10, 97, 7), // "advance"
QT_MOC_LITERAL(11, 105, 6), // "choice"
QT_MOC_LITERAL(12, 112, 7), // "newGame"
QT_MOC_LITERAL(13, 120, 11), // "writeToFile"
QT_MOC_LITERAL(14, 132, 6), // "player"
QT_MOC_LITERAL(15, 139, 4) // "time"

    },
    "Controller\0newPattern\0\0QVector<int>\0"
    "pattern\0loser\0addProgress\0progress\0"
    "showHighScores\0highScores\0advance\0"
    "choice\0newGame\0writeToFile\0player\0"
    "time"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_Controller[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
       7,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       4,       // signalCount

 // signals: name, argc, parameters, tag, flags
       1,    1,   49,    2, 0x06 /* Public */,
       5,    0,   52,    2, 0x06 /* Public */,
       6,    1,   53,    2, 0x06 /* Public */,
       8,    1,   56,    2, 0x06 /* Public */,

 // slots: name, argc, parameters, tag, flags
      10,    1,   59,    2, 0x0a /* Public */,
      12,    0,   62,    2, 0x0a /* Public */,
      13,    2,   63,    2, 0x0a /* Public */,

 // signals: parameters
    QMetaType::Void, 0x80000000 | 3,    4,
    QMetaType::Void,
    QMetaType::Void, QMetaType::Double,    7,
    QMetaType::Void, QMetaType::QString,    9,

 // slots: parameters
    QMetaType::Void, QMetaType::Int,   11,
    QMetaType::Void,
    QMetaType::Void, QMetaType::QString, QMetaType::QDateTime,   14,   15,

       0        // eod
};

void Controller::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        Controller *_t = static_cast<Controller *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: _t->newPattern((*reinterpret_cast< QVector<int>(*)>(_a[1]))); break;
        case 1: _t->loser(); break;
        case 2: _t->addProgress((*reinterpret_cast< double(*)>(_a[1]))); break;
        case 3: _t->showHighScores((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 4: _t->advance((*reinterpret_cast< int(*)>(_a[1]))); break;
        case 5: _t->newGame(); break;
        case 6: _t->writeToFile((*reinterpret_cast< QString(*)>(_a[1])),(*reinterpret_cast< QDateTime(*)>(_a[2]))); break;
        default: ;
        }
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        switch (_id) {
        default: *reinterpret_cast<int*>(_a[0]) = -1; break;
        case 0:
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
            typedef void (Controller::*_t)(QVector<int> );
            if (*reinterpret_cast<_t *>(func) == static_cast<_t>(&Controller::newPattern)) {
                *result = 0;
            }
        }
        {
            typedef void (Controller::*_t)();
            if (*reinterpret_cast<_t *>(func) == static_cast<_t>(&Controller::loser)) {
                *result = 1;
            }
        }
        {
            typedef void (Controller::*_t)(double );
            if (*reinterpret_cast<_t *>(func) == static_cast<_t>(&Controller::addProgress)) {
                *result = 2;
            }
        }
        {
            typedef void (Controller::*_t)(QString );
            if (*reinterpret_cast<_t *>(func) == static_cast<_t>(&Controller::showHighScores)) {
                *result = 3;
            }
        }
    }
}

const QMetaObject Controller::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_Controller.data,
      qt_meta_data_Controller,  qt_static_metacall, Q_NULLPTR, Q_NULLPTR}
};


const QMetaObject *Controller::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *Controller::qt_metacast(const char *_clname)
{
    if (!_clname) return Q_NULLPTR;
    if (!strcmp(_clname, qt_meta_stringdata_Controller.stringdata0))
        return static_cast<void*>(const_cast< Controller*>(this));
    return QObject::qt_metacast(_clname);
}

int Controller::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 7)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 7;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 7)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 7;
    }
    return _id;
}

// SIGNAL 0
void Controller::newPattern(QVector<int> _t1)
{
    void *_a[] = { Q_NULLPTR, const_cast<void*>(reinterpret_cast<const void*>(&_t1)) };
    QMetaObject::activate(this, &staticMetaObject, 0, _a);
}

// SIGNAL 1
void Controller::loser()
{
    QMetaObject::activate(this, &staticMetaObject, 1, Q_NULLPTR);
}

// SIGNAL 2
void Controller::addProgress(double _t1)
{
    void *_a[] = { Q_NULLPTR, const_cast<void*>(reinterpret_cast<const void*>(&_t1)) };
    QMetaObject::activate(this, &staticMetaObject, 2, _a);
}

// SIGNAL 3
void Controller::showHighScores(QString _t1)
{
    void *_a[] = { Q_NULLPTR, const_cast<void*>(reinterpret_cast<const void*>(&_t1)) };
    QMetaObject::activate(this, &staticMetaObject, 3, _a);
}
QT_END_MOC_NAMESPACE
