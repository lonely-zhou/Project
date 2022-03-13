package note.recordAndShare.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import note.recordAndShare.entity.User;
import note.recordAndShare.entity.dto.UserDto;
import note.recordAndShare.mapper.CommentMapper;
import note.recordAndShare.mapper.LooksMapper;
import note.recordAndShare.mapper.NoteMapper;
import note.recordAndShare.mapper.UserMapper;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@SaCheckRole(value = {"admin", "su-admin"}, mode = SaMode.OR)
@RequestMapping("/recordAndShare/admin/")
public class AdminController {
    private final UserMapper userMapper;
    private final NoteMapper noteMapper;
    private final CommentMapper commentMapper;
    private final LooksMapper looksMapper;

    /**
     * 分析页
     *
     * @return 用户数 笔记数 评论数
     */
    @GetMapping("selAnalysis")
    public NoteResultUtil selAnalysis() {
        int userCount = userMapper.selectCount(null).intValue();
        int noteCount = noteMapper.selectCount(null).intValue();
        int commentCount = commentMapper.selectCount(null).intValue();
        int lookCount = looksMapper.selectCount(null).intValue();
        return NoteResultUtil.success(MapUtil.builder()
                .put("userCount", userCount)
                .put("noteCount", noteCount)
                .put("commentCount", commentCount)
                .put("lookCount", lookCount).map());
    }

    /**
     * 查询所有用户+权限
     *
     * @param page 分页
     * @return 所有用户
     */
    @GetMapping("selAllUser")
    public NoteResultUtil selAllUser(@RequestParam("page") Integer page) {
        int count = userMapper.selectCount(new QueryWrapper<>()).intValue();
        Page<UserDto> userDtoList = new Page<>();
        BeanUtil.copyProperties(userMapper.selAllUser(new Page<>(page, 9)), userDtoList);
        return NoteResultUtil.success(String.valueOf(count), userDtoList);
    }

    /**
     * 修改用户角色
     *
     * @param rid 角色ID
     * @return ok
     */
    @GetMapping("updUserRole")
    public NoteResultUtil updUserRole(@RequestParam("rid") Integer rid, @RequestParam("id") String id) {
        User user = new User();
        user.setId(id);
        user.setRoleId(rid);
        if (userMapper.updateById(user) == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("修改失败");
    }
}
