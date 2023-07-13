package com.iot.example.boot.service;

import com.iot.example.boot.entity.TLcZone;
import iot.sh.web.service.BaseSevice;

/**
 * 使用beeanName配置自动代理事务,不再手工配置@Transactional
 * {@link com.iot.example.boot.config.WriteTransactionAutoProxy#writeBeanNameAutoProxyCreator}
 * @Transactional(rollbackFor = Exception.class)
 * @author Kenel
 *
 */
public interface LCZoneService extends BaseSevice<TLcZone,String> {

}
