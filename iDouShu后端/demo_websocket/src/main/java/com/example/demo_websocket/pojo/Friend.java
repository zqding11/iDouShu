package com.example.demo_websocket.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author dzk
 * @since 2024-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "friend_id", type = IdType.AUTO)
    private Integer friendId;

    private Integer user1Id;

    private Integer user2Id;

    private String message;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime; // 这里改成驼峰命名，与数据库字段一致

    private Integer fid;

    private Integer state;

}
