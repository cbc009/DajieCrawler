package jobfinder.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jobfinder.crawler.DajieCompanyCrawler;
import jobfinder.model.Company;
import jobfinder.util.DBUtil;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CompanyMapperImp implements CompanyMapper {

    Connection co;

    private final static String insertSql = "INSERT INTO `company` (`companyName`, `industryInvolved`, `totalGrade`, `careerGrade`, `skillGrade`, `atmosphereGrade`, `stressGrade`, `prospectGrade`, `type`) VALUES ('%s', '%s', '%f', '%f', '%f', '%f', '%f', '%f', '%s')";

    private CompanyMapperImp() {
        co = DBUtil.getConnection();
    }

    public void clear() {
        Statement stmt = null;
        try {
            stmt = co.createStatement();
            stmt.executeUpdate("delete from `company`");
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
    }

    public static CompanyMapper getCompanyMapperImpl() {
        return new CompanyMapperImp();
    }

    public List<Company> getCompanysBySql(String sql, List<String> columns) {
        List<Company> companyList = new ArrayList<Company>();

        Connection conn = DBUtil.getConnection();

        Statement stmt = null;

        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt("id"));
                company.setCompanyName(rs.getString("companyName"));
                company.setIndustryInvolved(rs.getString("industryInvolved"));
                company.setTotalGrade(rs.getFloat("totalGrade"));
                company.setCareerGrade(rs.getFloat("careerGrade"));
                company.setSkillGrade(rs.getFloat("skillGrade"));
                company.setAtmosphereGrade(rs.getFloat("atmosphere"));
                company.setStressGrade(rs.getFloat("stress"));
                company.setProspectGrade(rs.getFloat("prospect"));
                company.setCEOGrade(rs.getFloat("cEO"));
                company.setType(rs.getString("type"));

                companyList.add(company);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return companyList;

    }

    public void storeDataFromHtml(List<Document> documents, String type, int id) {
        if (documents == null || documents.size() != 3) {
            return;
        }
        String sql = "";
        try {
            Element nameElement = documents.get(0).select(".cor-nav h1").first();
            String name = nameElement.childNode(0).outerHtml();
            String industryInvolved = documents.get(0).select(".card-detail dd:nth-child(4) span:nth-child(2)").text();
            String strtotalGrade = documents.get(2).select(".dp-star:nth-child(1) em").attr("style").split(":")[1];
            float totalGrade = new Float(strtotalGrade.substring(0, strtotalGrade.indexOf("%"))) / 20;
            String strcareerGrade = documents.get(2).select(".cps-scores:nth-child(1) em").attr("style").split(":")[1];
            float careerGrade = new Float(strcareerGrade.substring(0, strcareerGrade.indexOf("%"))) / 20;
            String strskillGrade = documents.get(2).select(".cps-scores:nth-child(2) em").attr("style").split(":")[1];
            float skillGrade = new Float(strskillGrade.substring(0, strskillGrade.indexOf("%"))) / 20;
            String stratmosphere = documents.get(2).select(".cps-scores:nth-child(3) em").attr("style").split(":")[1];
            float atmosphere = new Float(stratmosphere.substring(0, stratmosphere.indexOf("%"))) / 20;
            String strstress = documents.get(2).select(".cps-scores:nth-child(4) em").attr("style").split(":")[1];
            float stress = new Float(strstress.substring(0, strstress.indexOf("%"))) / 20;
            String strprospect = documents.get(2).select(".cps-scores:nth-child(5) em").attr("style").split(":")[1];
            float prospect = new Float(strprospect.substring(0, strprospect.indexOf("%"))) / 20;

            sql = String.format(insertSql, name, industryInvolved, totalGrade, careerGrade, skillGrade, atmosphere, stress,
                    prospect, type);
            Statement stmt = null;
            stmt = co.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
    }

    public static void main(String[] args) {
        DajieCompanyCrawler crawler = new DajieCompanyCrawler();
        List<Document> documents = crawler.crawl("http://www.dajie.com/corp/1029096");
        CompanyMapperImp companyMapperImp = new CompanyMapperImp();
        companyMapperImp.storeDataFromHtml(documents, "1", 1029096);
    }
}
