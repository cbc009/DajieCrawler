package jobfinder.crawler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TypeUrlRunner implements Runnable {

    DajieCrawlerImpl dajieCrawlerImpl;
    String type;
    DajieJobUrlListCrawler dajieJobUrlListCrawler;

    public TypeUrlRunner(DajieCrawlerImpl dajieCrawlerImpl, String type) {
        this.dajieCrawlerImpl = dajieCrawlerImpl;
        this.type = type;
        dajieJobUrlListCrawler = new DajieJobUrlListCrawler();
    }

    public void run() {
        for (int i = 1; i < 2000; i++) {
            String url = String.format(DajieCrawlerImpl.basicUrlTemplete, type, i);
            List<String> cs = dajieJobUrlListCrawler.crawl(url);
            dajieCrawlerImpl.addJobUrlList(type, cs);
        }
    }

    public static void main(String[] args) {
        DajieCrawlerImpl dajieCrawlerImpl = new DajieCrawlerImpl();
        ExecutorService exec = Executors.newFixedThreadPool(4);
        exec.execute(new TypeUrlRunner(dajieCrawlerImpl, "1"));
        exec.execute(new TypeUrlRunner(dajieCrawlerImpl, "2"));
        exec.execute(new TypeUrlRunner(dajieCrawlerImpl, "3"));
        exec.execute(new TypeUrlRunner(dajieCrawlerImpl, "4"));
        exec.shutdown();
    }

}
