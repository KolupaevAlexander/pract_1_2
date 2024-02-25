package com.example.zd1_1up

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.NavHostFragment
import com.example.prakt1_2_v1.DBHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var nameEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var spinner: Spinner
    private lateinit var addButton: Button
    private lateinit var datePicker:DatePicker
    private val expenseList = ArrayList<ExpenseItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentLayout=inflater.inflate(R.layout.fragment_1, container, false)
        val navController=NavHostFragment.findNavController(this)

        datePicker = fragmentLayout.findViewById<DatePicker>(R.id.date_Consumption)

        nameEditText = fragmentLayout.findViewById(R.id.name_edittext)
        dateEditText = fragmentLayout.findViewById(R.id.name_edittext)
        amountEditText = fragmentLayout.findViewById(R.id.sum_edittext)
        spinner = fragmentLayout.findViewById(R.id.spinner)
        addButton = fragmentLayout.findViewById(R.id.button_addConsumtoin)

        val db = DBHelper (requireContext())

        addButton.setOnClickListener {
            val check = db.getConsumption(nameEditText.text.toString())
            if (check == false)
            {
                addExpense()
            }
            else
            {
                Toast.makeText(activity, "Такие данные уже есть", Toast.LENGTH_SHORT).show()
            }
        }
        return fragmentLayout
    }
    fun onExpenseItemClick(expenseItem: ExpenseItem) {
        // Обработка нажатия на элемент списка
        val bundle = Bundle()
        bundle.putString("category",expenseItem.category)
        bundle.putString("name", expenseItem.name)
        bundle.putString("date", expenseItem.date)
        bundle.putString("amount", expenseItem.amount)

        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.action_fragment1_to_fragment2, bundle)
    }

    private fun addExpense() {
        var day = datePicker.dayOfMonth
        var month = datePicker.month + 1
        var year = datePicker.year
        var string_category = "${spinner.selectedItem.toString()}"
        var string_date = "$day.$month.$year"
        var string_name = nameEditText.text.trim().toString()
        var string_sum = amountEditText.text.trim().toString()

        if (string_category.isNotEmpty() && string_date.isNotEmpty() && string_name.isNotEmpty() && string_sum.isNotEmpty()) {
            val expenseItem = ExpenseItem(string_name,string_category,string_date,string_sum)
            expenseList.add(expenseItem)
            val db = DBHelper (requireContext())
            db.addConsumption(expenseItem)
            // Очищаем поля ввода
            nameEditText.text.clear()
            dateEditText.text.clear()
            amountEditText.text.clear()
        }
        else {
            Toast.makeText(activity, "Введите корректные данные", Toast.LENGTH_SHORT).show()
        }
    }
}