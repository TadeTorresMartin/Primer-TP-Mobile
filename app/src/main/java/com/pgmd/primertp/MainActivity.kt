package com.pgmd.primertp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.pgmd.primertp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.tvUser.text = intent.getStringExtra("usuario")

        binding.btnCerrar.setOnClickListener {
            cerrarSesion()
        }
    }

    private fun cerrarSesion(){
        val preferencias = getSharedPreferences(LoginActivity.CREDENTIALS, MODE_PRIVATE)
        preferencias.edit().putBoolean("logued", false).apply()

        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}