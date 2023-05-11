package com.example.mychatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.Button
import android.widget.EditText
import com.example.mychatapp.databinding.ActivityOtpactivityBinding
import com.google.firebase.auth.FirebaseAuth

class VerificationActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    lateinit var editnumber:EditText
    lateinit var continueBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        editnumber = findViewById(R.id.editnumber)
        continueBtn = findViewById(R.id.editnumber)

        auth = FirebaseAuth.getInstance()
        if (auth!!.currentUser != null){
            val intent =Intent(this@VerificationActivity,MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        supportActionBar?.hide()
        editnumber.requestFocus()
        continueBtn.setOnClickListener {
            val intent = Intent(this,OTPActivity::class.java)
            intent.putExtra("phoneNumber",editnumber.text.toString())
            startActivity(intent)
        }
    }
}