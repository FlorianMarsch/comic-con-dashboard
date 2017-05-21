package service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AbstractService {
	Document getDocument(String urlString) throws IOException, MalformedURLException {
		String html = getResource(urlString);
		Document doc = Jsoup.parse(html);
		return doc;
	}

	String getResource(String urlString) throws IOException, MalformedURLException {
		String html = null;
		InputStream is = (InputStream) new URL(urlString).getContent();
		html = IOUtils.toString(is, "UTF-8");

		html = Normalizer.normalize(html, Normalizer.Form.NFD);
		html = html.replaceAll("[^\\p{ASCII}]", "");
		return html;
	}
}
