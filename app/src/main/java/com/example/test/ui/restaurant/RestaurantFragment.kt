package com.example.test.ui.restaurant

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.BR
import com.example.test.R
import com.example.test.data.remote.model.response.location.LocationDataResponse
import com.example.test.data.remote.model.response.location.LocationSuggestions
import com.example.test.data.remote.model.response.restaurant.RestaurantDataResponse
import com.example.test.databinding.FragmentRestaurantBinding
import com.example.test.ui.MainActivity
import com.example.test.ui.base.BaseFragment
import com.example.test.utils.DrawablePosition
import com.example.test.utils.OnDrawableClickListener
import com.example.test.utils.SpaceItemDecoration
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_restaurant.*
import javax.inject.Inject


/**
 * Created by Android.Developer.
 * @version 1.0
 */
open class RestaurantFragment : BaseFragment<FragmentRestaurantBinding, RestaurantViewModel>(),
    RestaurantNavigator, OnDrawableClickListener {

    @Inject
    override lateinit var viewModel: RestaurantViewModel

    override val bindingVariable: Int
        get() = BR.viewModel


    override val layoutId: Int
        get() = R.layout.fragment_restaurant


    private var restaurantCardAdapter: RestaurantCardAdapter? = null

    //Live Fused Location Code
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        rvRestaurants.layoutManager = LinearLayoutManager(activity)
        rvRestaurants.addItemDecoration(
            SpaceItemDecoration(
                resources.getDimensionPixelOffset(
                    R.dimen.margin_normal
                )
            )
        )
    }

    override fun onResume() {
        super.onResume()
        Log.VERBOSE
    }

    //        findNavController(this).navigate(R.id.action_nav_restaurant_to_nav_favourite_restaurant)
    open fun setUp() {
        if (!isNetworkConnected) {
            Snackbar.make(
                (activity as MainActivity).clFragmentContainer,
                getString(R.string.network_error),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(activity as MainActivity)
        checkGPSAndLocationPermission()

        pull_to_refresh.setOnRefreshListener {
            pull_to_refresh.isRefreshing = false
            ac_tv_search.setText("")
            viewModel.fetchRestaurants()
        }

        ac_tv_search.setDrawableClickListener(this)

        ac_tv_search.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val query = ac_tv_search.text.toString()
                    if (query.isNotEmpty()) {
                        viewModel.fetchLocation(query)
                    } else {
                        Toast.makeText(
                            activity as MainActivity,
                            "Please enter location to search",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    true
                }
                else -> false
            }
        }
    }

    override fun onRestaurantResponseReceived(restaurantDataResponse: RestaurantDataResponse) {
        restaurantCardAdapter = RestaurantCardAdapter(
            (activity as MainActivity),
            restaurantDataResponse.restaurants,
            viewModel.dataManager,
            onClickListener = {

            }
        )
        rvRestaurants.adapter = restaurantCardAdapter
    }

    override fun onLocationResponseReceived(locationDataResponse: LocationDataResponse) {
//        ac_tv_search.setAdapter(
//            LocationAdapter(
//                activity as MainActivity, R.layout.item_location,
//                locationDataResponse.location_suggestions as MutableList<LocationSuggestions>
//            )
//        )
        restaurantCardAdapter = RestaurantCardAdapter(
            (activity as MainActivity),
            locationDataResponse.location_suggestions,
            viewModel.dataManager,
            onClickListener = {
                val item = it as LocationSuggestions
                viewModel.fetchRestaurants(item.latitude!!, item.longitude!!)
            }
        )
        rvRestaurants.adapter = restaurantCardAdapter

    }

    override fun onNoResponseReceived() {
        Snackbar.make(
            (activity as MainActivity).clFragmentContainer,
            getString(R.string.something_went_wrong),
            Snackbar.LENGTH_LONG
        )
    }

    override fun onHandleError(error: String) {
        Snackbar.make(
            (activity as MainActivity).clFragmentContainer,
            getString(R.string.something_went_wrong),
            Snackbar.LENGTH_LONG
        )
    }

    override fun onClick(target: DrawablePosition) {
        val query = ac_tv_search.text.toString()
        when (target) {
            DrawablePosition.RIGHT -> viewModel.fetchLocation(query)
            else -> {
                throw IllegalAccessException("No Drawable action added")
            }
        }
    }

    private fun checkGPSAndLocationPermission() {
        // Check GPS is enabled
        val lm =
            (activity as MainActivity).getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(
                activity as MainActivity,
                "Please enable location services",
                Toast.LENGTH_SHORT
            ).show()
        }
        val permission: Int = ContextCompat.checkSelfPermission(
            activity as MainActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            setUpLiveLocation()
        } else {
            ActivityCompat.requestPermissions(
                activity as MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST
            )
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST = 1
    }

    private fun setUpLiveLocation() {
        if (ActivityCompat.checkSelfPermission(
                activity as MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity as MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST
            )
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener(activity as MainActivity) { location ->
            if (location != null) {
                lastLocation = location
                val currentLat = location.latitude
                val currentLon = location.longitude
                viewModel.fetchRestaurants(currentLat, currentLon)
            } else {
                // Fetching data with STATIC Lat-Lon
                viewModel.fetchRestaurants()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            setUpLiveLocation()
        } else {
            Toast.makeText(
                activity as MainActivity,
                "Please enable location services",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

/* fun apiCall() {
     val selectedGuid = (activity as MainActivity).getSelectedGuid()
     val selectedContract = (activity as MainActivity).getSelectedContract()

     if (isNetworkConnected) {
         if ((activity as MainActivity).viewModel.dashboardDetails != null) {
             viewModel.dashboardDetails = (activity as MainActivity).viewModel.dashboardDetails!!
             setUp()
         } else {
//                viewModel.getDashboardDetails(
//                    selectedGuid,
//                    selectedContract,
//                    activity = MainActivity()
//                )
         }
     } else {
         Snackbar.make(
             (activity as MainActivity).linearLayout,
             getString(R.string.network_error),
             Snackbar.LENGTH_LONG
         ).show()
     }
 }

 override fun onNoResponseReceived() {
     Snackbar.make(
         (activity as MainActivity).linearLayout,
         getString(R.string.something_went_wrong),
         Snackbar.LENGTH_LONG
     ).show()
 }

 override fun onHandleError(error: String) {
 }

 override fun onResponseReceivedDashboardDetails(dashboardResponse: DashboardResponse) {
     if (dashboardResponse.data?.contractServices?.data != null) {
         (activity as MainActivity).freislandLeaseNumber =
             dashboardResponse.data?.contractServices?.data?.contractLeaseAdvisorNumber.toString()
         (activity as MainActivity).dealerNumber =
             dashboardResponse.data?.contractServices?.data?.contractDealerNumber.toString()
     }
     setUp()
 }*/
}
