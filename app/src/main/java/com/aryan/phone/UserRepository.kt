package com.aryan.phone

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.*


class UserRepository {
    private  val  database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun fectchUsers(callback: (List<User>) ->Unit)
    {
        database.child("users").addListenerForSingleValueEvent(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<User>()
                for (userSnapshot in snapshot.children)
                {
                    val user = userSnapshot.getValue(User::class.java)
                    user?.let { userList.add(it) }
                }
                callback(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Repo","Failed")
            }
        })
    }
}