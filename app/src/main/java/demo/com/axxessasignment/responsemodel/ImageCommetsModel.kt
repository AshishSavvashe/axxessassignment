package demo.com.axxessasignment.responsemodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_comments")
 open class ImageComments{

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "imageId")
    var imageId:String ? = ""

    @ColumnInfo(name = "comments")
    var comments:String? = ""

    constructor() {}

    constructor(imageId: String?, comments: String?) {
        this.imageId = imageId
        this.comments = comments
    }

}