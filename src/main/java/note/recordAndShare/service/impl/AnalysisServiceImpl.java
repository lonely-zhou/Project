package note.recordAndShare.service.impl;

import cn.hutool.core.map.MapUtil;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Analysis;
import note.recordAndShare.mapper.*;
import note.recordAndShare.service.AnalysisService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.NoteResultUtil;
import note.utils.TimeUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 网站数据 服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-28 02:17:38
 */
@Service
@Component
@AllArgsConstructor
public class AnalysisServiceImpl extends ServiceImpl<AnalysisMapper, Analysis> implements AnalysisService {
    private final UserMapper userMapper;
    private final NoteMapper noteMapper;
    private final CommentMapper commentMapper;
    private final LooksMapper looksMapper;
    private final AnalysisMapper analysisMapper;

    /**
     * 更新网站数据
     */
    @Scheduled(cron = "0 0 0 1/15 * ?")
    public void insAnalysis() {
        int userNum = userMapper.selectCount(null).intValue();
        int noteNum = noteMapper.selectCount(null).intValue();
        int commentNum = commentMapper.selectCount(null).intValue();
        int lookNum = looksMapper.selectCount(null).intValue();
        String month = new TimeUtil().getFormatDateForThree() + "月";
        System.out.println(month);
        Analysis analysis = new Analysis();
        analysis.setId(month);
        analysis.setUserNum(userNum);
        analysis.setNoteNum(noteNum);
        analysis.setCommentNum(commentNum);
        analysis.setLookNum(lookNum);
        analysisMapper.updateById(analysis);
    }

    /**
     * 查询网站数据
     *
     * @return 网站数据
     */
    @Override
    public NoteResultUtil selAnalysis() {
        int num = 12;

        int[] userNum = new int[12];
        int[] noteNum = new int[12];
        int[] commentNum = new int[12];
        int[] lookNum = new int[12];
        for (int i = 0; i < num; i++) {
            String id;
            if (i < 9) {
                id = "0" + (i + 1) + "月";
            } else {
                id = (i + 1) + "月";
            }
            userNum[i] = analysisMapper.selectById(id).getUserNum();
            noteNum[i] = analysisMapper.selectById(id).getNoteNum();
            commentNum[i] = analysisMapper.selectById(id).getCommentNum();
            lookNum[i] = analysisMapper.selectById(id).getLookNum();
        }
        return NoteResultUtil.success(MapUtil.builder()
                .put("userNum", userNum)
                .put("noteNum", noteNum)
                .put("commentNum", commentNum)
                .put("lookNum", lookNum)
                .map());
    }
}
