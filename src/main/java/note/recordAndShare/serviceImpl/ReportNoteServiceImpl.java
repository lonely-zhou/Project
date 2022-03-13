package note.recordAndShare.serviceImpl;

import note.recordAndShare.entity.ReportNote;
import note.recordAndShare.mapper.ReportNoteMapper;
import note.recordAndShare.service.ReportNoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 举报表 服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-11 12:35:35
 */
@Service
public class ReportNoteServiceImpl extends ServiceImpl<ReportNoteMapper, ReportNote> implements ReportNoteService {

}
