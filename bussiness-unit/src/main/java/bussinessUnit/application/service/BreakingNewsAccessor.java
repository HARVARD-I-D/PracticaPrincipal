package bussinessUnit.application.service;

import bussinessUnit.application.domain.model.NewsEvent;
import bussinessUnit.infrastructure.port.DatamartRepository;

import java.util.List;

public class BreakingNewsAccessor {
    public void printLatestNews(DatamartRepository datamartRepository) {
        List<NewsEvent> breakingNews = datamartRepository.getLastNewsEvents(3);

        if(breakingNews.isEmpty()) {
            System.out.println("No hay noticias relevantes almacenadas");
            return;
        }

        System.out.println("\n--- Últimas noticias relevantes ---");
        for (NewsEvent news : breakingNews){
            System.out.println("[Noticiero: " + news.getId() + "]");
            System.out.println("[" + news.getPublishedAt() + "] " + news.getTitle());
            System.out.println("Resumen: " + news.getContent());
            System.out.println("Link: " + news.getUrl());
            System.out.println("------------------------------------");
        }
    }
}
