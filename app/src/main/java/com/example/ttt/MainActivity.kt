package com.example.ttt

import android.os.Bundle
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.ttt.databinding.ActivityMainBinding
import com.example.ttt.models.Board
import com.example.ttt.models.BoardState
import com.example.ttt.models.Cell



class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val vm: MainActivityViewModel by viewModels()
    private var timeSelected : Int = 10
    private var timeCountDown: CountDownTimer? = null
    private var timeProgress = 0
    private var isStart = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm.liveBoard.observe(this, onBoardChange)
        bindButtons()

    }

    private val onBoardChange = Observer { board: Board ->
        updateBoardCells(board)
        updateGameStatus(board.boardState)
    }

    private fun bindButtons() = with(binding) {
        buttonReset.setOnClickListener{
            vm.resetBoard()
        }
        cell0.setOnClickListener{vm.cellClicked(Cell.TOP_LEFT)}
        cell1.setOnClickListener{vm.cellClicked(Cell.TOP_CENTER)}
        cell2.setOnClickListener{vm.cellClicked(Cell.TOP_RIGHT)}
        cell3.setOnClickListener{vm.cellClicked(Cell.CENTER_LEFT)}
        cell4.setOnClickListener{vm.cellClicked(Cell.CENTER_CENTER)}
        cell5.setOnClickListener{vm.cellClicked(Cell.CENTER_RIGHT)}
        cell6.setOnClickListener{vm.cellClicked(Cell.BOTTOM_LEFT)}
        cell7.setOnClickListener{vm.cellClicked(Cell.BOTTOM_CENTER)}
        cell8.setOnClickListener{vm.cellClicked(Cell.BOTTOM_RIGHT)}
    }

 /**   private fun resetTime()
    {
        if (timeCountDown!=null)
        {
            timeCountDown!!.cancel()
            timeProgress=0
            timeCountDown=null
            val buttonReset: Button = findViewById(R.id.button_reset)
            isStart = true
            val progressBar = findViewById<ProgressBar>(R.id.pbTimer)
            progressBar.progress = 0
            val timeLeftTv: TextView = findViewById(R.id.tvTimeLeft)
            timeLeftTv.text = "0"
        }
    } **/
    private fun updateBoardCells(board: Board) {
        binding.cell0.setImageResource(board.getState(Cell.TOP_LEFT).res)
        binding.cell1.setImageResource(board.getState(Cell.TOP_CENTER).res)
        binding.cell2.setImageResource(board.getState(Cell.TOP_RIGHT).res)
        binding.cell3.setImageResource(board.getState(Cell.CENTER_LEFT).res)
        binding.cell4.setImageResource(board.getState(Cell.CENTER_CENTER).res)
        binding.cell5.setImageResource(board.getState(Cell.CENTER_RIGHT).res)
        binding.cell6.setImageResource(board.getState(Cell.BOTTOM_LEFT).res)
        binding.cell7.setImageResource(board.getState(Cell.BOTTOM_CENTER).res)
        binding.cell8.setImageResource(board.getState(Cell.BOTTOM_RIGHT).res)
    }


    private fun updateGameStatus(boardState: BoardState) = when(boardState) {
        BoardState.STARS_WIN -> {
            binding.textGameStatus.visibility = View.VISIBLE
            binding.textGameStatus.setText(R.string.message_stars_win)
        }
        BoardState.CIRCLES_WIN -> {
            binding.textGameStatus.visibility = View.VISIBLE
            binding.textGameStatus.setText(R.string.message_circles_win)
        }
        BoardState.INCOMPLETE -> {
            binding.textGameStatus.visibility = View.GONE
        }
        BoardState.DRAW -> {
            binding.textGameStatus.visibility = View.VISIBLE
            binding.textGameStatus.setText(R.string.message_draw)
        }
    }
}