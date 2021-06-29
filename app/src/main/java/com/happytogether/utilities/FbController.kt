package com.happytogether.utilities

import android.content.Context
import android.util.Log
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
        var hasil: MutableMap<String, String> = mutableMapOf<String, String>()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {
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
//        println(hasil)
        return hasil
    }

    fun bla() {
        println("abc" + readUserName())
    }
    private fun readUserPass() : MutableMap<String, String> {
        var hasil = mutableMapOf<String, String>()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val user = data.getValue(Users::class.java)
                        user.let {
                            hasil.put(data.key.toString(), it!!.userPassword)
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
//        for (key in readUserName().keys) {
////            Log.w("value : ", readUserName()[key].toString())
//      println("aweafwef")
//            println("Key = "+key +", "+"Value = "+readUserName()[key])
//        }
//        Log.w("out : ", readUserName().containsValue("ab").toString())
        if (readUserName().containsValue("ab")){
            return true
        }
        return false
    }

    fun isExistPass(srcPass : String): Boolean {
        Log.w("out : ", readUserPass().containsValue(srcPass).toString())
        if (readUserPass().containsValue(srcPass))
            return true
        return false
    }

    fun checkUser(srcName: String, srcPass: String): Boolean {
        val user = readUserName().keys
        val pass = readUserPass().filterValues { it == srcPass }.keys
        val userKey = user.first()
        val passKey = pass.first()
        Log.w("output : ", "${userKey}, ${passKey}")
        return userKey == passKey
    }


}