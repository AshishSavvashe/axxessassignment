package demo.parttimepayments.com.axxessasignment.view

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders.of
import androidx.recyclerview.widget.GridLayoutManager
import demo.parttimepayments.com.axxessasignment.R
import demo.parttimepayments.com.axxessasignment.adapters.RecyclerViewAdapter
import demo.parttimepayments.com.axxessasignment.databinding.FragmentHomeBinding
import demo.parttimepayments.com.axxessasignment.responsemodel.Image
import demo.parttimepayments.com.axxessasignment.viewmodel.ImagesDataViewModel


class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private var vm: ImagesDataViewModel?=null
    private var imageList: ArrayList<Image>?=null
    private var recyclerViewAdapter:RecyclerViewAdapter ?=null
    private val DELAY: Long = 250
    private var searchTask : Runnable ?=null
    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        init()
        return binding?.root
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        imageList = ArrayList()

        vm = of(this@HomeFragment).get(ImagesDataViewModel::class.java)

        binding!!.edtSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                mHandler.removeCallbacks(searchTask!!);
                mHandler.postDelayed(searchTask!!, DELAY)
            }
        })

        searchTask = Runnable {
            callApi()
        }

        vm!!.allImageShapes?.observe(activity!!, Observer {
            if (it.isNotEmpty()) {
                hidekeyBoard()
                binding!!.rvImageData.visibility = View.VISIBLE
                binding!!.rvImageData.setHasFixedSize(true)
                imageList = it as ArrayList<Image>?
                recyclerViewAdapter!!.updateList(it)
            } else {
                binding!!.rvImageData.visibility = View.GONE
            }
        })

        binding!!.rvImageData.layoutManager = GridLayoutManager(context,4)
        recyclerViewAdapter  = RecyclerViewAdapter(){ selectedItem ->
            var fragment = ResultFragment()
            var bundle = Bundle()
            bundle.putString("image_url", selectedItem.link)
            bundle.putString("image_id", selectedItem.id)
            fragment.arguments = bundle
            selectedItem.title?.let { title ->
                (context as MainActivity).launchFragment(
                    fragment,
                    "Title"
                )
            }
        }
        binding!!.rvImageData.adapter = recyclerViewAdapter

    }

    fun callApi(){
        vm!!.getDataFromNetwork(binding?.edtSearch?.text.toString(),context)
    }

    fun hidekeyBoard() {

        val inputMethodManager = activity!!.getSystemService(
            Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity!!.currentFocus != null)
            inputMethodManager.hideSoftInputFromWindow(
                activity!!.currentFocus!!.windowToken, 0)
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}