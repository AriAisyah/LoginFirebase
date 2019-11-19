package com.aisyah.loginfirebase

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtRegis.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        btnLogin.setOnClickListener {
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please insert email and password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val progressDialog = ProgressDialog(this, R.style.Theme_MaterialComponents_Light_Dialog)
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Login")
            progressDialog.show()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    progressDialog.hide()
                    loginEmail.setText("")
                    loginPassword.setText("")

                    if (!it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Successfully Login", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Dashboard::class.java)
                        startActivity(intent)
                    }
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Email or Password incorrect", Toast.LENGTH_SHORT).show()
                }

//
//            if (email == "aisyah190905@gmail.com" && password == "aisyah"){
//                Toast.makeText(this,"Login Success", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this,"Login Failed", Toast.LENGTH_SHORT).show()
//            }
        }

    }
}
