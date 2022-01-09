package note.recordAndShare.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description:
 * @Date: 2022/1/8 下午 7:38
 **/

@Data
@TableName("user")
public class UserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1859552324788487470L;

    /**
     * 标识符
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
    private Integer sex;

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
}
