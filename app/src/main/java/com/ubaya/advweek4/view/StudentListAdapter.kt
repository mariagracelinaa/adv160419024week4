package com.ubaya.advweek4.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.advweek4.R
import com.ubaya.advweek4.model.Student
import kotlinx.android.synthetic.main.student_list_item.view.*

//adapter untuk recycler view
class StudentListAdapter(val studentList:ArrayList<Student>) : RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>(){
    class StudentViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.student_list_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        //menampilkan data yang sesuai dari masing2 item
        val student = studentList[position]
        with(holder.view){
            txtID.text = student.id
            txtName.text = student.name

            //listener btnDetail, code navigation controllernya disini bkn di main activity
            btnDetail.setOnClickListener {
                val action = StudentListFragmentDirections.actionStudentDetail()
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount() = studentList.size

    fun updateStudentList(newStudentList: ArrayList<Student>){
        studentList.clear()
        studentList.addAll(newStudentList)
        //untuk memberi tahu jika ada data baru
        notifyDataSetChanged()
    }
}