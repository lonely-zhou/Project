package note.recordAndShare.service;

import note.recordAndShare.entity.ReportNote;
import com.baomidou.mybatisplus.extension.service.IService;
import note.utils.NoteResultUtil;

/**
 * <p>
 * 举报表 服务类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-11 12:35:35
 */
public interface ReportNoteService extends IService<ReportNote> {
    /**
     * 举报笔记
     *
     * @param reportNote 举报信息
     * @return ok
     */
    NoteResultUtil insReportNote(ReportNote reportNote);

    /**
     * 查询所有举报笔记
     *
     * @return 所有举报笔记
     */
    NoteResultUtil selAllReportNote();

    /**
     * 撤销举报笔记状态
     *
     * @param noteId 笔记id
     * @return ok
     */
    NoteResultUtil delReportNote(String noteId);
}
