package com.productions.wang.gates.chessapp.pieces


class King(col: Char,
           row: Int,
           color: String,
           override var pieceType: String = "king") : Piece(col, row, color){

    override fun move() {
        // Code to move the piece
        println("moving king")
    }
}

