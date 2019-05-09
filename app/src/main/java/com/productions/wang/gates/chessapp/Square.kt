package com.productions.wang.gates.chessapp

import android.util.Log
import com.productions.wang.gates.chessapp.pieces.Piece

class Square(var row: Char,
             var col: Int){
    var piece : Piece? = null;

    override fun toString(): String {
        return "$row$col " + piece?.toString()
    }
}