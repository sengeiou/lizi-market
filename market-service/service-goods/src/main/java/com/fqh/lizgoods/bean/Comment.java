package com.fqh.lizgoods.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品评论表
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_comment")
@ApiModel(value="Comment对象", description="商品评论表")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "评论的商品id")
    private Long goodsId;

    @ApiModelProperty(value = "评论人id")
    private Long memberId;

    @ApiModelProperty(value = "评论人昵称")
    private String nickname;

    @ApiModelProperty(value = "评论人头像")
    private String avatar;

    @ApiModelProperty(value = "评论具体内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "逻辑删除 0-未删除, 1-已删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "评论星级")
    private Integer starLevel;
}
