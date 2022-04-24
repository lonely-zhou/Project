package note.recordAndShare.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-04-01 05:15:33
 */
@Getter
@Setter
@TableName("user")
public class User {

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
     * 密码
     */
    @TableField("password")
    private String password;

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
     * 角色(1-管理员 2-普通用户)
     */
    @TableField("role_id")
    private Integer roleId;

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


}
