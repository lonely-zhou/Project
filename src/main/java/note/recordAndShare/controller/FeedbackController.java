package note.recordAndShare.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Feedback;
import note.recordAndShare.mapper.FeedbackMapper;
import note.recordAndShare.service.FeedbackService;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 反馈表 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-16 04:30:34
 */
@AllArgsConstructor
@RestController
@RequestMapping("/recordAndShare/feedback")
public class FeedbackController {

    private final FeedbackMapper feedbackMapper;
    private final FeedbackService feedbackService;

    /**
     * 意见反馈
     *
     * @param feedback 反馈信息
     * @return ok
     */
    @PostMapping("insFeedback")
    public NoteResultUtil insFeedback(@RequestBody Feedback feedback) {
        return feedbackService.insFeedback(feedback);
    }

    /**
     * 查询用户所有反馈
     *
     * @param page 分页
     * @return 用户所有反馈
     */
    @GetMapping("selAllFeedback")
    public NoteResultUtil selAllFeedback(@RequestParam("page") Integer page) {
        int count = feedbackMapper.selectCount(null).intValue();
        return NoteResultUtil.success(String.valueOf(count), feedbackMapper.selectPage(new Page<>(page, 9), null));
    }

    /**
     * 查询用户反馈
     *
     * @param page 分页
     * @return 用户反馈信息
     */
    @GetMapping("selUserFeedback")
    public NoteResultUtil selUserFeedback(@RequestParam("page") Integer page) {
        return feedbackService.selUserFeedback(page);
    }

    /**
     * 更新反馈状态
     *
     * @param feedback 反馈信息
     * @return 反馈状态
     */
    @PostMapping("updUserFeedbackState")
    public NoteResultUtil updUserFeedbackState(@RequestBody Feedback feedback) {
        return feedbackService.updUserFeedbackState(feedback);
    }

    /**
     * 删除用户反馈
     *
     * @param id 反馈id
     * @return ok
     */
    @DeleteMapping("delUserFeedback")
    public NoteResultUtil delUserFeedback(@RequestParam("id") String id) {
        return feedbackService.delUserFeedback(id);
    }
}
