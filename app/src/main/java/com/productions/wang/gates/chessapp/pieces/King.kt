package com.productions.wang.gates.chessapp.pieces

import android.util.Log
import com.productions.wang.gates.chessapp.Board
import com.productions.wang.gates.chessapp.Square

class King(col: Char, row: Int, color: String, board: Board, var moved : Boolean = false, override var pieceType: String = "king") : Piece(col, row, color, board){

    override fun moveTo(col: Char, row: Int) : Unit{
        if(board.canMove(this,col,row)){
            moved = true
            //check for castle
            if(col-this.col == -2){//queen side
                var square = board.mBoard.get(this.col-4)!!.get(this.row)
                var piece : Piece? = square.piece
                if(piece is Rook){
                    piece.moveTo(col+1,row)
                    square.update()
                    board.mBoard.get(col+1)!!.get(row).update()
                }
            }
            else if(col-this.col==2){//king side
                var square = board.mBoard.get(this.col+3)!!.get(this.row)
                var piece : Piece? = square.piece
                if(piece is Rook){
                    piece.moveTo(col-1,row)
                    square.update()
                    board.mBoard.get(col-1)!!.get(row).update()
                }
            }
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

        //castling
        if(canCastleQueen()){
            answer.add(board.mBoard.get(this.col-2)!!.get(this.row))
        }

        if(canCastleKing()){
            answer.add(board.mBoard.get(this.col+2)!!.get(this.row))
        }

        return answer
    }

    fun canCastleQueen() : Boolean{
        if(moved){
            return false
        }
        val mBoard  = board.mBoard
        var piece : Piece? = mBoard.get(this.col-4)!!.get(this.row).piece
        var canMove = !board.isChecked(color) && piece is Rook && piece.moved==false
        if(canMove){
            for(i in 1..4){
                var square : Square =  mBoard.get(this.col-i)!!.get(this.row)
                if(i!=4 && square.piece != null){//if it is not empty or enemy territory
                    canMove = false
                    break
                }
                if(!board.canMove(this,this.col-i, this.row)){
                    canMove = false
                    break
                }
            }
        }
        return canMove
    }
    fun canCastleKing() : Boolean{
        if(moved){
            return false
        }
        val mBoard  = board.mBoard
        var piece = mBoard.get(this.col+3)!!.get(this.row).piece
        var canMove = !board.isChecked(color) && piece is Rook && piece.moved==false
        if(piece is Rook && piece.moved==false){
            for(i in 1..3){
                var square : Square =  mBoard.get(this.col+i)!!.get(this.row)
                if(i!=3 && square.piece != null){//if it is not empty or enemy territory
                    canMove = false
                    break
                }
                if(!board.canMove(this,this.col-i, this.row)){
                    canMove = false
                    break
                }
            }
        }
        return canMove
    }
}

