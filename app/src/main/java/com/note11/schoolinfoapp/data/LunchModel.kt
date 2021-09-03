package com.note11.schoolinfoapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LunchModel(
    @SerializedName("MMEAL_SC_CODE") val mealCode: String, // 1: 조식, 2: 중식, 3: 석식
    @SerializedName("MLSV_YMD") var mealDate: String,
    @SerializedName("DDISH_NM") var menu: String
) : Parcelable
