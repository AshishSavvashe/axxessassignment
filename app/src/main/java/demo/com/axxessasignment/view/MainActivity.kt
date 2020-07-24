package demo.com.axxessasignment.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.NetworkInfo.State.CONNECTED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import demo.com.axxessasignment.R
import demo.com.axxessasignment.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity :  AppCompatActivity() {

    private var binding: ActivityMainBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding!!.toolbar.toolbar)
        supportActionBar?.hide()

        /** for handling title on toolbar  */
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.hide()
                setToolBarTitle(getString(R.string.app_name))
            } else {

                supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount-1 ).name?.let {

                    if (supportFragmentManager.backStackEntryCount >0){
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        supportActionBar?.show()
                        supportActionBar!!.setHomeAsUpIndicator(R.drawable.arrow_back_24)
                        setToolBarTitle(it)
                    }
                }

            }

            binding!!.toolbar.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    onBackPressed()
                }
            })
        }

        //Check for instance and then add main fragment
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, HomeFragment.newInstance())
                .commit()
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("Data--->","on back pressed")
        val fm = supportFragmentManager
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
        }
    }

    open fun launchFragment(fragment: Fragment, title: String) {
        if (isNetworkAvailable()) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.content_frame, fragment, title)
                .addToBackStack(title)
                .commitAllowingStateLoss()

        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()

        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        val networkState = networkInfo.state
        return networkState == CONNECTED || networkState == NetworkInfo.State.CONNECTING
    }

    fun setToolBarTitle(title: String) {
        //To set toolbar title from the fragment classes
        binding!!.toolbar.toolbarTitle.text = title
    }

}