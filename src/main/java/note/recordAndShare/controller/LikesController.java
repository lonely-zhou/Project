package note.recordAndShare.controller;

import lombok.Data;
import note.recordAndShare.service.LikesService;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 用户点赞 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 11:39:06
 */
@RestController
@Data
@RequestMapping("/recordAndShare/likes")
public class LikesController {

    private final LikesService likesService;

    /**
     * 用户点赞
     *
     * @param noteId 笔记ID
     * @return 点赞数
     */
    @GetMapping("/insLike")
    public NoteResultUtil insLike(@RequestParam("noteId") String noteId) {
        return likesService.insLike(noteId);
    }

    /**
     * 查询用户 所有点赞笔记
     *
     * @return 所有点赞笔记
     */
    @GetMapping("/selUserLikeList")
    public NoteResultUtil selUserLikeList(@RequestParam("page") Integer page) {
        return likesService.selUserLikeList(page);
    }

    /**
     * 用户是否点赞笔记
     *
     * @param noteId 笔记ID
     * @return 是否
     */
    @GetMapping("/isUserLikeNote")
    public NoteResultUtil isUserLikeNote(@RequestParam("noteId") String noteId) {
        return likesService.isUserLikeNote(noteId);
    }

    /**
     * 删除用户点赞笔记
     *
     * @param noteId 笔记ID
     * @return ok
     */
    @DeleteMapping("/delUserLikeNote")
    public NoteResultUtil delUserLikeNote(@RequestParam("noteId") String noteId) {
        return likesService.delUserLikeNote(noteId);
    }
}
