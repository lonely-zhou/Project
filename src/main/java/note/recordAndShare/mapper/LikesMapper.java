package note.recordAndShare.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import note.recordAndShare.entity.Likes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import note.recordAndShare.entity.dto.LikesDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户点赞 Mapper 接口
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 11:39:06
 */
@Mapper
public interface LikesMapper extends BaseMapper<Likes> {
    /**
     * 查询用户所有点赞笔记
     * @param page 分页
     * @param userId 用户ID
     * @return 用户所有点赞笔记
     */
    IPage<LikesDto> selUserLikeList(Page<?> page, @Param("userId") String userId);
}
