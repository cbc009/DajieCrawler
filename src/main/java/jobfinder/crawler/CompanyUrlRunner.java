package jobfinder.crawler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jobfinder.data.CompanyMapper;
import jobfinder.data.CompanyMapperImp;

import org.jsoup.nodes.Document;

public class CompanyUrlRunner implements Runnable {

    DajieCrawlerImpl dajieCrawlerImpl;
    String type;
    DajieCompanyCrawler dajieCompanyCrawler;
    CompanyMapper companyMapper;

    public CompanyUrlRunner(DajieCrawlerImpl dajieCrawlerImpl, CompanyMapper companyMapper, String type) {
        this.dajieCrawlerImpl = dajieCrawlerImpl;
        this.type = type;
        dajieCompanyCrawler = new DajieCompanyCrawler();
        this.companyMapper = companyMapper;
    }

    public void run() {
        List<String> urllList = dajieCrawlerImpl.getCompanyUrlList(type);
        while (urllList.size() > 0) {
            for (String url : urllList) {
                try {
                    String[] parameters = url.split("/");
                    int companyId = Integer.parseInt(parameters[parameters.length - 1]);
                    List<Document> documents = dajieCompanyCrawler.crawl(url);
                    companyMapper.storeDataFromHtml(documents, type, companyId);
                } catch (Exception e) {
                }
            }
            urllList = dajieCrawlerImpl.getCompanyUrlList(type);
        }
    }

    public static void main(String[] args) {
        DajieCrawlerImpl dajieCrawlerImpl = new DajieCrawlerImpl();
        dajieCrawlerImpl.companyUrlMap.get("1").add("http://www.dajie.com/corp/1029096");
        dajieCrawlerImpl.companyUrlMap.get("2").add("http://www.dajie.com/corp/1029096");
        dajieCrawlerImpl.companyUrlMap.get("3").add("http://www.dajie.com/corp/1029096");
        dajieCrawlerImpl.companyUrlMap.get("4").add("http://www.dajie.com/corp/1029096");
        dajieCrawlerImpl.companyUrlMap.get("1").add("http://www.dajie.com/corp/1029096");
        dajieCrawlerImpl.companyUrlMap.get("2").add("http://www.dajie.com/corp/1029096");
        dajieCrawlerImpl.companyUrlMap.get("3").add("http://www.dajie.com/corp/1029096");
        dajieCrawlerImpl.companyUrlMap.get("4").add("http://www.dajie.com/corp/1029096");
        CompanyMapper companyMapper = CompanyMapperImp.getCompanyMapperImpl();
        ExecutorService exec = Executors.newFixedThreadPool(4);
        exec.execute(new CompanyUrlRunner(dajieCrawlerImpl, companyMapper, "1"));
        exec.execute(new CompanyUrlRunner(dajieCrawlerImpl, companyMapper, "2"));
        exec.execute(new CompanyUrlRunner(dajieCrawlerImpl, companyMapper, "3"));
        exec.execute(new CompanyUrlRunner(dajieCrawlerImpl, companyMapper, "4"));
        exec.shutdown();
    }
}
