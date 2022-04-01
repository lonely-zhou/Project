package note.recordAndShare.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.ReportNote;
import note.recordAndShare.entity.dto.ReportNoteDto;
import note.recordAndShare.mapper.ReportNoteMapper;
import note.recordAndShare.service.ReportNoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.NoteResultUtil;
import note.utils.TimeUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 举报表 服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-11 12:35:35
 */
@Service
@AllArgsConstructor
public class ReportNoteServiceImpl extends ServiceImpl<ReportNoteMapper, ReportNote> implements ReportNoteService {
    private final ReportNoteMapper reportNoteMapper;

    /**
     * 举报笔记
     *
     * @param reportNote 举报信息
     * @return ok
     */
    @Override
    public NoteResultUtil insReportNote(ReportNote reportNote) {
//        reportNote.setId(UUID.randomUUID().toString());
        reportNote.setTime(new TimeUtil().getFormatDateForFive());
        if (reportNoteMapper.insert(reportNote) == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("举报失败");
    }

    /**
     * 查询所有举报笔记
     *
     * @return 所有举报笔记
     */
    @Override
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

    /**
     * 撤销举报笔记状态
     *
     * @param noteId 笔记id
     * @return ok
     */
    @Override
    public NoteResultUtil delReportNote(String noteId) {
        int count = reportNoteMapper.delete(new QueryWrapper<ReportNote>().eq("noteId", noteId));
        if (count > 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("撤销失败");
    }

    /**
     * 搜索举报笔记
     *
     * @param page  分页
     * @param title 笔记标题
     * @return 举报笔记
     */
    @Override
    public NoteResultUtil searchNote(Integer page, String title) {
        return NoteResultUtil.success(reportNoteMapper.searchNote(new Page<>(page, 9), title));
    }
}
