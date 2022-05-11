package note.recordAndShare.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Comment;
import note.recordAndShare.mapper.CommentMapper;
import note.recordAndShare.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.NoteResultUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-20 12:28:53
 */
@Service
@AllArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    private final CommentMapper commentMapper;

    /**
     * 查询笔记评论数
     *
     * @param noteId 笔记ID
     * @return 评论数
     */
    @Override
    public NoteResultUtil selNoteCommentCount(String noteId) {
        int count = commentMapper.selectCount(new QueryWrapper<Comment>().eq("note_id", noteId)).intValue();
        return NoteResultUtil.success(count);
    }

    /**
     * 增加评论
     *
     * @param comment 评论信息
     * @return ok
     */
    @Override
    public NoteResultUtil insNoteComment(Comment comment) {
        comment.setUserId(StpUtil.getExtra("user_id").toString());
        if (commentMapper.insert(comment) != 1) {
            return NoteResultUtil.error("评论失败");
        }
        return NoteResultUtil.success();
    }

    /**
     * 查询笔记所有评论
     *
     * @param noteId 笔记ID
     * @param page   分页
     * @return 笔记评论
     */
    @Override
    public NoteResultUtil selNoteCommentList(String noteId, Integer page) {
        int count = commentMapper.selectCount(new QueryWrapper<Comment>().eq("note_id", noteId)).intValue();
        return NoteResultUtil.success(String.valueOf(count), commentMapper.selNoteCommentList(new Page<>(page, 5), noteId));
    }

    /**
     * 查询用户所有评论
     *
     * @param page 页码
     * @return 用户所有评论
     */
    @Override
    public NoteResultUtil selUserCommentList(Integer page) {
        int count = commentMapper.selectCount(new QueryWrapper<Comment>().eq("user_id", StpUtil.getExtra("user_id").toString())).intValue();
        return NoteResultUtil.success(String.valueOf(count), commentMapper.selUserCommentList(new Page<>(page, 5), StpUtil.getExtra("user_id").toString()));
    }

    /**
     * 更新笔记评论
     *
     * @param comment 评论
     * @return 更新后评论
     */
    @Override
    public NoteResultUtil updNoteComment(Comment comment) {
        System.out.println(comment.getNoteId());
        int count = commentMapper.update(comment,new QueryWrapper<Comment>()
                .eq("note_id",comment.getNoteId())
                .eq("user_id",StpUtil.getExtra("user_id")));
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
    @Override
    public NoteResultUtil delNoteComment(String id) {
        int count = commentMapper.delete(new QueryWrapper<Comment>().eq("note_id",id));
        if (count == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("删除失败");
    }
}
