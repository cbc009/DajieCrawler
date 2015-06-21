package jobfinder.crawler;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DajieJobUrlListCrawler {

    // 定义一个方法，传入所属行业编号和筛选页面。抓取此行业所有职位数据，并存入Arraylist中
    public List<String> crawl(String URL) {
        Document JobDoc = null;
        List<String> jobUrlList = new ArrayList<String>();
        try {
            JobDoc = Jsoup.connect(URL).timeout(50000).get();
            Elements JobDetail = JobDoc.select("h3>a");
            // 该行业提供的职位已被抓取完时，则跳过该行业
            if (JobDetail.size() == 0) {
                return jobUrlList;
            }
            for (int j = 0; j < JobDetail.size(); j++) {
                jobUrlList.add(JobDetail.get(j).attr("href"));
            }
            return jobUrlList;
        } catch (Exception e) {
            return jobUrlList;
        }

    }

    public static void main(String[] args) {
        DajieJobUrlListCrawler crawler = new DajieJobUrlListCrawler();
        List<String> jobUrls = crawler.crawl("http://www.dajie.com/zhiwei/job_1_0_0_0_0_0_0_0_1");
        for (String jobUrl : jobUrls) {
            System.out.println(jobUrl);
        }
    }
}
