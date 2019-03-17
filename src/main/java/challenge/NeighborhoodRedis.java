package challenge;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe para mapear o bairro no Redis
 *
 */
@RedisHash("neighborhoods")
public final class NeighborhoodRedis implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@JsonProperty
	private String id;
	@JsonProperty
	private String name;
	@JsonProperty
	private List<RestaurantRedis> restaurants;

	/**
	 * To serialize with Jackson
	 */
	public NeighborhoodRedis() {}
	
	private NeighborhoodRedis(String id, String name, List<RestaurantMongo> restaurants) {
		this.id = id;
		this.name = name;
		this.restaurants = restaurants
							 .stream()
								.sorted(Comparator.comparing(RestaurantMongo::getName))
									.map(rm -> {
										return RestaurantRedis.of(rm);
									}).collect(Collectors.toList());
	}
	
	public static NeighborhoodRedis of(NeighborhoodMongo obj, List<RestaurantMongo> restaurants) {
		
		Objects.requireNonNull(obj);
		
		return new NeighborhoodRedis(obj.getId(), obj.getName(), Objects.requireNonNull(restaurants));
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public List<RestaurantRedis> getRestaurants() {
		return restaurants == null ? Collections.emptyList() : this.restaurants;
	}
}
