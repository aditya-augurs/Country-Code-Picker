package com.augurs.countrycodepicker.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.augurs.countrycodepicker.R
import com.augurs.countrycodepicker.models.CountryCodeDataModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CountryCodeBottomSheet(
    private val list: List<CountryCodeDataModel>,
    private val onClick:(CountryCodeDataModel?) -> Unit
): BottomSheetDialogFragment() {

    private lateinit var searchView: SearchView
    private lateinit var rv: RecyclerView

    private val searchList = ArrayList<CountryCodeDataModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.country_code_bottom_sheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.country_code_search_view)
        rv = view.findViewById(R.id.country_code_rv)
        initListener()
        loadRv()
    }

    private fun initListener(){
        searchView.setOnQueryTextListener(object: OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchCountry(newText)
                return true
            }
        })
    }

    private fun loadRv(){
        searchList.clear()
        searchList.addAll(list)
        rv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rv.adapter = CountryCodeSheetAdapter(searchList){
            onClick(it)
            dismiss()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchCountry(search: String?){
        searchList.clear()
        if (search != null && search.trim().isNotEmpty()){
            val newList = list.filter { it.name?.lowercase()?.contains(search.trim().lowercase()) == true }
            searchList.addAll(newList)
        }else{
            searchList.addAll(list)
        }
        rv.adapter?.notifyDataSetChanged()
    }

}