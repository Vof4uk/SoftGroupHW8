package ua.mykytenko.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import ua.mykytenko.to.News;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;

@Component
public class FileWriterSimpleImpl implements FileWriterService {
    private File file;
    private String resourceName;

    public FileWriterSimpleImpl(String resourceName) {
        this.resourceName = resourceName;
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
    public void writeToFile(Collection<News> news) {
        for (News n: news) {
            String string = String.format("%s : %s details ---->  %s%n", n.getReleaseTime(), n.getTitle(), n.getUrl());
            System.out.println(string);
            try {
                FileUtils.write(file, string + "\n", Charset.forName("UTF-8"), true);
            } catch (IOException e) {
                throw new RuntimeException("exception when tried to write file");
            }
        }
    }
}
