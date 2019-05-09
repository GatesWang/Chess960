package com.productions.wang.gates.chessapp

import android.util.Log
import com.productions.wang.gates.chessapp.pieces.*;


class Board (){
    val board: HashMap<Char, MutableList<Square>> = HashMap()

    init{
        for(i in 'a'..'h'){
            board.put(i, ArrayList<Square>(8))
            val col: MutableList<Square>? = board.get(i);
            for(j in 0..7){
                col?.add(Square(i,j+1))
            }
        }
    }

    init{
        for(i in 1..2) {
            var color: String = if (i == 1) "w" else "b"
            //add the pawns
            if (color.equals("w")) {
                for (i in 'a'..'h') {
                    val row = 1
                    board.get(i)?.get(row)?.piece = Pawn(i, row + 1, "w")
                }
            } else {
                for (i in 'a'..'h') {
                    val row = 6
                    board.get(i)?.get(row)?.piece = Pawn(i, row + 1, "b")
                }
            }
            //add the knights
            if (color.equals("w")) {
                val row = 0
                for (i in arrayListOf<Char>('b', 'g')) {
                    board.get(i)?.get(row)?.piece = Knight(i, row+1, "w")
                }
            } else {
                val row = 7
                for (i in arrayListOf<Char>('b', 'g')) {
                    board.get(i)?.get(row)?.piece = Knight(i, row+1, "b")
                }
            }
            //add bishops
            if (color.equals("w")) {
                val row = 0
                for (i in arrayListOf<Char>('c', 'f')) {
                    board.get(i)?.get(row)?.piece = Bishop(i, row+1, "w")
                }
            } else {
                val row = 7
                for (i in arrayListOf<Char>('c', 'f')) {
                    board.get(i)?.get(row)?.piece = Bishop(i, row+1, "b")
                }
            }
            //add rooks
            if (color.equals("w")) {
                val row = 0
                for (i in arrayListOf<Char>('a', 'h')) {
                    board.get(i)?.get(row)?.piece = Rook(i, row+1, "w")
                }
            } else {
                val row = 7
                for (i in arrayListOf<Char>('a', 'h')) {
                    board.get(i)?.get(row)?.piece = Rook(i, row+1, "b")
                }
            }
            //add queens
            if (color.equals("w")) {
                val row = 0
                for (i in arrayListOf<Char>('d')) {
                    board.get(i)?.get(row)?.piece = Queen(i, row+1, "w")
                }
            } else {
                val row = 7
                for (i in arrayListOf<Char>('d')) {
                    board.get(i)?.get(row)?.piece = Queen(i, row+1, "b")
                }
            }
            //add kings
            if (color.equals("w")) {
                val row = 0
                for (i in arrayListOf<Char>('e')) {
                    board.get(i)?.get(row)?.piece = King(i, row+1, "w")
                }
            } else {
                val row = 7
                for (i in arrayListOf<Char>('e')) {
                    board.get(i)?.get(row)?.piece = King(i, row+1, "b")
                }
            }

        }
        //printBoard()
    }

    fun printBoard(): Unit{
        for(i in 7 downTo 0){
            var line = ""
            for(j in 'a'..'h'){
                line = line + board.get(j)?.get(i).toString() + " "
            }
            Log.d(">>>", line)
        }
    }
}