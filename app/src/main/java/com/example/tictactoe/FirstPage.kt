package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

var firstPlayer:String = ""

class FirstPage : AppCompatActivity() {
    private lateinit var playerX:ImageView
    private lateinit var playerO:ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_first_page)

        playerX = findViewById(R.id.player_x)
        playerO = findViewById(R.id.player_o)

        playerX.setOnClickListener {
            firstPlayer = "x"
            startActivity(Intent(this,MainActivity::class.java))
        }

        playerO.setOnClickListener {
            firstPlayer = "o"
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
}