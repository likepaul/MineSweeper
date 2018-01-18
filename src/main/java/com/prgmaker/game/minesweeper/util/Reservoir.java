package com.prgmaker.game.minesweeper.util;

public class Reservoir implements ISample {
    public int[] gen(int n, int k) {
        if(k > n) {
            k = n;
        }
        int[] res = new int[k];
        int i=0, pos;
        for(; i < k; i++) {
            res[i] = i;
        }

        for(; i < n; i++) {
            pos = (int)(Math.random() * (i+1));
            if(pos < k) {
                res[pos] = i;
            }
        }

        return res;
    }
}
