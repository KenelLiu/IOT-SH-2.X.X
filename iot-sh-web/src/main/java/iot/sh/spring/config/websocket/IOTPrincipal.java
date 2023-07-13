package iot.sh.spring.config.websocket;

import java.security.Principal;

public class IOTPrincipal implements Principal {

	private String name;
	
	private Long tenantId;
	@Override
	public String getName() {
		return this.name;
	}
	public Long getTenantId(){
		return this.tenantId;
	}
	public IOTPrincipal(String name,Long tenantId){
		this.name=name;
		this.tenantId=tenantId;
	}
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IOTPrincipal[");
        sb.append("name="+this.name);
        sb.append("tenantId="+this.tenantId);
        sb.append("]");
        return sb.toString();
    }
	
}
