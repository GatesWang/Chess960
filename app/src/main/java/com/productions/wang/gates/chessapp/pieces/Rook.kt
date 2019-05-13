package com.productions.wang.gates.chessapp.pieces

import com.productions.wang.gates.chessapp.Board
import com.productions.wang.gates.chessapp.Square

class Rook(col: Char, row: Int, color: String, board: Board, var moved : Boolean = false, override var pieceType: String = "rook") : Piece(col, row, color, board){

    override fun moveTo(col: Char, row: Int) : Unit {
        if(board.canMove(this, col,row)){
            moved = true
            super.moveTo(col, row)
        }
    }

    override fun getMoveToSquares() : ArrayList<Square>{
        val mBoard  = board.mBoard
        var answer = ArrayList<Square>()
        var i =1
        while (board.isValidSquare(this.col - i, this.row)) {
            var otherSquare = mBoard.get(this.col - i)!!.get(this.row)
            if(otherSquare!!.piece!=null){
                if(otherSquare!!.piece!!.color!=color){
                    answer.add(otherSquare)
                }
                break
            }
            else{
                answer.add(otherSquare)
            }
            i++
        }
        i = 1
        while (board.isValidSquare(this.col + i, this.row )) {
            var otherSquare = mBoard.get(this.col + i)!!.get(this.row)
            if(otherSquare!!.piece!=null){
                if(otherSquare!!.piece!!.color!=color){
                    answer.add(otherSquare)
                }
                break
            }
            else{
                answer.add(otherSquare)
            }
            i++
        }
        i = 1
        while (board.isValidSquare(this.col, this.row + i)) {
            var otherSquare = mBoard.get(this.col)!!.get(this.row + i)
            if(otherSquare!!.piece!=null){
                if(otherSquare!!.piece!!.color!=color){
                    answer.add(otherSquare)
                }
                break
            }
            else{
                answer.add(otherSquare)
            }
            i++
        }
        i = 1
        while (board.isValidSquare(this.col, this.row - i)) {
            var otherSquare = mBoard.get(this.col)!!.get(this.row - i)
            if(otherSquare!!.piece!=null){
                if(otherSquare!!.piece!!.color!=color){
                    answer.add(otherSquare)
                }
                break
            }
            else{
                answer.add(otherSquare)
            }
            i++
        }
        return answer
    }

}