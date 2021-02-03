package example01.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import example01.dto.MemoDTO;

public class MemoDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public Connection dbConn() {
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			String dbUrl = "jdbc:oracle:thin:@localhost:1521/xe";
			String dbId = "example01";
			String dbPasswd = "1234";
			
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl,dbId,dbPasswd);
			System.out.println("--오라클 접속 성공--");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void dbConnClose() {
		try {
			if(rs != null) {rs.close();
			if(pstmt != null) {pstmt.close();}
			if(conn!=null) {conn.close();}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getMemoInsert(MemoDTO dto) {
		int result = 0;
		dbConn();
		try {
			String sql = "insert into memo values(seq_memo.nextval,?,?,CURRENT_TIMESTAMP)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getContent());
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbConnClose();
		}
		return result;
	}
	
	public ArrayList<MemoDTO> getSelectAll(int startRecord, int lastRecord) {
		ArrayList<MemoDTO> list = new ArrayList<MemoDTO>();
		dbConn();
		try {
			String basicSql = "";
			basicSql += "select * from memo where id > ?";
			basicSql += "order by id desc";
			
			String sql = "";
			sql += "select * from (select A.*, Rownum Rnum from (" + basicSql + ") A)";
			sql += "where Rnum >= ? and Rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.setInt(2, startRecord);
			pstmt.setInt(3, lastRecord);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemoDTO dto = new MemoDTO();
	              dto.setId(rs.getInt("id"));
	              dto.setName(rs.getString("name"));
	              dto.setContent(rs.getString("content"));
	              dto.setRegi_date(rs.getTimestamp("regi_date"));
	
				list.add(dto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			dbConnClose();
		}
			return list;
		}
	
	public MemoDTO getView(int id) {
		dbConn();
		MemoDTO dto = new MemoDTO();
		
		try {
			String sql = "select * from memo where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				dto.setContent(rs.getString("content"));
				dto.setRegi_date(rs.getTimestamp("regi_date"));
			}
			
			if (rs != null) { rs.close(); }
			if (pstmt != null) { pstmt.close(); }
		} catch(Exception e) {
			System.out.println("-- 접속 실패 --");
			e.printStackTrace();
		}
		
		return dto;
	}
	
	public int getTotalRecord() {
		int result = 0;
		dbConn();
		try {
			String sql = "select count(*) from memo where id > ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			dbConnClose();
		}
		return result;
	}
	
}
