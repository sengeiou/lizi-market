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
 * 商品品牌表
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_brand")
@ApiModel(value="Brand对象", description="商品品牌表")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "品牌描述")
    private String brandDesc;

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

    @ApiModelProperty(value = "品牌类型 0-自营品牌, 1-大型品牌")
    private Integer brandType;

    @ApiModelProperty(value = "是否注册 0-否, 1-是")
    private Integer isRegister;

    @ApiModelProperty(value = "品牌图片")
    private String brandImg;
}
