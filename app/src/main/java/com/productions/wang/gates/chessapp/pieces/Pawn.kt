package com.productions.wang.gates.chessapp.pieces

import android.app.AlertDialog
import android.util.Log
import com.productions.wang.gates.chessapp.Board
import com.productions.wang.gates.chessapp.MainActivity
import com.productions.wang.gates.chessapp.Square

class Pawn(col: Char, row: Int, color: String, board: Board, override var pieceType: String = "pawn", var firstMove : Boolean = true, var enPassantCapturable: Boolean = false) : Piece(col, row, color, board){

    override fun moveTo(col: Char, row: Int) : Unit{
        var mBoard  = board.mBoard
        if(board.canMove(this, col,row)){
            //reset halfmoves
            board.halfMove = -1
            //en passant
            var increment = 0
            if(color.equals("w")){
                increment = 1
            }
            else if(color.equals("b")){
                increment = -1
            }
            if(row == this.row + 2*increment){
                enPassantCapturable = true
            }

            //promotion
            if(color.equals("w") && row.equals(7)){
                board.promote(this)
            }
            if(color.equals("b") && row.equals(0)){
                board.promote(this)
            }

            var piece : Piece? = mBoard.get(col)!!.get(row-increment)!!.piece
            //en passant capturing
            if(piece is Pawn && piece.enPassantCapturable == true){
                mBoard.get(col)!!.get(row-increment).piece = null
                var thread2  = (board.context as MainActivity).runOnUiThread {
                    mBoard.get(col)!!.get(row-increment).update()
                }
            }

            super.moveTo(col, row)
            if(firstMove){
                firstMove = false
            }
        }
    }
    override fun getMoveToSquares() : ArrayList<Square>{
        val mBoard  = board.mBoard
        val answer = ArrayList<Square>()
        var increment = 0
        if(color.equals("w")){
            increment = 1
        }
        else if(color.equals("b")){
            increment = -1
        }

        //first move
        if(firstMove){
            if(mBoard.get(col)!!.get(row+increment)!!.piece==null&& mBoard.get(col)!!.get(row+2*increment)!!.piece==null){
                answer.add(mBoard.get(col)!!.get(row+2*increment))
            }
        }

        //en passant
        if(board.isValidSquare(col+1, row)){
            var piece : Piece? = mBoard.get(col+1)!!.get(row)!!.piece
            if(piece!=null && !piece.color.equals(color) && piece is Pawn && piece.enPassantCapturable==true){
                answer.add(mBoard.get(col+1)!!.get(row+increment))
            }
        }
        if(board.isValidSquare(col-1, row)){
            var piece : Piece? = mBoard.get(col-1)!!.get(row)!!.piece
            if(piece!=null && !piece.color.equals(color) && piece is Pawn && piece.enPassantCapturable==true){
                answer.add(mBoard.get(col-1)!!.get(row+increment))
            }
        }

        if(board.isValidSquare(col, row+increment)){
            if(mBoard.get(col)!!.get(row+increment)!!.piece==null){
                answer.add(mBoard.get(col)!!.get(row+increment))
            }
        }
        if(board.isValidSquare(col+1, row+increment)){
            var piece : Piece? = mBoard.get(col+1)!!.get(row+increment)!!.piece
            if(piece!=null && !piece.color.equals(color)){
                answer.add(mBoard.get(col+1)!!.get(row+increment))
            }
        }
        if(board.isValidSquare(col-1, row+increment)){
            var piece : Piece? = mBoard.get(col-1)!!.get(row+increment)!!.piece
            if(piece!=null && !piece.color.equals(color)){
                answer.add(mBoard.get(col-1)!!.get(row+increment))
            }
        }
        return answer
    }
}