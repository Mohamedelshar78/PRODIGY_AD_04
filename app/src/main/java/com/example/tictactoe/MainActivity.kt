package com.example.tictactoe

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

var remainingTimeInMillis = 10 * 1000 // Initial time: 5 minutes in milliseconds

class MainActivity : AppCompatActivity() {

    private var player = firstPlayer // Start with player X
    private val buttons = mutableListOf<Button>()
    private val board = Array(3) { arrayOfNulls<String>(3) } // To track board state
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var rePlay:Button

    private lateinit var timeTextView: TextView
    private lateinit var currentPlayerTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        rePlay = findViewById(R.id.replay)
        timeTextView = findViewById(R.id.timer)
        currentPlayerTextView = findViewById(R.id.curentPlayer)

        currentPlayerTextView.text = "Player ${player} ’s Turn"

        // Initialize buttons
        for (i in 1..9) {
            val resID = resources.getIdentifier("button$i", "id", packageName)
            val button: Button = findViewById(resID)
            buttons.add(button)
            button.setOnClickListener { onButtonClick(button) }
        }

        rePlay.setOnClickListener {
            resetBoard()
        }

        startCountdownTimer()

    }
    private fun onButtonClick(button: Button) {

        val index = buttons.indexOf(button)
        val row = index / 3
        val col = index % 3

        // Check if the button is already clicked
        if (button.text.isNotEmpty()) {
            return
        }

        // Update button text
        button.text = player
        board[row][col] = player

        // Check for win
        if (checkWin()) {
            Toast.makeText(this, "$player wins!", Toast.LENGTH_LONG).show()
            countDownTimer.cancel()
            return
        }

        // Switch player
        switchPlayer()

    }

    private fun switchPlayer() {
        player = if (player == "x") "o" else "x"
        currentPlayerTextView.text = "Player ${player} ’s Turn"
        remainingTimeInMillis = 10 * 1000
        timeTextView.text = "00:10"
        countDownTimer.cancel()
        startCountdownTimer()
    }

    private fun checkWin(): Boolean {
        // Check rows, columns and diagonals
        for (i in 0..2) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true

        return false
    }

    private fun resetBoard() {
        for (button in buttons) {
            button.text = ""
        }
        for (i in board.indices) {
            board[i].fill(null)
        }
        player = firstPlayer
    }

    private fun startCountdownTimer() {
        countDownTimer = object : CountDownTimer(remainingTimeInMillis.toLong(), 100) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished.toInt()
                updateTimerDisplay()
            }

            override fun onFinish() {
                switchPlayer()
            }

        }.start()
    }

    private fun updateTimerDisplay() {
        val minutes = remainingTimeInMillis / 60000
        val seconds = (remainingTimeInMillis % 60000) / 1000
        val formattedTime = String.format("%02d:%02d", minutes, seconds)
        timeTextView.text = formattedTime
    }


}
