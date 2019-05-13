package com.productions.wang.gates.chessapp

import android.content.Context
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import com.productions.wang.gates.chessapp.pieces.*;
import kotlin.math.absoluteValue
import android.app.Activity




class Board (val context: Context,
             var setUp : Boolean) {

    val mBoard: HashMap<Char, MutableList<Square>> = HashMap()
    fun printBoard(): Unit {
        for(i in 7 downTo 0){
            var line = ""
            for(j in 'a'..'h'){
                line = line + mBoard.get(j)?.get(i).toString() + " "
            }
            Log.d(">>>", line)
        }
    }
    fun isValidSquare(col: Char, row: Int): Boolean {
        return col>='a' && col<='h' && row>=0 && row<=7
    }

    var wKing : King? = null
    var bKing : King? = null

    fun isChecked(color: String): Boolean {
        var king : King?
        if(color.equals("w")){
            king = wKing
        }
        else{
            king = bKing
        }

        //horizontal and vertical
        var checkingPieces = arrayListOf("rook","queen")
        for(i in king!!.row-1 downTo 0){
            var otherPiece : Piece? = mBoard.get(king!!.col)?.get(i)?.piece
            if(otherPiece!=null){
                if(otherPiece.color.equals(color)){
                    break;
                }
                else{
                    if(checkingPieces.contains(otherPiece.pieceType)){
                        return true
                    }
                    else{
                        break
                    }
                }
            }
        }
        for(i in king!!.row+1..7){
            var otherPiece : Piece? = mBoard.get(king!!.col)?.get(i)?.piece
            if(otherPiece!=null){
                if(otherPiece.color.equals(color)){
                    break;
                }
                else{
                    if(checkingPieces.contains(otherPiece.pieceType)){
                        return true
                    }
                    else{
                        break
                    }
                }
            }
        }
        for(i in king!!.col-1 downTo 'a'){
            var otherPiece : Piece? = mBoard.get(i)?.get(king!!.row)?.piece
            if(otherPiece!=null){
                if(otherPiece.color.equals(color)){
                    break;
                }
                else{
                    if(checkingPieces.contains(otherPiece.pieceType)){
                        return true
                    }
                    else{
                        break
                    }
                }
            }
        }
        for(i in king!!.col+1..'h'){
            var otherPiece : Piece? = mBoard.get(i)?.get(king!!.row)?.piece
            if(otherPiece!=null){
                if(otherPiece.color.equals(color)){
                    break;
                }
                else{
                    if(checkingPieces.contains(otherPiece.pieceType)){
                        return true
                    }
                    else{
                        break
                    }
                }
            }
        }

        //diagonal
        checkingPieces = arrayListOf("bishop","queen")
        var i = 1
        while (king.col - i >= 'a' && king.row - i >= 0) {
            var otherPiece : Piece? = mBoard.get(king.col - i)?.get(king.row - i)?.piece
            if(otherPiece!=null){
                if(otherPiece.color.equals(color)){
                    break;
                }
                else{
                    if(checkingPieces.contains(otherPiece.pieceType)){
                        return true
                    }
                    else{
                        break
                    }
                }
            }
            i++
        }
        i=1
        while (king.col + i <= 'h' && king.row + i <= 7) {
            var otherPiece : Piece? = mBoard.get(king.col + i)?.get(king.row + i)?.piece
            if(otherPiece!=null){
                if(otherPiece.color.equals(color)){
                    break;
                }
                else{
                    if(checkingPieces.contains(otherPiece.pieceType)){
                        return true
                    }
                    else{
                        break
                    }
                }
            }
            i++
        }
        i=1
        while (king.col - i >= 'a' && king.row + i <= 7) {
            var otherPiece : Piece? = mBoard.get(king.col - i)?.get(king.row + i)?.piece
            if(otherPiece!=null){
                if(otherPiece.color.equals(color)){
                    break;
                }
                else{
                    if(checkingPieces.contains(otherPiece.pieceType)){
                        return true
                    }
                    else{
                        break
                    }
                }
            }
            i++
        }
        i=1
        while (king.col + i <= 'h' && king.row - i >= 0){
            var otherPiece : Piece? = mBoard.get(king.col + i)?.get(king.row - i)?.piece
            if(otherPiece!=null){
                if(otherPiece.color.equals(color)){
                    break;
                }
                else{
                    if(checkingPieces.contains(otherPiece.pieceType)){
                        return true
                    }
                    else{
                        break
                    }
                }
            }
            i++
        }

        //knights
        val increments = arrayListOf(-2,-1,1,2)
        for (increment1 in increments){
            var increment2 = 0
            if(increment1.absoluteValue==2){
                increment2 = 1
            }
            else if(increment1.absoluteValue==1){
                increment2 = 2
            }
            if(isValidSquare(king.col+increment1, king.row+increment2)){
                var piece : Piece? = mBoard.get(king.col+increment1)!!.get(king.row+increment2).piece
                if(piece!=null && !piece.color.equals(color) && piece.pieceType.equals("knight")){
                    return true
                }
            }
            if(isValidSquare(king.col+increment1, king.row-increment2)){
                var piece : Piece? = mBoard.get(king.col+increment1)!!.get(king.row-increment2).piece
                if(piece!=null && !piece.color.equals(color) && piece.pieceType.equals("knight")){
                    return true
                }
            }
        }

        //pawns
        for(i in arrayListOf<Int>(-1,1)){
            for(j in arrayListOf<Int>(-1,1)){
                if(isValidSquare(king.col+i, king.row+j)){
                    var piece = mBoard.get(king.col + i)?.get(king.row + j)?.piece
                    if(piece!=null && !piece.color.equals(color) && piece.pieceType.equals("pawn")){
                        return true
                    }
                }
            }
        }

        return false
    }
    fun promote(piece : Piece)  {
        var color = piece.color
        var col = piece.col
        var row = piece.row
        var pieces : ArrayList<String> = arrayListOf<String>(color+"knight",color+"queen", color+"rook", color+"bishop")
        var buttons : ArrayList<ImageButton> = arrayListOf<ImageButton>()
        var pieceType = ""

        var builder : AlertDialog.Builder  = AlertDialog.Builder(context)
        var linearLayout : LinearLayout = LinearLayout(context)
        linearLayout.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        linearLayout.orientation = LinearLayout.HORIZONTAL

        for(piece in pieces){
            var button : ImageButton = ImageButton(context)
            button.layoutParams = ViewGroup.LayoutParams(200,200)
            button.scaleType= ImageView.ScaleType.CENTER_INSIDE
            buttons.add(button)
            button.tag = piece.substring(1)
            val drawable = context.getResources().getIdentifier(piece, "drawable", context.getPackageName())
            button.setImageResource(drawable)
            linearLayout.addView(button)
        }
        builder.setView(linearLayout)
        var dialog = builder.create()

        for(button in buttons){
            button.setOnClickListener{v->
                pieceType = v.getTag().toString()
                if(pieceType.equals("queen")){
                    mBoard.get(col)!!.get(row).piece = Queen(col, row, color, this)
                }
                else if(pieceType.equals("bishop")){
                    mBoard.get(col)!!.get(row).piece = Bishop(col, row, color, this)
                }
                else if(pieceType.equals("rook")){
                    mBoard.get(col)!!.get(row).piece = Rook(col, row, color, this)
                }
                else if(pieceType.equals("knight")){
                    mBoard.get(col)!!.get(row).piece = Knight(col, row, color, this)
                }
                mBoard.get(col)!!.get(row).update()
                dialog.dismiss()
            }
        }
        dialog.setCancelable(false)
        dialog.show()
    }
    fun canMove(piece: Piece, col: Char, row: Int) : Boolean{
        val moveTosquare : Square = mBoard.get(col)?.get(row)!!
        val currentSquare : Square = mBoard.get(piece.col)?.get(piece.row)!!
        val moveToPiece = moveTosquare.piece

        //move
        moveTosquare.piece = piece
        var tempRow = piece.row
        var tempCol = piece.col
        piece.row = row
        piece.col = col
        currentSquare.piece = null

        //check for checks
        if(isChecked(piece.color)){
            Log.d(">>>", "illegal move, king is checked")
            //move back
            piece.row = tempRow
            piece.col = tempCol
            currentSquare.piece = piece
            moveTosquare.piece = moveToPiece
            return false
        }

        //move back
        piece.row = tempRow
        piece.col = tempCol
        currentSquare.piece = piece
        moveTosquare.piece = moveToPiece

        return true
    }
    fun loopBoard(playerTurn : String)  {
        var numberMoves = 0
        var king : King? = null
        if(playerTurn.equals("w")){
            king = wKing
        }
        else{
            king = bKing
        }

        for(i in 'a'..'h') {
            for (j in 0..7) {
                var piece : Piece? = mBoard.get(i)?.get(j)?.piece
                if(piece!=null){
                    if(piece is Pawn){
                        //reset en passant
                        if(piece.color.equals(playerTurn) && piece.enPassantCapturable){
                            piece.enPassantCapturable = false
                        }
                    }
                    if(playerTurn.equals(piece.color)){
                        for(square : Square in piece.getMoveToSquares()){
                            if(canMove(piece, square.col, square.row)){
                                numberMoves++
                            }
                        }
                    }
                }
            }
        }
        if(numberMoves==0){
            if(isChecked(playerTurn)){
                var winner = ""
                if(playerTurn.equals("w")){
                    winner = "black"
                }
                else{
                    winner = "white"
                }
                displayMenu("$winner wins by checkmate")
            }
            else{
                displayMenu("draw by stalemate")
            }
        }
    }
    fun displayMenu(title: String) {
        var builder : AlertDialog.Builder  = AlertDialog.Builder(context)
        builder.setTitle(title)
        var linearLayout = LinearLayout(context)
        linearLayout.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        linearLayout.orientation = LinearLayout.VERTICAL

        var playAgainButton = Button(context)
        playAgainButton.text =  "Play again"
        linearLayout.addView(playAgainButton)
        builder.setView(linearLayout)
        var dialog = builder.create()

        playAgainButton.setOnClickListener(View.OnClickListener { v->
            if (context is MainActivity) {
                context.createBoard()
                context.setUpPieces()
                dialog.dismiss()
            }
        })
        dialog.setCancelable(false)
        dialog.show()
    }
    fun toFENString(): String {
        return ""
    }

    //set up squares
    init{
        for(i in 'a'..'h'){
            mBoard.put(i, ArrayList<Square>(8))
            val col: MutableList<Square>? = mBoard.get(i);
            for(j in 0..7){
                col?.add(Square(i,j, context))
            }
        }
    }

    //set up pieces
    init{
        if(setUp){
            for(j in 1..2) {
                var color: String = if (j == 1) "w" else "b"
                //add the pawns
                if (color.equals("w")) {
                    for (i in 'a'..'h') {
                        val row = 1
                        Pawn(i, row, "w", this)
                    }
                } else {
                    for (i in 'a'..'h') {
                        val row = 6
                        Pawn(i, row, "b", this)
                    }
                }
                //add the knights
                if (color.equals("w")) {
                    val row = 0
                    for (i in arrayListOf<Char>('b', 'g')) {
                        Knight(i, row, "w", this)
                    }
                } else {
                    val row = 7
                    for (i in arrayListOf<Char>('b', 'g')) {
                        Knight(i, row, "b", this)
                    }
                }
                //add bishops
                if (color.equals("w")) {
                    val row = 0
                    for (i in arrayListOf<Char>('c', 'f')) {
                        Bishop(i, row, "w", this)
                    }
                } else {
                    val row = 7
                    for (i in arrayListOf<Char>('c', 'f')) {
                        Bishop(i, row, "b", this)
                    }
                }
                //add rooks
                if (color.equals("w")) {
                    val row = 0
                    for (i in arrayListOf<Char>('a', 'h')) {
                        Rook(i, row, "w", this)
                    }
                } else {
                    val row = 7
                    for (i in arrayListOf<Char>('a', 'h')) {
                        Rook(i, row, "b", this)
                    }
                }
                //add queens
                if (color.equals("w")) {
                    val row = 0
                    for (i in arrayListOf<Char>('d')) {
                        Queen(i, row, "w", this)
                    }
                } else {
                    val row = 7
                    for (i in arrayListOf<Char>('d')) {
                        Queen(i, row, "b", this)
                    }
                }
                //add kings
                if (color.equals("w")) {
                    val row = 0
                    for (i in arrayListOf<Char>('e')) {
                        King(i, row, "w", this)
                    }
                } else {
                    val row = 7
                    for (i in arrayListOf<Char>('e')) {
                        King(i, row, "b", this)
                    }
                }

            }
        }
    }

    //get king locations
    init{
        //find position of kings
        for(i in 'a'..'h') {
            for (j in 0..7) {
                var piece : Piece? = mBoard.get(i)?.get(j)?.piece
                if(piece is King && piece.color.equals("w")){
                    wKing = piece
                }
                else if(piece is King && piece.color.equals("b")){
                    bKing = piece
                }
            }
        }
    }
}