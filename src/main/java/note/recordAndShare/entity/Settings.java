package note.recordAndShare.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户设置
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-03 04:00:31
 */
@Getter
@Setter
@TableName("settings")
public class Settings {

    @TableId("user_id")
    private String userId;

    /**
     * 编辑器样式
     */
    @TableField("editor_style")
    private String editorStyle;


}
