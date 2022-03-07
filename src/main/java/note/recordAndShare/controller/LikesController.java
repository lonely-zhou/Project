package note.recordAndShare.controller;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import note.recordAndShare.entity.Likes;
import note.recordAndShare.entity.Note;
import note.recordAndShare.mapper.LikesMapper;
import note.recordAndShare.mapper.NoteMapper;
import note.utils.NoteResultUtil;
import note.utils.TimeUtil;
import note.utils.UserUtil;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 用户点赞 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 11:39:06
 */
@RestController
@Data
@RequestMapping("/recordAndShare/likes")
public class LikesController {

    private final LikesMapper likesMapper;
    private final NoteMapper noteMapper;

    /**
     * 用户点赞
     *
     * @param userId 用户ID
     * @param noteId 笔记ID
     * @return ok
     */
    @GetMapping("/addLike")
    public NoteResultUtil addLike(@RequestParam("userId") String userId, @RequestParam("noteId") String noteId) {
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
     * @param userId 用户ID
     * @return 所有点赞笔记
     */
    @GetMapping("/selUserLikeList")
    public NoteResultUtil selUserLikeList(@RequestParam("userId") String userId, @RequestParam("page") Integer page) {
        int count = likesMapper.selectCount(new QueryWrapper<Likes>().eq("user_id", userId)).intValue();
        return NoteResultUtil.success(String.valueOf(count), likesMapper.selUserLikeList(new Page<>(page, 5), userId));
    }

    /**
     * 用户是否点赞笔记
     *
     * @param noteId 笔记ID
     * @return 是否
     */
    @GetMapping("/isUserLikeNote")
    public NoteResultUtil isUserLikeNote(@RequestParam("noteId") String noteId) {
        int count = likesMapper.selectCount(new QueryWrapper<Likes>().eq("user_id", UserUtil.selUserId()).eq("note_id", noteId)).intValue();
        if (count != 1) {
            return NoteResultUtil.success("false");
        }
        return NoteResultUtil.success("true");
    }

    /**
     * 删除用户点赞笔记
     * @param userId 用户ID
     * @param noteId 笔记ID
     * @return ok
     */
    @DeleteMapping("/delUserLikeNote")
    public NoteResultUtil delUserLikeNote(@RequestParam("userId") String userId, @RequestParam("noteId") String noteId) {
        likesMapper.delete(new QueryWrapper<Likes>().eq("user_id", userId).eq("note_id", noteId));
        return NoteResultUtil.success();
    }
}
