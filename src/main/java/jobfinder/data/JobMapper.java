package jobfinder.data;

import java.util.List;

import jobfinder.model.Job;

import org.jsoup.nodes.Document;

public interface JobMapper {

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
    void storeDataFromHtml(Document document, String type);

    /**
     * 通过sql语句获取数据
     * 
     * @param sql
     *            SQL语句
     * @param columns
     *            字段的列表，作用是通过字段的顺序组装Job对象 例如columns ： [id, name, company] 那么Job就根据这个顺序来给这三个属性赋值
     * @return
     */
    List<Job> getJobsBySql(String sql, List<String> columns);

}
