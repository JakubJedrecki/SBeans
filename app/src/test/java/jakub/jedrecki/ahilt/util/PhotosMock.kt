package jakub.jedrecki.ahilt.util

import jakub.jedrecki.ahilt.domain.model.PhotoItem

class PhotosMock {
    companion object {
        val photosList = listOf(
            PhotoItem(1,1,"title","url", "thumbnail"),
            PhotoItem(1,2,"title2","url2", "thumbnail2"),
            PhotoItem(1,3,"title3","url3", "thumbnail3"),
            PhotoItem(1,4,"title4","url4", "thumbnail4")
        )
    }
}