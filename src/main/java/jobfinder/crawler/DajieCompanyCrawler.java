package jobfinder.crawler;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

//抓取公司主页、公司待遇页卡、公司评论页卡，分别存储在HomeDoc、SalaryDoc、CommentDoc中
public class DajieCompanyCrawler {

    public List<Document> crawl(String DajieUrl) {

        Document HomeDoc = null;
        try {
            // 抓取公司主页HTML存储在HomeDoc对象中
            HomeDoc = Jsoup.connect(DajieUrl).timeout(50000).get();
            // 抓取公司名称
            String FirmCampus = HomeDoc.select("h1").text();
            // 如果页面不存在，则跳过
            if (FirmCampus.equals("")) {
                return null;
            }
            // 抓取工资待遇页面
            Document SalaryDoc = null;
            SalaryDoc = Jsoup.connect(DajieUrl + "/salary").timeout(50000).get();

            // 抓取公司点评页面，评价没有抓全
            Document CommentDoc = null;
            CommentDoc = Jsoup.connect(DajieUrl + "/comment").timeout(50000).get();

            List<Document> companyDocs = new ArrayList<Document>();
            companyDocs.add(HomeDoc);
            companyDocs.add(SalaryDoc);
            companyDocs.add(CommentDoc);
            return companyDocs;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        DajieCompanyCrawler crawler = new DajieCompanyCrawler();
        List<Document> result = crawler.crawl("http://www.dajie.com/corp/10020061");
        if (result == null) {
            System.out.println("fault page");
            return;
        }
        for (Document document : result) {
            System.out.println("=========================================");
            System.out.println(document.html());
        }
    }
}
