package note.recordAndShare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import note.recordAndShare.entity.Feedback;
import note.utils.NoteResultUtil;

/**
 * <p>
 * 反馈表 服务类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-16 04:30:34
 */
public interface FeedbackService extends IService<Feedback> {
    /**
     * 意见反馈
     *
     * @param feedback 反馈信息
     * @return ok
     */
    NoteResultUtil insFeedback(Feedback feedback);

    /**
     * 查询用户反馈
     * @param page 分页
     * @return 用户反馈信息
     */
    NoteResultUtil selUserFeedback(Integer page);

    /**
     * 更新反馈状态
     * @param feedback 反馈信息
     * @return 反馈状态
     */
    NoteResultUtil updUserFeedbackState(Feedback feedback);

    /**
     * 删除用户反馈
     * @param id 反馈id
     * @return ok
     */
    NoteResultUtil delUserFeedback(String id);
}
