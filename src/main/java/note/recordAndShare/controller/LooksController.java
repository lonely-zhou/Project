package note.recordAndShare.controller;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import note.recordAndShare.entity.Looks;
import note.recordAndShare.entity.Note;
import note.recordAndShare.mapper.LooksMapper;
import note.recordAndShare.mapper.NoteMapper;
import note.utils.NoteResultUtil;
import note.utils.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户浏览 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 11:39:06
 */
@Data
@RestController
@RequestMapping("/recordAndShare/looks")
public class LooksController {

    private final LooksMapper looksMapper;
    private final NoteMapper noteMapper;

    @GetMapping("/insUserLook")
    public NoteResultUtil insUserLook(@RequestParam("userId") String userId, @RequestParam("noteId") String noteId) {
        int count = looksMapper.selectCount(new QueryWrapper<Looks>().eq("user_id", userId).eq("note_id", noteId)).intValue();
        if (count == 0) {
            Note note = noteMapper.selectOne(new QueryWrapper<Note>().eq("id", noteId).select("look"));
            note.setLook(note.getLook() + 1);
            noteMapper.update(note, new QueryWrapper<Note>().eq("id", noteId));
            Looks looks = new Looks();
            looks.setId(UUID.randomUUID().toString());
            looks.setUserId(userId);
            looks.setNoteId(noteId);
            looks.setTime(new TimeUtil().getFormatDateForFive());
            looksMapper.insert(looks);
        }
        return NoteResultUtil.success(count);
    }
}
