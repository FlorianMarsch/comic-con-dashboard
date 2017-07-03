package service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Eventbrite;

public class EventbriteService extends AbstractService{

	public Eventbrite possibleEvents() {
		try {
			String urlString = "https://www.eventbrite.de/directory/json?page=&cat=&subcat=&format=&q=comic+con&loc=stuttgart&date=&start_date=&end_date=&is_paid=&sort=best&crt=regular&slat=&slng=&radius=&vp_ne_lat=&vp_ne_lng=&vp_sw_lat=&vp_sw_lng=";
			String json = getResource(urlString);
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(json, Eventbrite.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("abbruch", e);
		}
	}
}
