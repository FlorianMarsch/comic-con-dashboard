import server.Server;
import service.EventbriteService;

public class Main {

	public static void main(String[] args) {

		Integer onPort = Integer.valueOf(System.getenv("PORT"));
		Server server = new Server();

		server.start(onPort);

		server.get("/api/ticket", (request, response) -> {
			return new EventbriteService().possibleEvents();
		});

	
	}
}
