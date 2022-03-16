package note.recordAndShare.serviceImpl;

import note.recordAndShare.entity.Feedback;
import note.recordAndShare.mapper.FeedbackMapper;
import note.recordAndShare.service.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

}
