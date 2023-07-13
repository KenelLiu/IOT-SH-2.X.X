var localUser='user1';//本机用户
var chatUser='user2';//点对点,对方测试用户。
var tenantId=1;//租户Id,区分单位[A医院/B医院] 每家医院tenantId有一个固定值,用于订阅本医院告警信息
var header={
	Authorization:localUser //连接websocket时的唯一标识,列如:用户token值,1.此值用来首次连接时鉴权,2.点对点的推送. 
}

