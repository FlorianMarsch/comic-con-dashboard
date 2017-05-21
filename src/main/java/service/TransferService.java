package service;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TransferService extends AbstractService {
	public Set<String> reciveTransfers(String id) {
		if (id == null || id.trim().isEmpty()) {
			return new HashSet<>();
		}
		try {
			String urlString = "http://classic.comunio.de/teamInfo.phtml?tid=" + id;
			Document doc = getDocument(urlString);
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

}
