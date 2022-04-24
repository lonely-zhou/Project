package note.recordAndShare.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description:
 * @Date: 2022/2/23 下午 2:31
 **/
@Data
public class LikesDto {
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
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 时间
     */
    @TableField("time")
    private String time;


}
