package note.recordAndShare.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Feedback;
import note.recordAndShare.mapper.FeedbackMapper;
import note.recordAndShare.service.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.NoteResultUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 反馈表 服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-16 04:30:34
 */
@Service
@AllArgsConstructor
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    private final FeedbackMapper feedbackMapper;

    /**
     * 新增意见反馈
     *
     * @param feedback 反馈信息
     * @return ok
     */
    @Override
    public NoteResultUtil insFeedback(Feedback feedback) {
        feedback.setId(UUID.randomUUID().toString());
        feedback.setUserId(StpUtil.getExtra("user_id").toString());
        if (feedbackMapper.insert(feedback) == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("反馈提交失败");
    }

    /**
     * 查询用户反馈
     *
     * @return 用户反馈信息
     */
    @Override
    public NoteResultUtil selUserFeedback(Integer page) {
        return NoteResultUtil.success(feedbackMapper.selectPage(new Page<>(page, 9), new QueryWrapper<Feedback>().eq("userId", StpUtil.getExtra("user_id"))));
    }

    /**
     * 更新反馈状态
     *
     * @param feedback 反馈信息
     * @return 反馈状态
     */
    @Override
    public NoteResultUtil updUserFeedbackState(Feedback feedback) {
        String state = feedbackMapper.selectById(feedback.getId()).getState();
        if (state.equals(feedback.getState())) {
            return NoteResultUtil.error("已经是取消状态");
        }
        feedback.setState(feedback.getState());
        int count = feedbackMapper.updateById(feedback);
        if (count == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("更新失败");
    }

    /**
     * 删除用户反馈
     *
     * @param id 反馈id
     * @return ok
     */
    @Override
    public NoteResultUtil delUserFeedback(String id) {
        int count = feedbackMapper.deleteById(id);
        if (count == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("删除失败");
    }
}
