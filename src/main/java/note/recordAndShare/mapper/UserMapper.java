package note.recordAndShare.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import note.recordAndShare.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import note.recordAndShare.entity.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-26 09:57:41
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询所有用户+权限
     * @param page 分页
     * @return 所有用户
     */
    IPage<UserDto> selAllUser(Page<?> page);
}
