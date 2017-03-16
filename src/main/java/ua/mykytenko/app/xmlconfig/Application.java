package ua.mykytenko.app.xmlconfig;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.mykytenko.service.FileWriterService;
import ua.mykytenko.service.NewsService;
import ua.mykytenko.to.News;

import java.util.Collection;

public class Application {
    private FileWriterService fileWriter;
    private NewsService newsService;

    public Application(NewsService newsService, FileWriterService fileWriter) {
        this.newsService = newsService;
        this.fileWriter = fileWriter;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        Application app = (Application) ctx.getBean("app");
        for (int i = 0; i < 5; i++) {
            app.doSomething();
        }
        ctx.close();
    }

    public void doSomething() {
        Collection<News> newsBatch = newsService.getNewsBatch();
        fileWriter.writeToFile(newsBatch);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
