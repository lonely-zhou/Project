package note.recordAndShare.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description:
 * @Date: 2022/3/11 下午 2:40
 **/
@Data
@TableName("reportNote")
public class ReportNoteDto {

    /**
     * 举报表ID
     */
    @TableId("id")
    private String id;

    /**
     * 举报者邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 违规笔记ID
     */
    @TableField("noteId")
    private String noteId;

    /**
     * 举报理由
     */
    @TableField("message")
    private List<String> message;

    /**
     * 时间
     */
    @TableField("time")
    private String time;

    private String title;

    /**
     * 文章摘要
     */
    @TableField("article_tabled")
    private String articleTabled;

    /**
     * 作者名字
     */
    @TableField("name")
    private String name;

}
