package com.cehome.apimanager.utils;

import com.cehome.apimanager.model.dto.SysDbReqDto;
import com.cehome.apimanager.model.dto.SysDbResDto;
import com.cehome.apimanager.model.po.DbConfig;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUtils {
    public static List<SysDbResDto> getTables(DbConfig dbConfig, String tableName) {
        List<SysDbResDto> list = new ArrayList<>();
        String url = "jdbc:mysql://" + dbConfig.getIp() + ":" + dbConfig.getPort() + "/" + dbConfig.getDbName() + "?user=" + dbConfig.getUserName() + "&password=" + dbConfig.getPassword()
                + "&useUnicode=true&characterEncoding=UTF8";
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        String sql = "select table_name, table_comment from information_schema.tables where table_schema='" + dbConfig.getDbName() + "' and table_type='base table'";
        if(!StringUtils.isEmpty(tableName)){
            sql += " and table_name like '%" + tableName + "%'";
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                SysDbResDto sysDbResDto = new SysDbResDto();
                sysDbResDto.setTableComment(rs.getString("table_comment"));
                sysDbResDto.setTableName(rs.getString("table_name"));
                list.add(sysDbResDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return list;
    }

    public static List<SysDbResDto> getColumnsInfo(DbConfig dbConfig, SysDbReqDto sysDbReqDto) {
        List<SysDbResDto> list = new ArrayList<>();
        String url = "jdbc:mysql://" + dbConfig.getIp() + ":" + dbConfig.getPort() + "/" + dbConfig.getDbName() + "?user=" + dbConfig.getUserName() + "&password=" + dbConfig.getPassword()
                + "&useUnicode=true&characterEncoding=UTF8";
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        String sql = "select column_name, column_type, column_comment from information_schema.columns where table_schema='" + dbConfig.getDbName() + "' and table_name='" + sysDbReqDto.getTableName() + "'";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                SysDbResDto sysDbResDto = new SysDbResDto();
                sysDbResDto.setColumnComment(rs.getString("column_comment"));
                sysDbResDto.setColumnName(rs.getString("column_name"));
                sysDbResDto.setColumnType(getColumnType(rs.getString("column_type")));
                list.add(sysDbResDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return list;
    }

    private static Integer getColumnType(String columnTypeStr){
        if(StringUtils.isEmpty(columnTypeStr)){
            return 2;
        }
        if(columnTypeStr.contains("int") || columnTypeStr.contains("smallint")
                || columnTypeStr.contains("tinyint")
                || columnTypeStr.contains("double")){
            return 1;
        }
        if(columnTypeStr.contains("varchar") || columnTypeStr.contains("text")
                || columnTypeStr.contains("char")){
            return 2;
        }
        return 0;
    }
}
