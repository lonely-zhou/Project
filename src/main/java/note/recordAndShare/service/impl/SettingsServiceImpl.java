package note.recordAndShare.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Settings;
import note.recordAndShare.mapper.SettingsMapper;
import note.recordAndShare.service.SettingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.NoteResultUtil;
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
        settings.setEditorStyle(editor);
        if (count != 0) {
            settingsMapper.update(settings,new QueryWrapper<Settings>().eq("user_id",userId));
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
        return NoteResultUtil.success(settingsMapper.selectOne(new QueryWrapper<Settings>().eq("user_id",StpUtil.getExtra("user_id").toString())));
    }

    /**
     * 更改用户设置-动态背景
     *
     * @param dynamicBackground 0or1
     * @return ok
     */
    @Override
    public NoteResultUtil updDynamicBackground(String dynamicBackground) {
        String userId = StpUtil.getExtra("user_id").toString();
        Settings settings = new Settings();
        settings.setDynamicBackground("true".equals(dynamicBackground) ? 1 : 0);
        return NoteResultUtil.success(settingsMapper.update(settings,new QueryWrapper<Settings>().eq("user_id",userId)));
    }
}
