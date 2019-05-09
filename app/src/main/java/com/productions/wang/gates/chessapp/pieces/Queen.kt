package com.productions.wang.gates.chessapp.pieces

class Queen(col: Char,
           row: Int,
           color: String,
           override var pieceType: String = "queen") : Piece(col, row, color){

    override fun move() {
        // Code to move the piece
        println("moving queen")
    }


}