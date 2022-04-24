package note.recordAndShare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import note.recordAndShare.entity.Likes;
import note.utils.NoteResultUtil;

/**
 * <p>
 * 用户点赞 服务类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 11:39:06
 */
public interface LikesService extends IService<Likes> {
    /**
     * 用户点赞
     *
     * @param noteId 笔记ID
     * @return 点赞数
     */
    NoteResultUtil insLike(String noteId);

    /**
     * 查询用户 所有点赞笔记
     *
     * @param page 分页
     * @return 所有点赞笔记
     */
    NoteResultUtil selUserLikeList(Integer page);

    /**
     * 用户是否点赞笔记
     *
     * @param noteId 笔记ID
     * @return 是否
     */
    NoteResultUtil isUserLikeNote(String noteId);

    /**
     * 删除用户点赞笔记
     *
     * @param noteId 笔记ID
     * @return ok
     */
    NoteResultUtil delUserLikeNote(String noteId);
}
