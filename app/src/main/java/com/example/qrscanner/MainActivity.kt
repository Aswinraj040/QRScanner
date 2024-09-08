package com.example.qrscanner

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    companion object {
        const val RESULT = "RESULT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.btn_scan)
        btn.setOnClickListener {
            val intent = Intent(applicationContext, Scanneractivity::class.java)
            startActivity(intent)
            finish()
        }

        var result = intent.getStringExtra(RESULT)
        val tv = findViewById<TextView>(R.id.result)

        if (result != null) {
            if (result.contains("https://") || result.contains("http://")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
                startActivity(intent)
            } else {
                tv.text = result.toString()
                val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = simpleDate.format(Date()).toString()
                val nameee= result.toString().substring(11)
                nameee.replace("RIT HOSTEL" , "")
                val rollno = result.toString().slice(0..9)
                val fullUrl = "https://script.google.com/macros/s/AKfycbztdlZYaEwGVSe3DFu4euzj9jAVEII6lBMGLFWoLOrTA7piUhfddMRafdsybdpy_Xz78A/exec?action=create&rollno=$rollno&name=$nameee&date=$currentDate"
                val stringRequest = StringRequest(
                    Request.Method.GET, fullUrl,
                    { response ->
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                    },
                    { error ->
                        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    })

                val queue = Volley.newRequestQueue(this)
                queue.add(stringRequest)
                result = ""
            }
        }
    }
}