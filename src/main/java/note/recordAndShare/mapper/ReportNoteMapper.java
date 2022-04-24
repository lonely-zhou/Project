package note.recordAndShare.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import note.recordAndShare.entity.ReportNote;
import note.recordAndShare.entity.dto.ReportNoteDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * 查询所有举报笔记id
     *
     * @return 有举报笔记id
     */
    @Select("select distinct noteId from reportNote")
    List<String> selAllReportNoteId();

    /**
     * 用举报笔记id查询笔记信息
     *
     * @param noteId 笔记id
     * @return 笔记信息
     */
    ReportNoteDto selReportNoteInfoById(@Param("noteId") String noteId);

    /**
     * 用id查询所有笔记举报理由
     *
     * @param noteId 笔记id
     * @return 所有笔记举报理由
     */
    List<String> selAllReportNoteMessageById(@Param("noteId") String noteId);

    /**
     * 搜索举报笔记
     * @param page 分页
     * @param title 笔记标题
     * @return 笔记信息
     */
    IPage<ReportNoteDto> searchNote(Page<?> page, @RequestParam("title") String title);
}
