package com.productions.wang.gates.chessapp.pieces

class Bishop(col: Char,
           row: Int,
           color: String,
           override var pieceType: String = "bishop") : Piece(col, row, color){

    override fun move() {
        // Code to move the piece
        println("moving bishop")
    }


}