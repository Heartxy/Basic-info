package bean;


public class PagingBean {
	private int totalCount; //总数量
	private int pageCount;  //总页数
	private int currentPage;  //当前页
	private int countPerPage;  //一页多少条数据
	private String prefixUrl;  //。。。。
	private Boolean isAnd;  //......
	
	public PagingBean(int currentPage,int totalCount,int countPerpage){
		this.countPerPage = countPerpage;
		pageCount = (totalCount - 1) / countPerPage + 1;  //求得总页数
		
		if (currentPage >= pageCount){
			currentPage = pageCount - 1;   //.....
		}
		
		if(currentPage < 0){
			currentPage = 0;   //.....
		}
		this.totalCount = totalCount;
		this.currentPage = currentPage;
	}
	
	public PagingBean() {
		
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}

	public String getPrefixUrl() {
		return prefixUrl;
	}

	public void setPrefixUrl(String prefixUrl) {
		this.prefixUrl = prefixUrl;
	}

	public Boolean getIsAnd() {
		return isAnd;
	}

	public void setIsAnd(Boolean isAnd) {
		this.isAnd = isAnd;
	}

	@Override
	//展示对象的属性值
	public String toString() {
		// TODO Auto-generated method stub
		return "PagingBean [totalCount = " + totalCount + ",totalPgeCount="+pageCount+",currentPage="+currentPage+"," +
				"	prefixUrl="+prefixUrl +",countPerPage="+countPerPage+",isAnd="+isAnd+"]";
	}
	
	
	
}
