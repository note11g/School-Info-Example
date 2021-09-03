package com.note11.schoolinfoapp.ui.screen.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.note11.schoolinfoapp.data.LunchModel
import com.note11.schoolinfoapp.data.SubjectModel
import com.note11.schoolinfoapp.data.UserModel
import com.note11.schoolinfoapp.network.lunch.LunchManager
import com.note11.schoolinfoapp.network.time.TimeManager
import com.note11.schoolinfoapp.util.DataUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.measureTimeMillis

class SplashViewModel : ViewModel() {
    var subjectList: List<SubjectModel>? = null
    var lunchList: List<LunchModel>? = null
    val loaded = MutableLiveData(false)

    fun getAllData(user: UserModel) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val subjects = async { TimeManager.getTimeTable(user) }
            val lunches = async { LunchManager.getLunch(user.schoolInfo) }
            subjectList = subjects.await()
            lunchList = lunches.await()
            loaded.postValue(true)
        } catch (e: Exception) {
            Log.e("getAllData", e.toString())
        }
    }

    fun lunchListProcessing(list: List<LunchModel>?): ArrayList<LunchModel> {
        val newList = arrayListOf<LunchModel>()

        if (list == null) return newList

        for (i in list.indices) if (list[i].mealCode == "2") {
            val item = list[i]
            val regex1 = Regex("""[\d.]""")
            val regex2 = Regex("""<br/>""")
            val newDate = Calendar.getInstance()
            val newDateText = item.mealDate
            var newMenuText = item.menu

            newMenuText = regex1.replace(newMenuText, "")
            newMenuText = regex2.replace(newMenuText, "\n")

            newDate.set(newDateText.substring(0, 4).toInt(), newDateText.substring(4, 6).toInt() - 1, newDateText.substring(6, 8).toInt())
            item.mealDate = SimpleDateFormat("M월 d일 (E)", Locale.KOREA).format(newDate.time)
            item.menu = newMenuText
            newList.add(item)
        }

        return newList
    }
}