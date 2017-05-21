package service;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import vo.Season;

public class GamedayService extends AbstractService{

	public Season reciveCurrentGameday() {
		try {
			String urlString = "http://stats.comunio.de/matchday";
			Document doc = getDocument(urlString);
			Elements lines = doc.select("#inhalt h3");

			Season result = new Season();

			for (int i = 0; i < lines.size(); i++) {
				Element line = lines.get(i);
				String tempGameday = line.html();
				if (tempGameday.contains("Spieltag")) {
					tempGameday = StringEscapeUtils.unescapeHtml(tempGameday);
					String[] split = tempGameday.split(". Spieltag ");

					Integer gameday = Integer.valueOf(split[0]);
					String season = split[1].replace("/", "-");
					result.setGameday(gameday);
					result.setSeason(season);
					return result;
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("abbruch", e);
		}
	}
}
