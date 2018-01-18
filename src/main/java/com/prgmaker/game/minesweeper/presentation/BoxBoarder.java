package com.prgmaker.game.minesweeper.presentation;

import com.prgmaker.game.minesweeper.util.Rect;

public class BoxBoarder extends GridBoarder {
    private Rect boarder;

    public BoxBoarder(GridDisplay display) {
        super(display);
        boarder = Rect.newInstance(1,1,1,1);
    }

    @Override
    protected Rect getBoarder() {
        return boarder;
    }

    @Override
    protected int getCellWidth() {
        return 0;
    }

    @Override
    protected String getRowText(int row) {
        StringBuilder sb = new StringBuilder();
        if(row == 0 || row == getHeight()-1) {
            sb.append(repChars('-', getWidth()));
        } else {
            sb.append('|').append(display.getRowText(row-1)).append('|');
        }
        return sb.toString();
    }
}
