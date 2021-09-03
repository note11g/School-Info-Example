package com.note11.schoolinfoapp.ui.screen.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.note11.schoolinfoapp.R
import com.note11.schoolinfoapp.data.LunchModel
import com.note11.schoolinfoapp.data.SubjectModel
import com.note11.schoolinfoapp.data.TimeModel
import com.note11.schoolinfoapp.databinding.ActivityMainBinding
import com.note11.schoolinfoapp.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        getData()

        //todo : Q.18 initActivity() 함수를 실행한다.

    }

    private fun getData() {
        viewModel.timeInfo.observe(this, { viewModel.setTime() })
        viewModel.timeTableTime.observe(this, {
            viewModel.addTimeAtSubjectList()
            viewModel.checkNowPeriod()
        })

        intent?.run {
            val lunch = getParcelableArrayListExtra<LunchModel>("lunchList")?.toList()
            val subject = getParcelableArrayListExtra<SubjectModel>("subjectList")?.toList()
            val storedTimeInfo = getParcelableExtra<TimeModel>("storedTimeInfo")
            viewModel.run {
                lunch?.let { lunchList.value = it }
                subject?.let { subjectList.value = it }
                storedTimeInfo?.let { timeInfo.value = it }
            }
        }
    }

    private fun initActivity() {
        val subjectAdapter = SubjectAdapter()
        val lunchAdapter = LunchAdapter()

        binding.recyclerMainTime.let {
            //todo : Q19. 위 코드의 spanCount 숫자를 바꾸어보고, 숫자가 의미하는 것이 무엇일지 생각해봅시다.
            it.layoutManager = GridLayoutManager(this, 2)
            it.adapter = subjectAdapter
        }

        val layoutManager = LinearLayoutManager(this)

        //todo : Q20. 위 코드의 HORIZONTAL 을 VERTICAL 로 바꾸면 어떻게 될까요?
        // 한 번 예상해보고 코드를 바꿔 실행해 봅시다.
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        binding.recyclerMainLunch.let {
            it.layoutManager = layoutManager
            it.adapter = lunchAdapter
        }
    }
}
