package com.productions.wang.gates.chessapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*


class MainActivity : AppCompatActivity() {
    var playerTurn = "w"
    var selectedMoveTo : ArrayList<Square>? = null
    var selected : Square? = null
    var board : Board? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createBoard()
        setUpButtons()
        setUpPieces()
    }

    fun createBoard(){
        //create the board
        board = Board(this@MainActivity, true)
        print_board_button.setOnClickListener{v ->
            board!!.printBoard()
        }
        resign_button.setOnClickListener{v ->
            var loser = ""
            if(playerTurn.equals("w")){
                loser = "white"
            }
            else{
                loser = "black"
            }
            board!!.displayMenu("${loser} resigns")
        }
        offer_draw_button.setOnClickListener{v ->

        }
        playerTurn = "w"
    }

    fun setUpButtons(){
        //create board buttons
        for(i in 1..8){
            for(j in 1..8){
                var squareButton : ImageButton = ImageButton(applicationContext)
                squareButton.setOnTouchListener( View.OnTouchListener { view, motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            val col = squareButton.tag.toString()[0]
                            val row = (squareButton.tag.toString()[1] - 0).toInt()- '0'.toInt()
                            val square = board!!.mBoard.get(col)!!.get(row)
                            val piece = square!!.piece
                            var moveToSquares = arrayListOf<Square>()
                            if(piece!=null && playerTurn.equals(piece?.color)){
                                moveToSquares = piece.getMoveToSquares()
                            }

                            //moving piece
                            var moved = false
                            if (square.highlighted == true) {
                                movePiece(board!!, square, col, row)
                                moved = true
                            }

                            //unhighlight
                            unhiglight()

                            //selecting piece
                            if(!moved){
                                selectPiece(square, moveToSquares)
                            }
                        }
                    }
                    return@OnTouchListener true
                })

                squareButton.layoutParams = ViewGroup.LayoutParams(125,125)
                squareButton.tag = "${'a'-1 + j}${8-i}"
                if((i+j)%2==0){
                    squareButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.lightColor))
                }
                else{
                    squareButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.darkColor))
                }
                gridLayout.addView(squareButton)
            }
        }
    }

    fun setUpPieces(){
        //set up pieces
        var gridLayout : GridLayout = gridLayout
        for(i in 'a'..'h'){
            for(j in 0..7){
                var imageButton = gridLayout.findViewWithTag<ImageButton>("$i$j")
                imageButton.scaleType= ImageView.ScaleType.CENTER_INSIDE
                board!!.mBoard.get(i)!!.get(j).button = imageButton
                board!!.mBoard.get(i)!!.get(j).update()
            }
        }
    }

    fun unhiglight(){
        var oldSelectedMoveTo = selectedMoveTo
        if(oldSelectedMoveTo!=null){
            for(otherSquare : Square in oldSelectedMoveTo){
                var col = otherSquare.button!!.tag.toString()[0]
                var row = otherSquare.button!!.tag.toString()[1]
                var number = (col-'a'+1) + row.toInt() - '0'.toInt()
                if(number%2==0){
                    otherSquare.button!!.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.lightColor))
                }
                else{
                    otherSquare.button!!.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.darkColor))
                }
                otherSquare.highlighted = false
            }
        }
    }

    fun movePiece(board : Board, square : Square, col: Char, row : Int){
        //returns true if we moved the piece
        selected!!.piece!!.moveTo(col, row)
        selected!!.piece = null
        selected!!.update()
        square.update()

        //change turns
        //loop the board at the start of each turn
        if (playerTurn.equals("w")) {
            playerTurn = "b"
        } else {
            playerTurn = "w"
        }
        board.loopBoard(playerTurn)
    }

    fun selectPiece(square: Square, moveToSquares : ArrayList<Square>){
        if (!(selected != null && selected!!.equals(square))) {
            var selectedNew = ArrayList<Square>()
            var piece = square.piece
            for (otherSquare: Square in moveToSquares) {//moveToSquares may be empty
                if (piece!=null && board!!.canMove(piece, otherSquare.col, otherSquare.row)) {
                    otherSquare.button!!.setBackgroundResource(R.drawable.highlighted)
                    otherSquare.highlighted = true
                    selectedNew.add(otherSquare)
                }
            }
            selected = square
            selectedMoveTo = selectedNew
        } else {//reselect selected
            selected = null
            selectedMoveTo = null
        }
    }

}
