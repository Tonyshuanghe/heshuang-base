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
    private FlowDataProperties.DataSource source = new FlowDataProperties.DataSource();
    private List<FlowDataProperties.Processor> processors;
    private FlowDataProperties.Sink sink;

    public FlowDataProperties() {
    }


    public class Sink {
        public Sink() {
        }
    }

    public class Processor {
        private String type;
        private String exec;

        public Processor() {
        }
    }

    public class DataSource {
        private FlowDataSourceConts type;
        private Boolean isInner;

        public DataSource() {
            this.type = FlowDataSourceConts.RABBIT;
            this.isInner = true;
        }
    }
}
