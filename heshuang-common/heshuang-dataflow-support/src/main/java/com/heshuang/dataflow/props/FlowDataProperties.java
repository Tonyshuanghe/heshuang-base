//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.props;

import com.heshuang.dataflow.conts.FlowDataSourceConts;
import lombok.Data;

import java.util.List;
@Data
public class FlowDataProperties {


    private DataSource source = new DataSource();

    private List<Processor> processors;

    private Sink sink;



    public class DataSource {
        private FlowDataSourceConts type = FlowDataSourceConts.RABBIT;

        private Boolean isInner = Boolean.valueOf(true);
    }

    public class Processor {
        private String type;

        private String exec;
    }

    public class Sink {}
}
