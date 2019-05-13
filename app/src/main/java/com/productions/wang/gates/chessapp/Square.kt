package com.productions.wang.gates.chessapp

import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import com.productions.wang.gates.chessapp.pieces.Piece
import java.security.AccessControlContext
import android.R
import android.graphics.drawable.Drawable



class Square(var col: Char, var row: Int, var context: Context, var highlighted : Boolean = false) {
    var piece: Piece? = null;
    var button : ImageButton? = null

    override fun toString(): String {
        return "$col$row " + piece?.toString()
    }
    fun update() : Unit{
        //updates button according to the square object
        var imgName = ""
        if(piece!=null){
            if(piece!!.color.equals("w")) {
                imgName += "w"
            }
            else {
                imgName += "b"
            }
            imgName+=piece!!.pieceType
            val drawable = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName())
            button!!.setImageResource(drawable);
        }
        else{
            button!!.setImageResource(R.color.transparent);
        }
    }
}

