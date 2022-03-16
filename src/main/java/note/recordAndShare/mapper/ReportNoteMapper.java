package note.recordAndShare.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import note.recordAndShare.entity.ReportNote;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import note.recordAndShare.entity.dto.ReportNoteDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 举报表 Mapper 接口
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-11 12:35:35
 */
@Mapper
public interface ReportNoteMapper extends BaseMapper<ReportNote> {
    /**
     * 查询所有举报笔记
     *
     * @param page 分页
     * @return 所有举报笔记
     */
    IPage<ReportNoteDto> selAllReportNote(Page<?> page);

    @Select("select distinct noteId from reportNote")
    List<String> selAllReportNoteId();

    ReportNoteDto selReportNoteInfoById(@Param("noteId") String noteId);

    List<String> selAllReportNoteMessageById(@Param("noteId") String noteId);
}
