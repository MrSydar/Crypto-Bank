package com.example.cryptobank.adapters

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptobank.R
import com.example.cryptobank.database.DBHelper
import com.example.cryptobank.datamodel.Currency

class FavoriteCurrencyAdapter(
    private val users: MutableList<Currency>, val context: Context
) : RecyclerView.Adapter<FavoriteCurrencyAdapter.messageViewHolder>() {
    var dbHelper = DBHelper(context)

    inner class messageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val currency: TextView = itemView.findViewById<TextView>(R.id.fav_currency_name);
        private val price: TextView = itemView.findViewById<TextView>(R.id.fav_currency_value);

        fun bind(curUser: Currency) {
            currency.text = curUser.currency
            price.text = curUser.price
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): messageViewHolder {
        return messageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_currency_item, parent, false)
        )
    }

    fun showBuilder(message: String) {
        val builder = AlertDialog.Builder(context)
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
        val crossIcon: ImageView = holder.itemView.findViewById<ImageView>(R.id.crossIcon)
//        val currCurrency = holder.itemView.findViewById<TextView>(R.id.fav_currency_name)
//
//        val plusIcon: ImageView = holder.itemView.findViewById<ImageView>(R.id.plusIcon)

//        plusIcon.setOnClickListener {
//            showBuilder("kek $position")
////            Toast.makeText(context, "Plus Clicked", Toast.LENGTH_LONG).show();
//        }
//
        crossIcon.setOnClickListener {
            Toast.makeText(context, "Cross Clicked", Toast.LENGTH_LONG).show();
            removeAt(position)
//            val currency = Currency(currCurrency.text as String, "0")
//            dbHelper.addCurrency(currency, "oleg@gmail.com")
        }

        println("clicked id: $position")

    }

    fun removeAt(position: Int) {
//        mDataset.remove(position);
        users.removeAt(position-1)
        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, mDataSet.size());
    }


    override fun getItemCount(): Int {
        return users.size
    }
}
