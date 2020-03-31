package com.example.conection_mysql

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var butAdd: Button
    lateinit var butShow: Button
    lateinit var etNome: EditText
    lateinit var etEmail: EditText
    lateinit var etTelefone: EditText
    lateinit var txNome: TextView
    lateinit var txEmail: TextView
    lateinit var txFone: TextView

    private val URL_ROOT = "http://192.168.15.14/teste/v1/?op="
    val URL_ADD_USER = URL_ROOT + "addusers"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        butAdd = findViewById(R.id.buttonSalvar)
        butShow = findViewById(R.id.buttonMostrar)
        etNome = findViewById(R.id.editTextNome)
        etEmail = findViewById(R.id.editTextEmail)
        etTelefone = findViewById(R.id.editTextFone)


        butAdd.setOnClickListener { addUser() }

        butShow.setOnClickListener {
            val intent = Intent(applicationContext, ShowUsers::class.java)
            startActivity(intent)
        }

    }


    private fun addUser() {

        val nome = editTextNome?.text.toString()
        val email = editTextEmail?.text.toString()
        val telefone = editTextFone?.text.toString()

        val stringRequest = object : StringRequest(Request.Method.POST, URL_ADD_USER,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG)
                        .show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG)
                        .show()
                }
            }) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("nome", nome)
                params.put("email", email)
                params.put("telefone", telefone)
                return params
            }
        }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}
