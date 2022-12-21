package com.wmj.sms.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DeleteDTO {
    private List<Serializable> ids;
    private Serializable id;
}
