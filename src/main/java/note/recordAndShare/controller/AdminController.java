package note.recordAndShare.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import note.recordAndShare.mapper.CommentMapper;
import note.recordAndShare.mapper.NoteMapper;
import note.recordAndShare.mapper.UserMapper;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: 管理员控制类
 * @Date: 2022/3/7 下午 5:25
 **/
@Data
@RestController
@RequestMapping("/recordAndShare/admin/")
public class AdminController {
    private final UserMapper userMapper;
    private final NoteMapper noteMapper;
    private final CommentMapper commentMapper;

    @SaCheckRole("admin")
    @GetMapping("selAnalysis")
    public NoteResultUtil selAnalysis() {
        int userCount = userMapper.selectCount(new QueryWrapper<>()).intValue();
        int noteCount = noteMapper.selectCount(new QueryWrapper<>()).intValue();
        int commentCount = commentMapper.selectCount(new QueryWrapper<>()).intValue();
        return NoteResultUtil.success(MapUtil.builder()
                .put("userCount", userCount)
                .put("noteCount", noteCount)
                .put("commentCount", commentCount).map());
    }
}
