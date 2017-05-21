package service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Matchdetails;
import vo.Score;

public class ResultService extends AbstractService {

	private ObjectMapper mapper;

	public ResultService() {
		// DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public List<Score> reciveResults(String season, String number) {
		if (season == null || number == null || season.trim().isEmpty() || number.trim().isEmpty()) {
			return new ArrayList<>();
		}
		try {
			String urlString = "http://stats.comunio.de/matchday/" + season + "/" + number;
			Document doc = getDocument(urlString);
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
			String urlString = "http://stats.comunio.de/matchdetails.php?mid=" + page;
			String html = getResource(urlString);
			Matchdetails details = mapper.readValue(html, Matchdetails.class);
			return details.getScorer();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("abbruch", e);
		}
	}
}
