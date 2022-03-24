package note.recordAndShare.controller;

import lombok.Data;
import note.recordAndShare.service.CollectsService;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户收藏表 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 12:25:12
 */
@Data
@RestController
@RequestMapping("/recordAndShare/collects")
public class CollectsController {

    private final CollectsService collectsService;

    /**
     * 增加用户收藏
     *
     * @param noteId 笔记ID
     * @return 收藏数
     */
    @GetMapping("/insUserNoteCollect")
    public NoteResultUtil insUserNoteCollect(@RequestParam("noteId") String noteId) {
        return collectsService.insUserNoteCollect(noteId);
    }

    /**
     * 查询用户所有收藏笔记
     *
     * @param page 分页
     * @return 用户所有收藏笔记
     */
    @GetMapping("/selUserNoteCollectList")
    public NoteResultUtil selUserNoteCollectList(@RequestParam("page") Integer page) {
        return collectsService.selUserNoteCollectList(page);
    }

    /**
     * 删除用户 收藏笔记
     *
     * @param noteId 笔记ID
     * @return ok
     */
    @DeleteMapping("/delUserNoteCollect")
    public NoteResultUtil delUserNoteCollect(@RequestParam("noteId") String noteId) {
        return collectsService.delUserNoteCollect(noteId);
    }

    /**
     * 用户是否收藏笔记
     *
     * @param noteId 笔记ID
     * @return true or false
     */
    @GetMapping("isUserNoteCollect")
    public NoteResultUtil isUserNoteCollect(@RequestParam("noteId") String noteId) {
        return collectsService.isUserNoteCollect(noteId);
    }
}
