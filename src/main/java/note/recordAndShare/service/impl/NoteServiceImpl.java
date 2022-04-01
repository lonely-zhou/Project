package note.recordAndShare.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Note;
import note.recordAndShare.entity.dto.NoteDto;
import note.recordAndShare.mapper.NoteMapper;
import note.recordAndShare.service.NoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.BuildArticleTabloidUtil;
import note.utils.ConstantUtil;
import note.utils.NoteResultUtil;
import note.utils.UserUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    /**
     * 增加笔记
     *
     * @param note 笔记
     * @return 0 or 1
     */
    @Override
    public NoteResultUtil insNote(Note note) {
        note.setUserId(StpUtil.getExtra("user_id").toString());
        note.setName(UserUtil.selUser().getUsername());
        note.setId(note.getName() + "-" + UUID.randomUUID());
        note.setArticleTabled(new BuildArticleTabloidUtil().buildArticleTabloid(note.getText()));
        int count = noteMapper.insert(note);
        if (count > 0) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("保存失败");
    }

    /**
     * 查询笔记列表
     *
     * @param page 页
     * @return 笔记列表
     */
    @Override
    public NoteResultUtil getNoteList(Integer page) {
        int count = noteMapper.selectCount(new QueryWrapper<>(null)).intValue();
        return NoteResultUtil.success(String.valueOf(count), noteMapper.selectNotePage(new Page<>(page, 9)));
    }

    /**
     * 查询用户笔记
     *
     * @param page    页码
     * @param message 是否公开
     * @return 用户笔记列表
     */
    @Override
    public NoteResultUtil selUserNote(Integer page, String message) {
        int count = noteMapper.selectCount(new QueryWrapper<Note>().eq("user_id", StpUtil.getExtra("user_id").toString())).intValue();
        if ("".equals(message)) {
            message = null;
        }
        return NoteResultUtil.success(String.valueOf(count),
                noteMapper.selectUserNote(new Page<>(page, 9), StpUtil.getExtra("user_id").toString(), message));
    }

    /**
     * 查询笔记
     *
     * @param noteId 笔记ID
     * @return 笔记
     */
    @Override
    public NoteResultUtil selNote(String noteId) {
        String message = noteMapper.selectOne(new QueryWrapper<Note>().eq("id", noteId).select("message")).getMessage();
        if (ConstantUtil.NOTE_MESSAGE.equals(message)) {
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
    @Override
    public NoteResultUtil updUserNote(Note note) {
        int count = noteMapper.update(note, new QueryWrapper<Note>().eq("id", note.getId()));
        if (count == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("更新失败");
    }

    /**
     * 删除用户笔记
     *
     * @param noteId 笔记ID
     * @return ok
     */
    @Override
    public NoteResultUtil delUserNote(String noteId) {
        int count = noteMapper.delete(new QueryWrapper<Note>().eq("id", noteId));
        if (count == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("删除失败");
    }

    /**
     * 搜索笔记
     *
     * @param q       搜索关键字
     * @param page    分页
     * @param orderBy 排序
     * @param groupBy 分组
     * @param label   标签
     * @return 笔记列表
     */
    @Override
    public NoteResultUtil selSearchNote(String q, Integer page, String orderBy, String groupBy, String label) {
        int count;
        if (ConstantUtil.UNDEFINED.equals(q)) {
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
    @Override
    public NoteResultUtil selLabelValuesList() {
        String[] label = noteMapper.selLabelValuesList().toArray(new String[0]);
        String str = ",";
        Set<String> labelSet = new HashSet<>();
        for (String s : label) {
            labelSet.addAll(Arrays.asList(s.split(str)));
        }
        return NoteResultUtil.success(labelSet);
    }

    /**
     * 查询分类笔记
     *
     * @param classification 分类
     * @param page           分页
     * @return 笔记列表
     */
    @Override
    public NoteResultUtil selClassificationNote(String classification, Integer page) {
        int count = noteMapper.selectCount(new QueryWrapper<Note>().eq("select_type", classification)).intValue();
        return NoteResultUtil.success(String.valueOf(count), noteMapper.selClassificationNote(new Page<NoteDto>(page, 9), classification));
    }
}
