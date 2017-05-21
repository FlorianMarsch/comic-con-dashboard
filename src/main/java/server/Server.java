package server;

import static spark.Spark.exception;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Route;
import spark.Spark;

public class Server {

	private ObjectMapper mapper;
	private List<String> routes = new ArrayList<>();
	
	public Server() {
		// DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public void start(Integer aPort) {
		port(aPort);
		staticFileLocation("/public");
		exception(Exception.class, (e, request, response) -> {
			response.status(500);
			response.header("Content-Type", "application/json");
			try {
				String publicErrorMessage = "Unknown error : " + UUID.randomUUID().toString();
				Exception message = new RuntimeException(publicErrorMessage);
				response.body(mapper.writeValueAsString(message));
				new RuntimeException(publicErrorMessage, e).printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
				// should not happen
				response.body("{\"error\":\"error\"}");
			}
		});
		
		Spark.get("/", (request, response) -> {
			response.status(200);
			response.header("Content-Type", "application/json");
			return mapper.writeValueAsString(routes);
		});
	}

	

	public void get(String path, Route route) {
		
		routes.add("GET "+path);
		
		Spark.get(path, (request, response) -> {
			Object result = route.handle(request, response);
			response.status(200);
			response.header("Content-Type", "application/json");
			return mapper.writeValueAsString(result);
		});
	}

}
