package com.example.ttt

import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ttt.models.Board
import com.example.ttt.models.BoardState
import com.example.ttt.models.Cell
import com.example.ttt.models.CellState

class MainActivityViewModel :ViewModel(){
    var board = Board()
    val liveBoard = MutableLiveData<Board> (board)


    /** timer **/
    //val COUNTDOWN_TIMER = 15000L
    //val ONE_SECOND = 1000L
    //val DONE = 0L
    //lateinit var timer: CountDownTimer
    //val currentTime = MutableLiveData<Long>()
    /** init {
        timer = object:CountDownTimer(COUNTDOWN_TIMER,ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                currentTime.value = millisUntilFinished / ONE_SECOND
            }

            override fun onFinish() {
                resetBoard()
                updateBoard()

                currentTime.value = DONE
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    } **/

    /** @param **/
    fun cellClicked(cell: Cell) {
        board.setCell(cell, CellState.Star)
        updateBoard()

        if(board.boardState == BoardState.INCOMPLETE){
            aiTurn()
        }
    }
    /** **/
    fun resetBoard() {
        board.resetBoard()
        updateBoard()
    }

    /** live update **/
    private fun updateBoard(){
        liveBoard.value = board
    }

    /** bot player  **/
    private fun aiTurn() {
        val aiWin = board.findNextWinningMove(CellState.Circle)
        val playerWin = board.findNextWinningMove(CellState.Star)
        when{
            aiWin != null -> board.setCell(aiWin, CellState.Circle)
            board.setCell(Cell.CENTER_CENTER, CellState.Circle) -> Unit
            else -> do {
                val cell = Cell.values().random()
                val placedSuccessfully = board.setCell(cell, CellState.Circle)
            } while(!placedSuccessfully)
        }
        /** old bot algorithm
        do{
            val cell = Cell.values().random()
            val placedSuccessfully = board.setCell(cell, CellState.Circle)
        } while(!placedSuccessfully)
         **/
        updateBoard()
    }
}