package com.example.demo.view

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.paint.Color
import tornadofx.*
import yahoofinance.YahooFinance
import yahoofinance.Stock
import java.math.BigDecimal

private fun return_price(name: String): Double? {
    val stocker = YahooFinance.get(name)?.quote?.price?.toDouble()
    return stocker
}

private fun return_currency(name: String): String? {
    val st_curr = YahooFinance.get(name)?.currency?.toString()
    return st_curr
}

class MainView : View("Stocks") {

    class Stock(id: Int, name: String,  stock_currency: String, start_val: Double, current_val: Double) {
        var id by property(id)
        fun idProperty() = getProperty(Stock::id)

    //    var name by property(name)
    //    fun nameProperty() = getProperty(Stock::name)
        private val nameProperty = SimpleStringProperty(name)
        fun nameProperty() = nameProperty
        var name by nameProperty

        var stock_currency by property(stock_currency)
        fun stock_currencyProperty() = getProperty(Stock::stock_currency)

        var start_val by property(start_val)
        fun start_valProperty() = getProperty(Stock::start_val)

        var current_val by property(current_val)
        fun current_valProperty() = getProperty(Stock::current_val)

    }


    public val stocks = FXCollections.observableArrayList<Stock>(
        Stock(1, "BP.L", "GBP", 2.3890, return_price("BP.L") ?:0.0),
        Stock(2, "CLIG.L", "GBP", 4.3950, 0.0),
        Stock(3, "EVR.L",  "GBP", 3.99540, 0.0),
        Stock(4, "FIS",  "GBP",112.2329, 0.0),
        Stock(5, "IAG.L",  "GBP",1.5454, 0.0),
        Stock(6, "PFE", "GBP", 27.7895, 0.0)
    )


    override val root =  vbox{

         val side = hbox {
             button("Update Prices") {
                 textFill = Color.RED
                 action { button_pressed() }
             }
             button("Press Me") {
                 textFill = Color.GREEN
                 action { button_pressed() } 
             }

         }

        tableview(stocks) {
            isEditable = true
            column("ID", Stock::idProperty).makeEditable()
            column("Name", Stock::nameProperty).makeEditable()
            column("Currency", Stock::stock_currencyProperty).makeEditable()
            column("Start Value", Stock::start_valProperty).makeEditable()
            column("Current Value", Stock::current_valProperty).makeEditable().cellFormat {
                text = it.toString()
                style {
                    if (it < 18) {
                        textFill = Color.RED
                    } else {
                        textFill = Color.BLACK
                    }
                }
            }
        }
    }
    private fun button_pressed(){
        for (current in stocks){
            current.current_val = return_price(current.name) ?: 0.0
            current.stock_currency = return_currency(current.name) ?: "NA"
        }
    }
}


