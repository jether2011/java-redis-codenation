package challenge;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author jether.rodrigues
 *
 * mongoimport <path to neighborhoods.json> -c neighborhoods -d challenge
 * db.getCollection('neighborhoods').createIndex({ geometry: "2dsphere" })
 */
@Repository
public interface NeighborhoodMongoRepository extends MongoRepository<NeighborhoodMongo, String> {
	
	@Query("{ geometry: { $geoIntersects: { $geometry: { type: \"Point\", coordinates: [ ?0, ?1 ] } } } }")
	NeighborhoodMongo findInNeighborhood(double x, double y);

}
