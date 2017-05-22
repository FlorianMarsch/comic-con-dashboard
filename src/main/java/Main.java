import server.Server;
import service.GamedayService;
import service.LineUpService;
import service.ResultService;
import service.TableService;
import service.TransferService;

public class Main {

	public static void main(String[] args) {

		Integer onPort = Integer.valueOf(System.getenv("PORT"));
		Server server = new Server();

		server.start(onPort);

		server.get("/api/lineup/:id", (request, response) -> {
			String id = request.params(":id");
			return new LineUpService().reciveLineUp(id);
		});

		server.get("/api/transfers/:id", (request, response) -> {
			String id = request.params(":id");
			return new TransferService().reciveTransfers(id);
		});
		
		server.get("/api/table/:id", (request, response) -> {
			String id = request.params(":id");
			return new TableService().reciveTable(id);
		});

		server.get("/api/currentGameday", (request, response) -> {
			return new GamedayService().reciveCurrentGameday();
		});

		server.get("/api/result/:season/:number", (request, response) -> {
			String season = request.params(":season");
			String number = request.params(":number");
			return new ResultService().reciveResults(season, number);
		});
	}
}
