package com.prgmaker.game.minesweeper;

import com.prgmaker.game.minesweeper.data.MineMap;
import com.prgmaker.game.minesweeper.presentation.BoxBoarder;
import com.prgmaker.game.minesweeper.presentation.GridDisplay;
import com.prgmaker.game.minesweeper.presentation.MineMapDisplay;
import com.prgmaker.game.minesweeper.presentation.RowColNumBoarder;
import com.prgmaker.game.minesweeper.util.Reservoir;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MineSweeper {
    private MineMap map;
    private GridDisplay display;
    private boolean bPickMine;

    public MineSweeper(int nRow, int nCol, int percentMine) {
        map = new MineMap(nRow, nCol, percentMine / 100f, new Reservoir());
        display = new BoxBoarder(new RowColNumBoarder(new BoxBoarder(new RowColNumBoarder(new BoxBoarder(new MineMapDisplay(map))))));
        bPickMine = false;
    }

    public boolean gameOver() {
        return bPickMine || map.isAllVisited();
    }

    public void show() {
        display.show();
    }

    public void showAll() {
        map.forceVisitAll();
    }

    public void turn(int row, int col) {
        if (!map.isTouched(row, col)) {
            if (map.isMine(row, col)) {
                bPickMine = true;
            } else {
                map.reveal(row, col);
            }
        }
    }

    public void mark(int row, int col) {
        map.mark(row, col);
    }

    public void unmark(int row, int col) {
        map.unmark(row, col);
    }

    public boolean win() {
        return !bPickMine && map.isAllVisited();
    }

    public static String[] commandParse(Scanner sc, Pattern cmdPattern, String prompt, String err) {
        String line;
        String[] res = null;
        Matcher matcher;
        do {
            System.out.print(prompt);
            matcher = cmdPattern.matcher(sc.nextLine());
            if (matcher.find()) {
                int nGroup = matcher.groupCount();
                res = new String[nGroup];
                for (int i = 0; i < nGroup; i++) {
                    res[i] = matcher.group(i + 1);
                }
            }
        } while (res == null);
        return res;
    }

    public static void run() {
        String[] cl;
        int row = 0, col = 0, percentMine = 0;
        Scanner sc = new Scanner(System.in);
        do {
            cl = commandParse(sc, Pattern.compile("\\s*(\\d+)\\s+(\\d+)\\s+(\\d+)"),
                    "Type <#ROW> <#COL> <#%MINE>: ", "Type again!");
            row = Integer.parseInt(cl[0]);
            col = Integer.parseInt(cl[1]);
            percentMine = Integer.parseInt(cl[2]);
        } while (row <= 0 || col <= 0 || percentMine <= 0 || percentMine >= 100);
        MineSweeper ms = new MineSweeper(row, col, percentMine);
        Pattern cmdPattern = Pattern.compile("\\s*([p m u])\\s+(\\d+)\\s+(\\d+)");
        ms.show();
        while (!ms.gameOver()) {
            cl = commandParse(sc, cmdPattern,
                    "<p | m | u> <row> <col>: ", "Type again!");
            row = Integer.parseInt(cl[1]);
            col = Integer.parseInt(cl[2]);
            switch (cl[0]) {
                case "p":
                    ms.turn(row,col);
                    break;
                case "m":
                    ms.mark(row, col);
                    break;
                case "u":
                    ms.unmark(row, col);
                    break;
            }
        }

        ms.showAll();
        System.out.println(ms.win() ? "WIN!" : "LOSE!");
    }

    public static void main(String[] args) {
        run();
    }
}
