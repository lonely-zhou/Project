package note.recordAndShare.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Likes;
import note.recordAndShare.entity.Note;
import note.recordAndShare.mapper.LikesMapper;
import note.recordAndShare.mapper.NoteMapper;
import note.recordAndShare.service.LikesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.NoteResultUtil;
import note.utils.TimeUtil;
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
@AllArgsConstructor
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes> implements LikesService {
    private final LikesMapper likesMapper;
    private final NoteMapper noteMapper;

    /**
     * 用户点赞
     *
     * @param noteId 笔记ID
     * @return 点赞数
     */
    @Override
    public NoteResultUtil insLike(String noteId) {
        String userId = StpUtil.getExtra("user_id").toString();
        int count = likesMapper.selectCount(new QueryWrapper<Likes>().eq("user_id", userId).eq("note_id", noteId)).intValue();
        if (count == 1) {
            likesMapper.delete(new QueryWrapper<Likes>().eq("user_id", userId).eq("note_id", noteId));
        } else {
            Likes likes = new Likes();
            likes.setId(UUID.randomUUID().toString());
            likes.setUserId(userId);
            likes.setNoteId(noteId);
            likes.setTime(new TimeUtil().getFormatDateForFive());
            likesMapper.insert(likes);
        }
        int likes = likesMapper.selectCount(new QueryWrapper<Likes>().eq("note_id", noteId)).intValue();
        Note note = new Note();
        note.setLikes(likes);
        note.setId(noteId);
        noteMapper.update(note, new QueryWrapper<Note>().eq("id", noteId));
        return NoteResultUtil.success(likes);
    }

    /**
     * 查询用户 所有点赞笔记
     *
     * @param page 分页
     * @return 所有点赞笔记
     */
    @Override
    public NoteResultUtil selUserLikeList(Integer page) {
        int count = likesMapper.selectCount(new QueryWrapper<Likes>().eq("user_id", StpUtil.getExtra("user_id").toString())).intValue();
        return NoteResultUtil.success(String.valueOf(count), likesMapper.selUserLikeList(new Page<>(page, 5), StpUtil.getExtra("user_id").toString()));
    }

    /**
     * 用户是否点赞笔记
     *
     * @param noteId 笔记ID
     * @return 是否
     */
    @Override
    public NoteResultUtil isUserLikeNote(String noteId) {
        int count = likesMapper.selectCount(new QueryWrapper<Likes>().eq("user_id", StpUtil.getExtra("user_id").toString()).eq("note_id", noteId)).intValue();
        if (count != 1) {
            return NoteResultUtil.success("false");
        }
        return NoteResultUtil.success("true");
    }

    /**
     * 删除用户点赞笔记
     *
     * @param noteId 笔记ID
     * @return ok
     */
    @Override
    public NoteResultUtil delUserLikeNote(String noteId) {
        int count = likesMapper.delete(new QueryWrapper<Likes>().eq("user_id", StpUtil.getExtra("user_id").toString()).eq("note_id", noteId));
        if (count == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("删除失败");
    }
}
