package demo.parttimepayments.com.axxessasignment.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import demo.parttimepayments.com.axxessasignment.responsemodel.ImageComments

@Dao
 interface ImageCommentsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(imageShapes: ImageComments)

    @Query("SELECT * FROM image_comments WHERE imageId= :imageId")
    fun getCommentListByImageId(imageId: String): LiveData<List<ImageComments>>

   @Query("DELETE FROM image_comments ")
    fun deleteAll()
}

