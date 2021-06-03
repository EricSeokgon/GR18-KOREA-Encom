package ncomegf.bpw.sample.domain;

import io.swagger.annotations.ApiModelProperty;

public class ArticleVO {

	@ApiModelProperty(value = "글번호",  example = "1")
	int articleSrl;

	@ApiModelProperty(value = "제목",  example = "제목입니다.")
	String subject;
	
	@ApiModelProperty(value = "내용",  example = "내용입니다.")
	String content;

	public int getArticleSrl() {
		return articleSrl;
	}

	public void setArticleSrl(int articleSrl) {
		this.articleSrl = articleSrl;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
