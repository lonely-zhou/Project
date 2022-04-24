package note.recordAndShare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import note.recordAndShare.entity.Note;
import note.utils.NoteResultUtil;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-26 09:57:41
 */
public interface NoteService extends IService<Note> {
    /**
     * 增加笔记
     *
     * @param note 笔记
     * @return 0 or 1
     */
    NoteResultUtil insNote(Note note);

    /**
     * 查询笔记列表
     *
     * @param page 页
     * @return 笔记列表
     */
    NoteResultUtil getNoteList(Integer page);

    /**
     * 查询用户笔记
     *
     * @param page    页码
     * @param message 是否公开
     * @return 用户笔记列表
     */
    NoteResultUtil selUserNote(Integer page, String message);

    /**
     * 查询笔记
     *
     * @param noteId 笔记ID
     * @return 笔记
     */
    NoteResultUtil selNote(String noteId);

    /**
     * 更新笔记
     *
     * @param note 笔记
     * @return ok
     */
    NoteResultUtil updUserNote(Note note);

    /**
     * 删除用户笔记
     *
     * @param noteId 笔记ID
     * @return ok
     */
    NoteResultUtil delUserNote(String noteId);

    /**
     * 搜索笔记
     *
     * @param q       搜索关键字
     * @param page    分页
     * @param orderBy 排序
     * @param groupBy 分组
     * @param label   标签
     * @return 笔记列表
     */
    NoteResultUtil selSearchNote(String q, Integer page, String orderBy, String groupBy, String label);

    /**
     * 查询分类笔记
     * @param classification 分类
     * @param page 分页
     * @return 笔记列表
     */
    NoteResultUtil selClassificationNote(String classification,Integer page);

    /**
     * 查询笔记所有标签
     *
     * @return 笔记所有标签
     */
    NoteResultUtil selLabelValuesList();
}
