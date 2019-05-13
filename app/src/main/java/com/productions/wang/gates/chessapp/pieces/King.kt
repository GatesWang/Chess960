package com.productions.wang.gates.chessapp.pieces

import com.productions.wang.gates.chessapp.Board
import com.productions.wang.gates.chessapp.Square


class King(col: Char, row: Int, color: String, board: Board, override var pieceType: String = "king") : Piece(col, row, color, board){

    override fun moveTo(col: Char, row: Int) : Unit{
        if(board.canMove(this,col,row)){
            super.moveTo(col, row)
        }
    }
    override fun getMoveToSquares() : ArrayList<Square>{
        val mBoard  = board.mBoard
        val answer = ArrayList<Square>()
        for(i in -1..1){
            for(j in -1..1){
                if(!(i==0 && j==0)){
                    if(board.isValidSquare(col+i,row+j)){
                        var piece = mBoard.get(col+i)!!.get(row+j).piece
                        if(piece==null){
                            answer.add(mBoard.get(col+i)!!.get(row+j))
                        }
                        else if(piece!=null && !piece.color.equals(color)){
                            answer.add(mBoard.get(col+i)!!.get(row+j))
                        }
                    }
                }
            }
        }
        return answer
    }
}

