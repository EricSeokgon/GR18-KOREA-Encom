package ncomegf.bpw.sample.domain;

import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Email;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class UseParamAndReulstVo {
	
	@ApiModel(value = "사용자 param")
	@Getter @Setter
	public static class Param {

		@Pattern(regexp = "[0-2]")
		@ApiModelProperty(value = "유형", required = true )
		private String type;

		@Email
		@ApiModelProperty(value = "이메일",  example = "abc@jiniworld.me")
		private String email;



	}

	
	@ApiModel(value = "사용자 Result")
	@Getter @Setter
	public static class Result {

		@Pattern(regexp = "[0-2]")
		@ApiModelProperty(value = "유형", required = true )
		private String type;

		@Email
		@ApiModelProperty(value = "이메일",  example = "abc@jiniworld.me")
		private String email;

		@ApiModelProperty(value = "이름")
		private String name;

		@Pattern(regexp = "[1-2]")
		@ApiModelProperty(value = "성별")
		private String sex;

		@DateTimeFormat(pattern = "yyMMdd")
		@ApiModelProperty(value = "생년월일", example = "yyMMdd")
		private String birthDate;

		@ApiModelProperty(value = "전화번호")
		private String phoneNumber;

		@ApiModelProperty(value = "비밀번호")
		private String password;

	}	
	


}

