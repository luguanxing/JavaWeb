package news.dao;

import java.util.List;
import news.entity.Adminer;
import news.entity.AdminerExample;
import org.apache.ibatis.annotations.Param;

public interface AdminerDao {
    int countByExample(AdminerExample example);

    int deleteByExample(AdminerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Adminer record);

    int insertSelective(Adminer record);

    List<Adminer> selectByExample(AdminerExample example);

    Adminer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Adminer record, @Param("example") AdminerExample example);

    int updateByExample(@Param("record") Adminer record, @Param("example") AdminerExample example);

    int updateByPrimaryKeySelective(Adminer record);

    int updateByPrimaryKey(Adminer record);
}