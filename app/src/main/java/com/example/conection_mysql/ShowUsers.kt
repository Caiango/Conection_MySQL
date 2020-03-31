package com.example.conection_mysql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class ShowUsers : AppCompatActivity() {

    private val URL_ROOT = "http://192.168.15.14/teste/v1/?op="
    val URL_GET_USER = URL_ROOT + "getusers"

    lateinit var mListViewUsers: ListView
    lateinit var mUsersList: MutableList<UserData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_users)

        mListViewUsers = findViewById(R.id.listViewUsers) as ListView
        mUsersList = mutableListOf<UserData>()
        loadUsers()
    }

    private fun loadUsers() {

        val stringRequest = StringRequest(
            Request.Method.GET,
            URL_GET_USER,
            Response.Listener<String> { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray(("user"))

                        for (i in 0..array.length() - 1) {
                            val objectArtist = array.getJSONObject(i)
                            val artist = UserData(
                                objectArtist.getString("nome"),
                                objectArtist.getString("email"),
                                objectArtist.getString("telefone")
                            )
                            mUsersList.add(artist)
                            val adapter = UserList(this, mUsersList)
                            mListViewUsers.adapter = adapter
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(
                    applicationContext,
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)
    }
}
