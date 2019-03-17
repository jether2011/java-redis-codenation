package challenge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantService service;

	@GetMapping
	public List<RestaurantMongo> findAll() {
		return service.findAll();
	}
	
	@GetMapping("findInNeighborhood")
	public NeighborhoodRedis findInNeighborhood(@RequestParam(name="x", required=true) double x, @RequestParam(name="y", required=true) double y) {
		return service.findInNeighborhood(x, y);
	}
}
