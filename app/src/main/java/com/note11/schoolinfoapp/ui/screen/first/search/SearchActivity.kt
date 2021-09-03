package com.note11.schoolinfoapp.ui.screen.first.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.note11.schoolinfoapp.R
import com.note11.schoolinfoapp.data.SchoolModel
import com.note11.schoolinfoapp.databinding.ActivitySearchBinding
import com.note11.schoolinfoapp.ui.base.BaseActivity
import com.note11.schoolinfoapp.ui.screen.first.select.SelectActivity
import kotlinx.coroutines.flow.*


class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {
    override val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        //todo : Q6. initRecyclerView 함수를 호출하는 코드를 작성해주세요.
        initRecyclerView()

        viewModel.searchQuery.observe(this, {
            //todo : Q7. 입력한 학교 이름의 길이가 1 보다 길때 학교를 추천해줍시다.
            inputBigSearch(it)
        })
    }

    private fun inputBigSearch(key : String){
        if (key.length > 1) viewModel.search()
    }

    private fun initRecyclerView() = with(binding) {
        val adapter = SearchAdapter { goNextStep(it) }
        rcvSearchList.layoutManager = LinearLayoutManager(this@SearchActivity)
        rcvSearchList.adapter = adapter
    }

    private fun goNextStep(info: SchoolModel) {

        //todo: Q8. info를 전달해주면서 SelectActivity 로 화면을 전환 시켜주려 합니다. 어떻게 해야할까요?
        gotoSelectPutInfo(info)
    }

    private fun gotoSelectPutInfo(info : SchoolModel){
        val intent = Intent(this, SelectActivity::class.java)
        intent.putExtra("schoolInfo", info)
        startActivity(intent)
    }
}
