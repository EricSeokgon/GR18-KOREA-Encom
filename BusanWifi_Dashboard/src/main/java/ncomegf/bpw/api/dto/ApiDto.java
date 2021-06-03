package ncomegf.bpw.api.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;

import com.ncom.core.vo.DataRow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ApiDto extends ResourceSupport {

	private HashMap param;
	private String massage;
	private List<Object> apiDataList;
	
	private DataRow result ;
	private Map map;

}
