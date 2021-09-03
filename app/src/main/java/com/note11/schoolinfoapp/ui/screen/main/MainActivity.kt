package com.note11.schoolinfoapp.ui.screen.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.note11.schoolinfoapp.R
import com.note11.schoolinfoapp.data.LunchModel
import com.note11.schoolinfoapp.data.SubjectModel
import com.note11.schoolinfoapp.data.TimeModel
import com.note11.schoolinfoapp.databinding.ActivityMainBinding
import com.note11.schoolinfoapp.ui.base.BaseActivity
import com.note11.schoolinfoapp.util.DataUtil
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        getData()
        initActivity()
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
            it.layoutManager = GridLayoutManager(this, 2)
            it.adapter = subjectAdapter
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        binding.recyclerMainLunch.let {
            it.layoutManager = layoutManager
            it.adapter = lunchAdapter
        }
    }
}
