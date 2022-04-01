package note.recordAndShare.service;

import note.recordAndShare.entity.Analysis;
import com.baomidou.mybatisplus.extension.service.IService;
import note.utils.NoteResultUtil;

/**
 * <p>
 * 网站数据 服务类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-28 02:17:38
 */
public interface AnalysisService extends IService<Analysis> {
    /**
     * 查询网站数据
     * @return 网站数据
     */
    NoteResultUtil selAnalysis();
}
