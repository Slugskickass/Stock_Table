package com.example.demo.view

import com.example.demo.app.Styles
import javafx.collections.FXCollections
import tornadofx.*



class MainView : View("Stocks") {

    class Stock(val id: Int, val name: String, val start_val: Double, val current_val: Double) {}

    private val stocks = FXCollections.observableArrayList<Stock>(
        Stock(1, "BP", 123.1, 23.5),
        Stock(2, "DLG", 123.1, 23.5),
        Stock(3, "PFE", 123.1, 23.5)
    )


    override val root =  tableview(stocks) {
            isEditable = true
        readonlyColumn("ID",Stock::id)
        readonlyColumn("Name",Stock::name)
        readonlyColumn("Start Value",Stock::start_val)
        readonlyColumn("Current Value",Stock::current_val)
        }
    }

