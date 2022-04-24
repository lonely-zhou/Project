package note.recordAndShare.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-04-01 05:15:33
 */
@Getter
@Setter
@TableName("role")
public class Role {

    /**
     * 标识符
     */
    @TableId("rid")
    private Integer rid;

    /**
     * 角色
     */
    @TableField("rname")
    private String rname;


}
