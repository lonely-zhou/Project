package note.recordAndShare.serviceImpl;

import note.recordAndShare.entity.Settings;
import note.recordAndShare.mapper.SettingsMapper;
import note.recordAndShare.service.SettingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户设置 服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-03 04:00:31
 */
@Service
public class SettingsServiceImpl extends ServiceImpl<SettingsMapper, Settings> implements SettingsService {

}
