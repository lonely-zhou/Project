package note.recordAndShare.service;

import note.utils.NoteResultUtil;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description:
 * @Date: 2022/3/21 上午 11:39
 **/

public interface AdminService {
    /**
     * 分析页
     *
     * @return 用户数 笔记数 评论数
     */
    NoteResultUtil selAnalysis();

    /**
     * 查询所有用户+权限
     *
     * @param page 分页
     * @return 所有用户
     */
    NoteResultUtil selAllUser(Integer page);

    /**
     * 修改用户角色
     *
     * @param rid 角色id
     * @param id  用户id
     * @return ok
     */
    NoteResultUtil updUserRole(Integer rid, String id);
}
