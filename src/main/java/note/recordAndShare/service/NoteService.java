package note.recordAndShare.service;

import note.recordAndShare.entity.Note;
import com.baomidou.mybatisplus.extension.service.IService;

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
    int insNote(Note note);
}
