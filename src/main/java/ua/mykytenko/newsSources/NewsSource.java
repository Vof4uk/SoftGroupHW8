package ua.mykytenko.newsSources;

import ua.mykytenko.to.News;

import java.util.Collection;

public interface NewsSource {
    Collection<News> getLastNews(int newsNumber);
}
