package ua.mykytenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.mykytenko.newsSources.NewsSource;
import ua.mykytenko.to.News;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewsServiceImpl implements NewsService {
    private List<NewsSource> sources;
    private int newsBatchSize;

    public NewsServiceImpl() {
    }

    public NewsServiceImpl(List<NewsSource> sources, int newsBatchSize) {
        this.sources = sources;
        this.newsBatchSize = newsBatchSize;
    }

    @Autowired
    public void setSources(List<NewsSource> sources) {
        this.sources = sources;
    }

    public void setNewsBatchSize(int newsBatchSize) {
        this.newsBatchSize = newsBatchSize;
    }

    @Override
    public List<News> getNewsBatch() {
        List<News> newNews = new ArrayList<>();
        for (NewsSource s : sources) {
            newNews.addAll(s.getLastNews(newsBatchSize));
        }
        return newNews;
    }
}
