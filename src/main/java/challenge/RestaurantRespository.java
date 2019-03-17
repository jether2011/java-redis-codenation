package challenge;

import java.util.List;

import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author jether.rodrigues
 *
 * mongoimport <path to restaurants.json> -c restaurants -d challenge
 * db.getCollection('restaurants').createIndex({ location: "2dsphere" })
 */
@Repository
public interface RestaurantRespository extends MongoRepository<RestaurantMongo, String> {
	
	@Query("{ location: { $geoWithin: { $geometry: ?0 } } }")
	List<RestaurantMongo> findAllFromNeighborhood(GeoJson<?> neighborhood);
}
