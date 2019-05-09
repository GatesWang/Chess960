package com.productions.wang.gates.chessapp.pieces

class Knight(col: Char,
           row: Int,
           color: String,
           override var pieceType: String = "knight") : Piece(col, row, color){

    override fun move() {
        // Code to move the piece
        println("moving knight")
    }


}