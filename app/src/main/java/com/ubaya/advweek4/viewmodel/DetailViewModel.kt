package com.ubaya.advweek4.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.advweek4.model.Student

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val studentsLD = MutableLiveData<Student>()

    private val TAG = "volleyTag"
    private var queue: RequestQueue?= null

    val studentLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    fun fetch(studentID : String?){
        queue = Volley.newRequestQueue(getApplication())
        var url = "http://adv.jitusolution.com/student.php?id=$studentID"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<Student>() {}.type
                val result =  Gson().fromJson<Student>(it, sType)
                studentsLD.value = result
                Log.d("showStudent", it)
            },
            {
                Log.d("errorStudent", it.toString())
            }).apply {
                tag = TAG
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}