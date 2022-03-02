package com.ubaya.advweek4.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ubaya.advweek4.model.Student

//jembatan model student dengan view(fragmentlist)
//add extend BiewModel
class ListViewModel:ViewModel() {
    //LD-> live data
    //tampung data yang ada (list of student)
    val studentsLD = MutableLiveData<ArrayList<Student>>()

    //opsional tapi sebaiknya ada
    //live data jika ada error
    //Mutable -> bisa di tambah, edit, delete
    //misal server gaada koneksi atau internet, server salah alamat
    val studentLoadErrorLD = MutableLiveData<Boolean>()
    //yang memberikan status loading (true/false)
    val loadingLD = MutableLiveData<Boolean>()

    fun refresh(){
        studentsLD.value = arrayListOf(
            Student("16055","Nonie","1998/03/28","5718444778","http://dummyimage.com/75x100.jpg/cc0000/ffffff"),
            Student("13312","Rich","1994/12/14","3925444073","http://dummyimage.com/75x100.jpg/5fa2dd/ffffff"),
            Student("11204","Dinny","1994/10/07","6827808747","http://dummyimage.com/75x100.jpg/5fa2dd/ffffff1")
        )

        studentLoadErrorLD.value = false
        loadingLD.value = false
    }
}