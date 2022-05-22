package com.ubaya.advweek4.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.ubaya.advweek4.databinding.FragmentStudentDetailBinding
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

class StudentDetailFragment : Fragment() , ShowNotificationClickListener, UpdateClickListener{
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentStudentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val stdID = StudentDetailFragmentArgs.fromBundle(requireArguments()).studentID
        viewModel.fetch(stdID)

        dataBinding.notificationListener = this
        dataBinding.updateListener = this
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.studentsLD.observe(viewLifecycleOwner){
            dataBinding.student = it
        }
    }

    override fun onShowNotificationClick(view: View) {
        viewModel.studentsLD.observe(viewLifecycleOwner){
            val student = it
            student?.let { student ->
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

    override fun onUpdateClick(view: View) {
        Toast.makeText(view.context, "Data Updated", Toast.LENGTH_SHORT).show()
//        val action = StudentListFragmentDirections.actionStudentDetail(view.tag.toString())
        Navigation.findNavController(view).popBackStack()
    }
}