package note.recordAndShare.controller;

import lombok.Data;
import note.recordAndShare.entity.Comment;
import note.recordAndShare.service.CommentService;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-20 12:28:53
 */
@Data
@RestController
@RequestMapping("/recordAndShare/comment")
public class CommentController {

    private final CommentService commentService;

    /**
     * 查询笔记评论数
     *
     * @param noteId 笔记ID
     * @return 评论数
     */
    @GetMapping("/selNoteCommentCount")
    public NoteResultUtil selNoteCommentCount(@RequestParam("noteId") String noteId) {
        return commentService.selNoteCommentCount(noteId);
    }

    /**
     * 增加评论
     *
     * @param comment 评论信息
     * @return ok
     */
    @PostMapping("/insNoteComment")
    public NoteResultUtil insNoteComment(@RequestBody Comment comment) {
        return commentService.insNoteComment(comment);
    }

    /**
     * 查询笔记所有评论
     *
     * @param noteId 笔记ID
     * @return 笔记评论
     */
    @GetMapping("/selNoteCommentList")
    public NoteResultUtil selNoteCommentList(@RequestParam("noteId") String noteId, @RequestParam("page") Integer page) {
        return commentService.selNoteCommentList(noteId, page);
    }

    /**
     * 查询用户所有评论
     *
     * @param page 页码
     * @return 用户所有评论
     */
    @GetMapping("/selUserCommentList")
    public NoteResultUtil selUserCommentList(@RequestParam("page") Integer page) {
        return commentService.selUserCommentList(page);
    }

    /**
     * 更新笔记评论
     *
     * @param comment 评论
     * @return 更新后评论
     */
    @PostMapping("/updNoteComment")
    public NoteResultUtil updNoteComment(@RequestBody Comment comment) {
        return commentService.updNoteComment(comment);
    }

    /**
     * 删除评论
     *
     * @param id 评论ID
     * @return ok
     */
    @DeleteMapping("/delNoteComment")
    public NoteResultUtil delNoteComment(@RequestParam("id") String id) {
        return commentService.delNoteComment(id);
    }
}
