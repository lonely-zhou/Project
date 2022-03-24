package note.recordAndShare.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.Data;
import note.recordAndShare.entity.Note;
import note.recordAndShare.service.NoteService;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * </p>
 * RequiresAuthentication
 *
 * @author lonelyzhou
 * @since 2022-01-26 09:57:41
 */
@Data
@RestController
@RequestMapping("/recordAndShare/note")
public class NoteController {

    private final NoteService noteService;

    /**
     * 查询笔记列表
     *
     * @param page 页
     * @return 笔记列表
     */
    @GetMapping("/getNoteList")
    public NoteResultUtil getNoteList(@RequestParam("page") Integer page) {
        return noteService.getNoteList(page);
    }

    /**
     * 增加笔记
     *
     * @param note 笔记
     * @return ok
     */
    @SaCheckLogin
    @PostMapping("/insNote")
    public NoteResultUtil insNote(@RequestBody Note note) {
        return noteService.insNote(note);
    }

    /**
     * 查询用户笔记
     *
     * @param page 页码
     * @return 用户笔记列表
     */
    @GetMapping("/selUserNote")
    public NoteResultUtil selUserNote(@RequestParam("page") Integer page, @RequestParam(value = "message", required = false) String message) {
        return noteService.selUserNote(page, message);
    }

    /**
     * 查询笔记
     *
     * @param noteId 笔记ID
     * @return 笔记
     */
    @GetMapping("/selNote")
    public NoteResultUtil selNote(@RequestParam("noteId") String noteId) {
        return noteService.selNote(noteId);

    }

    /**
     * 更新笔记
     *
     * @param note 笔记
     * @return ok
     */
    @PostMapping("/updUserNote")
    public NoteResultUtil updUserNote(@RequestBody Note note) {
        return noteService.updUserNote(note);
    }

    /**
     * 删除用户笔记
     *
     * @param noteId 笔记ID
     * @return ok
     */
    @DeleteMapping("delUserNote")
    public NoteResultUtil delUserNote(@RequestParam("noteId") String noteId) {
        return noteService.delUserNote(noteId);
    }

    /**
     * 搜索笔记
     *
     * @param q       搜索关键字
     * @param page    分页
     * @param orderBy 排序
     * @param groupBy 分组
     * @return 笔记列表
     */
    @GetMapping("selSearchNote")
    public NoteResultUtil selSearchNote(@RequestParam("q") String q, @RequestParam("page") Integer page,
                                        @RequestParam(value = "orderBy", required = false) String orderBy,
                                        @RequestParam(value = "groupBy", required = false) String groupBy,
                                        @RequestParam(value = "label", required = false) String label) {
        return noteService.selSearchNote(q, page, orderBy, groupBy, label);
    }

    /**
     * 查询笔记所有标签
     *
     * @return 笔记所有标签
     */
    @GetMapping("selLabelValuesList")
    public NoteResultUtil selLabelValuesList() {
        return noteService.selLabelValuesList();
    }

}
