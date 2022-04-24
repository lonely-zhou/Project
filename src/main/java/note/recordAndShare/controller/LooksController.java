package note.recordAndShare.controller;


import lombok.Data;
import note.recordAndShare.service.LooksService;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户浏览 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-02-19 11:39:06
 */
@Data
@RestController
@RequestMapping("/recordAndShare/looks")
public class LooksController {

    private final LooksService looksService;

    /**
     * 用户点赞笔记
     *
     * @param noteId 笔记id
     * @return ok
     */
    @GetMapping("/insUserLook")
    public NoteResultUtil insUserLook(@RequestParam("noteId") String noteId) {
        return looksService.insUserLook(noteId);
    }
}
