package com.productions.wang.gates.chessapp.pieces

class Rook(col: Char,
           row: Int,
           color: String,
           override var pieceType: String = "rook") : Piece(col, row, color){

    override fun move() {
        // Code to move the piece
        println("moving rook")
    }
}