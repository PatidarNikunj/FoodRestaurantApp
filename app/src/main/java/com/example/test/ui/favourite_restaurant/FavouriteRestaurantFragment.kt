package com.example.test.ui.favourite_restaurant

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.BR
import com.example.test.R
import com.example.test.data.remote.model.response.restaurant.RestaurantDataResponse
import com.example.test.databinding.FragmentFavouriteRestaurantBinding
import com.example.test.ui.MainActivity
import com.example.test.ui.base.BaseFragment
import com.example.test.ui.restaurant.RestaurantCardAdapter
import com.example.test.utils.SpaceItemDecoration
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_restaurant.*
import javax.inject.Inject


/**
 * Created by Android.Developer.
 * @version 1.0
 */
open class FavouriteRestaurantFragment :
    BaseFragment<FragmentFavouriteRestaurantBinding, FavouriteRestaurantViewModel>(),
    FavouriteRestaurantNavigator {

    @Inject
    override lateinit var viewModel: FavouriteRestaurantViewModel

    override val bindingVariable: Int
        get() = BR.viewModel


    override val layoutId: Int
        get() = R.layout.fragment_favourite_restaurant


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mainViewModel = (activity as MainActivity).viewModel
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

    private var restaurantCardAdapter: RestaurantCardAdapter? = null
    open fun setUp() {
        viewModel.fetchRestaurants()
    }

    override fun onResponseReceived(restaurantDataResponse: RestaurantDataResponse) {
        restaurantCardAdapter = RestaurantCardAdapter(
            (activity as MainActivity),
            restaurantDataResponse.restaurants,
            viewModel.dataManager,
            onClickListener = {}
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
