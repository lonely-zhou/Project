package note.recordAndShare.controller;


import lombok.Data;
import note.recordAndShare.service.SettingsService;
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

    private final SettingsService settingsService;

    /**
     * 更改用户默认编辑器
     *
     * @param editor 编辑器
     * @return ok
     */
    @GetMapping("/updUserEditorStyle")
    public NoteResultUtil updUserEditorStyle(@RequestParam("editor") String editor) {
        return settingsService.updUserEditorStyle(editor);
    }

    /**
     * 查询用户所有设置
     *
     * @return 用户所有设置
     */
    @GetMapping("/selUserSettingsList")
    public NoteResultUtil selUserSettingsList() {
        return settingsService.selUserSettingsList();
    }

    @GetMapping("updDynamicBackground")
    public NoteResultUtil updDynamicBackground(@RequestParam("dynamicBackground") String dynamicBackground) {
        return settingsService.updDynamicBackground(dynamicBackground);
    }
}
