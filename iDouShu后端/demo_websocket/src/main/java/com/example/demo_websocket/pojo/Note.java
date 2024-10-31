package com.example.demo_websocket.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author dzk
 * @since 2024-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "n_id", type = IdType.AUTO)
    private String nId;

    private String uId;

    private String coverImage;

    private String title;

    private String noteContent;

    private Integer good;

    private Integer collection;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 1为笔记0为视频
     */
    private Integer type;


}
