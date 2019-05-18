package com.productions.wang.gates.chessapp.pieces

import android.util.Log
import com.productions.wang.gates.chessapp.Board
import com.productions.wang.gates.chessapp.Square

abstract class Piece(var col: Char, var row: Int, var color: String, var board: Board) {

    init {
        board.mBoard.get(col)!!.get(row)!!.piece = this
    }

    abstract var pieceType: String

    abstract fun getMoveToSquares() : ArrayList<Square>
    open fun moveTo(col: Char, row: Int) {
        val moveTosquare: Square = board.mBoard.get(col)?.get(row)!!
        val currentSquare: Square = board.mBoard.get(this.col)?.get(this.row)!!

        if(moveTosquare.piece!=null && moveTosquare.piece!!.color!=color){//capture
            board.halfMove = -1
        }

        //move
        moveTosquare.piece = this
        this.row = row
        this.col = col
        currentSquare.piece = null


    }
    override fun toString(): String {
        return "$color$pieceType"
    }
}