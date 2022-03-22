package com.ubaya.advweek4.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.advweek4.model.Student

//jembatan model student dengan view(fragmentlist)
//add extend viewModel

//agar bisa mengacu pada seluruh project aplikasi ini -> AndroidViewModel(application)
//selanjutnya dipanggil di Volley.newRequestQueue()
class ListViewModel(application: Application):AndroidViewModel(application) {
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


    //week 5
    //variable yang digunakan untuk identifier sebuah string request yang akan dijalankan melalui volley
    //tujuannya untuk menjalankan fungsi cancel string request
    //string request bisa di cancel jika sudah diberi tag/ditandai
    private val TAG = "volleyTag"
    //queue merupakan object dr class request queue, dibuat null able
    //akan di inisialize dalam refresh()
    private var queue:RequestQueue ?= null

    fun refresh(){
        //hardcode
//        studentsLD.value = arrayListOf(
//            Student("16055","Nonie","1998/03/28","5718444778","http://dummyimage.com/75x100.jpg/cc0000/ffffff"),
//            Student("13312","Rich","1994/12/14","3925444073","http://dummyimage.com/75x100.jpg/5fa2dd/ffffff"),
//            Student("11204","Dinny","1994/10/07","6827808747","http://dummyimage.com/75x100.jpg/5fa2dd/ffffff1")
//        )
//
        studentLoadErrorLD.value = false
        loadingLD.value = true

        //pakai GSON dan Volley
        queue = Volley.newRequestQueue(getApplication())
        var url = "http://adv.jitusolution.com/student.php"
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                //GSON
                val sType = object : TypeToken<ArrayList<Student>>() {}.type
                //baca jsonnya, result akan bertipe array list student
                val result = Gson().fromJson<ArrayList<Student>>(response, sType)
                //update value studentsLD dengan result, karena result adalah array list student
                studentsLD.value = result

                loadingLD.value = false
                //jika sukses
                //cek isi json pakai Log.d(...)
                Log.d("showvolley", response.toString())
            },
            {
                loadingLD.value = false
                studentLoadErrorLD.value = true
                Log.d("showvolley", it.toString())
            })
        //untuk hapus jika terjadi onclear
        //jika nanti ada destroy observer nya
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    //cara clear
    //merupakan fungsi dr viewmodel yg akan dipanggil jika observer sedang inactive -> dalam hal ini fragment listnya inactive
    override fun onCleared() {
        super.onCleared()
        //semua request jika ada yg masih nyantol/jalan akan di cancel semua
        //string request mana yang mau di cancel? yang ada TAG nya tadi dibuat di awal
        queue?.cancelAll(TAG)
    }
}