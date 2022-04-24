package note.recordAndShare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import note.recordAndShare.entity.Looks;
import note.utils.NoteResultUtil;

/**
 * <p>
 * 用户浏览 服务类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 11:39:06
 */
public interface LooksService extends IService<Looks> {
    /**
     * 用户点赞笔记
     * @param noteId 笔记id
     * @return ok
     */
    NoteResultUtil insUserLook(String noteId);
}
