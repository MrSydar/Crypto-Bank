package com.example.cryptobank.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptobank.R
import com.example.cryptobank.datamodel.Currency


class RemoteCurrencyAdapter(
    private val users: MutableList<Currency>
) : RecyclerView.Adapter<RemoteCurrencyAdapter.MessageViewHolder>() {

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
            LayoutInflater.from(parent.context)
                .inflate(R.layout.remote_currency_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val curUser = users[position]
        holder.bind(curUser)
    }


    override fun getItemCount(): Int {
        return users.size
    }
}
