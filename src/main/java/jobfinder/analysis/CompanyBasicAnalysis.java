package jobfinder.analysis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jobfinder.data.CompanyMapper;
import jobfinder.data.CompanyMapperImp;
import jobfinder.model.Company;
import jobfinder.util.DBUtil;

import org.jfree.ui.RefineryUtilities;

public class CompanyBasicAnalysis {

    Connection co;
    CompanyMapper companyMapper;

    private final String basicSql = "select type, totalGrade from company";

    public CompanyBasicAnalysis() {
        co = DBUtil.getConnection();
        companyMapper = CompanyMapperImp.getCompanyMapperImpl();
    }

    private List<Company> getDatas() {

        List<Company> companyList = new ArrayList<Company>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = co.createStatement();
            rs = stmt.executeQuery(basicSql);

            while (rs.next()) {
                Company company = new Company();
                company.setType(rs.getString(1));
                company.setTotalGrade(rs.getFloat(2));
                companyList.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }

        }
        return companyList;
    }

    public void analysis() {
        List<Company> companyList = getDatas();
        // {行业:[总数， 评分]}
        Map<String, Float[]> jobData = new HashMap<String, Float[]>();
        Float[] typeData;
        for (Company company : companyList) {
            if (jobData.containsKey(company.getType())) {
                typeData = jobData.get(company.getType());
                typeData[0] = typeData[0] + 1;
                typeData[1] = typeData[1] + company.getTotalGrade();
            } else {
                Float[] newTypeData = { new Float(1), company.getTotalGrade() };
                jobData.put(company.getType(), newTypeData);
            }
        }
        for (Entry<String, Float[]> data : jobData.entrySet()) {
            typeData = data.getValue();
            typeData[1] = typeData[1] / typeData[0];
            System.out.println(String.format("%s, %f, %f", data.getKey(), typeData[0], typeData[1]));
        }

        CompanyGradeChart min = new CompanyGradeChart("折线图", jobData);
        min.pack();
        RefineryUtilities.centerFrameOnScreen(min);
        min.setVisible(true);
    }

    public static void main(String[] args) {
        CompanyBasicAnalysis positonSalaryAnalysis = new CompanyBasicAnalysis();
        positonSalaryAnalysis.analysis();
    }

}
