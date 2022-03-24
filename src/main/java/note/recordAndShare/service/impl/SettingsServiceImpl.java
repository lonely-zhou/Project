package note.recordAndShare.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Settings;
import note.recordAndShare.mapper.SettingsMapper;
import note.recordAndShare.service.SettingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.NoteResultUtil;
import note.utils.UserUtil;
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
@AllArgsConstructor
public class SettingsServiceImpl extends ServiceImpl<SettingsMapper, Settings> implements SettingsService {

    private final SettingsMapper settingsMapper;

    /**
     * 更改用户默认编辑器
     *
     * @param editor 编辑器
     * @return ok
     */
    @Override
    public NoteResultUtil updUserEditorStyle(String editor) {
        String userId = StpUtil.getExtra("user_id").toString();
        int count = settingsMapper.selectCount(new QueryWrapper<Settings>().eq("user_id", userId)).intValue();
        Settings settings = new Settings();
        settings.setUserId(userId);
        settings.setEditorStyle(editor);
        if (count != 0) {
            settingsMapper.updateById(settings);
        } else {
            settingsMapper.insert(settings);
        }
        return NoteResultUtil.success();
    }

    /**
     * 查询用户所有设置
     *
     * @return 用户所有设置
     */
    @Override
    public NoteResultUtil selUserSettingsList() {
        return NoteResultUtil.success(settingsMapper.selectById(StpUtil.getExtra("user_id").toString()));
    }
}
