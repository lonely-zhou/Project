package note.recordAndShare.controller;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import note.recordAndShare.entity.Comment;
import note.recordAndShare.mapper.CommentMapper;
import note.utils.NoteResultUtil;
import note.utils.UserUtil;
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

    private final CommentMapper commentMapper;

    /**
     * 查询笔记评论数
     *
     * @param noteId 笔记ID
     * @return 评论数
     */
    @GetMapping("/selNoteCommentCount")
    public NoteResultUtil selNoteCommentCount(@RequestParam("noteId") String noteId) {
        int count = commentMapper.selectCount(new QueryWrapper<Comment>().eq("note_id", noteId)).intValue();
        return NoteResultUtil.success(count);
    }

    /**
     * 增加评论
     *
     * @param comment 评论信息
     * @return ok
     */
    @PostMapping("/insNoteComment")
    public NoteResultUtil insNoteComment(@RequestBody Comment comment) {
        comment.setId(UUID.randomUUID().toString());
        if (commentMapper.insert(comment) != 1) {
            return NoteResultUtil.error("评论失败");
        }
        return NoteResultUtil.success();
    }

    /**
     * 查询笔记所有评论
     *
     * @param noteId 笔记ID
     * @return 笔记评论
     */
    @GetMapping("/selNoteCommentList")
    public NoteResultUtil selNoteCommentList(@RequestParam("noteId") String noteId, @RequestParam("page") Integer page) {
        int count = commentMapper.selectCount(new QueryWrapper<Comment>().eq("note_id", noteId)).intValue();
        return NoteResultUtil.success(String.valueOf(count), commentMapper.selNoteCommentList(new Page<>(page, 5), noteId));
    }

    /**
     * 查询用户所有评论
     *
     * @param page 页码
     * @return 用户所有评论
     */
    @GetMapping("/selUserCommentList")
    public NoteResultUtil selUserCommentList(@RequestParam("page") Integer page) {
        int count = commentMapper.selectCount(new QueryWrapper<Comment>().eq("user_id", UserUtil.selUserId())).intValue();
        return NoteResultUtil.success(String.valueOf(count), commentMapper.selUserCommentList(new Page<>(page, 5), UserUtil.selUserId()));
    }

    /**
     * 更新笔记评论
     *
     * @param comment 评论
     * @return 更新后评论
     */
    @PostMapping("/updNoteComment")
    public NoteResultUtil updNoteComment(@RequestBody Comment comment) {
        int count = commentMapper.updateById(comment);
        if (count == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("评论修改失败");
    }

    /**
     * 删除评论
     *
     * @param id 评论ID
     * @return ok
     */
    @DeleteMapping("/delNoteComment")
    public NoteResultUtil delNoteComment(@RequestParam("id") String id) {
        int count = commentMapper.deleteById(id);
        if (count == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("删除失败");
    }
}
