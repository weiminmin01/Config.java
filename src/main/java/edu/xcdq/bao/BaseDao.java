package edu.xcdq.bao;

import edu.xcdq.util.ConfigManager;

import java.sql.*;

public class BaseDao {
    protected Connection conn;
    protected PreparedStatement ps;
    protected ResultSet rs;

    public boolean getConnection(){
     String url= ConfigManager.getInstance().getString("jdbc.connection.url");
     String name=ConfigManager.getInstance().getString("jdbc.connection.Username");
     String password= ConfigManager.getInstance().getString("jdbc.connection.password");
     String driver=ConfigManager.getInstance().getString("jdbc.driver_class");
     try {
         Class.forName(driver);
         conn= DriverManager.getConnection(url,name,password);
     } catch (ClassNotFoundException | SQLException e) {
         e.printStackTrace();
         return false;
     }
        return true;
    }


  public int executeUpdate(String sql ,Object[] params){
      int updateRows=0;
    if (getConnection()){
        try{
            ps=conn.prepareStatement(sql);
            for (int i = 0; i <params.length ; i++) {
                ps.setObject(i+1,params[i]);
            }
            updateRows=ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    return updateRows;
  }



  public ResultSet executeSql(String sql,Object[] params){
        if (getConnection()){
            try{
                ps=conn.prepareStatement(sql);
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i+1,params[i]);
                }
                rs=ps.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return rs;
  }



  public boolean closeResource(){
  if (rs != null) {
      try {
          rs.close();
      } catch (SQLException throwables) {
          throwables.printStackTrace();
          return false;
      }
  }
      if (ps!= null) {
          try {
              ps.close();
          } catch (SQLException throwables) {
              throwables.printStackTrace();
              return false;
          }
      }
          if (conn != null) {
              try {
                  conn.close();
              } catch (SQLException throwables) {
                  throwables.printStackTrace();
                  return false;
              }

          }
      return true;

      }

}
