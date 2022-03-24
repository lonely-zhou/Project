package note.recordAndShare.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Collects;
import note.recordAndShare.entity.Note;
import note.recordAndShare.mapper.CollectsMapper;
import note.recordAndShare.mapper.NoteMapper;
import note.recordAndShare.service.CollectsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.NoteResultUtil;
import note.utils.TimeUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户收藏表 服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 12:25:12
 */
@Service
@AllArgsConstructor
public class CollectsServiceImpl extends ServiceImpl<CollectsMapper, Collects> implements CollectsService {
    private final CollectsMapper collectsMapper;
    private final NoteMapper noteMapper;

    /**
     * 增加用户收藏
     *
     * @param noteId 笔记ID
     * @return 收藏数
     */
    @Override
    public NoteResultUtil insUserNoteCollect(String noteId) {
        String userId = StpUtil.getExtra("user_id").toString();
        int count = collectsMapper.selectCount(new QueryWrapper<Collects>().eq("user_id", userId).eq("note_id", noteId)).intValue();
        if (count == 1) {
            collectsMapper.delete(new QueryWrapper<Collects>().eq("user_id", userId).eq("note_id", noteId));
        } else {
            Collects collects = new Collects();
            collects.setId(UUID.randomUUID().toString());
            collects.setUserId(userId);
            collects.setNoteId(noteId);
            collects.setTime(new TimeUtil().getFormatDateForFive());
            collectsMapper.insert(collects);
        }
        int collects = collectsMapper.selectCount(new QueryWrapper<Collects>().eq("user_id", userId).eq("note_id", noteId)).intValue();
        Note note = new Note();
        note.setCollection(collects);
        note.setId(noteId);
        noteMapper.update(note, new QueryWrapper<Note>().eq("id", noteId));
        return NoteResultUtil.success(collects);
    }

    /**
     * 查询用户所有收藏笔记
     *
     * @param page 分页
     * @return 用户所有收藏笔记
     */
    @Override
    public NoteResultUtil selUserNoteCollectList(Integer page) {
        int count = collectsMapper.selectCount(new QueryWrapper<Collects>().eq("user_id", StpUtil.getExtra("user_id"))).intValue();
        return NoteResultUtil.success(String.valueOf(count), collectsMapper.selUserCollectList(new Page<>(page, 5), StpUtil.getExtra("user_id").toString()));
    }

    /**
     * 删除用户 收藏笔记
     *
     * @param noteId 笔记ID
     * @return ok
     */
    @Override
    public NoteResultUtil delUserNoteCollect(String noteId) {
        collectsMapper.delete(new QueryWrapper<Collects>().eq("user_id", StpUtil.getExtra("user_id")).eq("note_id", noteId));
        int collectCount = collectsMapper.selectCount(new QueryWrapper<Collects>().eq("note_id", noteId)).intValue();
        Note note = new Note();
        note.setCollection(collectCount);
        note.setId(noteId);
        noteMapper.update(note, new QueryWrapper<Note>().eq("id", noteId));
        return NoteResultUtil.success();
    }

    /**
     * 用户是否收藏笔记
     *
     * @param noteId 笔记ID
     * @return true or false
     */
    @Override
    public NoteResultUtil isUserNoteCollect(String noteId) {
        int count = collectsMapper.selectCount(new QueryWrapper<Collects>().eq("user_id", StpUtil.getExtra("user_id")).eq("note_id", noteId)).intValue();
        if (count != 1) {
            return NoteResultUtil.success("false");
        }
        return NoteResultUtil.success("true");
    }
}
