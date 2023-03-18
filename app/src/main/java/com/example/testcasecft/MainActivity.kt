package com.example.testcasecft

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testcasecft.data_classes.BankCardInfo
import com.example.testcasecft.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)



        binding.getInformation.setOnClickListener{
            val apiInterface = ApiInterface.create().getInfo(binding.editTextNumber.text.toString())

            apiInterface.enqueue( object : Callback<BankCardInfo> {
                override fun onResponse(call: Call<BankCardInfo>, response: Response<BankCardInfo>) {
                    fillFields(response, binding)
                    Log.d("TestLog", binding.editTextNumber.text.toString())
                }
                override fun onFailure(call: Call<BankCardInfo>, t: Throwable) {
                    Log.d("TestLog", t.toString())
                }

            })
        }
        setContentView(binding.root)
    }

    fun fillFields(response: Response<BankCardInfo>, binding: ActivityMainBinding){
        binding.length.text = resources.getText(R.string.length)
        binding.length.text = binding.length.text.toString() + " " + response.body()?.number?.length.toString()

        binding.luhn.text = resources.getText(R.string.luhn)
        binding.luhn.text = binding.luhn.text.toString() + " " + response.body()?.number?.luhn.toString()

        binding.scheme.text = resources.getText(R.string.scheme)
        binding.scheme.text = binding.scheme.text.toString() + " " + response.body()?.scheme.toString()

        binding.type.text = resources.getText(R.string.type)
        binding.type.text = binding.type.text.toString() + " " + response.body()?.type.toString()

        binding.brand.text = resources.getText(R.string.brand)
        binding.brand.text = binding.brand.text.toString() + " " + response.body()?.brand.toString()

        binding.prepaid.text = resources.getText(R.string.prepaid)
        binding.prepaid.text = binding.prepaid.text.toString() + " " + response.body()?.prepaid.toString()

        binding.numeric.text = resources.getText(R.string.numeric)
        binding.numeric.text = binding.numeric.text.toString() + " " + response.body()?.country?.numeric.toString()

        binding.alpha2.text = resources.getText(R.string.alpha2)
        binding.alpha2.text = binding.alpha2.text.toString() + " " + response.body()?.country?.alpha2.toString()

        binding.countryName.text = resources.getText(R.string.countryName)
        binding.countryName.text = binding.countryName.text.toString() + " " + response.body()?.country?.name.toString()

        binding.emoji.text = resources.getText(R.string.emoji)
        binding.emoji.text = binding.emoji.text.toString() + " " + response.body()?.country?.emoji.toString()

        binding.currency.text = resources.getText(R.string.currency)
        binding.currency.text = binding.currency.text.toString() + " " + response.body()?.country?.currency.toString()

        binding.latitude.text = resources.getText(R.string.latitude)
        binding.latitude.text = binding.latitude.text.toString() + " " + response.body()?.country?.latitude.toString()

        binding.longitude.text = resources.getText(R.string.longitude)
        binding.longitude.text = binding.longitude.text.toString() + " " + response.body()?.country?.longitude.toString()

        binding.bankName.text = resources.getText(R.string.nameBank)
        binding.bankName.text = binding.bankName.text.toString() + " " + response.body()?.bank?.name.toString()

        binding.bankUrl.text = resources.getText(R.string.url)
        binding.bankUrl.text = binding.bankUrl.text.toString() + " " + response.body()?.bank?.url.toString()

        binding.bankPhone.text = resources.getText(R.string.phone)
        binding.bankPhone.text = binding.bankPhone.text.toString() + " " + response.body()?.bank?.phone.toString()

        binding.city.text = resources.getText(R.string.city)
        binding.city.text = binding.city.text.toString() + " " + response.body()?.bank?.city.toString()
    }
    
}