package note.recordAndShare.mapper;

import note.recordAndShare.entity.Settings;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户设置 Mapper 接口
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-03 04:00:31
 */
@Mapper
public interface SettingsMapper extends BaseMapper<Settings> {

}
