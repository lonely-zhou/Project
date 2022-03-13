package note.recordAndShare.entity.dto;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description:
 * @Date: 2022/1/27 下午 2:31
 **/

@Data
public class UserDto {

    /**
     * 标识符
     */
    @TableId("id")
    private String id;

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
    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 最后一次登录时间
     */
    @TableField("last_time")
    private String lastTime;
    /**
     * email邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 头像URL
     */
    @TableField("avatar_url")
    private String avatarUrl;

    private String rname;


}

