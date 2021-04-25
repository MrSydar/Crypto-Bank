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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class CurrencyAdapter(
    private val users: MutableList<Currency>, val context: Context, val loggedUser: String
) : RecyclerView.Adapter<CurrencyAdapter.messageViewHolder>() {
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
            LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false)
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

    private fun showAlertWithTextInputLayout(currCurrency: Currency) {
        val textInputLayout = TextInputLayout(context)

        val fdb = FirebaseFirestore.getInstance()
//        fdb.document("users/$loggedUser").get().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val doc = task.result
//
//                val document = fdb.collection("users").document(loggedUser)
//                val data = hashMapOf("currencies" to currCurrency)
//                document.set(data, SetOptions.merge()).addOnSuccessListener {
////                    changeActivity(MainActivity::class)
//                    Toast.makeText(context, "Sucsesss", Toast.LENGTH_SHORT).show()
//                }
//                doc?.let {
//                    val realPassword: String = doc.get("password").toString()
//                    Toast.makeText(context, "No such user $realPassword", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }


        textInputLayout.setPadding(
            context.resources.getDimensionPixelOffset(R.dimen.dp_19), // if you look at android alert_dialog.xml, you will see the message textview have margin 14dp and padding 5dp. This is the reason why I use 19 here
            0,
            context.resources.getDimensionPixelOffset(R.dimen.dp_19),
            0
        )

        val input = EditText(context)
//        textInputLayout.hint = "Amount in USD"
        textInputLayout.addView(input)

        val alert = AlertDialog.Builder(context)
            .setTitle("Add Currency")
            .setView(textInputLayout)
            .setMessage("Please enter of currency in USD")
            //Adding to Values to Database
            .setPositiveButton("Submit") { dialog, _ ->
                println("${textInputLayout.editText} editText")

                val document = fdb.collection("users").document(loggedUser)
//                val data = hashMapOf("currencies" to currCurrency)
                document.update("currencies", FieldValue.arrayUnion(currCurrency))
                    .addOnSuccessListener {
                        Toast.makeText(context, "Sucsesss", Toast.LENGTH_SHORT).show()
                    }


//
//                document.set(data, SetOptions.merge()).addOnSuccessListener {
//
////                    changeActivity(MainActivity::class)
//                    Toast.makeText(context, "Sucsesss", Toast.LENGTH_SHORT).show()
//                }
//                Toast.makeText(
//                    context,
//                    "${textInputLayout.getEditText()?.getText()} editText",
//                    Toast.LENGTH_LONG
//                )
//                    .show();

                dialog.cancel()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }.create()
        alert.show()
    }

    override fun onBindViewHolder(holder: messageViewHolder, position: Int) {
        val curUser = users[position]

        val activity = holder.itemView.context as Activity
        holder.bind(curUser)

        val starIcon: ImageView = holder.itemView.findViewById<ImageView>(R.id.starIcon)
        val currCurrency = holder.itemView.findViewById<TextView>(R.id.remote_currency_name)
        val currCurrencyVal = holder.itemView.findViewById<TextView>(R.id.remote_currency_value)
        val plusIcon: ImageView = holder.itemView.findViewById<ImageView>(R.id.crossIcon)

        plusIcon.setOnClickListener {
            showAlertWithTextInputLayout(
                Currency(
                    currCurrency.text.toString(),
                    currCurrencyVal.text.toString()
                )
            )
//            showBuilder("kek $position")
            Toast.makeText(context, "Plus Clicked", Toast.LENGTH_LONG).show();
        }

        starIcon.setOnClickListener {
            Toast.makeText(context, "Star Clicked", Toast.LENGTH_LONG).show();
            val currency = Currency(currCurrency.text as String, "0")
            dbHelper.addCurrency(currency, loggedUser)
        }

        println("clicked id: $position")

    }


    override fun getItemCount(): Int {
        return users.size
    }
}