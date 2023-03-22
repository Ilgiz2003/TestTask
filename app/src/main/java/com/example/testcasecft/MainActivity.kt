package com.example.testcasecft

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.testcasecft.data_classes.BankCardInfo
import com.example.testcasecft.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://lookup.binlist.net/"
    private var inputList = mutableListOf<Number>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val bankCardInfoApi = retrofit.create(ApiInterface::class.java)
        setupAutoTextComplete()

        binding.getInformation.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val editText = if (isEditTextEmpty(binding.editTextNumber)) {
                    "1"
                } else {
                    binding.editTextNumber.text.toString()
                }
                val bankCardInfo: BankCardInfo? = try {
                    bankCardInfoApi.getInfo(editText)
                } catch (e: HttpException) {
                    null
                }
                Log.d("testLog", inputList.toString())
                runOnUiThread {
                    fillInputList(binding.editTextNumber)
                    fillFields(bankCardInfo, binding)
                }
            }
        }

        binding.bankUrl.setOnClickListener {
            goToBankUrl(binding.bankUrl.text.toString().drop(5))
        }
        binding.bankPhone.setOnClickListener {
            callBankTelephone(binding.bankPhone.text.toString().drop(7))
        }
        binding.latitude.setOnClickListener {
            goToMaps(
                binding.latitude.text.toString().drop(10),
                binding.longitude.text.toString().drop(11)
            )
        }
        binding.longitude.setOnClickListener {
            goToMaps(
                binding.latitude.text.toString().drop(10),
                binding.longitude.text.toString().drop(11)
            )
        }

    }

    private fun fillFields(bankCardInfo: BankCardInfo?, binding: ActivityMainBinding) {
        binding.length.text = resources.getText(R.string.length)
        binding.length.text =
            binding.length.text.toString() + " " + bankCardInfo?.number?.length.toString()

        binding.luhn.text = resources.getText(R.string.luhn)
        binding.luhn.text =
            binding.luhn.text.toString() + " " + bankCardInfo?.number?.luhn.toString()

        binding.scheme.text = resources.getText(R.string.scheme)
        binding.scheme.text = binding.scheme.text.toString() + " " + bankCardInfo?.scheme.toString()

        binding.type.text = resources.getText(R.string.type)
        binding.type.text = binding.type.text.toString() + " " + bankCardInfo?.type.toString()

        binding.brand.text = resources.getText(R.string.brand)
        binding.brand.text = binding.brand.text.toString() + " " + bankCardInfo?.brand.toString()

        binding.prepaid.text = resources.getText(R.string.prepaid)
        binding.prepaid.text =
            binding.prepaid.text.toString() + " " + bankCardInfo?.prepaid.toString()

        binding.numeric.text = resources.getText(R.string.numeric)
        binding.numeric.text =
            binding.numeric.text.toString() + " " + bankCardInfo?.country?.numeric.toString()

        binding.alpha2.text = resources.getText(R.string.alpha2)
        binding.alpha2.text =
            binding.alpha2.text.toString() + " " + bankCardInfo?.country?.alpha2.toString()

        binding.countryName.text = resources.getText(R.string.countryName)
        binding.countryName.text =
            binding.countryName.text.toString() + " " + bankCardInfo?.country?.name.toString()

        binding.emoji.text = resources.getText(R.string.emoji)
        binding.emoji.text =
            binding.emoji.text.toString() + " " + bankCardInfo?.country?.emoji.toString()

        binding.currency.text = resources.getText(R.string.currency)
        binding.currency.text =
            binding.currency.text.toString() + " " + bankCardInfo?.country?.currency.toString()

        binding.latitude.text = resources.getText(R.string.latitude)
        binding.latitude.text =
            binding.latitude.text.toString() + " " + bankCardInfo?.country?.latitude.toString()

        binding.longitude.text = resources.getText(R.string.longitude)
        binding.longitude.text =
            binding.longitude.text.toString() + " " + bankCardInfo?.country?.longitude.toString()

        binding.bankName.text = resources.getText(R.string.nameBank)
        binding.bankName.text =
            binding.bankName.text.toString() + " " + bankCardInfo?.bank?.name.toString()

        binding.bankUrl.text = resources.getText(R.string.url)
        binding.bankUrl.text =
            binding.bankUrl.text.toString() + " " + bankCardInfo?.bank?.url.toString()

        binding.bankPhone.text = resources.getText(R.string.phone)
        binding.bankPhone.text =
            binding.bankPhone.text.toString() + " " + bankCardInfo?.bank?.phone.toString()

        binding.city.text = resources.getText(R.string.city)
        binding.city.text = binding.city.text.toString() + " " + bankCardInfo?.bank?.city.toString()
    }

    private fun goToBankUrl(url: String) {
        if (!url.endsWith("null")) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://$url"))
            startActivity(browserIntent)
        }
    }

    private fun callBankTelephone(telephone: String) {
        if (!telephone.endsWith("null")) {
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$telephone"))
            startActivity(callIntent)
        }
    }

    private fun goToMaps(latitude: String, longitude: String) {
        if (latitude != "null" && longitude != "null") {
            val mapIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.co.in/maps?q=$latitude,$longitude")
            )
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    private fun isEditTextEmpty(editText: EditText): Boolean {
        return editText.text.toString().isEmpty()
    }

    private fun fillInputList(editText: EditText) {
        if(!isEditTextEmpty(editText)){
            if (inputList.size == 6) {
                inputList.removeAt(5);
            }
            inputList.add(0, editText.text.toString().toInt())
        }
        binding.editTextNumber.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                inputList
            )
        )

    }

    private fun setupAutoTextComplete() {
        binding.editTextNumber.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                inputList
            )
        )
        binding.editTextNumber.threshold = 0
        binding.editTextNumber.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (v.windowVisibility != View.INVISIBLE) {
                    return@OnFocusChangeListener
                }
                if (hasFocus){
                    binding.editTextNumber.showDropDown()
                }
                else{
                    binding.editTextNumber.dismissDropDown()
                }
            }
        binding.editTextNumber.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                binding.editTextNumber.showDropDown()
            }
            false
        })
    }

}