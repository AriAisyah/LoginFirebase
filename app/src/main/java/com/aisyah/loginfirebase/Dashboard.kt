package com.aisyah.loginfirebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val user = FirebaseDatabase.getInstance().reference.child("Users")
        val userId = user.child(FirebaseAuth.getInstance().currentUser!!.uid)

        txtEmail.text = FirebaseAuth.getInstance().currentUser!!.email

        userId.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                txtNama.text = p0.child("Name").value as String
            }

        })

        btnSignout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
        }

    }
}
