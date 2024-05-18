package com.pgmd.primertp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.google.gson.Gson
import com.pgmd.primertp.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding
    val tipo: Array<Tipo> = Tipo.values()
    var tipoSelected: Tipo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipo)
        binding.spinTipo.adapter = adapter

        val thirdVM = ViewModelProvider(this).get<Validaciones>()

        binding.spinTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tipoSelected = tipo[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                tipoSelected = null
            }

        }

        binding.tfMail.addTextChangedListener { email ->
            thirdVM.validEmail(email.toString())
        }
        binding.tfUsuario.addTextChangedListener { usuario ->
            thirdVM.validateUser(usuario.toString())
        }
        binding.tfContraseA.addTextChangedListener { contrasena ->
            thirdVM.validatePass(contrasena.toString())
        }
        binding.tfId.addTextChangedListener { id ->
            thirdVM.validateId(id.toString().toInt())
        }

        thirdVM.viewState.observe(this) {state ->
            when (state) {
                is ThirdStates.SuccesEmail -> {
                    binding.layoutTfMail.error = null
                }
                is ThirdStates.ErrorMail -> {
                    binding.layoutTfMail.error = "Formato de mail incorrecto"
                }

                is ThirdStates.SuccesPass -> {
                    binding.layoutTfContraseA.error = null
                }
                is ThirdStates.ErrorPass -> {
                    binding.layoutTfContraseA.error = "${state.pass.length}/3"
                }

                is ThirdStates.SuccesUser -> {
                    binding.layoutTfUsuario.error = null
                }
                is ThirdStates.ErrorUser -> {
                    binding.layoutTfUsuario.error = "Campo vacio"
                }

                is ThirdStates.SuccesId -> {
                    binding.layoutTfId.error = null
                }
                is ThirdStates.ErrorId -> {
                    binding.layoutTfId.error = "${state.id.length}/10"
                }

                is ThirdStates.SuccesButton ->{
                    binding.btnRegistro.isEnabled = true
                }
                is ThirdStates.ErrorButton ->{
                    binding.btnRegistro.isEnabled = false
                }
            }
        }

        binding.btnRegistro.setOnClickListener {

            val mail = binding.tfMail.text.toString()
            val usuario = binding.tfUsuario.text.toString()
            val contrasena = binding.tfContraseA.text.toString()
            val id = binding.tfId.text.toString()
            val tipo = binding.spinTipo.selectedItem as Tipo

            val preferencias = getSharedPreferences(LoginActivity.CREDENTIALS, MODE_PRIVATE)
            val user = Usuario(mail, usuario, id.toInt(), contrasena, tipo)

            val gson = Gson()
            val personaJson = gson.toJson(user)

            val editor = preferencias.edit()
            editor.putString("usuario", personaJson)
            editor.apply()

            println("El usuairo creado es: "+preferencias.getString("usuario", ""))

            goHome()
        }
    }

    private fun goHome(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}