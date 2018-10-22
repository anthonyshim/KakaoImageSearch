package com.anthony.kakaoimagesearch.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.anthony.kakaoimagesearch.R
import com.anthony.kakaoimagesearch.common.BaseRecyclerViewAdapter
import com.anthony.kakaoimagesearch.common.EndlessRecyclerOnScrolInterface
import com.anthony.kakaoimagesearch.common.EndlessRecyclerOnScrollListener
import com.anthony.kakaoimagesearch.databinding.ActivityMainBinding
import com.anthony.kakaoimagesearch.main.detail.DetailActivity
import com.anthony.kakaoimagesearch.model.Document
import com.facebook.drawee.backends.pipeline.Fresco

class MainActivity : AppCompatActivity(), EndlessRecyclerOnScrolInterface {
    companion object {
        val TAG = MainActivity::class.java.canonicalName
    }

//    네이티브 앱 키
//    6776760ce546433cefb244302b8c0fe4
//    REST API 키
//    167c4fd5f2d25357f9e574e3079024d3
//    JavaScript 키
//    1b4ba5140d83df03f8936d1e0167f5c1
//    Admin 키
//    2bd9bc5440dba5c2744d30568b19be6f

//    사용자가 입력한 검색 키워드에 대한 이미지 검색 결과를 화면에 표시합니다.
////    이미지를 검색하면 리스트형태로 이미지를 표시합니다.
////    검색된 이미지를 터치 하면 상세 화면이 표시됩니다.
////    상세화면은 원본 이미지 한장만 표시합니다.(추가 정보는 자유)

    //    검색어 필드는 반드시 존재하며, 화면 상단에 표시합니다.
//    검색 버튼은 별도로 존재하지 않습니다.
//    1초 이상 검색어 입력이 없을 경우 검색을 수행합니다.
//    검색 결과가 없거나 오류가 발생한 경우 이를 사용자에게 알려주어야 합니다.
//    검색 결과를 표시하는 뷰는 어떤 뷰를 사용해도 무방합니다.
//    검색 결과로 표시되는 이미지의 가로 크기는 화면 폭과 동일하게 표시되며, 세로 크기는 이미지 비율에 맞게 조
//    정되어야 합니다.
//    검색 결과는 세로 스크롤 형태로 표시합니다.
//    검색 결과에 다음 페이지가 있을 경우 적절하게 표현되어야 합니다.

//    GET /v2/search/image HTTP/1.1
//    Host: dapi.kakao.com
//    Authorization: KakaoAK {app_key}

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this@MainActivity)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this@MainActivity, R.layout.activity_main).apply {
            mainViewModel = MainViewModel(this@MainActivity)
            viewModel = mainViewModel
            recycler.adapter = MainImageAdapter(object : BaseRecyclerViewAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, adapter: BaseRecyclerViewAdapter<*, *>) {
                    showDetail((adapter as MainImageAdapter).getItem(position))
                }

            })
            recycler.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            recycler.layoutManager = LinearLayoutManager(this@MainActivity).apply {
                orientation = LinearLayoutManager.VERTICAL
                endlessRecyclerOnScrollListener = EndlessRecyclerOnScrollListener(this) { mainViewModel.searchNextImage() }
                recycler.addOnScrollListener(endlessRecyclerOnScrollListener)
            }
        }
    }

    override fun loadingFinish() {
        endlessRecyclerOnScrollListener.setFinished()
    }

    override fun resetScroll() {
        endlessRecyclerOnScrollListener.reset()
    }

    private fun showDetail(document: Document) {
        DetailActivity.startActivity(this@MainActivity, document)
    }
}
