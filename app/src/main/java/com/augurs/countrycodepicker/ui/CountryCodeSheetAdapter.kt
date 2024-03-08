package com.augurs.countrycodepicker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.augurs.countrycodepicker.R
import com.augurs.countrycodepicker.models.CountryCodeDataModel
import com.google.android.material.card.MaterialCardView

class CountryCodeSheetAdapter(
    private val list: List<CountryCodeDataModel>,
    private val onClick: (CountryCodeDataModel?) -> Unit
): RecyclerView.Adapter<CountryCodeSheetAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view.rootView){
        val img: ImageView
        val name: TextView
        val code: TextView
        val root: MaterialCardView
        init {
            img = view.findViewById(R.id.country_code_list_flag_img)
            name = view.findViewById(R.id.country_code_list_name_tv)
            code = view.findViewById(R.id.country_code_list_dial_code_tv)
            root = view.findViewById(R.id.country_code_list_root)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_code_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.name.text = data.name
        holder.code.text = data.dialCode
        holder.img.setImageDrawable(data.data?.imageDrawable)
        holder.root.setOnClickListener {
            onClick(data)
        }
    }
}