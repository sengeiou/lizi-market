package com.fqh.lizgoods.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品分类表
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_categories")
@ApiModel(value="Categories对象", description="商品分类表")
public class Categories implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "上级ID, 0为顶级")
    private Long pid;

    @ApiModelProperty(value = "分类名称")
    private String cateName;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "逻辑删除 0-未删除, 1-已删除")
    @TableLogic
    private Boolean isDeleted;

    @TableField(exist = false)
    private List<Categories> children = new ArrayList<>(); //子节点
}
