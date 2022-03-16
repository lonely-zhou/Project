package note.recordAndShare.controller;


import cn.hutool.core.lang.UUID;
import lombok.Data;
import note.recordAndShare.entity.Feedback;
import note.recordAndShare.mapper.FeedbackMapper;
import note.utils.NoteResultUtil;
import note.utils.TimeUtil;
import note.utils.UserUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 反馈表 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-16 04:30:34
 */
@Data
@RestController
@RequestMapping("/recordAndShare/feedback")
public class FeedbackController {

    private final FeedbackMapper feedbackMapper;

    /**
     * 意见反馈
     * @param feedback 反馈信息
     * @return ok
     */
    @PostMapping("insFeedback")
    public NoteResultUtil insFeedback(@RequestBody Feedback feedback) {
        feedback.setId(UUID.randomUUID().toString());
        feedback.setTime(new TimeUtil().getFormatDateForFive());
        feedback.setUserId(UserUtil.selUserId());
        if (feedbackMapper.insert(feedback) == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("反馈提交失败");
    }
}
