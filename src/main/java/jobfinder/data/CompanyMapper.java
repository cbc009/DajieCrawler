package jobfinder.data;

import java.util.List;

import jobfinder.model.Company;

import org.jsoup.nodes.Document;

public interface CompanyMapper {

    /**
     * 清空数据库
     */
    void clear();

    /**
     * 
     * @param html
     *            这个就是第一组从网上爬下来的页面
     * @param type
     *            这个是我们上次说的大类的分类
     */
    void storeDataFromHtml(List<Document> documents, String type, int id);

    /**
     * 通过sql语句获取数据
     * 
     * @param sql
     *            SQL语句
     * @param columns
     *            字段的列表，作用是通过字段的顺序组装Company对象 例如columns ： [id, name] 那么Company就根据这个顺序来给这三个属性赋值
     * @return
     */
    List<Company> getCompanysBySql(String sql, List<String> columns);
}
