package note.recordAndShare.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 反馈表
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-16 04:30:34
 */
@Getter
@Setter
@TableName("feedback")
public class Feedback {

    /**
     * 反馈表ID
     */
    @TableId("id")
    private String id;

    /**
     * 用户ID
     */
    @TableField("userId")
    private String userId;

    /**
     * 反馈信息
     */
    @TableField("text")
    private String text;

    /**
     * 时间
     */
    @TableField("time")
    private String time;


}
