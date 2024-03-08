package com.augurs.countrycodepicker.ui

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.augurs.countrycodepicker.R
import com.augurs.countrycodepicker.models.CountryCodeDataModel
import com.augurs.countrycodepicker.utils.CountryCodeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CountryCodePicker @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var mView: View? = null
    private var tv: TextView? = null
    private var img: ImageView? = null
    private val dataList = ArrayList<CountryCodeDataModel>()
    private var fragmentManager: FragmentManager? = null

    private var selectedCountry: CountryCodeDataModel? = null

    private var countryDataLoaded = false

    init {
        setWillNotDraw(false)
        mView = LayoutInflater.from(context).inflate(R.layout.country_code_view,this,false)
        tv = mView?.findViewById(R.id.country_code_tv)
        img = mView?.findViewById(R.id.country_flag_img)
        initListener()
        CoroutineScope(Dispatchers.Main).launch {
            dataList.clear()
            withContext(Dispatchers.IO){dataList.addAll(CountryCodeUtils.getCountryCodes(context))}
            countryDataLoaded = true
            loadView()
        }
        addView(mView)
    }

    fun init(fm: FragmentManager){
        fragmentManager = fm
    }

    private fun initListener(){
        mView?.rootView?.setOnClickListener {
            if (fragmentManager == null){
                throw Exception("Not initialized use init method")
            }else{
                val sheet = CountryCodeBottomSheet(dataList){
                    it?.let { data ->
                        selectedCountry = data
                        invalidate()
                        requestLayout()
                    }
                }
                sheet.show(fragmentManager!!,"")
            }
        }
    }

    private fun loadView(){
        if (dataList.isNotEmpty()){
            tv?.text = "${selectedCountry?.code} ${selectedCountry?.dialCode}"
            img?.setImageDrawable(selectedCountry?.data?.imageDrawable)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        loadView()
    }

    fun getCountryCodeWithPlus(): String = selectedCountry?.dialCode?:""

    fun getCountryCodeWithoutPlus(): String = selectedCountry?.dialCode?.removePrefix("+")?:""

    /**
     * set country name example: IN
     */
    fun setDefaultCountryByName(countryName: String){
        CoroutineScope(Dispatchers.Main).launch {
            val data = withContext(Dispatchers.IO){CountryCodeUtils.getCountryCodes(context).find { f -> f.code == countryName.trim().uppercase() }}
            data?.let {
                selectedCountry = it
                invalidate()
                requestLayout()
            }
        }
    }

    /**
     * set country code example: +91
     */
    fun setDefaultCountryByCode(countryCode: String){
        CoroutineScope(Dispatchers.Main).launch {
            val data = withContext(Dispatchers.IO){CountryCodeUtils.getCountryCodes(context).find { f -> f.dialCode == countryCode.trim() }}
            data?.let {
                selectedCountry = it
                invalidate()
                requestLayout()
            }
        }
    }
}