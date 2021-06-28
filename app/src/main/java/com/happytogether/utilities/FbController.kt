package com.happytogether.utilities

import android.content.Context
import android.widget.EditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.happytogether.data.Users

class FbController (context: Context) {
    private var database = Firebase.database
    private val ref = database.getReference("USERS")
    private val mContext = context

    fun saveUser(users: Users) {
        var userID = ref.push().key.toString()

        ref.child(userID).setValue(users).apply {
            addOnCompleteListener {  }
            addOnCanceledListener {  }
            addOnFailureListener {  }
            addOnSuccessListener {  }
        }
    }

    private fun readUserName() : MutableMap<String, String> {
        var hasil = mutableMapOf<String, String>()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val user = data.getValue(Users::class.java)
                        user.let {
                            hasil.put(data.key.toString(), it!!.userName)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return hasil
    }

    fun deleteUser(srcName : String) {
        val key = readUserName().filterValues { it == srcName }.keys
        ref.child(key.first()).removeValue()
    }

    fun isExist(srcName : String): Boolean {
        if (readUserName().containsValue(srcName))
            return true
        return false
    }

    private fun readUser() : MutableMap<String, ArrayList<String>> {
        var hasil = mutableMapOf<String, ArrayList<String>>()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val user = data.getValue(Users::class.java)
                        user.let {
                            hasil.put(data.key.toString(), arrayListOf(it!!.userName, it.userPassword))
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return hasil
    }

    fun checkUser(srcName: String, srcPass: String): Boolean {
        val user = readUser().filterValues { it[0] == srcName }.keys
        val pass = readUser().filterValues { it[1] == srcPass }.keys
        if ( user == pass )
            return true
        return false
    }
}