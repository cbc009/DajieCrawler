package jobfinder.crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jobfinder.data.CompanyMapper;
import jobfinder.data.CompanyMapperImp;
import jobfinder.data.JobMapper;
import jobfinder.data.JobMapperImp;

public class DajieCrawlerImpl implements DajieCrawler {

    public Map<String, List<String>> companyUrlMap;
    public Map<String, List<String>> jobMap;
    public Set<Integer> existingCompanyIds;
    public List<String> types;

    CompanyMapper companyMapper;
    JobMapper jobMapper;

    public final int GET_SIZE = 10;

    public final static int basicPage = 1;
    public final static String basicUrlTemplete = "http://www.dajie.com/zhiwei/job_%s_0_0_0_0_0_0_0_%d";

    public DajieCrawlerImpl() {
        companyUrlMap = new HashMap<String, List<String>>();
        companyUrlMap.put("1", new ArrayList<String>());
        companyUrlMap.put("2", new ArrayList<String>());
        companyUrlMap.put("3", new ArrayList<String>());
        companyUrlMap.put("4", new ArrayList<String>());
        companyUrlMap.put("5", new ArrayList<String>());
        companyUrlMap.put("6", new ArrayList<String>());
        companyUrlMap.put("7", new ArrayList<String>());
        companyUrlMap.put("8", new ArrayList<String>());
        companyUrlMap.put("9", new ArrayList<String>());
        companyUrlMap.put("10", new ArrayList<String>());
        companyUrlMap.put("11", new ArrayList<String>());
        companyUrlMap.put("12", new ArrayList<String>());

        existingCompanyIds = new HashSet<Integer>();

        jobMap = new HashMap<String, List<String>>();
        jobMap.put("1", new ArrayList<String>());
        jobMap.put("2", new ArrayList<String>());
        jobMap.put("3", new ArrayList<String>());
        jobMap.put("4", new ArrayList<String>());
        jobMap.put("5", new ArrayList<String>());
        jobMap.put("6", new ArrayList<String>());
        jobMap.put("7", new ArrayList<String>());
        jobMap.put("8", new ArrayList<String>());
        jobMap.put("9", new ArrayList<String>());
        jobMap.put("10", new ArrayList<String>());
        jobMap.put("11", new ArrayList<String>());
        jobMap.put("12", new ArrayList<String>());

        types = new ArrayList<String>();
        types.add("1");
        types.add("2");
        types.add("3");
        types.add("4");
        types.add("5");
        types.add("6");
        types.add("7");
        types.add("8");
        types.add("9");
        types.add("10");
        types.add("11");
        types.add("12");

        companyMapper = CompanyMapperImp.getCompanyMapperImpl();
        jobMapper = JobMapperImp.getJobMappeImpl();
    }

    public void addJobUrlList(String type, List<String> urls) {
        synchronized (jobMap) {
            jobMap.get(type).addAll(urls);
        }
    }

    public List<String> getJobUrlList(String type) {
        synchronized (jobMap) {
            List<String> jobUrlList = new ArrayList<String>();
            int maxSize = jobMap.get(type).size();
            if (maxSize <= 0) {
                return new ArrayList<String>();
            }
            int endIndex = GET_SIZE > maxSize ? maxSize : GET_SIZE;
            for (int i = 0; i < endIndex; i++) {
                jobUrlList.add(jobMap.get(type).get(i));
            }
            // 将本次爬取的公司页面删除
            jobMap.put(type, jobMap.get(type).subList(endIndex, maxSize));
            return jobUrlList;
        }
    }

    public void addCompanyUrlList(String type, List<String> urls) {
        synchronized (companyUrlMap) {
            companyUrlMap.get(type).addAll(urls);
            for (String url : urls) {
                try {
                    String[] parameters = url.split("/");
                    int companyId = Integer.parseInt(parameters[parameters.length - 1]);
                    if (!existingCompanyIds.contains(companyId)) {
                        companyUrlMap.get(type).add(url);
                    }
                } catch (Exception e) {
                    return;
                }
            }
        }
    }

    public List<String> getCompanyUrlList(String type) {
        synchronized (companyUrlMap) {
            List<String> companyUrlList = new ArrayList<String>();
            int maxSize = companyUrlMap.get(type).size();
            if (maxSize <= 0) {
                return new ArrayList<String>();
            }
            int endIndex = GET_SIZE > maxSize ? maxSize : GET_SIZE;
            for (int i = 0; i < endIndex; i++) {
                companyUrlList.add(companyUrlMap.get(type).get(i));
            }
            // 将本次爬取的公司页面删除
            companyUrlMap.put(type, companyUrlMap.get(type).subList(endIndex, maxSize));
            return companyUrlList;
        }
    }

    public void crawler() {
        ExecutorService exec1 = Executors.newFixedThreadPool(12);
        // 让工作页面爬完之后再爬工作页面
        ExecutorService exec2 = Executors.newFixedThreadPool(12);
        for (String type : types) {
            exec1.execute(new TypeUrlRunner(this, type));
        }
        try {
            Thread.sleep(3000);
            for (String type : types) {
                exec2.execute(new JobUrlRunner(this, jobMapper, type));
            }
            Thread.sleep(3000);
            for (String type : types) {
                exec2.execute(new CompanyUrlRunner(this, companyMapper, type));
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        exec1.shutdown();
        exec2.shutdown();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // DajieCrawler dajieCrawler = new DajieCrawlerImpl();
        // dajieCrawler.crawler();
    }
}
