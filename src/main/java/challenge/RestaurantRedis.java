package challenge;

import java.io.Serializable;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe para mapear o restaurante no Redis
 *
 */
public final class RestaurantRedis implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private String id;
	@JsonProperty
	private String name;
	@JsonProperty
	private double x;
	@JsonProperty
	private double y;
	
	/**
	 * To serialize with Jackson
	 */
	public RestaurantRedis() {}
	
	private RestaurantRedis(String id, String name, GeoJsonPoint location) {
		this.id = id;
		this.name = name;
		this.x = location.getX();
		this.y = location.getY();
	}
	
	public static RestaurantRedis of(RestaurantMongo restaurant) {
		return new RestaurantRedis(restaurant.getId(), restaurant.getName(), restaurant.getLocation());
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
