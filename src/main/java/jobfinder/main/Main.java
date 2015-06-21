package jobfinder.main;

import jobfinder.analysis.CompanyBasicAnalysis;
import jobfinder.analysis.JobBasicAnalysis;
import jobfinder.crawler.DajieCrawler;
import jobfinder.crawler.DajieCrawlerImpl;
import jobfinder.data.CompanyMapperImp;
import jobfinder.data.JobMapperImp;

public class Main {

    public static void main(String[] args) {
        for (String string : args) {
            System.out.println(string);
        }
        if (args[0].equals("-c")) {
            JobMapperImp.getJobMappeImpl().clear();
            CompanyMapperImp.getCompanyMapperImpl().clear();
            DajieCrawler dajieCrawler = new DajieCrawlerImpl();
            dajieCrawler.crawler();
        } else if (args[0].equals("-a")) {
            JobBasicAnalysis positonSalaryAnalysis = new JobBasicAnalysis();
            positonSalaryAnalysis.analysis();
            CompanyBasicAnalysis companyBasicAnalysis = new CompanyBasicAnalysis();
            companyBasicAnalysis.analysis();
        }
    }

}
