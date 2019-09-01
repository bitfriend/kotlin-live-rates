package com.rates.app

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CustomAdapter(internal var context: Context, private val items: ArrayList<ItemModel>) : BaseAdapter() {

    internal var inflater: LayoutInflater

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.list_item, parent, false)

        val txtSymbol = view.findViewById<TextView>(R.id.symbol)
        val txtPrice = view.findViewById<TextView>(R.id.price)

        txtSymbol.setText(items[position].symbol)
        txtPrice.setText(items[position].price)

        when (items[position].direction) {
            1 -> {
                txtSymbol.setTextColor(Color.GREEN)
                txtPrice.setTextColor(Color.GREEN)
            }
            -1 -> {
                txtSymbol.setTextColor(Color.RED)
                txtPrice.setTextColor(Color.RED)
            }
        }

        return view
    }
}