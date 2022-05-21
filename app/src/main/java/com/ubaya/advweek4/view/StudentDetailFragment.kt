package com.ubaya.advweek4.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.htmlEncode
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.advweek4.R
import com.ubaya.advweek4.model.Student
import com.ubaya.advweek4.util.loadImage
import com.ubaya.advweek4.viewmodel.DetailViewModel
import com.ubaya.advweek4.viewmodel.ListViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_student_detail.*
import kotlinx.android.synthetic.main.fragment_student_detail.txtDobFragment
import kotlinx.android.synthetic.main.fragment_student_detail.txtIDFragment
import kotlinx.android.synthetic.main.fragment_student_detail.txtNameFragment
import kotlinx.android.synthetic.main.fragment_student_detail.txtPhoneFragment
import kotlinx.android.synthetic.main.fragment_student_detail.view.*
import kotlinx.android.synthetic.main.fragment_student_list.*
import kotlinx.android.synthetic.main.student_list_item.view.*
import java.util.*
import java.util.concurrent.TimeUnit

class StudentDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments.let {
            val stdID = StudentDetailFragmentArgs.fromBundle(requireArguments()).studentID
            viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
            viewModel.fetch(stdID)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.studentsLD.observe(viewLifecycleOwner){
            val student = it
            student?.let { student ->
                imgDetail.loadImage(it.photoUrl, progressLoadingStudentPhotoDetail)
                txtIDFragment.setText(it.id)
                txtNameFragment.setText(it.name)
                txtDobFragment.setText(it.dob)
                txtPhoneFragment.setText(it.phone)

                btnNotification.setOnClickListener{
                    Observable.timer(5, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            Log.d("mynotif", "Five Seconds")
                            Log.d("Message", student.name.toString())
                            student.name?.let { studentName ->
                                MainActivity.showNotification(
                                    studentName,
                                    "A new notification created",
                                    R.drawable.ic_baseline_person_24
                                )

                            }
                        }
                }
            }
        }
    }
}