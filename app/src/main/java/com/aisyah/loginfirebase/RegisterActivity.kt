package com.aisyah.loginfirebase

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegis.setOnClickListener {
            val nama = regisNama.text.toString()
            val email = regisEmail.text.toString()
            val password = regisPassword.text.toString()

            if (nama.isEmpty()) {
                Toast.makeText(this, "Please insert name", Toast.LENGTH_SHORT).show()
                regisNama.requestFocus()
            }

            if (email.isEmpty()) {
                Toast.makeText(this, "Please insert email", Toast.LENGTH_SHORT).show()
                regisEmail.requestFocus()
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Please insert password", Toast.LENGTH_SHORT).show()
                regisPassword.requestFocus()
            }
            val progressDialog = ProgressDialog(this, R.style.Theme_MaterialComponents_Light_Dialog)
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Creating user. . .")
            progressDialog.show()
                        
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    progressDialog.hide()

                    if (it.isSuccessful) {
                        val userId = FirebaseAuth.getInstance().currentUser!!.uid
                        val currentUser =
                            FirebaseDatabase.getInstance().reference.child("Users").child(userId)
                        currentUser.child("Name").setValue(nama)

                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                    } else {
                        Toast.makeText(this,"Authentication failed", Toast.LENGTH_SHORT).show()
                    }

                }
        }

    }
}
