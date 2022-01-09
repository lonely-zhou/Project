package note.recordAndShare.entity;

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
 * @since 2022-01-08 07:37:25
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
