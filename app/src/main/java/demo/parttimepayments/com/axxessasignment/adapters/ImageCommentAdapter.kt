package demo.parttimepayments.com.axxessasignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import demo.parttimepayments.com.axxessasignment.R
import demo.parttimepayments.com.axxessasignment.databinding.ImageCommentItemBinding
import demo.parttimepayments.com.axxessasignment.databinding.ItemImageShapesBinding
import demo.parttimepayments.com.axxessasignment.responsemodel.Image
import demo.parttimepayments.com.axxessasignment.responsemodel.ImageComments


class ImageCommentAdapter() : RecyclerView.Adapter<ImageCommentAdapter.ViewHolder>()  {
    private lateinit var mContext: Context

    var list: List<ImageComments> =  mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        return ViewHolder(ImageCommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvComments.text =list[position].comments

    }

    fun setData(list: List<ImageComments>?) {
        this.list = list!!
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ImageCommentItemBinding) :
        RecyclerView.ViewHolder(binding.root){}

}