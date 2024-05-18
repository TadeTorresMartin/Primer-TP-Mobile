package com.pgmd.primertp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ValidacionesLog: ViewModel() {

    var viewState: MutableLiveData<verif> = MutableLiveData()

    var id: Int = 0
    var contraseña: String = ""


    private fun validLog() {
        if( contraseña.isNotEmpty() && id.toString().isNotEmpty()){
            viewState.value = verif.SuccesButtom
        }else{
            viewState.value = verif.ErrorButtom
        }

    }

    fun validarPass(pass: String){

        this.contraseña = pass
        if(pass.isNotEmpty() && pass.length > 3){
            viewState.value = verif.SuccesPass
        }else{
            viewState.value = verif.ErrorPass(pass)
        }
        validLog()
    }

    fun validarId(idF: Int) {
        this.id = idF
        if(!(id.toString().equals(0)) && id.toString().length == 10){
            viewState.value = verif.SuccesId
        }else{
            viewState.value = verif.ErrorId(idF)
        }
        validLog()
    }
}

sealed class verif(){
    data class ErrorId (val id: Int): verif()
    object SuccesId: verif()
    object SuccesPass: verif()
    data class ErrorPass (val pass: String): verif()
    object SuccesButtom: verif()
    object ErrorButtom: verif()
}