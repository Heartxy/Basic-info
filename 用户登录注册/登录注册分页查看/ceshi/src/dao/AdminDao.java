package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.AdminBean;
import util.DBUtil;
import util.MD5;

//admin数据表的操作

public class AdminDao {
	
	//通过 id 删除
	public void delete(int id) {
	// TODO Auto-generated method stub
	String sql = "delete from admin where id = " + id;
	Connection conn = DBUtil.getConn();
	Statement state = null;
	try {
	state = conn.createStatement();state.executeUpdate(sql);
	} catch (Exception e) {
	e.printStackTrace();
	} finally {
	DBUtil.close(state, conn);
	}
	}
	
	//通过id获取adminBean对象
	public AdminBean getById(int id) {
	// TODO Auto-generated method stub
	String sql = "select * from admin where id =" + id;
	Connection connection = DBUtil.getConn();
	Statement statement = null;
	ResultSet resultSet = null;
	AdminBean adminBean = null;
	try {
	statement = connection.createStatement();
	resultSet = statement.executeQuery(sql);
	while (resultSet.next()) {
	adminBean = new AdminBean();
	adminBean.setId(resultSet.getInt("id"));
	adminBean.setUsername(resultSet.getString("username"));
	adminBean.setPassword(resultSet.getString("password"));
	adminBean.setSalt(resultSet.getString("salt"));
	adminBean.setCreateDate(resultSet.getString("create_date"));
	}
	} catch (Exception e) {
	e.printStackTrace();
	} finally {
	DBUtil.close(resultSet, statement, connection);
	}
	return adminBean;
	}


	//修改管理员
	public void update(AdminBean adminBean) {
	// TODO Auto-generated method stub
	String sql = "update admin set username='" + adminBean.getUsername() + "',password='" + adminBean.getPassword()+ "',salt='" + adminBean.getSalt()+ "' where id='" + adminBean.getId() + "'";
	Connection conn = DBUtil.getConn();
	Statement state = null;
	try {
	state = conn.createStatement();
	state.executeUpdate(sql);
	} catch (Exception e) {e.printStackTrace();
	} finally {
	DBUtil.close(state, conn);
	}
	}
	


	//查看管理员
	public List<AdminBean> list(){
		String sql="select * from admin";
		Connection conn=DBUtil.getConn();
		Statement stmt=null;
		ResultSet rs=null;
		
		//adminBean对象数组
		List<AdminBean> adminBeans= new ArrayList<AdminBean>();
		try{
			stmt = conn.createStatement();
			rs= stmt.executeQuery(sql);
			//adminBean对象
			AdminBean adminBean;
			while(rs.next()){
				adminBean=new AdminBean();
				adminBean.setId(rs.getInt("id"));
				adminBean.setUsername(rs.getString("username"));
				adminBean.setPassword(rs.getString("password"));
				adminBean.setSalt(rs.getString("salt"));
				adminBean.setCreateDate(rs.getString("create_date"));
				adminBeans.add(adminBean);  //添加到数组里
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DBUtil.close(rs, stmt, conn);  //断开连接
		}
		return adminBeans;
	}
	
	//获取数据库中数据总量
	public int getCount(){
		ResultSet rs=null;
		Statement stmt=null;
		Connection conn=null;
		
		int size=0;
		
		try{
			conn=DBUtil.getConn();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select count(*)count from admin");
			
			if(rs.next())
				size=rs.getInt("count");
		}catch(SQLException se){
			se.printStackTrace();
		}
		finally
		{
			DBUtil.close(rs,stmt, conn);
		}
		return size;
	}
	//获取每一个分页的数据
	public List<AdminBean> getListByPage(int start ,int size){  //第一个参数是起始位置，第二个参数是一页显示的记录数
		String sql="select * from admin limit "+start+","+size;
		Connection conn=DBUtil.getConn();
		Statement stmt=null;
		ResultSet rs=null;
		
		List<AdminBean> adminBeans = new ArrayList<AdminBean>();
		
		try{
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			AdminBean adminBean;
			while(rs.next()){
				adminBean=new AdminBean();
				adminBean.setId(rs.getInt("id"));
				adminBean.setUsername(rs.getString("username"));
				adminBean.setPassword(rs.getString("password"));
				adminBean.setSalt(rs.getString("salt"));
				adminBean.setCreateDate(rs.getString("create_date"));
				adminBeans.add(adminBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		DBUtil.close(rs, stmt, conn);
		}
		return adminBeans;
	}
	
	
	//登录
	public AdminBean checkLogin(String username, String password) {
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs=null;
		
		String sql="select * from admin where username ='" + username+"'";
		//建立管理员对象
		AdminBean adminBean = null;
		
		try {
			state=conn.createStatement();
			rs = state.executeQuery(sql);
		
		if (rs.next()) {
		// 如果有结果，是认为是通过验证了
			System.out.println(MD5.GetMD5Code(password+rs.getString("salt")));
		if(rs.getString("password").equals(MD5.GetMD5Code(password+rs.getString("salt")))) 
		{
					//判断输入的密码在在转换成MD5加密后是否跟数据库里读出的相等
					//相等的话就把从数据库里读出的属性对AdminBean对象进行赋值
				adminBean = new AdminBean();
				adminBean.setId(rs.getInt("id"));
				adminBean.setUsername(rs.getString("username"));
				adminBean.setPassword(rs.getString("password"));
				adminBean.setSalt(rs.getString("salt"));
				adminBean.setCreateDate(rs.getString("create_date"));
		}
		else{
			System.out.println("密码错误"+MD5.GetMD5Code(password+rs.getString("salt")+"与正确"+rs.getString("password")));
		}
		}
		} catch (Exception e) {
		e.printStackTrace();
		}finally {
			DBUtil.close(rs, state, conn);
			}
		return adminBean;
		}


	
	//检查用户是否已经存在
	public boolean checkReg(String name) {
		boolean flag = true;
		// 查询用户是否已存在
		Connection connection = DBUtil.getConn();
		Statement statement = null;
		ResultSet rs = null;
		try {
		statement = connection.createStatement();
		//把数据库里所有的用户名都给作为结果集返回来了
		rs = statement.executeQuery("select username from admin");
		while (rs.next()) {
		if (name.equals(rs.getString("username"))) {
		flag = false;
		}
		}
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		DBUtil.close(rs, statement, connection);
		}
		return flag;
		}
	


	//添加管理员
	public void save(AdminBean adminBean) {
		// TODO Auto-generated method stub
		//向数据库里添加管理员的信息
		String sql = "insert into admin(username,password,salt,create_date) values('" +
		adminBean.getUsername() + "','"
		+ adminBean.getPassword() + "','" + adminBean.getSalt() + "','" +
		adminBean.getCreateDate() + "')";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		try {
		state = conn.createStatement();
		state.executeUpdate(sql);
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		DBUtil.close(state, conn);
		}
		}
}

