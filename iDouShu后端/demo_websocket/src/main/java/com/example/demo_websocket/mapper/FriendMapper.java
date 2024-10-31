package com.example.demo_websocket.mapper;

import com.example.demo_websocket.pojo.Friend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dzk
 * @since 2024-10-28
 */
@Mapper
public interface FriendMapper extends BaseMapper<Friend> {

    @Select("SELECT COUNT(*) > 0 FROM friend WHERE user_1_id = #{user1Id} AND user_2_id = #{user2Id}")
    boolean existsByUserIds(@Param("user1Id") Integer user1Id, @Param("user2Id") Integer user2Id);

    @Select("UPDATE friend SET state = 1 WHERE user_1_id = #{user1Id} AND user_2_id = #{user2Id}")
    int updateStateToRead(@Param("user1Id") Integer user1Id, @Param("user2Id") Integer user2Id);

    @Select("SELECT * FROM friend WHERE user_1_id = #{userId} AND message IS NOT NULL ORDER BY create_time DESC")
    List<Friend> findByUserId(@Param("userId") Integer userId);

    @Select("SELECT * FROM friend WHERE f_id = #{fid}")
    List<Friend> findByFid(@Param("fid") Integer fid);

    @Select("SELECT f_id FROM friend WHERE user_1_id = #{user1Id} AND user_2_id = #{user2Id}")
    Integer findFriendId(@Param("user1Id") Integer user1Id, @Param("user2Id") Integer user2Id);

    @Insert("INSERT INTO friend (user_1_id, user_2_id, message, state,f_id) " +
            "VALUES (#{user1Id}, #{user2Id}, #{message}, #{state}, #{fid})")
    int insert(Friend friend);

    @Select("SELECT COUNT(*) FROM friend")
    Integer count();

    @Update("UPDATE friend " +
            "SET message = #{message}, " +
            "state = #{state} " +
            "WHERE f_id = #{fid}")
    int updateFriend(Friend friend);

}
