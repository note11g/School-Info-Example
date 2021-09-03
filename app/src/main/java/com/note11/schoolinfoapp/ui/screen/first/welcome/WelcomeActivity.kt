package com.note11.schoolinfoapp.ui.screen.first.welcome

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.note11.schoolinfoapp.R
import com.note11.schoolinfoapp.databinding.ActivityWelcomeBinding
import com.note11.schoolinfoapp.ui.base.BaseActivity
import com.note11.schoolinfoapp.ui.screen.first.search.SearchActivity
import android.os.PowerManager
import android.provider.Settings


class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(R.layout.activity_welcome) {
    override val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //todo : Q5. btn_welcome_start 를 눌렀을 때 SearchActivity 로 이동하게 해줍시다.
        // 현재는 txt_welcome_content 를 눌렀을 때 SearchActivity 로 이동하게 되어있습니다.




        batteryOptimizationPopup()
    }

    private fun welcomClickGotoSearch(){
        binding.btnWelcomeStart.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    @SuppressLint("BatteryLife")
    private fun batteryOptimizationPopup() {
        val pm = getSystemService(POWER_SERVICE) as PowerManager

        if (pm.isIgnoringBatteryOptimizations(packageName)) return

        Intent().also {
            it.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            it.data = Uri.parse("package:$packageName")
            startActivity(it)
        }
    }
}