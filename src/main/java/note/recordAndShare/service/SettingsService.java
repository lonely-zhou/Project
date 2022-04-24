package note.recordAndShare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import note.recordAndShare.entity.Settings;
import note.utils.NoteResultUtil;

/**
 * <p>
 * 用户设置 服务类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-03 04:00:31
 */
public interface SettingsService extends IService<Settings> {
    /**
     * 更改用户默认编辑器
     *
     * @param editor 编辑器
     * @return ok
     */
    NoteResultUtil updUserEditorStyle(String editor);

    /**
     * 查询用户所有设置
     *
     * @return 用户所有设置
     */
    NoteResultUtil selUserSettingsList();

    /**
     * 更改用户设置-动态背景
     * @param dynamicBackground 0or1
     * @return ok
     */
    NoteResultUtil updDynamicBackground(String dynamicBackground);
}
