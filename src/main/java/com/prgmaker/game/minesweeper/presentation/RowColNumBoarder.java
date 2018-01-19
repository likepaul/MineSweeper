package com.prgmaker.game.minesweeper.presentation;

import com.prgmaker.game.minesweeper.util.Rect;

public class RowColNumBoarder extends GridBoarder {
    private Rect boarder;
    private int leftPartCellWidth, leftPartGap, upperPartCellWidth;

    public RowColNumBoarder(GridDisplay display) {
        super(display);
        int nRow = getRowCount();
        int nCol = getColCount();
        leftPartCellWidth = nRow - 1 == 0 ? 1 : (int) (Math.log(nRow - 1) / Math.log(10)) + 1;
        upperPartCellWidth = nCol - 1 == 0 ? 1 : (int) (Math.log(nCol - 1) / Math.log(10)) + 1;
        leftPartGap = 1;
        boarder = Rect.newInstance(leftPartCellWidth + leftPartGap, 1, 0, 0);
        measureCellWidth(upperPartCellWidth);
    }

    @Override
    protected Rect getBoarder() {
        return boarder;
    }

    @Override
    protected int getCellWidth() {
        return upperPartCellWidth;
    }

    @Override
    protected String getRowText(int row) {
        StringBuilder sb = new StringBuilder();
        Rect offset = getOffset();
        if(row == 0) {
            sb.append(repChars(' ', offset.left));
            int nCol = getColCount();
            int measuredCellWidth = getMeasuredCellWidth();
            int cellGap = getCellGap();
            for(int col = 0; col < nCol; col++) {
                sb.append(String.format("%0" + measuredCellWidth + "d", col));
                if(col != nCol-1) {
                    sb.append(repChars(' ', cellGap));
                }
            }
            // you have the responsibility of right padding, if this row is within your boarder
            sb.append(repChars(' ', getWidth()-sb.length()));
        } else if(row < offset.top || row >= offset.top + getRowCount()) {
            sb.append(repChars(' ', boarder.left)).append(display.getRowText(row-boarder.top));
        } else {
            sb.append(String.format("%0" + leftPartCellWidth + "d", row - offset.top))
                    .append(repChars(' ', leftPartGap)).append(display.getRowText(row-boarder.top));
        }

        return sb.toString();
    }
}
