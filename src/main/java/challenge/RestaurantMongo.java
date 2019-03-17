package challenge;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Classe para mapear o restaurante no MongoDB
 *
 */
@Document(collection = "restaurant")
public final class RestaurantMongo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String name;
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint location;
	
	public RestaurantMongo() {}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public GeoJsonPoint getLocation() {
		return location;
	}
}
