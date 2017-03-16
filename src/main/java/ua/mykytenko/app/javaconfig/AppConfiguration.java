package ua.mykytenko.app.javaconfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.mykytenko.newsSources.NewsSource;
import ua.mykytenko.service.CashedFileWriterServiceImpl;
import ua.mykytenko.service.FileWriterService;
import ua.mykytenko.service.NewsService;
import ua.mykytenko.service.NewsServiceImpl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = {
//        "ua.mykytenko.service",
        "ua.mykytenko.newsSources"})
public class AppConfiguration {

    @Bean
    public NewsService getNewsService(NewsSource source){
        NewsServiceImpl newsService = new NewsServiceImpl();
        newsService.setNewsBatchSize(5);
        List<NewsSource> sources = new ArrayList<>();
        sources.add(source);
        newsService.setSources(sources);
        return newsService;
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public FileWriterService getFileWriter(){
        CashedFileWriterServiceImpl writerService = new CashedFileWriterServiceImpl("news.txt", 20);
        writerService.setFormatter(DateTimeFormatter.ISO_DATE);
        return writerService;
    }

    @Bean(name = "app")
    public Application getApplication(NewsService ns, FileWriterService fws){
        return new Application(ns, fws);
    }
}
