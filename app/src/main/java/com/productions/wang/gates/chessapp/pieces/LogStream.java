package com.productions.wang.gates.chessapp.pieces;

import android.util.Log;

import java.io.PrintStream;

public class LogStream extends PrintStream {
    public String bestMove = "";
    public boolean foundBest = false;
    public static void main(String[] argv) throws Exception {
        System.setOut(new LogStream(System.out));
    }

    public LogStream(PrintStream out1) {
        super(out1);
    }

    public void write(byte buf[], int off, int len) {
        try {
            super.write(buf, off, len);
            String temp = new String(buf);
            if(temp.length()> 10){
                if(temp.contains("bestmove")){
                    bestMove = temp;
                }
            }
        } catch (Exception e) {
        }
    }

    public void flush() {
        super.flush();
    }

    @Override
    public PrintStream printf(String format, Object... args) {
        foundBest = false;
        if(format.length()> 10){
            if(format.contains("bestmove")){
                for(Object arg : args){
                    if(arg.toString().length()<=5){
                        bestMove = arg.toString();
                        foundBest = true;
                        break;
                    }
                }
            }
        }
        return super.printf(format, args);
    }
}