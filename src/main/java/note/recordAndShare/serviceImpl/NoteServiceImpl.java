package note.recordAndShare.serviceImpl;

import cn.hutool.core.lang.UUID;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Note;
import note.recordAndShare.mapper.NoteMapper;
import note.recordAndShare.service.NoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.BuildArticleTabloidUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-26 09:57:41
 */
@Service
@AllArgsConstructor
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {
    private final NoteMapper noteMapper;

    @Override
    public int insNote(Note note) {
        note.setId(note.getName() + "-" + UUID.randomUUID());
        note.setArticleTabled(new BuildArticleTabloidUtil().buildArticleTabloid(note.getText()));
        return noteMapper.insert(note);
    }
}
