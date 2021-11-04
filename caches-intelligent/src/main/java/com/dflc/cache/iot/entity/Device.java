package com.dflc.cache.iot.entity;

import lombok.Data;
import org.legomd.cache.core.Cachedable;
import java.util.Date;

@Data
public class Device extends Cachedable {

    protected Long id;

    protected Long id_;

    protected String name;

    protected Date createdAt;

    protected Date updatedAt;

    protected Integer status;

    private Integer state;

    private String seq;

    private String describe;

    private Integer type;

    private Integer orgSeqId;

    private Long orgId;

    private String code;

    //    Device(List<Object> list){
//        //"seq","id", "name", "code","id_","org_id","org_seq_id"
//        this.seq=(String) list.get(0);
//    }
    @Override
    public String cacheKey() {
        return this.getSeq();
    }

    public void clean() {
        this.id = null;

        this.id_ = null;

        this.name = null;

        this.createdAt = null;

        this.updatedAt = null;

        this.status = null;

        this.state = null;

        this.seq = null;

        this.describe = null;

        this.type = null;

        this.orgSeqId = null;

        this.orgId = null;

        this.code = null;
    }
}
