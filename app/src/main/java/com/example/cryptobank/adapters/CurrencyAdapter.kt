package com.example.cryptobank.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptobank.R
import com.example.cryptobank.datamodel.Currency


class CurrencyAdapter(
    private val users: MutableList<Currency>, val context: Context
) : RecyclerView.Adapter<CurrencyAdapter.messageViewHolder>() {

    class messageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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


    override fun onBindViewHolder(holder: messageViewHolder, position: Int) {
        val curUser = users[position]
        val activity = holder.itemView.context as Activity

        holder.bind(curUser)
//        holder.itemView.setOnClickListener {
//            Thread() {
//                run {
//                    Thread.sleep(1000)
//                }
//                val intent = Intent(activity, UserInfo::class.java)
//                intent.putExtra("userID", position);
//
//                context.startActivity(intent)
////                    finish()
//            }.start()
//        }

        println("clicked id: $position")

    }


    override fun getItemCount(): Int {
        return users.size
    }
}