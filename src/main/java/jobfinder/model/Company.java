package jobfinder.model;

public class Company {

    private int id;
    private String companyName;
    private String industryInvolved;
    private float totalGrade;
    private float careerGrade;
    private float skillGrade;
    private float atmosphereGrade;
    private float stressGrade;
    private float prospectGrade;
    private float CEOGrade;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustryInvolved() {
        return industryInvolved;
    }

    public void setIndustryInvolved(String industryInvolved) {
        this.industryInvolved = industryInvolved;
    }

    public float getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(float totalGrade) {
        this.totalGrade = totalGrade;
    }

    public float getCareerGrade() {
        return careerGrade;
    }

    public void setCareerGrade(float careerGrade) {
        this.careerGrade = careerGrade;
    }

    public float getSkillGrade() {
        return skillGrade;
    }

    public void setSkillGrade(float skillGrade) {
        this.skillGrade = skillGrade;
    }

    public float getAtmosphereGrade() {
        return atmosphereGrade;
    }

    public void setAtmosphereGrade(float atmosphereGrade) {
        this.atmosphereGrade = atmosphereGrade;
    }

    public float getStressGrade() {
        return stressGrade;
    }

    public void setStressGrade(float stressGrade) {
        this.stressGrade = stressGrade;
    }

    public float getProspectGrade() {
        return prospectGrade;
    }

    public void setProspectGrade(float prospectGrade) {
        this.prospectGrade = prospectGrade;
    }

    public float getCEOGrade() {
        return CEOGrade;
    }

    public void setCEOGrade(float cEOGrade) {
        CEOGrade = cEOGrade;
    }

}
