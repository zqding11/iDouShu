package com.example.demo_websocket.service.impl;

import com.example.demo_websocket.pojo.Note;
import com.example.demo_websocket.mapper.NoteMapper;
import com.example.demo_websocket.service.NoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dzk
 * @since 2024-10-27
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

}
