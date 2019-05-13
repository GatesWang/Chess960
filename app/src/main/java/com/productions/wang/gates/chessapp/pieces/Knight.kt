package com.productions.wang.gates.chessapp.pieces

import com.productions.wang.gates.chessapp.Board
import com.productions.wang.gates.chessapp.Square
import kotlin.math.absoluteValue

class Knight(col: Char, row: Int, color: String, board: Board, override var pieceType: String = "knight") : Piece(col, row, color, board){

    override fun moveTo(col: Char, row: Int) : Unit{
        if(board.canMove(this,col,row)){
            super.moveTo(col, row)
        }
    }
    override fun getMoveToSquares() : ArrayList<Square>{
        val mBoard  = board.mBoard
        val answer = ArrayList<Square>()
        val increments = arrayListOf(-2,-1,1,2)
        for (increment1 in increments){
            var increment2 = 0
            if(increment1.absoluteValue==2){
                increment2 = 1
            }
            else if(increment1.absoluteValue==1){
                increment2 = 2
            }
            if(board.isValidSquare(this.col+increment1, this.row+increment2)){
                var piece : Piece? = mBoard.get(this.col+increment1)!!.get(this.row+increment2).piece
                if(piece==null){
                    answer.add(mBoard.get(this.col+increment1)!!.get(this.row+increment2))
                }
                else if(piece!=null && piece.color!=color){
                    answer.add(mBoard.get(this.col+increment1)!!.get(this.row+increment2))
                }
            }
            if(board.isValidSquare(this.col+increment1, this.row-increment2)){
                var piece : Piece? = mBoard.get(this.col+increment1)!!.get(this.row-increment2).piece
                if(piece==null){
                    answer.add(mBoard.get(this.col+increment1)!!.get(this.row-increment2))
                }
                else if(piece!=null && piece.color!=color){
                    answer.add(mBoard.get(this.col+increment1)!!.get(this.row-increment2))
                }
            }
        }
        return answer
    }

}