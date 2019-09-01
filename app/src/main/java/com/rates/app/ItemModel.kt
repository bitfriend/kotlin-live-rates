package com.rates.app

class ItemModel {
    lateinit var symbol: String
    lateinit var price: String
    var direction: Int = 0

    constructor(symbol: String, price: String) {
        this.symbol = symbol
        this.price = price
    }
}
