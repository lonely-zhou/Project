package note.recordAndShare.service;

import note.recordAndShare.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import note.utils.NoteResultUtil;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-20 12:28:53
 */
public interface CommentService extends IService<Comment> {
    /**
     * 查询笔记评论数
     *
     * @param noteId 笔记ID
     * @return 评论数
     */
    NoteResultUtil selNoteCommentCount(String noteId);

    /**
     * 增加评论
     *
     * @param comment 评论信息
     * @return ok
     */
    NoteResultUtil insNoteComment(Comment comment);

    /**
     * 查询笔记所有评论
     *
     * @param page   分页
     * @param noteId 笔记ID
     * @return 笔记评论
     */
    NoteResultUtil selNoteCommentList(String noteId, Integer page);

    /**
     * 查询用户所有评论
     *
     * @param page 页码
     * @return 用户所有评论
     */
    NoteResultUtil selUserCommentList(Integer page);
    /**
     * 更新笔记评论
     *
     * @param comment 评论
     * @return 更新后评论
     */
    NoteResultUtil updNoteComment(Comment comment);
    /**
     * 删除评论
     *
     * @param id 评论ID
     * @return ok
     */
    NoteResultUtil delNoteComment(String id);
}
