package note.recordAndShare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-07 02:33:54
 */
@Getter
@Setter
@TableName("UserAdmin")
public class UserAdmin {

    @TableId(value = "Uid", type = IdType.AUTO)
    private Integer uid;

    @TableField("name")
    private String name;

    @TableField("nickName")
    private String nickName;

    @TableField("password")
    private String password;


}
