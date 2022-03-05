package note.recordAndShare.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户浏览
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 11:39:06
 */
@Getter
@Setter
@TableName("looks")
public class Looks {

    @TableId("id")
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("note_id")
    private String noteId;

    @TableField("time")
    private String time;


}
