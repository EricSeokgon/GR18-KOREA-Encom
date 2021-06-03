package ncomegf.bpw.sample.api;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataSet;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ncomegf.bpw.sample.domain.ArticleVO;
import ncomegf.bpw.sample.domain.UseParamAndReulstVo;
import ncomegf.bpw.sample.domain.UseParamAndReulstVo.Result;
import ncomegf.bpw.sample.service.SampleService;

@RestController
@RequestMapping("/api/bpw/sample")
@Api(value = "APIs 글자....")
public class SampleApiController {

	@Resource
	private SampleService sampleService;

	@ApiOperation(value = "@PathVariable 이용")
	@GetMapping(value = "/usePathVariable/{user_id}")
	public @ResponseBody HashMap usePathVariable(@PathVariable("user_id") String user_id) throws Exception {
		HashMap datarow = sampleService.selectUser(user_id);
		return datarow;
	}
	

	@ApiOperation(value = "@RequestParam 이용")
	@GetMapping("/useRequestParam")
	public @ResponseBody ArticleVO useRequestParam(
																		@ApiParam(value = "api param 설명1", required = true)  @RequestParam("srl") int srl,
																		@ApiParam(value = "api param 설명2")  @RequestParam("subject") String subject
																		) {
		ArticleVO vo = new ArticleVO();
		vo.setArticleSrl(srl);
		vo.setSubject("subject");
		vo.setContent("content");
		return vo;
	}

	
	@ApiOperation(value = "VO 이용")
	@GetMapping("/useVO")
	public @ResponseBody ArticleVO useVO(ArticleVO vo) {
		vo.setArticleSrl(1);
		vo.setSubject("title");
		vo.setContent("content");
		return vo;
	}
	
	
	@ApiOperation(value = "하나의 클래스에 param, result 포함 ")
	@GetMapping("/useParamAndReulstVo")
	public @ResponseBody UseParamAndReulstVo.Result  useParamAndReulst(UseParamAndReulstVo.Param param) {


		UseParamAndReulstVo.Result result = new Result();
		
		return result;
	}	
	
	
	//===============
	

	@ApiOperation(value = "@RequestParam 이용")
	@GetMapping("/member")
	public @ResponseBody DataRow useRequestParam2(
																		@ApiParam(value = "api param 설명1", required = true)  @RequestParam("srl") int srl,
																		@ApiParam(value = "api param 설명2")  @RequestParam("subject") String subject
																		) {
//		ArticleVO vo = new ArticleVO();
//		vo.setArticleSrl(srl);
//		vo.setSubject("subject");
//		vo.setContent("content");
		return new DataRow();
	}

	

}
