package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBUtil {
	//eshop为数据库名称，db_url为数据库的url，db_user为数据库用户名 db_panssword为数据库密码
	public static String db_url = "jdbc:mysql://localhost:3306/02gao?useUnicode=true&characterEncoding=UTF-8"; //设置统一的字符集
	public static String db_user="root";
	public static String db_password="happy";
	
	public static Connection getConn(){
		//该方法用来与数据库建立连接
		Connection conn=null;  //创建一个连接对象
		try{
			//放在try里捕捉异常，防止连接错误
			Class.forName("com.mysql.jdbc.Driver");//加载mysql的驱动
			conn=DriverManager.getConnection(db_url,db_user,db_password);
			//通过DriveManager类的getConnection方法与数据库进行连接			
		}catch(Exception e){
			e.printStackTrace(); //如果遇到异常就把异常路径报出来
		}
		return conn;
	}
	public static void close(Statement state,Connection conn){
		//按照打开的反向顺序关闭state和conn
		if(state!=null){
			//只要state不为空就关闭
			try{
				state.close();
			}
			catch(SQLException e){
				//如果有数据库异常就把路径输出
				e.printStackTrace();
			}
		}
		if(conn!=null){
			//只要conn不为空就关闭
			try{
				conn.close();
			}
			catch(SQLException e){
				//如果有数据库异常就把路径输出
				e.printStackTrace();
			}
		}
	}
	public static void close(ResultSet rs,Statement state,Connection conn){
		//按照打开的反向顺序关闭rs,state和conn
		if(rs!=null){
			//只要rs不为空就关闭
			try{
				rs.close();
			}
			catch(SQLException e){
				//如果有数据库异常就把路径输出
				e.printStackTrace();
			}
		}
		if(state!=null){
			//只要state不为空就关闭
			try{
				state.close();
			}
			catch(SQLException e){
				//如果有数据库异常就把路径输出
				e.printStackTrace();
			}
		}
		if(conn!=null){
			//只要conn不为空就关闭
			try{
				conn.close();
			}
			catch(SQLException e){
				//如果有数据库异常就把路径输出
				e.printStackTrace();
			}
		}
	}
}
