package com.ubaya.advweek4.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
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
    //val studentList:ArrayList<Student>
    //private val studentDetailAdapter = StudentDetailAdapter(arrayListOf())
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

        //recycle view
//        recView.layoutManager = LinearLayoutManager(context)
//        recView.adapter = studentListAdapter
        //val studentList:ArrayList<Student>
        //val student = studentList[0]

//        val student = studentList[0]
//        with(s){
//            txtIDFragment.setText("Halo")
//            txtNameFragment.setText(studentList[0].name.toString())
//            txtDobFragment.setText(studentList[0].dob.toString())
//            txtPhoneFragment.setText(studentList[0].phone.toString())
//        }

        observeViewModel()
    }

    private fun observeViewModel() {
//        viewModel.studentsLD.observe(viewLifecycleOwner){
//            studentDetailAdapter.updateStudentList(it)
//        }
    }
}