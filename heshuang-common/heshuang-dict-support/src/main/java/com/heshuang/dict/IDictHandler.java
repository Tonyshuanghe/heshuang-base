//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dict;


import com.heshuang.dict.dto.DictCodeProperty;
import com.heshuang.dict.dto.DictSteamProperty;
import com.heshuang.dict.dto.DictTypeDto;

import java.util.List;
import java.util.Set;

public interface IDictHandler {
    <T> T decode(T var1, Set<DictCodeProperty> var2);

    void setSource(List<DictTypeDto> var1);

    void setSteamId(String var1);

    void setSteamSource(Set<DictSteamProperty> var1);
}
