package com.ubaya.advweek4.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ubaya.advweek4.model.Student

class DetailViewModel: ViewModel() {
    val studentsLD = MutableLiveData<Student>()

    val studentLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    fun fetch(){
        studentsLD.value = Student("16055","Nonie","1998/03/28","5718444778","http://dummyimage.com/75x100.jpg/cc0000/ffffff")

        studentLoadErrorLD.value = false
        loadingLD.value = false
    }
}