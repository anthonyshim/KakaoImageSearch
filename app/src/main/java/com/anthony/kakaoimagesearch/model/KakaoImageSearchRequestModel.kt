package com.anthony.kakaoimagesearch.model

data class KakaoImageSearchRequestModel (
        var query: String,   // 검색을 원하는 질의어, 필수
        var sort: String,    // 결과 문서 정렬 방식, Optional(accuracy), accuracy(정확도순) /recency(최신순)
        var page: Int,       // 결과 페이지 번호, Optional(기본 1), 1-50 사이 Integer
        var size: Int       // 한 페이지에 보여질 문서의 개수, Optional(기본 80), 1-80 사이 Integer
)