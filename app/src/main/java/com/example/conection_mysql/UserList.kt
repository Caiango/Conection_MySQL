package com.example.conection_mysql

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class UserList(private val context: Activity, internal var UserData: List<UserData>) :
    ArrayAdapter<UserData>(context, R.layout.user_layout, UserData) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.user_layout, null, true)

        val txNome = listViewItem.findViewById(R.id.textViewUsernameID) as TextView
        val txEmail = listViewItem.findViewById(R.id.textViewUseremailID) as TextView
        val txFone = listViewItem.findViewById(R.id.textViewUserfoneID) as TextView

        val myUser = UserData[position]
        txNome.text = myUser.nome
        txEmail.text = myUser.email
        txFone.text = myUser.telefone

        return listViewItem

    }
}
