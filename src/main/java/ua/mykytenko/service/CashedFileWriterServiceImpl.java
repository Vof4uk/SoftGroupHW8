package ua.mykytenko.service;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.mykytenko.to.News;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class CashedFileWriterServiceImpl implements FileWriterService {
    private int cacheSize = 5;//default
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private File file;
    private String resourceName;
    private Set<News> newsSet = new HashSet<>();

    public CashedFileWriterServiceImpl(String resourceName, int cacheSize) {
        this.resourceName = resourceName;
        this.cacheSize = cacheSize;
    }

    @Autowired(required = false)
    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public void init(){
        URL url = getClass().getClassLoader().getResource(resourceName);
        try {
            file = new File(url.toURI());
            if(!file.canWrite()){
                System.out.println("file cannot be written");
            }
        } catch (URISyntaxException | NullPointerException e) {
            System.out.println("file cannot be found");
        }
    }

    @Override
    public void writeToFile(Collection<News> newsBatch) {
        for (News n: newsBatch) {
            String string = String.format("%s : %s details ---->  %s%n", formatter.format(n.getReleaseTime()), n.getTitle(), n.getUrl());
            System.out.println(string);
            try {
                cacheOrFlush(n);
            } catch (IOException e) {
                throw new RuntimeException("exception when tried to write file");
            }
        }
    }

    private void cacheOrFlush(News news) throws IOException{
        newsSet.add(news);
        if(newsSet.size() >= cacheSize){
            flush();
        }
    }

    private void flush() throws IOException{
        List<News> newsList = new ArrayList<>();
        newsList.addAll(newsSet);
        Collections.sort(newsList, (n1, n2) -> n2.getReleaseTime().compareTo(n1.getReleaseTime()));
        for (News n : newsList) {
            String string = String.format("%s : %s details ---->  %s%n", n.getReleaseTime(), n.getTitle(), n.getUrl());
            FileUtils.write(file, string, Charset.forName("UTF-8"), true);
        }
        newsSet = new HashSet<>();
    }

    public void destroy(){
        try {
            flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
