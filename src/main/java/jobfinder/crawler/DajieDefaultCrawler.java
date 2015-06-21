package jobfinder.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DajieDefaultCrawler {

    public Document crawl(String URL) {
        Document JobDoc = null;
        try {
            JobDoc = Jsoup.connect(URL).timeout(50000).get();
            return JobDoc;
        } catch (Exception e) {
            return null;
        }

    }

    public static void main(String[] args) {
        DajieDefaultCrawler crawler = new DajieDefaultCrawler();
        Document doc = crawler.crawl("http://job.dajie.com/926c331c-30621-47cf-b38b-d701d9a82537.html");
        if (doc == null) {
            System.out.println("fault page");
            return;
        }
        System.out.println(doc.html());
    }

}
