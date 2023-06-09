package com.kh.mini_sample.dao;

import com.kh.mini_sample.common.Common;
import com.kh.mini_sample.vo.SongVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LikeDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 좋아요 순으로 노래 조회
    public List<SongVO> songChart(String id) {
        List<SongVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT S.SONG_ID, S.TITLE, S.ARTIST, S.SONG_URL, S.COVER_URL, COUNT(L.LIKE_ID) AS LIKE_COUNT " +
                    "FROM SONG S " +
                    "JOIN LIKES L ON S.SONG_ID = L.SONG_ID " +
                    "GROUP BY S.SONG_ID, S.TITLE, S.ARTIST, S.SONG_URL, S.COVER_URL " +
                    "ORDER BY LIKE_COUNT DESC, S.TITLE ASC";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                SongVO vo = new SongVO();
                int songId = rs.getInt("SONG_ID");
                String songTitle = rs.getString("TITLE");
                String songArtist = rs.getString("ARTIST");
                String songUrl = rs.getString("SONG_URL");
                String coverUrl = rs.getString("COVER_URL");
                int likeCount = rs.getInt("LIKE_COUNT");

                vo.setSongId(songId);
                vo.setTitle(songTitle);
                vo.setArtist(songArtist);
                vo.setSong_url(songUrl);
                vo.setCover_url(coverUrl);
                vo.setLike_count(likeCount);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
