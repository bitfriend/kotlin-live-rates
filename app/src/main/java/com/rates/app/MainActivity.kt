package com.rates.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import java.io.IOException
import java.util.Timer
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    private lateinit var listView: ListView

    private var listItems: ArrayList<ItemModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById<ListView>(R.id.list_view)

        Timer("SettingUp", false).schedule(0, 10000) {
            run("https://mt4-api-staging.herokuapp.com/rates")
        }
    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                var body: String? = response.body?.string()

                val result: JSONObject = JSONObject(body)
                var rates = result.getJSONArray("rates")
                var i: Int

                for (i in 0 until rates.length()) {
                    var rate = rates.getJSONObject(i)
                    addItem(rate.getString("symbol"), rate.getString("price"))
                }

                runOnUiThread {
                    val adapter: CustomAdapter = CustomAdapter(applicationContext, listItems)
                    listView.adapter = adapter
                }
            }
        })
    }

    fun addItem(symbol: String, price: String) {
        var found: Int = -1;
        var i: Int
        for (i in 0 until listItems.count()) {
            if (listItems[i].symbol == symbol) {
                if (listItems[i].price.toDouble() < price.toDouble()) {
                    listItems[i].direction = 1
                } else if (listItems[i].price.toDouble() > price.toDouble()) {
                    listItems[i].direction = -1
                } else {
                    listItems[i].direction = 0
                }
                listItems[i].price = price
                found = i
                break
            }
        }
        if (found == -1) {
            var model = ItemModel(symbol, price)
            listItems.add(model)
        }
    }
}
