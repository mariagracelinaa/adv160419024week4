package com.ubaya.advweek4.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.ubaya.advweek4.viewmodel.DetailViewModel
import com.ubaya.advweek4.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_student_detail.*
import kotlinx.android.synthetic.main.fragment_student_detail.txtDobFragment
import kotlinx.android.synthetic.main.fragment_student_detail.txtIDFragment
import kotlinx.android.synthetic.main.fragment_student_detail.txtNameFragment
import kotlinx.android.synthetic.main.fragment_student_detail.txtPhoneFragment
import kotlinx.android.synthetic.main.fragment_student_detail.view.*
import kotlinx.android.synthetic.main.fragment_student_list.*
import kotlinx.android.synthetic.main.student_list_item.view.*

class StudentDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private val TAG = "volleyTag"
    private var queue: RequestQueue?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch()

        arguments?.let {
            val stdID = StudentDetailFragmentArgs.fromBundle(requireArguments()).studentID

            queue = Volley.newRequestQueue(context)
            var url = "http://adv.jitusolution.com/student.php?id=$stdID"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val result = Gson().fromJson<Student>(response, Student::class.java)

                    viewModel.studentsLD.value = result
                    Log.d("showvolley", response.toString())
                },
                {
                    Log.d("showvolley", it.toString())
                })
            stringRequest.tag = TAG
            queue?.add(stringRequest)

        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.studentsLD.observe(viewLifecycleOwner){
            val student = viewModel.studentsLD.value
            student?.let {
                txtIDFragment.setText(it.id)
                txtNameFragment.setText(it.name)
                txtDobFragment.setText(it.dob)
                txtPhoneFragment.setText(it.phone)
            }
        }
    }
}