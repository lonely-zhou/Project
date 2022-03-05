package note.recordAndShare.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户收藏表
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 12:25:12
 */
@Getter
@Setter
@TableName("collects")
public class Collects {

    /**
     * 收藏表ID
     */
    @TableId("id")
    private String id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 笔记ID
     */
    @TableField("note_id")
    private String noteId;

    /**
     * 收藏时间
     */
    @TableField("time")
    private String time;


}
