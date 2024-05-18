package com.pgmd.primertp

import androidx.core.util.PatternsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Validaciones() : ViewModel() {

    var viewState : MutableLiveData<ThirdStates> = MutableLiveData()

    var mail: String = ""
    var usuario: String = ""
    var password: String = ""
    var id: Int = 0

    private fun validateButtom() {

        if ((mail.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(mail).matches())
            && (password.isNotEmpty() && password.length > 3)
            && (usuario.isNotEmpty())
            && (id.toString().length == 10) ){

            viewState.value = ThirdStates.SuccesButton

        }else{
            viewState.value = ThirdStates.ErrorButton
        }
    }

    fun validEmail(email: String){
        this.mail = email
        if (email.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {

            viewState.value = ThirdStates.SuccesEmail

        }else{
            viewState.value = ThirdStates.ErrorMail
        }
        validateButtom()
    }

    fun validatePass(pass: String){
        this.password = pass
        if (pass.isNotEmpty() && pass.length > 3) {

            viewState.value = ThirdStates.SuccesPass

        }else{
            viewState.value = ThirdStates.ErrorPass(pass)
        }
        validateButtom()
    }

    fun validateUser(user: String){
        this.usuario = user
        if (user.isNotEmpty()) {

            viewState.value = ThirdStates.SuccesUser

        }else{
            viewState.value = ThirdStates.ErrorUser
        }
        validateButtom()
    }

    fun validateId(idF: Int){
        this.id = idF
        if (!(id.toString().equals(0)) && id.toString().length == 10) {

            viewState.value = ThirdStates.SuccesId

        }else{
            viewState.value = ThirdStates.ErrorId(idF.toString())
        }
        validateButtom()
    }

}

sealed class ThirdStates(){
    object SuccesEmail: ThirdStates()
    object ErrorMail: ThirdStates()
    object SuccesPass: ThirdStates()
    data class ErrorPass(val pass: String): ThirdStates()
    object SuccesButton: ThirdStates()
    object ErrorButton: ThirdStates()
    object SuccesUser: ThirdStates()
    object ErrorUser: ThirdStates()
    object SuccesId: ThirdStates()
    data class ErrorId(val id: String): ThirdStates()
}