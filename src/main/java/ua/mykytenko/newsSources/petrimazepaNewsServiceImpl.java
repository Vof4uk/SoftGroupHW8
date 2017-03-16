package ua.mykytenko.newsSources;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ua.mykytenko.to.News;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class petrimazepaNewsServiceImpl implements NewsSource {
    private static final String URL = "https://petrimazepa.com";

    public List<News> getLastNews(int newsNumber) {
        Document doc = null;
        try {
            doc = Jsoup.connect(URL)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36 OPR/43.0.2442.1144")
                    .validateTLSCertificates(false)
                    .get();
        } catch (IOException e) {
            System.out.println("cannot connect to petrimazepa.com");
            return new ArrayList<>();
        }

        Elements elements = doc.select(".news-cell");
        List<News> news = parseNews(elements);
        return news.subList(0, newsNumber);
    }

    private List<News> parseNews(Elements elements){
        List<News> news = new ArrayList<>();
        for (Element element: elements) {
            news.add(new News(parseToLocalDateTime(element.select(".message-item").attr("data-datetime").trim()),
                    element.select(".news-header").text(),
                    URL + element.select("a").attr("href")));
        }
        return news;
    }

    private LocalDateTime parseToLocalDateTime(String string){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSSSSS");
        return LocalDateTime.parse(string, formatter);
    }
}
