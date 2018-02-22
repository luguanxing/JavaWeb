package news.dao;

import java.util.List;
import news.entity.New;
import news.entity.NewExample;
import org.apache.ibatis.annotations.Param;

public interface NewDao {
    int countByExample(NewExample example);

    int deleteByExample(NewExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(New record);

    int insertSelective(New record);

    List<New> selectByExample(NewExample example);

    New selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") New record, @Param("example") NewExample example);

    int updateByExample(@Param("record") New record, @Param("example") NewExample example);

    int updateByPrimaryKeySelective(New record);

    int updateByPrimaryKey(New record);
}