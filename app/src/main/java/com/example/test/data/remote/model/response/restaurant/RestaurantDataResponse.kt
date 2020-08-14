package  com.example.test.data.remote.model.response.restaurant

data class RestaurantDataResponse(
    val results_found: Int,
    val results_start: Int,
    val results_shown: Int,
    val restaurants: List<Restaurants>
)