package com.example.cryptobank.adapters


import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptobank.R
import com.example.cryptobank.database.DBHelper
import com.example.cryptobank.datamodel.Currency
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class RemoteCurrencyAdapter(
    private val users: MutableList<Currency>, val context: Context
) : RecyclerView.Adapter<RemoteCurrencyAdapter.messageViewHolder>() {
    var dbHelper = DBHelper(context)

    inner class messageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val currency: TextView = itemView.findViewById<TextView>(R.id.remote_currency_name);
        private val price: TextView = itemView.findViewById<TextView>(R.id.remote_currency_value);

        fun bind(curUser: Currency) {
            currency.text = curUser.currency
            price.text = curUser.price
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): messageViewHolder {
        return messageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.remote_currency_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: messageViewHolder, position: Int) {
        val curUser = users[position]

        val activity = holder.itemView.context as Activity
        holder.bind(curUser)

        val currCurrency = holder.itemView.findViewById<TextView>(R.id.remote_currency_name)


        println("clicked id: $position")

    }


    override fun getItemCount(): Int {
        return users.size
    }
}
