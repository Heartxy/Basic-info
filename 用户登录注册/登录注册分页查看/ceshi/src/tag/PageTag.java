package tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import bean.PagingBean;

public class PageTag extends SimpleTagSupport {
	private PagingBean pagingBean;
	private HttpServletRequest request;
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();    //字符串输出流
		if(pagingBean!=null){
			sb.append("<nav class='pagination'>");//菜单标签
			//处理上一页和首页
			if(pagingBean.getCurrentPage()<=0){
				sb.append("<li class='disabled'><a>首页</a></li>");
				sb.append("<li class='disabled'><a href='#'>上一页</a></li>");
			}
			else{
				sb.append("<li><a href='").append(pagingBean.getPrefixUrl()).append("'aria-label='Previous'><span aria-hidden='true'>首页</span></a></li>");
				sb.append("<li><a href='").append(pagingBean.getPrefixUrl()).append(pagingBean.getIsAnd()?"&":"?").append("currentPage=").append(pagingBean.getCurrentPage()-1).append("'>上一页</a></li>");
			}
			
			//处理下一页和尾页
			if(pagingBean.getCurrentPage()>=(pagingBean.getPageCount()-1)) {
				sb.append("<li class='disabled'><a>下一页</a></li>");
				sb.append("<li class='disabled'><a>尾页</a></li>");
			} else {
				sb.append("<li><a href='").append(pagingBean.getPrefixUrl()).append(pagingBean.getIsAnd()?"&":"?").append("currentPage=").append(pagingBean.getCurrentPage()+1).append("'>下一页</a></li>");
				sb.append("<li><a href='").append(pagingBean.getPrefixUrl()).append(pagingBean.getIsAnd()?"&":"?").append("currentPage=").append(pagingBean.getPageCount()-1).append("' aria-label='Previous'><span aria-hidden='true'> 尾 页</span></a></li>");
			}
			sb.append("<li><a href='#'>");
			sb.append("<span>");
			sb.append(pagingBean.getCurrentPage()+1);
			sb.append("/");
			sb.append(pagingBean.getPageCount());
			sb.append("</span>");
			sb.append("</a>");
			sb.append("</li>");
			sb.append("</ul></nav>");
		}
		getJspContext().getOut().write(sb.toString());
	}
	/**
	 * @return the pagingBean
	 */
	public PagingBean getPagingBean() {
		return pagingBean;
	}
	/**
	 * @param pagingBean the pagingBean to set
	 */
	public void setPagingBean(PagingBean pagingBean) {
		this.pagingBean = pagingBean;
	}
	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	/**
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	
}
