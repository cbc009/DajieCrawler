package jobfinder.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jobfinder.crawler.DajieDefaultCrawler;
import jobfinder.model.Job;
import jobfinder.util.DBUtil;

import org.jsoup.nodes.Document;

public class JobMapperImp implements JobMapper {

    Connection co;

    private JobMapperImp() {
        co = DBUtil.getConnection();
    }

    public void clear() {
        Statement stmt = null;
        try {
            stmt = co.createStatement();
            stmt.executeUpdate("delete from `job`");
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static JobMapper getJobMappeImpl() {
        return new JobMapperImp();
    }

    private final String insert_Sql = "INSERT INTO `job` (`jobName`, `companyName`, `workCity`, `minSalary`, `maxSalary`, `workExperience`, `industry`, `positionType`, `companyType`, `companyScale`, `recruitingNum`, `type`) VALUES ('%s', '%s', '%s', '%d', '%d', '%s', '%s', '%s', '%s', '%s', '%d', '%s')";

    public void storeDataFromHtml(Document document, String type) {
        String name = document.select("h2.job-end-title").text();
        String companyName = document.select(".job-end-tip a:nth-child(1)").text();
        String workCity = document.select(".job-info-kind1 li:nth-child(1) strong").html();
        String salary = document.select(".job-info-kind1 li:nth-child(2) strong").html();
        String[] sw = salary.split("-");
        int minSalary = 1000;
        int maxSalary = 1000;
        try {
            if (sw.length == 2) {
                minSalary = Integer.parseInt(sw[0]);
                maxSalary = Integer.parseInt(sw[1]);
            } else {
                minSalary = Integer.parseInt(salary.substring(0, salary.indexOf("及")));
                maxSalary = minSalary + 1000;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        String workExperience = document.select(".job-info-kind1 li:nth-child(3) strong").html();
        String industry = document.select(".job-info-kind2 li:nth-child(1) strong").html();
        String positionType = document.select(".job-info-kind2 li:nth-child(2) strong").html();
        String companyType = document.select(".job-info-kind2 li:nth-child(3) strong").html();
        String companyScale = document.select(".job-info-kind2 li:nth-child(4) strong").html();
        int recruitingNum = 3;
        try {
            String strrecruitingNum = document.select(".job-info-kind2 li:nth-child(6) strong").html();
            recruitingNum = Integer.parseInt(strrecruitingNum.substring(0, strrecruitingNum.indexOf("人")));
        } catch (Exception e) {
            // TODO: handle exception
        }
        String sql = String.format(insert_Sql, name, companyName, workCity, minSalary, maxSalary, workExperience, industry,
                positionType, companyType, companyScale, recruitingNum, type);
        Statement stmt = null;
        try {
            stmt = co.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();

            System.out.println(e.getMessage());
            System.out.println(sql);
        }
    }

    public List<Job> getJobsBySql(String sql, List<String> columns) {
        List<Job> jobList = new ArrayList<Job>();

        Connection conn = DBUtil.getConnection();

        Statement stmt = null;

        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Job job = new Job();
                job.setId(rs.getInt("id"));
                job.setJobName(rs.getString("jobName"));
                job.setCompanyName(rs.getString("companyName"));
                job.setWorkCity(rs.getString("workCity"));
                job.setMinSalary(rs.getInt("minSalary"));
                job.setMaxSalary(rs.getInt("maxSalary"));
                job.setWorkExperience(rs.getString("workExperience"));
                job.setIndustry(rs.getString("industry"));
                job.setPositionType(rs.getString("positionType"));
                job.setCompanyType(rs.getString("companyType"));
                job.setCompanyScale(rs.getString("companyScale"));
                job.setDepartment(rs.getString("department"));
                job.setRecruitingNum(rs.getInt("recruitingNum"));
                job.setType(rs.getString("type"));

                jobList.add(job);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
            }

        }

        return jobList;

    }

    public static void main(String[] args) {
        DajieDefaultCrawler crawler = new DajieDefaultCrawler();
        Document document = crawler.crawl("http://job.dajie.com/4a9b6594-ac1a-4bf4-a046-6e5aee7b4cd2.html");
        JobMapperImp jobMapperImp = new JobMapperImp();
        jobMapperImp.storeDataFromHtml(document, "1");
    }

}
