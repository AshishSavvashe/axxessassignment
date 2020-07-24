package demo.com.axxessasignment.responsemodel



class ImageShapes (
     val id: Int,
     var data: List<Datum> ,
     var success: Boolean,
     var status: Long
)

data class Datum (
    val id: String,
    val title: String,
    val images: ArrayList<Image>?
)

data class Image (
    val id: String?,
    val title: String?,
    var link: String?
) {

}

