package com.heshuang.demo.mqdemo.producer;

import com.heshuang.demo.mqdemo.GenderType;
import com.heshuang.dict.anno.DictCode;
import com.heshuang.dict.anno.DictConf;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/9/23 15:38
 * Description: 测试
 */
@DictConf
@Getter
@Setter
public class DictDemo {
    @DictCode(conts = GenderType.class)
    private  String sex;
}
