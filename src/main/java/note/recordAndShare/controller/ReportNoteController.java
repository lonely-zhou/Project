package note.recordAndShare.controller;


import lombok.Data;
import note.recordAndShare.entity.ReportNote;
import note.recordAndShare.service.ReportNoteService;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 举报表 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-11 12:35:35
 */
@Data
@RestController
@RequestMapping("/recordAndShare/report-note")
public class ReportNoteController {

    private final ReportNoteService reportNoteService;

    /**
     * 举报笔记
     *
     * @param reportNote 举报信息
     * @return ok
     */
    @PostMapping("insReportNote")
    public NoteResultUtil insReportNote(@RequestBody ReportNote reportNote) {
        return reportNoteService.insReportNote(reportNote);
    }

    /**
     * 查询所有举报笔记
     *
     * @return 所有举报笔记
     */
    @GetMapping("selAllReportNote")
    public NoteResultUtil selAllReportNote() {
        return reportNoteService.selAllReportNote();
    }

    /**
     * 撤销举报笔记状态
     *
     * @param noteId 笔记id
     * @return ok
     */
    @DeleteMapping("delReportNote")
    public NoteResultUtil delReportNote(@RequestParam("noteId") String noteId) {
        return reportNoteService.delReportNote(noteId);
    }
}
