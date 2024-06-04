package com.aryan.phone

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etNum: EditText
    private lateinit var btSend: Button
    private lateinit var btRecieve: Button
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
   //   }

        etName=findViewById(R.id.etName)
        etNum= findViewById(R.id.etNum)
        btSend=findViewById(R.id.btSend)
        btRecieve=findViewById(R.id.button2)

        userViewModel =ViewModelProvider(this).get(UserViewModel::class.java)

        btSend.setOnClickListener {
            val name= etName.text.toString().trim()
            val num= etNum.text.toString().trim()

            if(name.isEmpty()|| num.isEmpty())
            {
                Toast.makeText(this,"Fill all fields",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val user= User(name,num)

            val database = Firebase.database
            val myRef = database.getReference("users")

            myRef.push().setValue(user).addOnCompleteListener {
                task->
                if(task.isSuccessful)
                {
                    Toast.makeText(this," Successful",Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(this," unSuccessful",Toast.LENGTH_LONG).show()
                }
            }

        }
        btRecieve.setOnClickListener { userViewModel.fetchUsers() }

        userViewModel.users.observe(this, Observer {
            users->
            users.forEach{
                user->
                Log.d("Main Activity","User:${user.name}, Number:${user.number}")
            }
        })
    }
}