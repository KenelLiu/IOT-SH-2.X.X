package com.iot.example.boot.service.impl;

import java.util.List;

import javax.annotation.Resource;
import com.iot.example.boot.entity.TLcZone;
import com.iot.example.boot.mapper.write.LCZoneMapper;
import com.iot.example.boot.service.LCZoneService;
import iot.sh.spring.exception.BussinessException;
import org.springframework.stereotype.Service;

@Service(value="LCZoneService")
public class LCZoneServiceImpl implements LCZoneService {
	@Resource
    LCZoneMapper lcZoneMapper;
	
	public TLcZone findByKey(String code){
		return lcZoneMapper.findByKey(code);
	}
	
	public int add(TLcZone tlcMap) throws BussinessException {
		TLcZone dbTLcMap=lcZoneMapper.findByKey(tlcMap.getCode());
		if(dbTLcMap!=null)
			throw new BussinessException("编号已存在,不能重复添加");
		return lcZoneMapper.add(tlcMap);
	}
    
	public int update(TLcZone tlcMap) throws BussinessException{
		return update(tlcMap, false);
	}
	
	public int update(TLcZone tlcMap,boolean bQuery) throws BussinessException{
		if(bQuery){
			TLcZone dbTLcMap=lcZoneMapper.findByKey(tlcMap.getCode());
			if(dbTLcMap==null)
				throw new BussinessException("编号不存在,不能更新");
		}
		return lcZoneMapper.update(tlcMap);
	}
	
	public int deleteByKey(TLcZone tlcMap){
		return lcZoneMapper.deleteByKey(tlcMap);
	}
	
	public long getCount(TLcZone tlcMap){
		return lcZoneMapper.getCount(tlcMap);
	}
	
	public List<TLcZone> getListModel(TLcZone tlcMap){
		return lcZoneMapper.getListModel(tlcMap);
	}	
}
