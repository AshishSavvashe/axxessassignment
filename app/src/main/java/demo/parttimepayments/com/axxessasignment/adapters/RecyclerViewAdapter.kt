package demo.parttimepayments.com.axxessasignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import demo.parttimepayments.com.axxessasignment.R
import demo.parttimepayments.com.axxessasignment.responsemodel.Image

class RecyclerViewAdapter(var mItemList: MutableList<Image> = mutableListOf(),  val callback: (Image) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private lateinit var mContext: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        mContext = parent.context

        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_shapes, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (viewHolder is ItemViewHolder) {
            populateItemRows(viewHolder, position)
        } else if (viewHolder is LoadingViewHolder) {
            showLoadingView(viewHolder, position)
        }
    }

    override fun getItemCount(): Int {
        return if (mItemList == null) 0 else mItemList!!.size
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        return if (mItemList!![position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    private inner class ItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvItem: ImageView

        init {
            tvItem = itemView.findViewById(R.id.imgShape)
        }
    }

    private inner class LoadingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }

    private fun showLoadingView(viewHolder: LoadingViewHolder, position: Int) {
        //ProgressBar would be displayed
    }

    private fun populateItemRows(viewHolder: ItemViewHolder, position: Int) {
        val item = mItemList!![position]

        if(mItemList[position].link!!.isNotEmpty()) {
            Glide.with(mContext)
                .load(mItemList[position].link)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(viewHolder.tvItem)
        } else {
            Glide.with(mContext)
                .load(R.drawable.no_image)
                .into(viewHolder.tvItem)
        }

        viewHolder.tvItem.setOnClickListener {
            callback.invoke(mItemList[position])
        }

    }

    fun updateList( list: List<Image>){
        this.mItemList.addAll(list)
        notifyDataSetChanged()
    }


}