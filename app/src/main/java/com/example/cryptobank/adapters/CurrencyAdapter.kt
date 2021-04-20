package com.example.cryptobank.adapters

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptobank.R
import com.example.cryptobank.activities.KontoActivity
import com.example.cryptobank.datamodel.Currency


class CurrencyAdapter(
    private val users: MutableList<Currency>, val context: Context
) : RecyclerView.Adapter<CurrencyAdapter.messageViewHolder>() {

    inner class messageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val currency: TextView = itemView.findViewById<TextView>(R.id.userName);
        private val price: TextView = itemView.findViewById<TextView>(R.id.userScore);

        fun bind(curUser: Currency) {
            currency.text = curUser.currency
            price.text = curUser.price
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): messageViewHolder {
        return messageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false)
        )
    }

    fun showBuilder(message: String) {
        var builder = AlertDialog.Builder(context)
        builder.setTitle("addCurrency")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog: DialogInterface?, which: Int -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onBindViewHolder(holder: messageViewHolder, position: Int) {
        val curUser = users[position]

        val activity = holder.itemView.context as Activity
        holder.bind(curUser)

        val starIcon: ImageView = holder.itemView.findViewById<ImageView>(R.id.starIcon)

        val plusIcon: ImageView = holder.itemView.findViewById<ImageView>(R.id.starIcon)
        plusIcon.setOnClickListener {
            Toast.makeText(context, "Plus Clicked", Toast.LENGTH_LONG).show();
        }

        starIcon.setOnClickListener {
            Toast.makeText(context, "Star Clicked", Toast.LENGTH_LONG).show();
        }

        println("clicked id: $position")

    }


    override fun getItemCount(): Int {
        return users.size
    }
}