package demo.com.axxessasignment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import demo.com.axxessasignment.R
import demo.com.axxessasignment.adapters.ImageCommentAdapter
import demo.com.axxessasignment.databinding.FragmentResultBinding
import demo.com.axxessasignment.responsemodel.ImageComments
import demo.com.axxessasignment.viewmodel.ImagesDataViewModel


class ResultFragment : Fragment() {

    private var binding: FragmentResultBinding? = null
    private var image_url :String?=null
    private var image_id :String?=null
    lateinit var mViewModel: ImagesDataViewModel
    private lateinit var adapter : ImageCommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        init()
        return binding?.root

    }

    private fun init() {

        mViewModel = ViewModelProviders.of(this).get(ImagesDataViewModel::class.java)

        if (arguments != null) {
            image_url = arguments!!.getString("image_url")
            image_id = arguments!!.getString("image_id")
        }

        if(image_url!!.isNotEmpty()) {
            Glide.with(context!!)
                .load(image_url)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(binding!!.image)
        } else {
            Glide.with(context!!)
                .load(R.drawable.no_image)
                .into(binding!!.image)
        }

        adapter = ImageCommentAdapter()
        binding!!.rvComments.layoutManager = LinearLayoutManager(context)
        binding!!.rvComments.adapter = adapter

        mViewModel.getCommentListByImageId(image_id!!)!!.observe(
            activity!!, Observer<List<ImageComments>> {
                adapter.setData(it)
            })

        binding!!.btnsubmit.setOnClickListener {
            if(binding!!.edCooment.text.isNullOrEmpty()){
                Toast.makeText(context, getString(R.string.enter_comment), Toast.LENGTH_SHORT).show()
            }else{
                val newEntry = ImageComments()
                newEntry.imageId = image_id!!
                newEntry.comments = binding!!.edCooment.text.toString().trim()
                mViewModel.insert(newEntry)
            }
        }
    }

    companion object {

    }
}