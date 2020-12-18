package com.example.demo.view

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.scene.chart.PieChart
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableView
import javafx.scene.paint.Color
import tornadofx.*
import yahoofinance.YahooFinance
import yahoofinance.Stock
import java.math.BigDecimal
import javax.swing.event.ChangeListener

private fun return_price(name: String): Double? {
    val stocker = YahooFinance.get(name)?.quote?.price?.toDouble()
    return stocker
}

private fun return_currency(name: String): String? {
    val st_curr = YahooFinance.get(name)?.currency?.toString()
    return st_curr
}

val dollartopound = 0.7

class MainView : View("Stocks") {

    val available_currencies = FXCollections.observableArrayList<String>("GBp", "USD", "Euro")

    class Stock(
        id: Int,
        name: String,
        number_held: Int,
        stock_currency: String,
        start_val: Double,
        current_val: Double,
        current_return: Double,
        current_holding: Double
    ) {
        var id by property(id)
        fun idProperty() = getProperty(Stock::id)

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

        var current_holding by property(current_holding)
        fun current_holdingProperty() = getProperty(Stock::current_holding)


    }

    public var stocks = FXCollections.observableArrayList<Stock>(
        Stock(1, "BP.L", 100, "GBP", 238.90, 1.0, 1.0, 10.0),
        Stock(2, "CLIG.L", 100, "GBP", 439.50, 1.0, 1.0, 10.0),
        Stock(3, "EVR.L", 100, "GBP", 399.540, 1.0, 1.0, 10.0),
        Stock(4, "FIS", 3, "GBP", 151.8057, 1.0, 1.0, 10.0),
        Stock(5, "IAG.L", 100, "GBP", 154.54, 1.0, 1.0, 10.0),
        Stock(6, "PFE", 7, "GBP", 36.880, 1.0, 1.0, 10.0)
    )

    public val piedata = stocks.map { item -> PieChart.Data(item.name, item.current_holding) }.observable()

    init {
        stocks.addListener(ListChangeListener { change ->
            piedata.clear()
            stocks.forEach { piedata.add(PieChart.Data(it.name, it.current_holding)) }
        })
    }

    override val root =  vbox{

        val side = hbox {
            button("Update Prices") {
                textFill = Color.RED
                action { button_pressed1() }}

            button("Delete Row") {
                textFill = Color.GREEN
                action { button_pressed2() }}

            button("Add Row") {
                textFill = Color.GREEN
                action { button_pressed2() }}

            button("Load") {
                textFill = Color.BLUE
                action { load() }}

            button("Save") {
                textFill = Color.BLUE
                action { save() }}
            combobox<String> { items=available_currencies }
        }

        val mytable = tableview(stocks){
        //   mytable.setFixedCellSize(25)
        //   table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30))

           isEditable = true

            column("ID", Stock::idProperty)
            column("Name", Stock::nameProperty).makeEditable().useTextField()
            column("Number held", Stock::number_heldProperty).makeEditable()
            column("Currency", Stock::stock_currencyProperty)
            column("Start Value", Stock::start_valProperty).makeEditable()
            column("Current Value", Stock::current_valProperty)
            column("Current holding", Stock::current_holdingProperty)
            column("Current return", Stock::current_returnProperty).cellFormat {
                text = it.toString()
                style {
                    if (it < 1) {
                        textFill = Color.RED
                        backgroundColor += (Color.LIGHTGRAY)
                    } else {
                        textFill = Color.GREEN
                    }
                }
            }
        }

        val middle = hbox {
        var total_value: Double   = 0.0
//            for (items in stocks){values = values + items.current_return}
//            label(" The current return is $values")
//        total_value  = stocks.forEach( { it.current_return +=total_value })
        }

        piechart("Current Retun", piedata)
    }

    private fun button_pressed1(){
        for (current in stocks){
            current.current_val = return_price(current.name) ?: 0.0
            current.stock_currency = return_currency(current.name) ?: "NA"
            current.current_return = 100 * (( current.current_val.toDouble() / current.start_val.toDouble()) -1 )
            current.current_holding = current.number_held * current.current_val
            if (current.stock_currency == "GBp" ){current.current_holding =current.current_holding / 100}
            if (current.stock_currency == "USD" ){current.current_holding =current.current_holding * dollartopound}
        }
    }

    private fun button_pressed2(){
//
//        tableView().getColumns().get(0).setVisible(false);
//        tableView.getColumns().get(0).setVisible(true);
//
        }

    private fun load(){}

    private fun save(){}
    }



