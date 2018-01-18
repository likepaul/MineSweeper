package com.prgmaker.game.minesweeper.data;

import com.prgmaker.game.minesweeper.util.ISample;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

public class MineMap extends Observable{
    private final int nRow, nCol, nMine, nCell;
    private int nVis;
    public static final int EMPTY = 0;
    public static final int MINE = 9;
    public static final int FLAG = 10;
    public static final int HIDE = 11;

    private static final int NOVIS = 0;
    private static final int VIS = 1;
    private static final int MARK = 2;

    private int[][] map, mark;
    private ISample sample;

    public MineMap(int nRow, int nCol, float mineRate, ISample sample) {
        this.nRow = nRow;
        this.nCol = nCol;
        nCell = nRow*nCol;
        nMine = Math.max(1, (int)(nCell * mineRate));
        this.sample = sample;
        map = new int[nRow][nCol];
        mark = new int[nRow][nCol];
        buildMap();
    }

    private void buildMap() {
        int[] res = sample.gen(nCell, nMine);
        int row, col;
        for(int i = 0; i < nMine; i++) {
            row = res[i]/nCol;
            col = res[i]%nCol;
            map[row][col] = MINE;
            for(int r = Math.max(0, row-1); r <= row+1 && r < nRow; r++) {
                for(int c = Math.max(0, col-1); c <= col+1 && c < nCol; c++) {
                    if(map[r][c] != MINE) {
                        map[r][c]++;
                    }
                }
            }
        }
    }

    public void reveal(int row, int col) {
        Queue<Pair> q = new LinkedList<>();
        if(mark[row][col] == NOVIS && map[row][col] != MINE) {
            q.offer(new Pair(row, col));
            mark[row][col] = VIS;
            nVis++;
            setChanged();
        }

        Pair p;
        while (!q.isEmpty()) {
            p = q.poll();
            row = p.row;
            col = p.col;

            if (map[row][col] == EMPTY) {
                for(int r = Math.max(row-1, 0); r <= row+1 && r < nRow; r++) {
                    for(int c = Math.max(col-1, 0); c <= col+1 && c < nCol; c++) {
                        if(mark[r][c] == NOVIS && map[r][c] != MINE) {
                            q.offer(new Pair(r, c));
                            mark[r][c] = VIS;
                            nVis++;
                            setChanged();
                        }
                    }
                }
            }
        }

        notifyObservers();
    }

    public int getRowCount() {
        return nRow;
    }

    public int getColCount() {
        return nCol;
    }

    public boolean isAllVisited() {
        return nCell - nMine - nVis == 0;
    }

    public boolean isTouched(int row, int col) {
        return mark[row][col] != NOVIS;
    }

    public boolean isMine(int row, int col) {
        return map[row][col] == MINE;
    }

    public void forceVisitAll() {
        for(int r = 0; r < nRow; r++) {
            for(int c= 0; c < nCol; c++) {
                if(mark[r][c] != VIS) {
                    mark[r][c] = VIS;
                    setChanged();
                }
            }
        }

        notifyObservers();
    }

    public int getCell(int row, int col) {
        if (mark[row][col] == NOVIS) {
            return HIDE;
        } else if(mark[row][col] == MARK) {
            return FLAG;
        } else {
            return map[row][col];
        }
    }

    public void mark(int row, int col) {
        if(mark[row][col] == NOVIS) {
            mark[row][col] = MARK;
            setChanged();
            notifyObservers();
        }
    }

    public void unmark(int row, int col) {
        if (mark[row][col] == MARK) {
            mark[row][col] = NOVIS;
            setChanged();
            notifyObservers();
        }
    }

    private class Pair {
        int row, col;

        public Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
