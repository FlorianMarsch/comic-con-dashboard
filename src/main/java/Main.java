import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import java.io.InputStream;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Matchdetails;
import vo.Score;

public class Main {

	public static void main(String[] args) {

		new Main().init();

	}

	public Main() {
		// DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	private ObjectMapper mapper;

	public void init() {
		port(Integer.valueOf(System.getenv("PORT")));
		staticFileLocation("/public");

		get("/api/lineup/:id", (request, response) -> {
			String id = request.params(":id");
			Set<String> recivedLineUp = reciveLineUp(id);
			return mapper.writeValueAsString(recivedLineUp);
		});

		get("/api/transfers/:id", (request, response) -> {
			String id = request.params(":id");
			Set<String> recivedTransfers = reciveTransfers(id);
			return mapper.writeValueAsString(recivedTransfers);
		});

		get("/api/gameday/:season/:number", (request, response) -> {
			String season = request.params(":season");
			String number = request.params(":number");
			List<Score> recivedScores = reciveResults(season, number);
			return mapper.writeValueAsString(recivedScores);
		});

	}

	public List<Score> reciveResults(String season, String number) {
		if (season == null || number == null || season.trim().isEmpty() || number.trim().isEmpty()) {
			return new ArrayList<>();
		}
		try {
			String html = null;
			String urlString = "http://stats.comunio.de/matchday/" + season + "/" + number;
			InputStream is = (InputStream) new URL(urlString).getContent();
			html = IOUtils.toString(is, "UTF-8");

			html = Normalizer.normalize(html, Normalizer.Form.NFD);
			html = html.replaceAll("[^\\p{ASCII}]", "");

			Document doc = Jsoup.parse(html);
			Elements lines = doc.select(" .zoomable a");
			// http://stats.comunio.de/matchdetails.php?mid=3363

			List<Score> goals = new ArrayList<>();
			for (int i = 0; i < lines.size(); i++) {
				Element line = lines.get(i);
				String page = line.attr("id").replace("_lnk", "").replace("m", "");

				goals.addAll(getGoals(page));
			}
			System.out.println(goals);
			return goals;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("abbruch", e);
		}
	}

	public List<Score> getGoals(String page) {
		if (page == null || page.trim().isEmpty()) {
			return new ArrayList<>();
		}
		try {
			String html = null;
			String urlString = "http://stats.comunio.de/matchdetails.php?mid=" + page;
			InputStream is = (InputStream) new URL(urlString).getContent();
			html = IOUtils.toString(is, "UTF-8");

			html = Normalizer.normalize(html, Normalizer.Form.NFD);
			html = html.replaceAll("[^\\p{ASCII}]", "");

			Matchdetails details = mapper.readValue(html, Matchdetails.class);

			return details.getScorer();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("abbruch", e);
		}
	}

	public Set<String> reciveTransfers(String id) {
		if (id == null || id.trim().isEmpty()) {
			return new HashSet<>();
		}
		try {
			String html = null;
			String urlString = "http://classic.comunio.de/teamInfo.phtml?tid=" + id;
			InputStream is = (InputStream) new URL(urlString).getContent();
			html = IOUtils.toString(is, "UTF-8");

			html = Normalizer.normalize(html, Normalizer.Form.NFD);
			html = html.replaceAll("[^\\p{ASCII}]", "");

			Document doc = Jsoup.parse(html);
			Elements tables = doc.select(" .tablecontent03 tbody");
			Element transferTable = tables.get(2);// third table

			Elements lines = transferTable.select("td:first-child");

			Set<String> teamList = new HashSet<String>();
			for (int i = 1; i < lines.size(); i++) {
				Element line = lines.get(i);
				String tempName = line.html();
				tempName = StringEscapeUtils.unescapeHtml(tempName);
				String norm = Normalizer.normalize(tempName, Normalizer.Form.NFD);
				norm = norm.replaceAll("[^\\p{ASCII}]", "");
				String trim = norm.trim();

				teamList.add(trim);
			}
			System.out.println(teamList);
			return teamList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("abbruch", e);
		}
	}

	public Set<String> reciveLineUp(String id) {
		if (id == null || id.trim().isEmpty()) {
			return new HashSet<>();
		}
		try {
			String html = null;
			String urlString = "http://classic.comunio.de/playerInfo.phtml?pid=" + id;
			InputStream is = (InputStream) new URL(urlString).getContent();
			html = IOUtils.toString(is, "UTF-8");

			html = Normalizer.normalize(html, Normalizer.Form.NFD);
			html = html.replaceAll("[^\\p{ASCII}]", "");

			Document doc = Jsoup.parse(html);
			Elements lines = doc.select(".name_cont");

			Set<String> teamList = new HashSet<String>();
			for (int i = 0; i < lines.size(); i++) {
				Element line = lines.get(i);
				String tempName = line.html();
				tempName = StringEscapeUtils.unescapeHtml(tempName);
				String norm = Normalizer.normalize(tempName, Normalizer.Form.NFD);
				norm = norm.replaceAll("[^\\p{ASCII}]", "");
				String trim = norm.trim();

				teamList.add(trim);
			}
			System.out.println(teamList);
			return teamList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("abbruch", e);
		}
	}

}
