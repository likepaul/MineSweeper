package com.prgmaker.game.minesweeper.util;

public class Rect {
    public int left, top, right, bottom;

    public static Rect newInstance(int left, int top, int right, int bottom) {
        Rect r = new Rect();
        r.left = left;
        r.right = right;
        r.top = top;
        r.bottom = bottom;
        return r;
    }

    public static Rect newInstance() {
        return newInstance(0, 0, 0, 0);
    }

    public Rect combineUpdate(Rect a, Rect b) {
        left = a.left+b.left;
        right = a.right+b.right;
        top = a.top + b.top;
        bottom = a.bottom + b.bottom;
        return this;
    }
}
