package com.note11.schoolinfoapp.ui.screen.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.note11.schoolinfoapp.R
import com.note11.schoolinfoapp.data.LunchModel
import com.note11.schoolinfoapp.data.SubjectModel
import com.note11.schoolinfoapp.databinding.ActivitySplashBinding
import com.note11.schoolinfoapp.ui.base.BaseActivity
import com.note11.schoolinfoapp.ui.screen.first.welcome.WelcomeActivity
import com.note11.schoolinfoapp.ui.screen.main.MainActivity
import com.note11.schoolinfoapp.util.DataUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loaded.observe(this, {
            // todo : Q3. it이 2라면 gotoMain 함수를 실행해주는데
            //  이때 viewModel.subjectList, viewModel.lunchList를 인수로 받는다.
            if (it) goToMain(viewModel.subjectList, viewModel.lunchList)
        })

        // todo : Q1. loadData() 함수를 실행한다.
        loadData()
    }

    private fun loadData() = lifecycleScope.launch {
        val user = DataUtil(this@SplashActivity).getUserInfoOnce()

        if (user != null) {
            viewModel.getAllData(user)
        } else {
            // TODO: Q2. WelcomeActivity 로 이동해줍니다.
            goToWelcome()
        }
    }

    private fun goToWelcome() {
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }

    private fun goToMain(subjectList: List<SubjectModel>?, lunchList: List<LunchModel>?) {
        val lunchArrayList = viewModel.lunchListProcessing(lunchList)
        val subjectArrayList = arrayListOf<SubjectModel>()
        subjectList?.let { subjectArrayList.addAll(it) }

        Intent(this, MainActivity::class.java).also {
            it.putParcelableArrayListExtra("lunchList", lunchArrayList)
            it.putParcelableArrayListExtra("subjectList", subjectArrayList)

            lifecycleScope.launch {
                it.putExtra("storedTimeInfo", DataUtil(applicationContext).getTimeInfoOnce())
                delay(800)
                startActivity(it)
                finish()
            }
        }
    }
}