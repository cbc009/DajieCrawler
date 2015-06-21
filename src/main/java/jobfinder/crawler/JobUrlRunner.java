package jobfinder.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jobfinder.data.JobMapper;
import jobfinder.data.JobMapperImp;

import org.jsoup.nodes.Document;

public class JobUrlRunner implements Runnable {

    DajieCrawlerImpl dajieCrawlerImpl;
    String type;
    DajieDefaultCrawler dajieDefaultCrawler;
    JobMapper jobMapper;

    public JobUrlRunner(DajieCrawlerImpl dajieCrawlerImpl, JobMapper jobMapper, String type) {
        this.dajieCrawlerImpl = dajieCrawlerImpl;
        this.type = type;
        dajieDefaultCrawler = new DajieDefaultCrawler();
        this.jobMapper = jobMapper;
    }

    public void run() {
        List<String> urllList = dajieCrawlerImpl.getJobUrlList(type);
        while (urllList.size() > 0) {
            List<String> companyList = new ArrayList<String>();
            for (String url : urllList) {
                Document document = dajieDefaultCrawler.crawl(url);
                jobMapper.storeDataFromHtml(document, type);
                companyList.add(document.select(".job-end-tip a").attr("href"));
            }
            dajieCrawlerImpl.addCompanyUrlList(type, companyList);
            urllList = dajieCrawlerImpl.getJobUrlList(type);
        }
    }

    public static void main(String[] args) {
        DajieCrawlerImpl dajieCrawlerImpl = new DajieCrawlerImpl();
        dajieCrawlerImpl.jobMap.get("1").add("http://job.dajie.com/4a9b6594-ac1a-4bf4-a046-6e5aee7b4cd2.html");
        dajieCrawlerImpl.jobMap.get("2").add("http://job.dajie.com/4a9b6594-ac1a-4bf4-a046-6e5aee7b4cd2.html");
        dajieCrawlerImpl.jobMap.get("3").add("http://job.dajie.com/4a9b6594-ac1a-4bf4-a046-6e5aee7b4cd2.html");
        dajieCrawlerImpl.jobMap.get("4").add("http://job.dajie.com/4a9b6594-ac1a-4bf4-a046-6e5aee7b4cd2.html");
        dajieCrawlerImpl.jobMap.get("1").add("http://job.dajie.com/4a9b6594-ac1a-4bf4-a046-6e5aee7b4cd2.html");
        dajieCrawlerImpl.jobMap.get("2").add("http://job.dajie.com/4a9b6594-ac1a-4bf4-a046-6e5aee7b4cd2.html");
        dajieCrawlerImpl.jobMap.get("3").add("http://job.dajie.com/4a9b6594-ac1a-4bf4-a046-6e5aee7b4cd2.html");
        dajieCrawlerImpl.jobMap.get("4").add("http://job.dajie.com/4a9b6594-ac1a-4bf4-a046-6e5aee7b4cd2.html");
        JobMapper jobMapper = JobMapperImp.getJobMappeImpl();
        ExecutorService exec = Executors.newFixedThreadPool(4);
        exec.execute(new JobUrlRunner(dajieCrawlerImpl, jobMapper, "1"));
        exec.execute(new JobUrlRunner(dajieCrawlerImpl, jobMapper, "2"));
        exec.execute(new JobUrlRunner(dajieCrawlerImpl, jobMapper, "3"));
        exec.execute(new JobUrlRunner(dajieCrawlerImpl, jobMapper, "4"));
        exec.shutdown();
    }

}
