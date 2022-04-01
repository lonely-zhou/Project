package note.recordAndShare.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Looks;
import note.recordAndShare.entity.Note;
import note.recordAndShare.mapper.LooksMapper;
import note.recordAndShare.mapper.NoteMapper;
import note.recordAndShare.service.LooksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.NoteResultUtil;
import note.utils.TimeUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户浏览 服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 11:39:06
 */
@Service
@AllArgsConstructor
public class LooksServiceImpl extends ServiceImpl<LooksMapper, Looks> implements LooksService {
    private final LooksMapper looksMapper;
    private final NoteMapper noteMapper;

    /**
     * 用户点赞笔记
     *
     * @param noteId 笔记id
     * @return ok
     */
    @Override
    public NoteResultUtil insUserLook(String noteId) {
        String userId = StpUtil.getExtra("user_id").toString();
        int count = looksMapper.selectCount(new QueryWrapper<Looks>().eq("user_id", userId).eq("note_id", noteId)).intValue();
        if (count == 0) {
            Note note = noteMapper.selectOne(new QueryWrapper<Note>().eq("id", noteId).select("look"));
            note.setLook(note.getLook() + 1);
            noteMapper.update(note, new QueryWrapper<Note>().eq("id", noteId));
            Looks looks = new Looks();
//            looks.setId(UUID.randomUUID().toString());
            looks.setUserId(userId);
            looks.setNoteId(noteId);
            looks.setTime(new TimeUtil().getFormatDateForFive());
            looksMapper.insert(looks);
        }
        return NoteResultUtil.success(count);
    }
}
