package note.recordAndShare.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 举报表
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-11 11:30:56
 */
@Getter
@Setter
@TableName("report")
public class Report {

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
     * 违规用户ID
     */
    @TableField("noteId")
    private String noteId;

    /**
     * 违规笔记ID
     */
    @TableField("userId")
    private String userId;


}
