package com.pgmd.primertp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.google.gson.Gson
import com.pgmd.primertp.databinding.ActivityLoginBinding
import com.pgmd.primertp.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val preferencias = getSharedPreferences(CREDENTIALS, MODE_PRIVATE)

        if (!(preferencias.getBoolean("logued", false))) {

            val thirdVn = ViewModelProvider(this).get<ValidacionesLog>()

            binding.tfId.addTextChangedListener { id ->
                thirdVn.validarId(id.toString().toInt())
            }
            binding.tfContraseA.addTextChangedListener{ pass ->
                thirdVn.validarPass(pass.toString())
            }

            thirdVn.viewState.observe(this){state ->
                when(state){
                    is verif.ErrorButtom -> {
                        binding.btnIngresar.isEnabled = false
                    }
                    is verif.ErrorId -> {
                        binding.layTfId.error = "${state.id.toString().length}/10"
                    }
                    is verif.ErrorPass -> {
                        binding.layTfContraseA.error = "${state.pass.length}/10"
                    }
                    is verif.SuccesButtom -> {
                        binding.btnIngresar.isEnabled = true
                    }
                    is verif.SuccesId -> {
                        binding.layTfId.error = null
                    }
                    is verif.SuccesPass -> {
                        binding.layTfContraseA.error = null
                    }
                }

            }

            binding.btnRegistro.setOnClickListener {
                goRegistro()
            }

            binding.btnIngresar.setOnClickListener {
                val id: Int = binding.tfId.text.toString().toInt()
                val pass: String = binding.tfContraseA.text.toString()

                if (validData(id, pass)) {
                    val editor = preferencias.edit()
                    editor.putBoolean("logued", true)
                    editor.apply()

                    goHome()
                }
            }
        }else {
            goHome()
        }
    }

    private fun validData(id: Int, pass: String): Boolean {
        val preferencias = getSharedPreferences(CREDENTIALS, MODE_PRIVATE)
        val personaJson = preferencias.getString("usuario", "")

        val gson = Gson()
        val person = gson.fromJson(personaJson, Usuario::class.java)

        if (id == person.id && pass == person.password){
            return true
        }else{
            return false
        }
    }

    private fun goHome() {

        val preferencias = getSharedPreferences(CREDENTIALS, MODE_PRIVATE)
        val personaJson = preferencias.getString("usuario", "")

        val gson = Gson()
        val person = gson.fromJson(personaJson, Usuario::class.java)

        intent = Intent(this, MainActivity::class.java)
        intent.putExtra("usuario", person.nombre)
        startActivity(intent)
    }

    private fun goRegistro(){
        intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }

    companion object{
        const val CREDENTIALS = "Credenciales"
    }
}
