package com.user.dao.impl;

import java.sql.SQLException;

import com.dao.BaseDao;
import com.user.po.User;

public class UserDaoImp extends BaseDao {
	public int save(User user) throws SQLException {
		// SQL语句
		String save_sql = "INSERT INTO t_user VALUES (T_USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Object[] params = new Object[]{
				user.getUsername() == null  ?  "" : user.getUsername(),
				user.getPassword() == null ? "" : user.getPassword(),
				user.getRule() == null ? "" : user.getRule(),
			    user.getRealname() == null ? "" : user.getRealname(),
			    user.getSex() == null ? "" : user.getSex(),
			    user.getCity() == null ? "" : user.getCity().getCityId(),
			    user.getCertType() == null ? "" : user.getCertType().getId(),
			    user.getCert() == null ? "" : user.getCert(),
			    new java.sql.Date(user.getBirthday().getTime()),
			    user.getUserType() == null ? "" : user.getUserType().getId(),
			    user.getContent() == null ? "" : user.getContent(),
			    user.getStatus() == null ? "" : user.getStatus(),
			    user.getLoginIp() == null ? "" : user.getLoginIp(),
			    user.getImagePath() == null ? "" : user.getImagePath()
		};
		return getNonQuery(save_sql, params);
		
	}
}
