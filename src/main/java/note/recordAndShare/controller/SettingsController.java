package note.recordAndShare.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import note.recordAndShare.entity.Settings;
import note.recordAndShare.mapper.SettingsMapper;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户设置 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-03 04:00:31
 */
@Data
@RestController
@RequestMapping("/recordAndShare/settings")
public class SettingsController {

    private final SettingsMapper settingsMapper;

    /**
     * 更改用户默认编辑器
     *
     * @param userId 用户ID
     * @param editor 编辑器
     * @return ok
     */
    @GetMapping("/updUserEditorStyle")
    public NoteResultUtil updUserEditorStyle(@RequestParam("userId") String userId, @RequestParam("editor") String editor) {
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

    @GetMapping("/selUserSettingsList")
    public NoteResultUtil selUserSettingsList(@RequestParam("userId") String userId) {
        return NoteResultUtil.success(settingsMapper.selectById(userId));
    }
}
