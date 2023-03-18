package com.example.testcasecft.data_classes

import com.google.gson.annotations.SerializedName

data class Bank(
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("url"   ) var url   : String? = null,
    @SerializedName("phone" ) var phone : String? = null,
    @SerializedName("city"  ) var city  : String? = null
)
