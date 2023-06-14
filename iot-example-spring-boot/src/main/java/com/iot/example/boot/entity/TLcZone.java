package com.iot.example.boot.entity;

import com.iot.sh.entity.BaseModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TLcZone  extends BaseModel {
	
	private static final long serialVersionUID = 6900095081870432118L;

	private String code;

    private String name;

    private String type;

    private String mapCode;

    private String createUser;

    private Date createTime;

    private String modifyUser;

    private Date modifyTime;
    //=======extra======//    
    private String mapName;
    private String fileUrl;
    private List<String> beacons;
    private List<TLcZoneVertex> vertexs;

}