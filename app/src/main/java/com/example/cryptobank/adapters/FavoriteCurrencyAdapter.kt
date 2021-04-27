package com.example.cryptobank.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptobank.R
import com.example.cryptobank.database.DBHelper
import com.example.cryptobank.datamodel.Currency

class FavoriteCurrencyAdapter(
    private val users: MutableList<Currency>, private val context: Context, private val loggedUser: String
) : RecyclerView.Adapter<FavoriteCurrencyAdapter.MessageViewHolder>() {
    private var dbHelper = DBHelper(context)

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val currency: TextView = itemView.findViewById(R.id.favorite_currency_name)
        private val price: TextView = itemView.findViewById(R.id.remote_currency_value)

        fun bind(curUser: Currency) {
            currency.text = curUser.currency
            price.text = curUser.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.favorite_currency_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val curUser = users[position]
        holder.bind(curUser)
        val crossIcon: ImageView = holder.itemView.findViewById(R.id.crossIcon)
        val currCurrency = holder.itemView.findViewById<TextView>(R.id.favorite_currency_name)
        crossIcon.setOnClickListener {
            Toast.makeText(context, "Cross Clicked", Toast.LENGTH_LONG).show()
            dbHelper.deleteRowFromTable(loggedUser, currCurrency.text.toString())
            removeAt(position + 1)
        }
    }

    private fun removeAt(position: Int) {
        users.removeAt(position - 1)
        notifyItemRemoved(position)
    }


    override fun getItemCount(): Int {
        return users.size
    }
}
