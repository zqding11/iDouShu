package com.example.demo_websocket.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo_websocket.pojo.Note;
import com.example.demo_websocket.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 笔记控制器类，处理笔记的增、删、查、改操作
 */
@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    /**
     * 按创建时间查询和分页的接口
     *
     * @param type 排序类型（time表示按时间排序）
     * @param page 当前页码
     * @param size 每页大小
     * @return 分页结果
     */
    @GetMapping("/list")
    public Page<Note> listNotes(
            @RequestParam String type,
            @RequestParam int page,
            @RequestParam int size) {

        QueryWrapper<Note> queryWrapper = new QueryWrapper<>();

        if ("time".equals(type)) {
            queryWrapper.orderByDesc("create_time");
        } else if ("good".equals(type)) {
            queryWrapper.orderByDesc("good");
        }

        Page<Note> notePage = new Page<>(page, size);
        return noteService.page(notePage, queryWrapper);
    }

    /**
     * 根据用户 ID 查询笔记列表
     *
     * @param userid 用户 ID
     * @return 笔记列表
     */
    @GetMapping("/mylist")
    public List<Note> mylistNotes(@RequestParam String userid) {
        QueryWrapper<Note> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userid);  // 根据 userId 进行查询
        return noteService.list(queryWrapper);
    }

    /**
     * 添加新的笔记
     *
     * @param note 笔记对象
     * @return 操作结果
     */
    @PostMapping("/add")
    public Map<String, Object> addNote(@RequestBody Note note) {
        note.setCreateTime(new Date()); // 自动填充创建时间
        note.setNId(getNid()); // 设置笔记 ID

        boolean success = noteService.save(note);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("success", true);
            response.put("noteId", note.getNId()); // 假设 Note 的主键 ID 会自动生成
        } else {
            response.put("success", false);
            response.put("message", "Failed to add note");
        }

        return response;
    }

    /**
     * 根据 ID 获取单个笔记信息
     *
     * @param id 笔记 ID
     * @return 笔记对象
     */
    @GetMapping("/get")
    public Note getNoteById(@RequestParam("id") String id) {
        return noteService.getById(id);
    }

    /**
     * 更新笔记
     *
     * @param note 笔记对象，包含更新后的数据
     * @return 操作结果
     */
    @PutMapping("/update")
    public String updateNote(@RequestBody Note note) {
        boolean success = noteService.updateById(note);
        return success ? "true" : "false";
    }

    /**
     * 生成新的笔记 ID
     *
     * @return 生成的笔记 ID
     */
    private String getNid() {
        double randomNumber = Math.random();
        return "N" + String.valueOf(randomNumber).substring(2, 12);
    }

    /**
     * 根据关键词搜索笔记
     *
     * @param keyword 搜索关键词
     * @param page 当前页码
     * @param size 每页大小
     * @return 分页结果
     */
    @GetMapping("/search")
    public Page<Note> searchNotes(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        QueryWrapper<Note> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", keyword).or().like("note_content", keyword);

        Page<Note> notePage = new Page<>(page, size);
        return noteService.page(notePage, queryWrapper);
    }
}
