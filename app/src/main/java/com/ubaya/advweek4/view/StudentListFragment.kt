package com.ubaya.advweek4.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.advweek4.R
import com.ubaya.advweek4.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_student_list.*

class StudentListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val studentListAdapter = StudentListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        //recycle view
        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = studentListAdapter

        //Refresh view model agar volleynya dipanggil lagi dan akan mendapatkan data terbaru dan update recycle view
        refreshLayout.setOnRefreshListener {
            recView.visibility = View.GONE
            txtError.visibility = View.GONE
            progressLoad.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    //Untuk aksion yg dibutuhkan observer
    private fun observeViewModel() {
        //kalau ada perubahan maka di update
        viewModel.studentsLD.observe(viewLifecycleOwner){
            studentListAdapter.updateStudentList(it)
        }

        //kalauu loading
        viewModel.loadingLD.observe(viewLifecycleOwner){
            if(it){
                recView.visibility = View.GONE
                progressLoad.visibility = View.VISIBLE
            }else{
                recView.visibility = View.VISIBLE
                progressLoad.visibility = View.GONE
            }
        }

        //kalau error
        viewModel.studentLoadErrorLD.observe(viewLifecycleOwner){
            txtError.visibility = if(it) View.VISIBLE else View.GONE
        }
    }

}