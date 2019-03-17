package challenge;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	private final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);
	
	@Autowired private NeighborhoodMongoRepository neighborhoodRepository;
	@Autowired private RestaurantRespository restaurantRespository;
	@Autowired private RedisTemplate<String, NeighborhoodRedis> redisTemplate;
	
	private ObjectMapper MAPPER;
	{
		MAPPER = new ObjectMapper();
		//MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	@Override
	public List<RestaurantMongo> findAll() {
		return restaurantRespository.findAll();
	}
	
	@Override
	public NeighborhoodRedis findInNeighborhood(double x, double y) {
		NeighborhoodMongo found = neighborhoodRepository.findInNeighborhood(x, y);
		NeighborhoodRedis neighborhoodRedis = null;
		
		if(redisTemplate.hasKey(found.getId())) {
			String json = String.valueOf(this.redisTemplate.opsForHash().get(found.getId(), found.getId()));
			try {
				neighborhoodRedis = MAPPER.readValue(json, NeighborhoodRedis.class);
			} catch (IOException e) {
				logger.error("Error on MAPPER.readValue! {}", e.getMessage());
			}
		} else {
			List<RestaurantMongo> restaurants = restaurantRespository.findAllFromNeighborhood(Objects.requireNonNull(found.getGeometry()));
			neighborhoodRedis = NeighborhoodRedis.of(found, restaurants);
			try {
				this.redisTemplate.opsForHash().put(found.getId(), found.getId(), MAPPER.writeValueAsString(neighborhoodRedis));
			} catch (JsonProcessingException e) {
				logger.error("Error on MAPPER.writeValueAsString! {}", e.getMessage());
			}
		}
		
		return neighborhoodRedis;
	}
}
