package note.recordAndShare.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import note.recordAndShare.entity.Comment;
import note.recordAndShare.entity.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-20 12:28:53
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 查询笔记评论
     *
     * @param page   页码
     * @param noteId 笔记ID
     * @return 笔记评论
     */
    IPage<CommentDto> selNoteCommentList(Page<?> page, @Param("noteId") String noteId);

    /**
     * 查询用户评论 列表
     *
     * @param page 页码
     * @param userId 用户ID
     * @return 用户评论 列表
     */
    IPage<CommentDto> selUserCommentList(Page<?> page, @Param("userId") String userId);
}
