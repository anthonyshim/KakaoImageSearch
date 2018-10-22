package com.anthony.kakaoimagesearch.model

import android.os.Parcel
import android.os.Parcelable

data class ImageSearchResponseModel(
        val meta: Meta,
        val documents: List<Document>
)

data class Meta(
        var total_count: Int,       // 검색어에 검색된 문서수
        var pageable_count: Int,    // total_count 중에 노출가능 문서수
        var is_end: Boolean         // 현재 페이지가 마지막 페이지인지 여부. 값이 false이면 page를 증가시켜 다음 페이지를 요청할 수 있음.
)

data class Document(
        var collection: String,     // 컬렉션
        var thumbnail_url: String,  // 이미지 썸네일 URL
        var image_url: String,      // 이미지 URL
        var width: Int,             // 이미지의 가로 크기
        var height: Int,            // 이미지의 세로 크기
        var display_sitename: String,   // 출처명
        var doc_url: String,        // 문서 URL
        var datetime: String        // 문서 작성시간. ISO 8601. [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(collection)
        parcel.writeString(thumbnail_url)
        parcel.writeString(image_url)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeString(display_sitename)
        parcel.writeString(doc_url)
        parcel.writeString(datetime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Document> {
        override fun createFromParcel(parcel: Parcel): Document {
            return Document(parcel)
        }

        override fun newArray(size: Int): Array<Document?> {
            return arrayOfNulls(size)
        }
    }
}