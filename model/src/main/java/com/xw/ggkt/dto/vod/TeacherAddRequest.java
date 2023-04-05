package com.xw.ggkt.dto.vod;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xw.ggkt.model.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TeacherAddRequest {
    private String name;

    private String intro;

    private String career;

    private Integer level;

    private String avatar;

    private Integer sort;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date joinDate;
}
