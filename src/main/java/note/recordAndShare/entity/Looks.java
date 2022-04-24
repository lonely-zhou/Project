package note.recordAndShare.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户浏览
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-04-01 05:15:33
 */
@Getter
@Setter
@TableName("looks")
public class Looks {

    @MppMultiId
    @TableField("user_id")
    private String userId;

    @MppMultiId
    @TableField("note_id")
    private String noteId;

    @TableField("time")
    private String time;


}
