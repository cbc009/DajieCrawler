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

import jobfinder.data.JobMapper;
import jobfinder.data.JobMapperImp;
import jobfinder.model.Job;
import jobfinder.util.DBUtil;

import org.jfree.ui.RefineryUtilities;

public class JobBasicAnalysis {

    Connection co;
    JobMapper jobMapper;

    private final String basicSql = "select positionType, minSalary, maxSalary, recruitingNum from job";

    public JobBasicAnalysis() {
        co = DBUtil.getConnection();
        jobMapper = JobMapperImp.getJobMappeImpl();
    }

    private List<Job> getDatas() {

        List<Job> jobList = new ArrayList<Job>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = co.createStatement();
            rs = stmt.executeQuery(basicSql);

            while (rs.next()) {
                Job job = new Job();
                job.setPositionType(rs.getString(1));
                job.setMinSalary(rs.getInt(2));
                job.setMaxSalary(rs.getInt(3));
                job.setRecruitingNum(rs.getInt(4));
                jobList.add(job);
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
        return jobList;
    }

    public void analysis() {
        List<Job> jobList = getDatas();
        // {职位:[总数， minSalary加总, maxSalary加总]}
        Map<String, Long[]> jobData = new HashMap<String, Long[]>();
        Long[] typeData;
        for (Job job : jobList) {
            if (jobData.containsKey(job.getPositionType())) {
                typeData = jobData.get(job.getPositionType());
                typeData[0] = typeData[0] + 1;
                typeData[1] = typeData[1] + job.getMinSalary();
                typeData[2] = typeData[2] + job.getMaxSalary();
                typeData[3] = typeData[3] + job.getRecruitingNum();
            } else {
                Long[] newTypeData = { new Long(1), new Long(job.getMinSalary()), new Long(job.getMaxSalary()),
                        new Long(job.getRecruitingNum()) };
                jobData.put(job.getPositionType(), newTypeData);
            }
        }
        for (Entry<String, Long[]> data : jobData.entrySet()) {
            typeData = data.getValue();
            typeData[1] = typeData[1] / typeData[3];
            typeData[2] = typeData[2] / typeData[3];
            System.out.println(String.format("%s, %d, %d, %d, %d", data.getKey(), typeData[0], typeData[1], typeData[2],
                    typeData[3]));
        }

        PositonMinSalaryChart min = new PositonMinSalaryChart("折线图", jobData);
        min.pack();
        RefineryUtilities.centerFrameOnScreen(min);
        min.setVisible(true);

        PositonMaxSalaryChart max = new PositonMaxSalaryChart("折线图", jobData);
        max.pack();
        RefineryUtilities.centerFrameOnScreen(max);
        max.setVisible(true);

        PositionDemandChart demand = new PositionDemandChart("折线图", jobData);
        demand.pack();
        RefineryUtilities.centerFrameOnScreen(demand);
        demand.setVisible(true);
    }

    public static void main(String[] args) {
        JobBasicAnalysis positonSalaryAnalysis = new JobBasicAnalysis();
        positonSalaryAnalysis.analysis();
    }
}
