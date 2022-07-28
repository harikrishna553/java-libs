package com.sample.app.dto;

public sealed class Shape {

}

/* 1. A permitted subclass may be declared final */
final class Circle extends Shape {
}

/*
 * 2. A permitted subclass may be declared sealed to allow its part of the
 * hierarchy to be extended further than envisaged by its sealed superclass
 */
sealed class Square extends Shape permits FilledSquare {
}

final class FilledSquare extends Square {
}

/*
 * 3. A permitted subclass may be declared non-sealed so that its part of the
 * hierarchy reverts to being open for extension by unknown subclasses.
 */
non-sealed class Rectangle extends Shape {
}

class MyRect extends Rectangle {
}