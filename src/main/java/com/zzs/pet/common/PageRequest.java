package com.zzs.pet.common;

import lombok.Data;

/**
 * @author Wongbuer
 * @createDate 2024/5/19
 */
@Data
public class PageRequest {
    private Integer current = 1;
    private Integer size = 10;
}
