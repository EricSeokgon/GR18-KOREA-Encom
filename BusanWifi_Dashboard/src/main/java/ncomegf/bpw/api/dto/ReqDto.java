package ncomegf.bpw.api.dto;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ReqDto   {

	@ApiModelProperty(value = "시리얼", example = "내용입니다.")
	private String	serial;

	@ApiModelProperty(value = "네트워크 Id", example = "내용입니다.")
	private String	networkId;
	
	@ApiModelProperty(value = "블루투스클라이언트Id", example = "내용입니다.")
	private String	bluetoothClientId;
	
	@ApiModelProperty(value = "클라이언트Id", example = "내용입니다.")
	private String	clientId;
	
	@ApiModelProperty(value = "조직Id", example = "내용입니다.")
	private String	organizationId;
	
	private Map queryParam;
	
	@Builder.Default
	private int pagePer =1;
	
	private String apiKey;
	
	private String reqUrl;
	
	
	private String jsonString;
	

	public HashMap getParam() {
		
		HashMap hm = new HashMap();
		hm.putAll(getQueryParam());
		
		hm.put("pagePer", pagePer);
		hm.put("apiKey", apiKey);
		hm.put("reqUrl", reqUrl);
		hm.put("jsonString", jsonString);
		
		return hm;
		
	}
	

	public String getQueryParamUrl() {
		String timespan = queryParam.getOrDefault("timespan", "").toString();
		String clientId = queryParam.getOrDefault("clientId", "").toString();
		String serial = queryParam.getOrDefault("serial", "").toString();
		String v0 = queryParam.getOrDefault("v0", "").toString();
		String t0 = queryParam.getOrDefault("t0", "").toString();
		String perPage = queryParam.getOrDefault("perPage", "").toString();
		String startingAfter = queryParam.getOrDefault("startingAfter", "").toString();
		
		String str = "";
		str += !timespan.equals("") ? "timespan="+timespan+"&" : "";
		str += !clientId.equals("") ? "clientId="+clientId+"&" : "";
		str += !serial.equals("") ? "serial="+serial+"&" : "";
		str += !perPage.equals("") ? "perPage="+perPage+"&" : "";
		str += !startingAfter.equals("") ? "startingAfter="+startingAfter+"&" : "";
		str += !t0.equals("") ? "t0="+t0+"&" : "";
		str += v0.equals("true") ? "v0" : "";
		return str;
	}
}
