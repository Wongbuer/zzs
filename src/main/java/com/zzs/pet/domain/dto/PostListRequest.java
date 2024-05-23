package com.zzs.pet.domain.dto;

import com.zzs.pet.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Wongbuer
 * @createDate 2024/5/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostListRequest extends PageRequest {
    private String type;
    private String keywords;
    private Long userId;
    private Long myUserId;
}
