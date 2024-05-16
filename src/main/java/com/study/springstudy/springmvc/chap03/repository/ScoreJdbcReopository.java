package com.study.springstudy.springmvc.chap03.repository;

import com.study.springstudy.springmvc.chap03.entity.Score;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreJdbcReopository implements ScoreRepositoy {


    private String url = "jdbc:mariadb://localhost:3306/spring5";
    private String username = "root";
    private String password = "mariadb";

    public ScoreJdbcReopository() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(Score score) {

        try(Connection conn = DriverManager.getConnection(url,username,password)){
            String sql = "INSERT INTO tbl_score " +
                    "(stu_name, kor, eng, math, total, average, grade) " +
                    "VALUES(?,?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,score.getStuName());
            pstmt.setInt(2,score.getKor());
            pstmt.setInt(3,score.getEng());
            pstmt.setInt(4,score.getMath());
            pstmt.setInt(5,score.getTotal());
            pstmt.setDouble(6,score.getAverage());
            pstmt.setString(7,score.getGrade().toString());

            int result = pstmt.executeUpdate();

            if(result==1) return true;


        }catch (Exception e){
           e.printStackTrace();
        }

        return false;



    }

    @Override
    public List<Score> findAll() {
        List<Score> scoreList = new ArrayList<>();

        try(Connection conn = connect()){

            String sql = "SELECT * FROM tbl_score";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
               Score s = new Score(rs);
               scoreList.add(s);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return scoreList;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
