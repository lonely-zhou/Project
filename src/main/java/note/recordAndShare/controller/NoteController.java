package note.recordAndShare.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import note.recordAndShare.entity.Note;
import note.recordAndShare.mapper.NoteMapper;
import note.recordAndShare.service.NoteService;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * <p>
 * 前端控制器
 * </p>
 * RequiresAuthentication
 *
 * @author lonelyzhou
 * @since 2022-01-26 09:57:41
 */
@Data
@RestController
@RequestMapping("/recordAndShare/note")
public class NoteController {

    private final NoteMapper noteMapper;
    private final NoteService noteService;
    String undefined = "undefined";

    /**
     * 查询笔记列表
     *
     * @param page 页
     * @return 笔记列表
     */
    @GetMapping("/getNoteList")
    public NoteResultUtil getNoteList(@RequestParam("page") Integer page) {
        int count = noteMapper.selectCount(new QueryWrapper<>(null)).intValue();
        return NoteResultUtil.success(String.valueOf(count), noteMapper.selectNotePage(new Page<>(page, 9)));
    }

    /**
     * 增加笔记
     *
     * @param note 笔记
     * @return ok
     */
    @SaCheckLogin
    @PostMapping("/insNote")
    public NoteResultUtil insNote(@RequestBody Note note) {
        if (noteService.insNote(note) > 0) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("保存失败");
    }

    /**
     * 查询用户笔记
     *
     * @param userid 用户ID
     * @param page   页码
     * @return 用户笔记列表
     */
    @GetMapping("/selUserNote")
    public NoteResultUtil selUserNote(@RequestParam("userid") String userid,
                                      @RequestParam("page") Integer page,
                                      @RequestParam(value = "message", required = false) String message) {
        int count = noteMapper.selectCount(new QueryWrapper<Note>().eq("user_id", userid)).intValue();
        if ("".equals(message)) {
            message = null;
        }
        return NoteResultUtil.success(String.valueOf(count),
                noteMapper.selectUserNote(new Page<>(page, 9), userid, message));
    }

    /**
     * 查询笔记
     *
     * @param noteId 笔记ID
     * @return 笔记
     */
    @GetMapping("/selNote")
    public NoteResultUtil selNote(@RequestParam("noteId") String noteId) {
        String str = "0";
        String message = noteMapper.selectOne(new QueryWrapper<Note>().eq("id", noteId).select("message")).getMessage();
        if (str.equals(message)) {
            return NoteResultUtil.success(noteMapper.selectOne(new QueryWrapper<Note>().eq("id", noteId)));
        } else {
            return NoteResultUtil.error("笔记未公开");
        }

    }

    /**
     * 更新笔记
     *
     * @param note 笔记
     * @return ok
     */
    @PostMapping("/updUserNote")
    public NoteResultUtil updUserNote(@RequestBody Note note) {
        noteMapper.update(note, new QueryWrapper<Note>().eq("id", note.getId()));
        return NoteResultUtil.success();
    }

    /**
     * 删除用户笔记
     *
     * @param noteId 笔记ID
     * @return ok
     */
    @GetMapping("/delUserNote")
    public NoteResultUtil delUserNote(@RequestParam("noteId") String noteId) {
        noteMapper.delete(new QueryWrapper<Note>().eq("id", noteId));
        return NoteResultUtil.success();
    }

    /**
     * 搜索笔记
     *
     * @param q       搜索关键字
     * @param page    分页
     * @param orderBy 排序
     * @param groupBy 分组
     * @return 笔记列表
     */
    @GetMapping("/selSearchNote")
    public NoteResultUtil selSearchNote(@RequestParam("q") String q, Integer page,
                                        @RequestParam(value = "orderBy", required = false) String orderBy,
                                        @RequestParam(value = "groupBy", required = false) String groupBy,
                                        @RequestParam(value = "label", required = false) String label) {
        int count;
        if (undefined.equals(q)) {
            q = label;
            count = noteMapper.selectCount(new QueryWrapper<Note>().like("label_values", q)).intValue();
        } else {
            count = noteMapper.selectCount(new QueryWrapper<Note>().like("title", q)).intValue();
        }
        if (groupBy == null) {
            groupBy = "";
        }
        return NoteResultUtil.success(String.valueOf(count), noteMapper.selSearchNote(new Page<>(page, 9), q, orderBy, groupBy, label));
    }

    /**
     * 查询笔记所有标签
     *
     * @return 笔记所有标签
     */
    @GetMapping("/selLabelValuesList")
    public NoteResultUtil selLabelValuesList() {
        String[] label = noteMapper.selLabelValuesList().toArray(new String[0]);
        String str = ",";
        Set<String> labelSet = new HashSet<>();
        for (String s : label) {
            labelSet.addAll(Arrays.asList(s.split(str)));
        }
        return NoteResultUtil.success(labelSet);
    }

}
