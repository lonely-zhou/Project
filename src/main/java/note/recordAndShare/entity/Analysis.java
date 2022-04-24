package note.recordAndShare.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 网站数据
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-04-01 05:15:33
 */
@Getter
@Setter
@TableName("analysis")
public class Analysis {

    /**
     * id
     */
    @TableId("id")
    private String id;

    /**
     * 用户数
     */
    @TableField("userNum")
    private Integer userNum;

    /**
     * 笔记数
     */
    @TableField("noteNum")
    private Integer noteNum;

    /**
     * 评论数
     */
    @TableField("commentNum")
    private Integer commentNum;

    /**
     * 浏览数
     */
    @TableField("lookNum")
    private Integer lookNum;


}
