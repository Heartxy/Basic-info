package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AdminBean;
import dao.AdminDao;
import util.Constants;
import util.MD5;
import util.StringUtil;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//设置编码方式并获取method
		req.setCharacterEncoding("utf-8");
		String method = req.getParameter("method");
		if ("login".equals(method)) {
			//如果是login就到login页面
			login(req, resp);
		} 
		else if("addUser".equals(method)){
			addUser(req,resp);
		}
		else if("list".equals(method))
		{
			listUsers(req,resp);
		}
		else if("toUpdate".equals(method)){
			toUpdate(req,resp);
		}
		else if("delete".equals(method)){
			delete(req,resp);
		}
		else if ("end".equals(method)) {
			//如果是end就到end页面
			end(req, resp);
		}
	}

	
	
	

	//*********************删除管理员*********************
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
	IOException {
	// TODO Auto-generated method stub
	req.setCharacterEncoding("utf-8");
	int id = StringUtil.StringToInt(req.getParameter("updateId"));
	if (id > 1) {
		AdminDao adminDao = new AdminDao();
		adminDao.delete(id);
		resp.sendRedirect(req.getContextPath() + "/admin/AdminServlet?method=list&status=3");
	} else if (id == 1) {
		resp.sendRedirect(req.getContextPath() + "/admin/AdminServlet?method=list&status=1");
	} else {
		resp.sendRedirect(req.getContextPath() + "/admin/AdminServlet?method=list&status=2");
	}
	}
	
	
	//*********************查看管理员*********************
	private void listUsers(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	// TODO Auto-generated method stub
	/*涉及分页
	 req.setCharacterEncoding("utf-8");
	 
	String status = req.getParameter("status");
	
	AdminDao adminDao = new AdminDao();
	
	int currentPage = StringUtil.StringToInt(req.getParameter("currentPage"));
	int countSize = adminDao.getCount();
	PagingBean pagingBean = new PagingBean(currentPage, countSize,Constants.PAGE_SIZE_1);
	
	List<AdminBean> adminBeans = adminDao.getListByPage(currentPage *Constants.PAGE_SIZE_1, Constants.PAGE_SIZE_1);
	
	pagingBean.setPrefixUrl(req.getContextPath() + "/admin/AdminServlet?method=list");
	
	pagingBean.setIsAnd(true);
	
	req.setAttribute(Constants.SESSION_ADMIN_BEANS, adminBeans);
	req.setAttribute("pagingBean", pagingBean);
	
	if (status != null) {
	req.getRequestDispatcher("listUsers.jsp?status=" + status).forward(req, resp);
	} else {
	req.getRequestDispatcher("listUsers.jsp").forward(req, resp);
	}
	*/
}
	
	//*********************从数据库里得到该用户的信息*********************
	private void toUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
	IOException {
		String updateId = req.getParameter("updateId");
		int id = StringUtil.StringToInt(updateId);
		
		if (id == 1) {
			resp.sendRedirect(req.getContextPath() + "/admin/AdminServlet?method=list&status=1");
		} else {
		
		AdminDao adminDao = new AdminDao();
		
		//得到要修改用户的信息
		AdminBean updateBean=adminDao.getById(id);
		
		req.setAttribute("updateBean", updateBean);
		RequestDispatcher rd=req.getRequestDispatcher("addUser.jsp");
		rd.forward(req, resp);
		}
	}
	
	
	//*********************添加和修改管理员*********************************
	private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
	IOException {
	req.setCharacterEncoding("utf-8");
	
	AdminDao adminDao = new AdminDao();
	
	String updateId = req.getParameter("updateId");
	
	//获取注册时提交的用户名和密码
	String username = req.getParameter("username");
	String password = req.getParameter("password");
	
	//建立对象
	AdminBean adminBean = new AdminBean();
	
	adminBean.setUsername(username);
	
	//获取盐值，对密码加密，
	String salt = StringUtil.getRandomString(10);
	String md5Pwd = MD5.GetMD5Code(MD5.GetMD5Code(password)+salt);
	
	adminBean.setSalt(salt);
	adminBean.setPassword(md5Pwd);
	
	//添加时候的日期
	SimpleDateFormat createDate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	adminBean.setCreateDate(createDate1.format(new Date()));
	/**
	* updateId 空时为添加，不为空为修改
	*/
	
	
	if (!updateId.equals("")) {
		int id = StringUtil.StringToInt(updateId);
		adminBean.setId(id);
		adminBean.setSalt(salt);
		
		boolean flag = adminDao.checkReg(username);
		
		
		if (flag) {
			// 修改成功，写入数据库
			
			adminDao.update(adminBean);
			
			resp.sendRedirect("adminServlet?method=list&status=2");
		} else {
			// 修改失败，跳回
			resp.sendRedirect("addUser.jsp?status=2");
		}
	}
	else{
	
		boolean flag = adminDao.checkReg(username);
		if (flag) {
			// 注册成功，写入数据库
			adminDao.save(adminBean);
			resp.sendRedirect("addUser.jsp?status=1");
		} else {
			// 注册失败，跳回
			resp.sendRedirect("addUser.jsp?status=2");
			}
		}
	}

	//*********************退出登录*********************
	private void end(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//设置编码方式并获取status
		req.setCharacterEncoding("utf-8");
		String status = req.getParameter("status");
		if (status != null && "1".equals(status)) {
			//如果status是1且不为空的话，就注销用户信息并跳转到登录界面
			req.getSession().invalidate();
			resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
		}
	}

	//*********************登录*********************
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置编码方式并获取用户名和进过MD5加密后的密码
		req.setCharacterEncoding("utf-8");
		String username = req.getParameter("username");
		String password = MD5.GetMD5Code(req.getParameter("password"));
		
		AdminDao adminDao = new AdminDao();
		//建立adminDao对象对数据库操作并把结果赋给一个adminDao对象
		AdminBean adminBean = adminDao.checkLogin(username, password);
		if (adminBean != null) {
			// 不为空就是登录成功并把这个用户存到session中然后调转到后台主页面
			System.out.println("可以登录");
			req.getSession().setAttribute(Constants.SESSION_ADMIN_BEAN, adminBean);
			// req.getRequestDispatcher("main.jsp").forward(req, resp);
			//req.getContextPath()获取当前项目根地址
			resp.sendRedirect(req.getContextPath() + "/admin/main.jsp");
		} else {
			//登录失败则重新登录
			resp.sendRedirect(req.getContextPath() + "/admin/login.jsp?status=1");
		}
	}
}
