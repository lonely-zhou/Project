package note.recordAndShare.mapper;

import note.recordAndShare.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-08 07:37:25
 */
//@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
}
