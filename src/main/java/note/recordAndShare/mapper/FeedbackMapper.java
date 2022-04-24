package note.recordAndShare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import note.recordAndShare.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 反馈表 Mapper 接口
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-16 04:30:34
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

}
