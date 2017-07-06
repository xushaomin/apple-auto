/*package com.appleframework.auto.service.fence;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;
import com.github.davidmoten.grumpy.core.Position;
import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Circle;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Polygon;
import com.github.davidmoten.rtree.geometry.Rectangle;

import rx.Observable;
import rx.functions.Func1;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring-*.xml" })
public class FenceCalculateServiceTest {
	
	@Resource
	private FenceEntityService fenceEntityService;
	
	private RTree<String, Geometry> tree = RTree.create();
	
	@Test
	public void get() {
		try {
			//fenceInfoService.clear();
			List<FenceEntityWithBLOBs> list = fenceEntityService.findAll();
			for (FenceEntityWithBLOBs entity : list) {
				System.out.println(entity.toString());
				
				if(entity.getFenceType() == 1) {
					double x = Double.parseDouble(entity.getLongitudes());
					double y = Double.parseDouble(entity.getLatitudes());
					double radius = entity.getRadius() / 1000;
					tree = tree.add(entity.getId() +"-->" + entity.getName(), Geometries.circle(x, y, radius));

				} else if(entity.getFenceType() == 2) {
					
					String latitudes = entity.getLatitudes();
					String longitudes = entity.getLongitudes();
					
					String[] latitudeArray = latitudes.split(",");
					String[] longitudeArray = longitudes.split(",");
					
					double x1 = 0, y1 = 0;
					double x2 = 0, y2 = 0;
					
					if(latitudeArray.length == 2) {						
						x1 = Double.parseDouble(longitudeArray[0]);
						y1 = Double.parseDouble(latitudeArray[0]);
						x2 = Double.parseDouble(longitudeArray[1]);
						y2 = Double.parseDouble(latitudeArray[1]);
						
					} else if(latitudeArray.length == 4) {
						x1 = Double.parseDouble(longitudeArray[0]);
						y1 = Double.parseDouble(latitudeArray[0]);
						x2 = Double.parseDouble(longitudeArray[2]);
						y2 = Double.parseDouble(latitudeArray[2]);
					} else {
						continue;
					}
					
					double lon1, lat1, lon2, lat2;
					if(x1 >= x2) {
						lon1 = x2;
						lon2 = x1;
					}
					else {
						lon1 = x1;
						lon2 = x2;
					}
					
					if(y1 >= y2) {
						lat1 = y2;
						lat2 = y1;
					}
					else {
						lat1 = y1;
						lat2 = y2;
					}
					
					Rectangle rectangle = Geometries.rectangleGeographic(lon1, lat1, lon2, lat2);
					tree = tree.add(entity.getId() +"-->" + entity.getName(), rectangle);
				} else if(entity.getFenceType() == 3 || entity.getFenceType() == 4) {
					
					String latitudes = entity.getLatitudes();
					String longitudes = entity.getLongitudes();
										
					Polygon polygon = Geometries.polygon(ListUtil.string2DoubleList(longitudes), ListUtil.string2DoubleList(latitudes));
					tree = tree.add(entity.getId() +"-->" + entity.getName(), polygon);
					
				} else {
					
				}
			}
			
			System.out.println(tree.asString());
			
			long t = System.currentTimeMillis();

			double x = 113.980252;
			double y = 22.545987;
			
			this.cal(x, y);
			
			x = 113.954309;
			y = 22.561115;
			
			this.cal(x, y);

			x = 113.973353;
			y = 22.545561;
			this.cal(x, y);

			
			System.out.println(System.currentTimeMillis() - t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cal(double x, double y) {
		Point location = Geometries.point(x, y);
		Observable<Entry<String, Geometry>> result = search(tree, location);
		result.forEach(item -> {
			System.out.println(item.value());
		});
	}
	
	
	public Observable<Entry<String, Geometry>> search(RTree<String, Geometry> tree, Point location) {
        // First we need to calculate an enclosing lat long rectangle for this
        // distance then we refine on the exact distance
        //final Position from = Position.create(lonLat.y(), lonLat.x());
        //Rectangle bounds = createBounds(from, distanceKm);

        return tree.search(location)
        // filter on the exact distance from the centre of the GeoCircle
        .filter(new Func1<Entry<String, Geometry>, Boolean>() {
           

            @Override
            public Boolean call(Entry<String, Geometry> entry) {
            	Geometry geo = entry.geometry();
            	if(geo instanceof Circle) {
            		Position from = Position.create(location.y(), location.x());
            		Circle circle = (Circle)entry.geometry();
                    Position centre = Position.create(circle.y(), circle.x());
                    //System.out.println(from.getDistanceToKm(centre));
                    return from.getDistanceToKm(centre) < circle.radius();
            	}
            	else if(geo instanceof Rectangle) {
            		Rectangle rectangle = (Rectangle)entry.geometry();
            		boolean isContains = rectangle.contains(location.x(), location.y());
                    //System.out.println(isContains);
            		return isContains;
            	}
            	else {
            		Polygon polygon = (Polygon)entry.geometry();
            		boolean isContains = polygon.contains(location.x(), location.y());
                    //System.out.println(isContains);
            		return isContains;
            	}
            }
        });
        
    }
	

}
*/