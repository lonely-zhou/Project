package note.recordAndShare.controller;


import lombok.Data;
import note.recordAndShare.service.AnalysisService;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 网站数据 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-03-28 02:17:38
 */
@Data
@RestController
@RequestMapping("/recordAndShare/analysis")
public class AnalysisController {
    private final AnalysisService analysisService;

    /**
     * 查询网站数据
     *
     * @return 网站数据
     */
    @GetMapping("selAnalysis")
    public NoteResultUtil selAnalysis() {
        return analysisService.selAnalysis();
    }
}
