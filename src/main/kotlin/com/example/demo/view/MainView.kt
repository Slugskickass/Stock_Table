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
class MainView : View("Stocks") {

    class Stock(id: Int, name: String,  start_val: Double, current_val: Double) {
        var id by property(id)
        fun idProperty() = getProperty(Stock::id)

    //    var name by property(name)
    //    fun nameProperty() = getProperty(Stock::name)
        private val nameProperty = SimpleStringProperty(name)
        fun nameProperty() = nameProperty
        var name by nameProperty


        var start_val by property(start_val)
        fun start_valProperty() = getProperty(Stock::start_val)

        var current_val by property(current_val)
        fun current_valProperty() = getProperty(Stock::current_val)

    }


    public val stocks = FXCollections.observableArrayList<Stock>(
        Stock(1, "BP", 123.1, return_price("BP.L") ?:0.0),
        Stock(2, "CLIG.L", 123.1, 23.5),
        Stock(3, "DLG", 123.1, 23.5),
        Stock(4, "EVR", 123.1, 23.5),
        Stock(5, "FIS", 123.1, 23.5),
        Stock(6, "IAG", 123.1, 23.5),
        Stock(7, "PFE", 123.1, 23.5)
    )


    override val root =  vbox{

         val side = hbox {
             button("Update Prices") {
                 textFill = Color.RED
                 action { button_pressed1() }
             }
             button("Press Me") {
                 textFill = Color.GREEN
                 action { button_pressed2() }
             }

         }

        tableview(stocks) {
            isEditable = true
            column("ID", Stock::idProperty).makeEditable()
            column("Name", Stock::nameProperty).makeEditable()
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
    private fun button_pressed1(){
        for (current in stocks){
            current.current_val = return_price(current.name) ?: 0.0
            current.stock_currency = return_currency(current.name) ?: "NA"
        }
    }
    private fun button_pressed2(){
        for (current in stocks){
            current.current_val = return_price(current.name) ?: 0.0
        }
    }
}


