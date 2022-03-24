package note.recordAndShare.service;

import note.recordAndShare.entity.Collects;
import com.baomidou.mybatisplus.extension.service.IService;
import note.utils.NoteResultUtil;

/**
 * <p>
 * 用户收藏表 服务类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 12:25:12
 */
public interface CollectsService extends IService<Collects> {
    /**
     * 增加用户收藏
     *
     * @param noteId 笔记ID
     * @return 收藏数
     */
    NoteResultUtil insUserNoteCollect(String noteId);

    /**
     * 查询用户所有收藏笔记
     *
     * @param page 分页
     * @return 用户所有收藏笔记
     */
    NoteResultUtil selUserNoteCollectList(Integer page);

    /**
     * 删除用户 收藏笔记
     *
     * @param noteId 笔记ID
     * @return ok
     */
    NoteResultUtil delUserNoteCollect(String noteId);

    /**
     * 用户是否收藏笔记
     *
     * @param noteId 笔记ID
     * @return true or false
     */
    NoteResultUtil isUserNoteCollect(String noteId);
}
