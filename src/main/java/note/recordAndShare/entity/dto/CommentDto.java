package note.recordAndShare.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description:
 * @Date: 2022/2/21 下午 2:17
 **/
@TableName("comment")
@Data
public class CommentDto {

    /**
     * 评论ID
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
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 评论内容
     */
    @TableField("message")
    private String message;

    /**
     * 时间
     */
    @TableField("time")
    private String time;

    /**
     * 头像URL
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;
}
