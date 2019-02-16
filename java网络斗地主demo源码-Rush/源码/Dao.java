package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {
	
	public static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // ����JDBC����

	public static String dbURL = "jdbc:sqlserver://GAODONG-PC:1433; DatabaseName=RUSH"; // ���ӷ����������ݿ�

	public static String userName = "sa"; // Ĭ���û���

	public static String userPwd = "123456"; // ����

	public static Connection con = null;

	public Dao() {
		try {
			if (con == null) {
				Class.forName(driverName);
				con = DriverManager.getConnection(dbURL, userName, userPwd);
			}
		} catch (Exception e) {
		}
	}

	/*
	 * ����˺ź������Ƿ���ȷ ����ȷ����״̬��1
	 */
	public String Check(String id, String psw) {
		String info = "0"; // �˺Ż��������
		try {
			String sql = "select * from userinfo where userid=? and password=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, psw);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("state").trim().equals("1")) {
					info = "1"; // �ѵ�¼
				} else {
					info = "2"; // �ɹ���¼
				}
			}
			ps.close();
		} catch (SQLException e) {
		}
		return info;
	}

	/*
	 * ����˺��Ƿ����
	 */
	public boolean Check(String id) {
		try {
			String sql = "select * from userinfo where userid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return true;
			ps.close();
		} catch (SQLException e) {
		}
		return false;
	}

	/*
	 * ָ��ĳ��״̬������
	 */
	public void setState(String state, String id) {
		try {
			String sql = "update userinfo set state=? where userid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, state);
			ps.setString(2, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * ������״̬����0��1,һ��Ϊ�رշ�����ʱ��
	 */
	public void setAllState(String state) {
		try {
			String sql = "update userinfo set state=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, state);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateScore(String _id, int _score) {
		try {
			Statement sql = con.createStatement();
			sql.execute("update userinfo set score=score+" + _score
					+ "where userid='" + _id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getScore(String _id) {
		int score = 0;
		try {
			Statement sql = con.createStatement();
			ResultSet rs = sql
					.executeQuery("select score from userinfo where userid='"
							+ _id + "'");
			rs.next();
			score = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return score;
	}

	public String getIcon(String _id) {
		String icon = "";
		try {
			Statement sql = con.createStatement();
			ResultSet rs = sql
					.executeQuery("select icon from userinfo where userid='"
							+ _id + "'");
			rs.next();
			icon = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return icon;
	}

	public void Adduser(String id, String psw) {
		try {
			Statement sql = con.createStatement();
			sql.execute("insert into userinfo(userid,password,state,score,icon)values('"
					+ id + "','" + psw + "','0',0,'0')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
