package challenge;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Classe para mapear o bairro no MongoDB
 *
 */
@Document(collection = "neighborhoods")
public final class NeighborhoodMongo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String name;
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJson<?> geometry;

	public NeighborhoodMongo() {}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public GeoJson<?> getGeometry() {
		return geometry;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NeighborhoodMongo other = (NeighborhoodMongo) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
}
