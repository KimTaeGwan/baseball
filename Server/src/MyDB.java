import java.sql.*;

public class MyDB {
	private static MyDB instance;

	private MyDB() {
	}

	public synchronized static MyDB getInstance() {
		if (instance == null) {
			instance = new MyDB();
		}
		return instance;
	}

	Connection con;
	Statement stmt;
	ResultSet rs; // SELECT문에서 사용

	String url = "jdbc:oracle:thin:@B101-009:1521:orcl";
	String user = "BIT7"; // ID
	String pw = "BIT7"; // PW

	// db접속
	void Connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, user, pw);
			stmt = con.createStatement();
			System.out.println("성공");
		}

		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// 접속해제
	public void DisConnect() {
		try {
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
			System.out.println("접속 해제");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 전체일정 가져오기
	public int SelectDateCount() {
		String sql = "select count(*) from match"; // 경기개수 받아오기
		int count = 0;

		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	// 팀이름, 경기번호 받아오기
	public void GetTeam(Team[] home, Team[] away, int[] matchnum) {

		String sql = "select a_team,h_team,m_number from match where m_end = 0 order by m_number";
		try {
			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next()) {

				home[i].team_name = rs.getString(2);
				away[i].team_name = rs.getString(1);
				matchnum[i] = rs.getInt(3);
				System.out.println(home[i].team_name + "/" + away[i].team_name);
				i++;
				String home1, away1;

			}
		}

		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// 선수 불러오기
	public void SelectPlayer(Team team) {
		// 투수
		String sql = "SELECT p.p_name,PI.p_id FROM TEAM T, player P, pitcher PI"
				+ " WHERE t.t_name = p.t_name AND p.t_name='" + team.team_name
				+ "'AND p.po_number=1 AND p.p_id= pi.p_id ORDER BY p.m_count ASC, p.p_id ASC";
		try {

			rs = stmt.executeQuery(sql);
			String str = null + "/";
			while (rs.next()) {
				str += rs.getString(1) + "/";
				str += rs.getInt(2) + "@";
			}
			String[] token = str.split("@");
			String[] token1 = token[0].split("/");
			team.pitcher_name = token1[1];
			team.pitcher_number = Integer.valueOf(token1[2]);
			System.out.println(team.pitcher_name + "/" + team.pitcher_number);
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		// 타자
		String sql1 = "SELECT p.p_name,B.p_id FROM TEAM T, player P, BATTER B WHERE t.t_name= p.t_name "
				+ "AND b.p_id= p.p_id AND t.t_name='" + team.team_name + "' AND p.p_id= b.p_id";

		try {
			rs = stmt.executeQuery(sql1);
			int i = 0;
			while (rs.next()) {
				team.batter_name[i] = rs.getString(1);
				team.batter_number[i] = rs.getInt(2);
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 투수,일정 로테이션
	public void Rotation(int m_num, int h_pid, int a_pid) {
		String sql = "update player set m_count = m_count+1 where p_id =" + h_pid; // 홈투수
		String sql1 = "update player set m_count = m_count+1 where p_id =" + a_pid; // 어웨이투수
		String sql2 = "update match m set m_end = m_end+1 where m_number =" + m_num; // 경기카운트

		try {
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);
			stmt.executeUpdate(sql2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 경기 결과 갱신
	public void MatchEnd(int match_num, int a_score, int h_score, String Win_Team, String Lose_Team, int idx) {
		String sql = null, sql1 = null, sql2 = null;
		if (idx == 0) {
			sql = "update match set a_score = " + a_score + ", h_score = " + h_score + ", win_team='" + Win_Team
					+ "', lose_team= '" + Lose_Team + "' where m_number=" + match_num;

			sql1 = "update team set win=(select count(*) FROM match where win_team = '" + Win_Team + "') where t_name='"
					+ Win_Team + "'";

			sql2 = "update team set lose=(select count(*) FROM match where lose_team = '" + Lose_Team
					+ "') where t_name='" + Lose_Team + "'";
		} else if (idx == 1) {
			sql = "update match set a_score = " + a_score + ", h_score = " + h_score + "," + "draw = 1 where m_number="
					+ match_num;

			sql1 = "update team set draw=(select count(*) FROM match where " + "(a_team='" + Win_Team + "' or h_team='"
					+ Win_Team + "') and draw=1) where t_name='" + Win_Team + "'";

			sql2 = "update team set draw=(select count(*) FROM match where " + "(a_team='" + Lose_Team + "' or h_team='"
					+ Lose_Team + "') and draw=1) where t_name='" + Lose_Team + "'";
		}

		try {
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);
			stmt.executeUpdate(sql2);
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
