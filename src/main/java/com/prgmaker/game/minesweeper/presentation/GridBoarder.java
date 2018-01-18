package com.prgmaker.game.minesweeper.presentation;

import com.prgmaker.game.minesweeper.util.Rect;

public abstract class GridBoarder extends GridDisplay {
    protected GridDisplay display;

    public GridBoarder(GridDisplay display) {
        this.display = display;
        display.addObserver(this);
    }

    @Override
    protected final int getRowCount() {
        return display.getRowCount();
    }

    @Override
    protected final int getColCount() {
        return display.getColCount();
    }

    @Override
    protected final int getWidth() {
        Rect boarder = getBoarder();
        return display.getWidth() + boarder.left + boarder.right;
    }

    @Override
    protected final int getHeight() {
        Rect boarder = getBoarder();
        return display.getHeight() + boarder.top + boarder.bottom;

    }

    @Override
    protected final Rect getOffset() {
        return Rect.newInstance().combineUpdate(getBoarder(), display.getOffset());
    }

    @Override
    protected final int measureCellWidth(int req) {
        return display.measureCellWidth(Math.max(req, getCellWidth()));
    }

    @Override
    protected final int getMeasuredCellWidth() {
        return display.getMeasuredCellWidth();
    }

    protected final int getCellGap() {
        return display.getCellGap();
    }
}
