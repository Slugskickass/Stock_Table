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

    class Stock(id: Int, name: String,  number_held: Int, stock_currency: String, start_val: Double, current_val: Double, current_return: Double) {
        var id by property(id)
        fun idProperty() = getProperty(Stock::id)

        //    var name by property(name)
        //    fun nameProperty() = getProperty(Stock::name)
        private val nameProperty = SimpleStringProperty(name)
        fun nameProperty() = nameProperty
        var name by nameProperty

        var number_held by property(number_held)
        fun number_heldProperty() = getProperty(Stock::number_held)

        var stock_currency by property(stock_currency)
        fun stock_currencyProperty() = getProperty(Stock::stock_currency)

        var start_val by property(start_val)
        fun start_valProperty() = getProperty(Stock::start_val)

        var current_val by property(current_val)
        fun current_valProperty() = getProperty(Stock::current_val)

        var current_return by property(current_return)
        fun current_returnProperty() = getProperty(Stock::current_return)

    }


    public val stocks = FXCollections.observableArrayList<Stock>(
        Stock(1, "BP.L", 1,"GBP", 238.90, 1.0, 1.0),
        Stock(2, "CLIG.L", 1,"GBP", 439.50, 1.0, 1.0),
        Stock(3, "EVR.L",  1,"GBP", 399.540, 1.0, 1.0),
        Stock(4, "FIS",  1,"GBP",151.8057, 1.0, 1.0),
        Stock(5, "IAG.L",  1,"GBP",154.54, 1.0, 1.0),
        Stock(6, "PFE", 1,"GBP", 36.880, 1.0, 1.0)
    )//return_price("BP.L" ?:0.0),


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
            column("Number held", Stock::number_heldProperty).makeEditable()
            column("Currency", Stock::stock_currencyProperty).makeEditable()
            column("Start Value", Stock::start_valProperty).makeEditable()
            column("Current Value", Stock::current_valProperty).makeEditable()
            column("Current return", Stock::current_returnProperty).makeEditable().cellFormat {
                text = it.toString()
                style {
                    if (it < 1) {
                        textFill = Color.RED
                    } else {
                        textFill = Color.GREEN
                    }
                }
            }
        }
 //       piechart("Value") {
 //           for (current in stocks){
 //               data(current.name, 10.0)
 //           }
//        }



    }
    private fun button_pressed1(){
        for (current in stocks){
            current.current_val = return_price(current.name) ?: 0.0
            current.stock_currency = return_currency(current.name) ?: "NA"
            current.current_return = 100 * (( current.current_val.toDouble() / current.start_val.toDouble()) -1 )
    //        print(current.name)
    //        print(' ')
    //        println(current.current_val.toDouble() / current.start_val.toDouble() )

        }
    }
    private fun button_pressed2(){
        for (current in stocks){
            current.current_val = return_price(current.name) ?: 0.0
            current.stock_currency = return_currency(current.name) ?: "NA"
        }
    }
}


