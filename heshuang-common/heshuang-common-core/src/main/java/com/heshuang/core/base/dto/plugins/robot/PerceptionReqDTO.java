package com.heshuang.core.base.dto.plugins.robot;

import lombok.Data;

/**
 * @description:
 * @author: heshuang
 */
@Data
public class PerceptionReqDTO {
    private InputTextReqDTO inputText;
    private InputImageReqDTO inputImage;
    private SelfInfoReqDTO selfInfo;
}
