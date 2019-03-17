package challenge;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author jether.rodrigues
 *
 */
@Configuration
public class RedisConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@Value("${spring.redis.host}")
    private String redisHostName;

    @Value("${spring.redis.port}")
    private int redisPort;
    
    @Bean
	RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(this.redisHostName, this.redisPort);
	}
    
    @Bean
	public <T> RedisTemplate<String, T> redisTemplate() {
		final RedisTemplate<String, T> template = new RedisTemplate<String, T>();
		template.setConnectionFactory(redisConnectionFactory());
		
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new Jackson2JsonRedisSerializer<>(NeighborhoodRedis.class));
		template.afterPropertiesSet();
		
		return template;
	}
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerSubtypes(
        		NeighborhoodMongo.class,
        		NeighborhoodRedis.class,
        		RestaurantMongo.class
        	);
        return objectMapper;
    }
}
