package ua.mykytenko.service;

import ua.mykytenko.to.News;

import java.util.Collection;

public interface FileWriterService {
    void writeToFile(Collection<News> news);
}
