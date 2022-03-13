package note.recordAndShare.controller;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import note.recordAndShare.entity.ReportNote;
import note.recordAndShare.entity.dto.ReportNoteDto;
import note.recordAndShare.mapper.ReportNoteMapper;
import note.utils.NoteResultUtil;
import note.utils.TimeUtil;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    private final ReportNoteMapper reportNoteMapper;

    /**
     * 举报笔记
     *
     * @param reportNote 举报信息
     * @return ok
     */
    @PostMapping("insReportNote")
    public NoteResultUtil insReportNote(@RequestBody ReportNote reportNote) {
        reportNote.setId(UUID.randomUUID().toString());
        reportNote.setTime(new TimeUtil().getFormatDateForFive());
        reportNoteMapper.insert(reportNote);
        return NoteResultUtil.success();
    }

    /**
     * 查询所有举报笔记
     *
     * @return 所有举报笔记
     */
    @GetMapping("selAllReportNote")
    public NoteResultUtil selAllReportNote() {
        List<Object> allReportNote = new ArrayList<>();
        List<String> allReportNoteId = reportNoteMapper.selAllReportNoteId();
        for (String s : allReportNoteId) {
            ReportNoteDto reportNoteDto = reportNoteMapper.selReportNoteInfoById(s);
            List<String> allReportNoteMessageById = reportNoteMapper.selAllReportNoteMessageById(s);
            reportNoteDto.setMessage(allReportNoteMessageById);
            allReportNote.add(reportNoteDto);
        }
        return NoteResultUtil.success(allReportNote);
    }

    @DeleteMapping("delReportNote")
    public NoteResultUtil delReportNote(@RequestParam("noteId") String noteId) {
        if (reportNoteMapper.delete(new QueryWrapper<ReportNote>().eq("noteId", noteId)) > 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("撤销失败");
    }
}
