package note.recordAndShare.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import note.recordAndShare.entity.Note;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import note.recordAndShare.entity.dto.NoteDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-26 09:57:41
 */
@Mapper
public interface NoteMapper extends BaseMapper<Note> {
    /**
     * 分页查询
     *
     * @param page ""
     * @return ""
     */
    IPage<NoteDto> selectNotePage(Page<?> page);

    /**
     * 用户笔记查询
     *
     * @param message 是否公开
     * @param page    页码
     * @param userid  userid
     * @return 笔记列表
     */
    IPage<NoteDto> selectUserNote(Page<?> page, @Param("userid") String userid, String message);

    /**
     * 查询 搜索
     *
     * @param page    分页
     * @param q       搜索关键字
     * @param label   标签
     * @param orderBy 排序
     * @param groupBy 分组
     * @return 笔记列表
     */
    IPage<NoteDto> selSearchNote(Page<?> page, @Param("q") String q,
                                 @Param("orderBy") String orderBy,
                                 @Param("groupBy") String groupBy,
                                 @Param("label") String label);

    /**
     * 查询笔记所有标签
     *
     * @return 笔记所有标签
     */
    @Select("select distinct label_values from note")
    List<String> selLabelValuesList();

    /**
     * 查询分类笔记
     *
     * @param classification 分类
     * @param page           分页
     * @return 笔记列表
     */
    IPage<NoteDto> selClassificationNote(Page<?> page, @Param("classification") String classification);

}
