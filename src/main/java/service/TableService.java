package service;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import vo.Point;

public class TableService extends AbstractService {
	public Set<Point> reciveTable(String id) {
		if (id == null || id.trim().isEmpty()) {
			return new HashSet<>();
		}
		try {
			String urlString = "http://classic.comunio.de/teamInfo.phtml?tid=" + id;
			Document doc = getDocument(urlString);
			Elements tables = doc.select(" .tablecontent03 tbody");
			Element transferTable = tables.get(1);// second table

			Elements lines = transferTable.select("tr");

			Set<Point> teamList = new HashSet<Point>();
			for (int i = 1; i < lines.size(); i++) {
				Element line = lines.get(i);

				Elements select = line.select("td");
				Element positionEL = select.get(0);
				Element trainerEL = select.get(1);
				Element pointsEL = select.get(2);
				Element valueEL = select.get(3);

				Integer position = getPosition(positionEL);
				String trainer = getTrainer(trainerEL);
				Set<String> archivments = getArchivments(trainerEL);
				Integer trainerId = getTrainerId(trainerEL);
				Integer points = getPoints(pointsEL);
				Integer value = getValue(valueEL);

				Point row = new Point();
				row.setPosition(position);
				row.setTrainer(trainer);
				row.setPoints(points);
				row.setValue(value);
				row.setArchivments(archivments);
				row.setTrainerId(trainerId);
				teamList.add(row);
			}
			return teamList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("abbruch", e);
		}
	}

	private Integer getValue(Element valueEL) {
		return getPosition(valueEL);
	}

	private Integer getPoints(Element pointsEL) {
		return getPosition(pointsEL);
	}

	private String getTrainer(Element trainerEL) {
		Elements select = trainerEL.select("a");
		String tempName = select.get(0).html();
		tempName = StringEscapeUtils.unescapeHtml(tempName.replace(".", ""));
		String norm = Normalizer.normalize(tempName, Normalizer.Form.NFD);
		norm = norm.replaceAll("[^\\p{ASCII}]", "");
		return norm.trim();
	}
	private Integer getTrainerId(Element trainerEL) {
		Elements select = trainerEL.select("a");
		String tempName = select.get(0).attr("href");
		tempName = StringEscapeUtils.unescapeHtml(tempName.replace("playerInfo.phtml?pid=", ""));
		String norm = Normalizer.normalize(tempName, Normalizer.Form.NFD);
		norm = norm.replaceAll("[^\\p{ASCII}]", "");
		String trim = norm.trim();
		if (trim.isEmpty()) {
			return 0;
		}
		return Integer.valueOf(trim);
	}

	private Set<String> getArchivments(Element trainerEL) {
		Set<String> archivments = new HashSet<>();
		Elements select = trainerEL.select("img");
		for (int i = 0; i < select.size(); i++) {
			Element element = select.get(i);
			String tempName = element.attr("alt");
			tempName = StringEscapeUtils.unescapeHtml(tempName.replace(".", ""));
			String norm = Normalizer.normalize(tempName, Normalizer.Form.NFD);
			norm = norm.replaceAll("[^\\p{ASCII}]", "");
			archivments.add(norm.trim());
		}
		return archivments;
	}

	private Integer getPosition(Element positionEL) {
		String tempName = positionEL.html();
		tempName = StringEscapeUtils.unescapeHtml(tempName.replace(".", ""));
		String norm = Normalizer.normalize(tempName, Normalizer.Form.NFD);
		norm = norm.replaceAll("[^\\p{ASCII}]", "");
		String trim = norm.trim();
		if (trim.isEmpty()) {
			return 0;
		}
		return Integer.valueOf(trim);
	}

}
