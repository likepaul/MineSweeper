package com.prgmaker.game.minesweeper.presentation;

import com.prgmaker.game.minesweeper.data.MineMap;
import com.prgmaker.game.minesweeper.util.Rect;

public class MineMapDisplay extends GridDisplay {
    private MineMap map;
    private int cellGap;
    private int measuredCellWidth;
    private int cellWidth;
    private Rect rect = Rect.newInstance();

    private static char SYM_EMPTY = ' ';
    private static char SYM_MINE = '*';
    private static char SYM_FLAG = 'F';
    private static char SYM_HIDE = '?';

    public MineMapDisplay(MineMap map) {
        this.map = map;
        map.addObserver(this);
        cellGap = 3;
        cellWidth = measuredCellWidth = 1;
    }

    @Override
    protected int getRowCount() {
        return map.getRowCount();
    }

    @Override
    protected int getColCount() {
        return map.getColCount();
    }

    @Override
    protected int getWidth() {
        int nCol = map.getColCount();

        return nCol * measuredCellWidth + (nCol - 1) * cellGap;
    }

    @Override
    protected int getHeight() {
        return map.getRowCount();
    }

    @Override
    protected Rect getOffset() {
        return rect;
    }

    @Override
    protected Rect getBoarder() {
        return rect;
    }

    @Override
    protected int measureCellWidth(int req) {
        return measuredCellWidth = Math.max(cellWidth, req);
    }

    @Override
    protected int getMeasuredCellWidth() {
        return measuredCellWidth;
    }

    @Override
    protected int getCellGap() {
        return cellGap;
    }

    @Override
    protected int getCellWidth() {
        return cellWidth;
    }

    @Override
    protected String getRowText(int row) {
        int nCol = map.getColCount();
        StringBuilder sb = new StringBuilder(getWidth());
        int cell;
        for (int col = 0; col < nCol; col++) {
            cell = map.getCell(row, col);
            if (cell == MineMap.EMPTY) {
                sb.append(repChars(SYM_EMPTY, measuredCellWidth));
            } else if (cell == MineMap.MINE) {
                sb.append(repChars(SYM_EMPTY, measuredCellWidth-1)).append(SYM_MINE);
            } else if (cell == MineMap.FLAG) {
                sb.append(repChars(SYM_EMPTY, measuredCellWidth-1)).append(SYM_FLAG);
            } else if (cell == MineMap.HIDE) {
                sb.append(repChars(SYM_EMPTY, measuredCellWidth-1)).append(SYM_HIDE);
            } else {
                sb.append(String.format("%"+measuredCellWidth+"d", cell));
            }

            if(col != nCol-1) {
                sb.append(repChars(' ', cellGap));
            }
        }
        return sb.toString();
    }
}
