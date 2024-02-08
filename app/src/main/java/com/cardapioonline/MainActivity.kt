package com.cardapioonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cardapioonline.adapter.OptionsAdapter
import com.cardapioonline.databinding.ActivityMainBinding
import com.cardapioonline.model.Option
import java.text.NumberFormat
import java.util.Currency

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var appetizerAdapter: OptionsAdapter
    private lateinit var mainCourseAdapter: OptionsAdapter
    private lateinit var drinksAdapter: OptionsAdapter
    private lateinit var dessertAdapter: OptionsAdapter

    private val appetizerOptions: MutableList<Option> = mutableListOf()
    private val mainCourseOptions: MutableList<Option> = mutableListOf()
    private val drinksOptions: MutableList<Option> = mutableListOf()
    private val dessertOptions: MutableList<Option> = mutableListOf()

    private val selectedOptions: MutableList<Option> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerViewAppetizer = binding.recyclerViewAppetizerOptions
        recyclerViewAppetizer.layoutManager = LinearLayoutManager(this)
        recyclerViewAppetizer.setHasFixedSize(true)

        appetizerAdapter = OptionsAdapter(this, appetizerOptions)
        appetizerAdapter.onItemClickListener = {
            calculateTotals(it)
        }
        recyclerViewAppetizer.adapter = appetizerAdapter

        val recyclerViewMainCourse = binding.recyclerViewMainCourseOptions
        recyclerViewMainCourse.layoutManager = LinearLayoutManager(this)
        recyclerViewMainCourse.setHasFixedSize(true)

        mainCourseAdapter = OptionsAdapter(this, mainCourseOptions)
        mainCourseAdapter.onItemClickListener = {
            calculateTotals(it)
        }
        recyclerViewMainCourse.adapter = mainCourseAdapter

        val recyclerViewDrinks = binding.recyclerViewDrinksOptions
        recyclerViewDrinks.layoutManager = LinearLayoutManager(this)
        recyclerViewDrinks.setHasFixedSize(true)

        drinksAdapter = OptionsAdapter(this, drinksOptions)
        drinksAdapter.onItemClickListener = {
            calculateTotals(it)
        }
        recyclerViewDrinks.adapter = drinksAdapter

        val recyclerViewDessert = binding.recyclerViewDessertOptions
        recyclerViewDessert.layoutManager = LinearLayoutManager(this)
        recyclerViewDessert.setHasFixedSize(true)

        dessertAdapter = OptionsAdapter(this, dessertOptions)
        dessertAdapter.onItemClickListener = {
            calculateTotals(it)
        }
        recyclerViewDessert.adapter = dessertAdapter

        binding.buttonCheckout.setOnClickListener {
            finishOrder()
        }

        loadOptionsList()
    }

    private fun loadOptionsList() {
        val carpaccio = Option(R.drawable.carpaccio, "Carpaccio de Abobrinha", 18.90, 10)
        val bruschetta =
            Option(R.drawable.bruschetta, "Bruschetta de Tomate e Manjericão", 22.50, 12)
        val croquettes = Option(R.drawable.croquettes, "Croquetes de Queijo e Ervas", 26.80, 15)
        val turnovers = Option(R.drawable.turnovers, "Mini Pasteis de Camarão", 32.50, 18)

        appetizerOptions.add(carpaccio)
        appetizerOptions.add(bruschetta)
        appetizerOptions.add(croquettes)
        appetizerOptions.add(turnovers)

        val risotto = Option(R.drawable.risotto, "Risoto de Cogumelos Trufado", 45.90, 20)
        val filet = Option(R.drawable.filet, "Filé Mignon ao Molho de Vinho", 58.50, 25)
        val spaghetti = Option(R.drawable.spaghetti, "Espaguete de Frutos do Mar", 52.80, 22)
        val lasagna = Option(R.drawable.lasagna, "Lasanha de Berinjela", 20.90, 10)

        mainCourseOptions.add(risotto)
        mainCourseOptions.add(filet)
        mainCourseOptions.add(spaghetti)
        mainCourseOptions.add(lasagna)

        val mojito = Option(R.drawable.mojito, "Mojito de Manga e Hortelã", 17.90, 5)
        val mocktail = Option(R.drawable.mocktail, "Mocktail de Morango e Hortelã", 12.50, 3)

        drinksOptions.add(mojito)
        drinksOptions.add(mocktail)

        val mousse = Option(R.drawable.mousse, "Mousse de Chocolate Belga", 19.90, 10)
        val pudding = Option(R.drawable.pudding, "Pudim de Leite Condensado", 16.50, 8)
        val tiramisu = Option(R.drawable.tiramisu, "Tiramisu Tradicional", 24.80, 15)
        val brulle = Option(R.drawable.brulle, "Creme Brûlée de Baunilha", 22.50, 12)

        dessertOptions.add(mousse)
        dessertOptions.add(pudding)
        dessertOptions.add(tiramisu)
        dessertOptions.add(brulle)
    }

    private fun calculateTotals(option: Option) {
        if (option.isChecked) {
            if (!selectedOptions.contains(option)) {
                selectedOptions.add(option)
            }
        } else {
            selectedOptions.remove(option)
        }

        updateTotalPrice()
        updateTotalTime()
    }

    private fun getSelectedOptionsPrice(): Double {
        var price = 0.0

        selectedOptions.forEach {
            price += it.price
        }

        return price
    }

    private fun updateTotalPrice() {
        val totalPrice = getSelectedOptionsPrice()
        val formatter = NumberFormat.getCurrencyInstance()
        formatter.currency = Currency.getInstance("BRL")

        binding.textTotalPrice.text =
            formatter.format(totalPrice).replace(".", ",").replace("R$", "R$ ")
    }

    private fun getSelectedOptionsTime(): Int {
        var time = 0

        selectedOptions.forEach {
            time += it.time
        }

        return time
    }

    private fun updateTotalTime() {
        val totalTime = getSelectedOptionsTime()
        binding.textTotalTime.text = "$totalTime minutos"
    }

    private fun finishOrder() {
        val totalPrice = getSelectedOptionsPrice()

        if (totalPrice > 0) {
            val formatter = NumberFormat.getCurrencyInstance()
            formatter.currency = Currency.getInstance("BRL")

            val message =
                "O pedido foi enviado ao balcão. O valor total é " + formatter.format(totalPrice)

            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Selecione pelo menos uma opção", Toast.LENGTH_SHORT).show()
        }
    }
}