package com.note11.schoolinfoapp.ui.screen.first.time

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.note11.schoolinfoapp.R
import com.note11.schoolinfoapp.data.TimeModel
import com.note11.schoolinfoapp.data.UserModel
import com.note11.schoolinfoapp.databinding.ActivitySetTimeBinding
import com.note11.schoolinfoapp.ui.base.BaseActivity
import com.note11.schoolinfoapp.ui.screen.main.MainActivity
import com.note11.schoolinfoapp.ui.screen.splash.SplashActivity
import com.note11.schoolinfoapp.util.DataUtil
import com.note11.schoolinfoapp.util.alarm.NotificationUtil
import kotlinx.coroutines.launch

class SetTimeActivity : BaseActivity<ActivitySetTimeBinding>(R.layout.activity_set_time) {
    override val viewModel: SetTimeViewModel by viewModels()
    private lateinit var receivedInfo: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity()
    }

    private fun getUserInfo() {
        receivedInfo = intent.getParcelableExtra("userInfo")!!
    }

    private fun initActivity() {
        // todo : Q11. 전 액티비티에서 가져온 데이터를 이용해 유저 데이터를 여기서 불러오려 합니다.
        getUserInfo()

        binding.vm = viewModel

        viewModel.classBeforeLunch.observe(this, {
            if (it.toIntOrNull() != null) {
                viewModel.lunchEndPeriod.value = "${it.toInt() + 1}교시는 언제 시작하나요?"
            }
        })

        binding.btnTimeNext.setOnClickListener {
            //todo : Q12. id가 btn_time_next 인 버튼을 눌렀을 때 endToSetUp 함수를 실행해주려 합니다.
            endToSetUp()
        }
    }

    private fun endToSetUp() = let { act ->
        // todo : Q.13. 입력한 정보를 가져온다.
        val inputTime = viewModel.getTimesByModel()

        if (inputTime == null) {
            // todo : Q14. 시간을 모두 입력하지 않았을 때, 토스트 메시지를 띄워주려 합니다. 어떤 코드가 들어가야할까요?
            Toast.makeText(applicationContext, "입력하지 않은 값이 있습니다.", Toast.LENGTH_LONG).show()
        } else lifecycleScope.launch {
            DataUtil(act).run {
                setUserInfo(receivedInfo)
                setTimeInfo(inputTime)
            }
            NotificationUtil(applicationContext).notificationSetting(7, 45)

            //todo : Q.15 SplashActivity 로 이동한다.
            goToSplash()
        }
    }

    private fun goToSplash() {
        startActivity(Intent(this, SplashActivity::class.java))
        ActivityCompat.finishAffinity(this)
    }
}