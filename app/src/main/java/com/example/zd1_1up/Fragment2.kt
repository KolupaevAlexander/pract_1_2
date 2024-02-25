package com.example.zd1_1up

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
 * Use the [Fragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var recyclerView: RecyclerView
    lateinit var DBHelper : DBHelper
    private  lateinit var expenseList: ArrayList<ExpenseItem>
    private lateinit var adapter: ExpenseAdapter2
    private lateinit var delButton: Button
    lateinit var edittext: EditText
    lateinit var editButton: Button
    lateinit var expense: ExpenseItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }
    fun onExpenseItemClick(expenseItem: ExpenseItem) {
        val itemValue = expenseItem.name
        var amount: String = expenseItem.amount
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Вы выбрали: $itemValue")
            .setCancelable(true)
            .setPositiveButton("Удалить")
            { _, _ ->
                val result = DBHelper.deleteConsumption(itemValue)
                if (result)
                {
                    expenseList.remove(expenseItem)
                    update()
                }
            }
            .setNegativeButton("Редактировать")
            { _, _ ->
                expense = expenseItem
            }
        val dialog = builder.create()
        dialog.show()
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentLayout =inflater.inflate(R.layout.fragment_2, container, false)
        recyclerView = fragmentLayout.findViewById(R.id.recuclerview)
        delButton = fragmentLayout.findViewById(R.id.del_Consumtoins)
        edittext = fragmentLayout.findViewById(R.id.sum_edittext)
        editButton = fragmentLayout.findViewById(R.id.edit_Consumtoins)
        update()
        delButton.setOnClickListener {
            val result = DBHelper.deleteAllConsumption()
            if (result)
            {
                expenseList.clear()
                update()
                Toast.makeText(activity, "Все данные удалены", Toast.LENGTH_SHORT).show()
            }
        }

        editButton.setOnClickListener {
            val db = DBHelper (requireContext())
            var check = db.editConsumptionInfo(expense.name, expense.category, expense.date, edittext.text.toString())
            if (check)
            {
                update()
                Toast.makeText(activity, "Данные успешно обновлены", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(activity, "Данные успешно обновлены", Toast.LENGTH_SHORT).show()
            }
        }
        return fragmentLayout
    }
    fun update()
    {
        DBHelper = DBHelper(requireContext())
        expenseList = DBHelper.readallConsumption()
        adapter = ExpenseAdapter2(expenseList, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }
}
