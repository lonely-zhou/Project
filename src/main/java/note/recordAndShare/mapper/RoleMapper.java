package note.recordAndShare.mapper;

import note.recordAndShare.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-26 09:57:41
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 查询所有权限
     *
     * @return 权限列表
     */
    @Select("select role from rname")
    List<String> selAllRole();
}
