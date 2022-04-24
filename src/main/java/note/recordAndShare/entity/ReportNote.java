package note.recordAndShare.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 举报表
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-04-01 05:15:33
 */
@Getter
@Setter
@TableName("reportNote")
public class ReportNote {

    /**
     * 举报表ID
     */
    @MppMultiId
    @TableField("userId")
    private String userId;

    /**
     * 举报者邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 违规笔记ID
     */
    @MppMultiId
    @TableField("noteId")
    private String noteId;

    /**
     * 举报理由
     */
    @TableField("message")
    private String message;

    /**
     * 时间
     */
    @TableField("time")
    private String time;


}
