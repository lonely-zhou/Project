package note.recordAndShare.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.User;
import note.recordAndShare.entity.dto.UserDto;
import note.recordAndShare.mapper.CommentMapper;
import note.recordAndShare.mapper.LooksMapper;
import note.recordAndShare.mapper.NoteMapper;
import note.recordAndShare.mapper.UserMapper;
import note.recordAndShare.service.AdminService;
import note.utils.NoteResultUtil;
import org.springframework.stereotype.Service;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description:
 * @Date: 2022/3/21 上午 11:40
 **/
@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserMapper userMapper;
    private final NoteMapper noteMapper;
    private final CommentMapper commentMapper;
    private final LooksMapper looksMapper;

    /**
     * 分析页
     *
     * @return 用户数 笔记数 评论数
     */
    @Override
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
    @Override
    public NoteResultUtil selAllUser(Integer page) {
        int count = userMapper.selectCount(new QueryWrapper<>()).intValue();
        Page<UserDto> userDtoList = new Page<>();
        BeanUtil.copyProperties(userMapper.selAllUser(new Page<>(page, 9)), userDtoList);
        return NoteResultUtil.success(String.valueOf(count), userDtoList);
    }

    /**
     * 修改用户角色
     *
     * @param rid 角色id
     * @param id  用户id
     * @return ok
     */
    @Override
    public NoteResultUtil updUserRole(Integer rid, String id) {
        User user = new User();
        user.setId(id);
        user.setRoleId(rid);
        if (userMapper.updateById(user) == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("修改失败");
    }

    /**
     * 搜索用户
     *
     * @param page     分页
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public NoteResultUtil searchUser(Integer page, String username) {
        return NoteResultUtil.success(userMapper.searchUser(new Page<>(page, 9), username));
    }
}
