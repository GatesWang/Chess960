package com.productions.wang.gates.chessapp


import android.os.*
import android.support.v7.app.AppCompatActivity

import android.support.v4.content.ContextCompat
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.productions.wang.gates.chessapp.pieces.LogStream
import uci.UCIProtocol
import java.io.*
import java.util.concurrent.locks.ReentrantReadWriteLock
import android.graphics.Bitmap




class MainActivity : AppCompatActivity() {
    var playerTurn = "w"
    var selectedMoveTo : ArrayList<Square>? = null
    var selected : Square? = null
    var board : Board? = null

    var protocol : UCIProtocol? = null
    val root = Environment.getExternalStorageDirectory()
    var fileInput :  File? = null
    var input : FileInputStream? = null
    var output : LogStream? = null
    var bestMove = ""
    var startSquare : Square? = null
    var endSquare: Square? = null
    var promotion = ""
    var againstAI = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("?",",")

        createBoard()
        setUpButtons()
        setUpPieces()

        fileInput = File(root.getAbsolutePath() + "/input.txt")
        input = FileInputStream(fileInput)
        output = LogStream(System.out)
        protocol = UCIProtocol()

    }

    fun createBoard(){
        //create the board
        board = Board(this@MainActivity, true, againstAI)
        print_board_button.setOnClickListener{v ->
            Log.d(">>>", playerTurn)
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
                            if(piece!=null && playerTurn.equals(piece.color)){
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
        Log.d(">>>", playerTurn)
        //returns true if we moved the piece
        selected!!.piece!!.moveTo(col, row)
        selected!!.piece = null
        selected!!.update()
        square.update()

        //change turns
        //loop the board at the start of each turn
        if (playerTurn.equals("w")) {
            playerTurn = "b"
        }else{
            playerTurn = "w"
        }
        board!!.loopBoard(playerTurn)

        if(playerTurn.equals("b") && againstAI) {
            var thread1 = Runnable {
                Log.d(">>>", "starting thread")
                fileInput!!.writeText("uci\n")
                fileInput!!.appendText("isready\n")
                fileInput!!.appendText("position fen " + board!!.toFENString(playerTurn) + "\n")
                fileInput!!.appendText("go searchmoves movetime 1000\n")
                input = FileInputStream(fileInput)
                protocol!!.mainLoop(input, output, true)
                while (output!!.foundBest == false) {

                }
                bestMove = output!!.bestMove
                startSquare = board!!.mBoard.get(bestMove[0])!!.get(bestMove[1].toInt() - '0'.toInt() - 1)
                endSquare = board!!.mBoard.get(bestMove[2])!!.get(bestMove[3].toInt() - '0'.toInt() - 1)
                if(bestMove.length==5){
                    promotion = ""+bestMove[4]
                }
                //Log.d(">>>", startSquare.toString())
                //Log.d(">>>", endSquare.toString())
                //Log.d(">>>", "promotion $promotion")

                startSquare!!.piece!!.moveTo(endSquare!!.col, endSquare!!.row)
                startSquare!!.piece = null
                input!!.close()

                var thread2  = runOnUiThread {
                    startSquare!!.update()
                    endSquare!!.update()
                }

                if (playerTurn.equals("w")) {
                    playerTurn = "b"
                }else{
                    playerTurn = "w"
                }
                board!!.loopBoard(playerTurn)
            }
            Thread(thread1).start()
        }

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

    fun printInput(){
        var reader =  BufferedReader(FileReader(fileInput))
        for(line in reader.readLines()){
            Log.d(">>>",line)
        }
    }

}
