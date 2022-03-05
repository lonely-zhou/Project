package note.recordAndShare.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * @since 2022-01-26 09:57:41
 */
@Getter
@Setter
@TableName("note")
public class Note {

    /**
     * 标识符
     */
    @TableId("id")
    private String id;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 正文
     */
    @TableField("text")
    private String text;

    /**
     * 标签
     */
    @TableField("label_values")
    private String labelValues;

    /**
     * 文章类型
     */
    @TableField("select_type")
    private String selectType;

    /**
     * 笔记分类
     */
    @TableField("select_categories")
    private String selectCategories;

    /**
     * 文章等级
     */
    @TableField("select_grade")
    private Integer selectGrade;

    /**
     * 原文章作者
     */
    @TableField("original_author")
    private String originalAuthor;

    /**
     * 文章（0-公开  1-私密）
     */
    @TableField("message")
    private String message;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private String createTime;

    /**
     * 点赞
     */
    @TableField("likes")
    private Integer likes;

    /**
     * 作者名字
     */
    @TableField("name")
    private String name;

    /**
     * 文章摘要
     */
    @TableField("article_tabled")
    private String articleTabled;

    /**
     * 浏览次数
     */
    @TableField("look")
    private Integer look;

    /**
     * 收藏数
     */
    @TableField("collection")
    private Integer collection;

    /**
     * 作者ID
     */
    @TableField("user_id")
    private String userId;

    @TableField("note_type")
    private String noteType;

}
