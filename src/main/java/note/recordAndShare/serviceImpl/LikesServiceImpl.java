package note.recordAndShare.serviceImpl;

import note.recordAndShare.entity.Likes;
import note.recordAndShare.mapper.LikesMapper;
import note.recordAndShare.service.LikesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户点赞 服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 11:39:06
 */
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes> implements LikesService {

}
