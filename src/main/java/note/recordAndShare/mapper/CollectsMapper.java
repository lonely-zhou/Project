package note.recordAndShare.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import note.recordAndShare.entity.Collects;
import note.recordAndShare.entity.dto.CollectsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户收藏表 Mapper 接口
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 12:25:12
 */
@Mapper
public interface CollectsMapper extends BaseMapper<Collects> {
    /**
     * 查询用户所有收藏
     * @param page 分页
     * @param userId 笔记ID
     * @return 用户所有收藏
     */
    IPage<CollectsDto> selUserCollectList(Page<?> page, @Param("userId") String userId);
}
