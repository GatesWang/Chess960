package com.productions.wang.gates.chessapp.pieces

abstract class Piece(var col: Char,
                     var row: Int,
                     var color: String){

    abstract var pieceType: String

    abstract fun move()

    override fun toString(): String {
        return "$color$pieceType"
    }
}