package note.recordAndShare.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户点赞
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-04-01 05:15:33
 */
@Getter
@Setter
@TableName("likes")
public class Likes {

    /**
     * 用户ID
     */
    @MppMultiId
    @TableField("user_id")
    private String userId;

    /**
     * 笔记ID
     */
    @MppMultiId
    @TableField("note_id")
    private String noteId;

    /**
     * 时间
     */
    @TableField("time")
    private String time;


}
