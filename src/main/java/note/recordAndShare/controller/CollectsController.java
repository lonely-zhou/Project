package note.recordAndShare.controller;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import note.recordAndShare.entity.Collects;
import note.recordAndShare.entity.Note;
import note.recordAndShare.mapper.CollectsMapper;
import note.recordAndShare.mapper.NoteMapper;
import note.utils.NoteResultUtil;
import note.utils.TimeUtil;
import note.utils.UserUtil;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户收藏表 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 12:25:12
 */
@Data
@RestController
@RequestMapping("/recordAndShare/collects")
public class CollectsController {

    private final CollectsMapper collectsMapper;
    private final NoteMapper noteMapper;

    /**
     * 增加用户收藏
     *
     * @param userId 用户ID
     * @param noteId 笔记ID
     * @return 收藏数
     */
    @GetMapping("/insUserNoteCollect")
    public NoteResultUtil insUserNoteCollect(@RequestParam("userId") String userId, @RequestParam("noteId") String noteId) {
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
    @GetMapping("/selUserNoteCollectList")
    public NoteResultUtil selUserNoteCollectList(@RequestParam("page") Integer page) {
        int count = collectsMapper.selectCount(new QueryWrapper<Collects>().eq("user_id", UserUtil.selUserId())).intValue();
        return NoteResultUtil.success(String.valueOf(count), collectsMapper.selUserCollectList(new Page<>(page, 5), UserUtil.selUserId()));
    }

    /**
     * 删除用户 收藏笔记
     *
     * @param noteId 笔记ID
     * @return ok
     */
    @DeleteMapping("/delUserNoteCollect")
    public NoteResultUtil delUserNoteCollect(@RequestParam("noteId") String noteId) {
        collectsMapper.delete(new QueryWrapper<Collects>().eq("user_id", UserUtil.selUserId()).eq("note_id", noteId));
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
    @GetMapping("isUserNoteCollect")
    public NoteResultUtil isUserNoteCollect(@RequestParam("noteId") String noteId) {
        int count = collectsMapper.selectCount(new QueryWrapper<Collects>().eq("user_id", UserUtil.selUserId()).eq("note_id", noteId)).intValue();
        if (count != 1) {
            return NoteResultUtil.success("false");
        }
        return NoteResultUtil.success("true");
    }
}
