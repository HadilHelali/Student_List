package com.gl4.tp2

import Student
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import studentListAdapter
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    val recyclerView : RecyclerView by lazy { findViewById(R.id.recyclerview) }
    val spinner : Spinner by lazy { findViewById(R.id.spinner) }
    val search : EditText by lazy { findViewById(R.id.editText) }
    val presence : RadioGroup by lazy { findViewById(R.id.ratioGroup) }

    var studentListCours = arrayListOf<Student>(
        Student("Helali","Hadil","F"),
        Student("Khayati","Mouhamed Aziz","H"),
        Student("Mouhamed","Feriel","F"),
        Student("Achour","Ines","F"),
    )

    var studentListTP = arrayListOf<Student>(
        Student("Trimech","Raoua","F"),
        Student("Boutheib","Mouhamed","H"),
        Student("Samet","Rayen","H"),
        Student("Sammari","Amel","F")
    )

    var studentList = studentListCours


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.adapter = studentListAdapter(studentList,"")
        // Spinner values :
        var matieres = listOf<String>("Cours", "TP")
        // Spinner adapter :
        spinner.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line, matieres
        )
        // gérer les clics sur un élement d'un spinner:
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var selectedSpinner = adapterView?.getItemAtPosition(position).toString()
                Toast.makeText(
                    adapterView?.getContext(),
                    "You selected $selectedSpinner",
                    Toast.LENGTH_SHORT
                ).show()
                if (position == 0)
                    studentList = studentListCours
                     else {
                    studentList = studentListTP }
                //studentList = AffecteeList
                recyclerView.adapter = studentListAdapter(studentList,"")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // nothing
            }
        }
        /* Presence button */
        presence.setOnCheckedChangeListener { radioGroup, i ->
            run {
                val selectedId = radioGroup.checkedRadioButtonId
                val radioButton = findViewById<RadioButton>(selectedId)
                recyclerView.adapter = studentListAdapter(studentList,radioButton.text.toString())
            }}

            /* RecyclerView */
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                val dividerItemDecoration = DividerItemDecoration(
                    recyclerView.context,
                    (layoutManager as LinearLayoutManager).orientation
                )
                addItemDecoration(dividerItemDecoration)
            }

            search.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    Log.d("filter", "beforeTextChanged");
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    (recyclerView.adapter as studentListAdapter).filter.filter(s)
                }

                override fun afterTextChanged(p0: Editable?) {
                    Log.d("filter", "afterTextChanged");
                }

            })


        }
    }
