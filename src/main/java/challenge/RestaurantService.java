package challenge;

import java.util.List;

public interface RestaurantService {
	
	List<RestaurantMongo> findAll();
	NeighborhoodRedis findInNeighborhood(double x, double y);

}
