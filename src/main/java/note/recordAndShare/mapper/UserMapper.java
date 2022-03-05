package note.recordAndShare.mapper;

import note.recordAndShare.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

}
