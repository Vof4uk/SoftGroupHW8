package ua.mykytenko.to;

import java.time.LocalDateTime;

public class News {
    private LocalDateTime releaseTime;
    private String title;
    private String url;

    public News(LocalDateTime releaseTime, String title, String url) {
        this.releaseTime = releaseTime;
        this.title = title;
        this.url = url;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        return getUrl() != null ? getUrl().equals(news.getUrl()) : news.getUrl() == null;

    }

    @Override
    public int hashCode() {
        return getUrl() != null ? getUrl().hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("News{");
        sb.append("releaseTime=").append(releaseTime);
        sb.append(", title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
