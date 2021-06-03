package ncomegf.bpw.api.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ncom.core.util.Config;
import com.ncom.core.vo.DataRow;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ncomegf.base.dao.Session1Dao;
import ncomegf.bpw.api.dto.ApiDto;
import ncomegf.bpw.api.dto.ReqDto;

@Slf4j
@Service
public class CommonApiService extends EgovAbstractServiceImpl {

	@Value("${bpw.apikey}")
	String apikey;
	
	@Value("${bpw.apikey2}")
	String apikey2;
	
	@Resource
	private Session1Dao dao;

	public ApiDto select(ReqDto reqDto) throws ParseException {

		String jsonString = "[]";
		List<Object> apiDataList = new ArrayList<Object>();

		if ("live".equals(Config.props.getString("bpw.api.live"))) {
			if(reqDto.getQueryParamUrl().indexOf("v0") > -1)
			{
				jsonString = this.getRemoteApi(Config.props.getString("bpw.api.url2") + StringUtils.substringAfter(reqDto.getReqUrl(), "/bpw/api"), reqDto.getQueryParamUrl());
			}
			else
			{
				jsonString = this.getRemoteApi(Config.props.getString("bpw.api.url") + StringUtils.substringAfter(reqDto.getReqUrl(), "/bpw/api"), reqDto.getQueryParamUrl());
			}

			if (StringUtils.isNotEmpty(jsonString)) {

				JSONParser parser = new JSONParser();
				Object obj = parser.parse(jsonString);
				
				
				if(obj instanceof JSONObject ) {
					JSONObject jsonObj = (JSONObject) obj;
					apiDataList.add(jsonObj);
				}else {
					JSONArray jsonObj = (JSONArray) obj;
					apiDataList.add(jsonObj);
				}
				
				
				reqDto.setJsonString(jsonString);
				//dao.insert("/bpw/api/common.insert", reqDto.getParam());// insert
			}

		} else {

			if (reqDto.getPagePer() > 1) {// 복수건
				List<DataRow> dataList = dao.selectList("/bpw/api/common.select", reqDto.getParam());// selectList

				for (DataRow map : dataList) {
					jsonString = (String) map.get("data");
					JSONParser parser = new JSONParser();
					Object obj = parser.parse(jsonString);
					JSONArray jsonObj = (JSONArray) obj;
					apiDataList.add(jsonObj);
				}

			} else {// 단건
				DataRow map = dao.selectOne("/bpw/api/common.select", reqDto.getParam());// selectOne

				jsonString = (String) map.get("data");
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(jsonString);
				

				if(obj instanceof JSONObject ) {
					JSONObject jsonObj = (JSONObject) obj;
					apiDataList.add(jsonObj);
				}else {
					JSONArray jsonObj = (JSONArray) obj;
					apiDataList.add(jsonObj);
				}
				
			}
		}

		return ApiDto.builder().apiDataList(apiDataList).param(reqDto.getParam()).build();
	}

	public String getRemoteApi(String strUrl, String urlParam) {
		log.debug("================== Ncom Api json Call : " + strUrl+", / Param : "+urlParam);
		
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(strUrl+"?"+urlParam);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000); // 서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
			// 조직마다 키가 있다.
			con.addRequestProperty("X-Cisco-Meraki-API-Key", apikey);
			
			con.setRequestMethod("GET");

			// URLConnection에 대한 doOutput 필드값을 지정된 값으로 설정한다. URL 연결은 입출력에 사용될 수 있다. URL 연결을 출력용으로 사용하려는 경우 DoOutput 플래그를 true로 설정하고, 그렇지 않은 경우는 false로 설정해야 한다. 기본값은 false이다.

			con.setDoOutput(false);

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				log.debug("================== Ncom Api json Result : " + sb.toString());
			} else {
				log.debug("================== Ncom Api json Result : " + con.getResponseMessage());
			}

			con.disconnect();
		} catch (Exception e) {
			System.err.println(e.toString());
		}

		return sb.toString();
	}

}