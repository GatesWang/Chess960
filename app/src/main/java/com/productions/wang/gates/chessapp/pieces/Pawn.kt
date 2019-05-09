package com.productions.wang.gates.chessapp.pieces

class Pawn(col: Char,
             row: Int,
             color: String,
             override var pieceType: String = "pawn") : Piece(col, row, color){

    override fun move() {
        // Code to move the piece
        println("moving pawn")
    }


}