package com.prgmaker.game.minesweeper.presentation;

import com.prgmaker.game.minesweeper.util.Rect;

import java.util.Observable;
import java.util.Observer;

public abstract class GridDisplay extends Observable implements Observer {
    protected abstract int getRowCount();

    protected abstract int getColCount();

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected abstract Rect getOffset();

    protected abstract Rect getBoarder();

    protected abstract int measureCellWidth(int req);

    protected abstract int getMeasuredCellWidth();

    protected abstract int getCellGap();

    protected abstract int getCellWidth();

    protected abstract String getRowText(int row);

    @Override
    public void update(Observable o, Object arg) {
        if (countObservers() == 0) {
            show();
        } else {
            setChanged();
            notifyObservers();
        }
    }

    public final void show() {
        int nRow = getHeight();
        for (int r = 0; r < nRow; r++) {
            System.out.println(getRowText(r));
        }
    }

    protected String repChars(char c, int rep) {
        if(rep < 0) throw new IllegalArgumentException(""+rep);
        char[] chars = new char[rep];
        int pi = 0;
        while (rep-- > 0) {
            chars[pi++] = c;
        }

        return String.valueOf(chars);
    }
}
