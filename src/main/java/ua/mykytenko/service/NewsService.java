package ua.mykytenko.service;

import ua.mykytenko.to.News;

import java.util.List;

public interface NewsService {
    List<News> getNewsBatch();
}
