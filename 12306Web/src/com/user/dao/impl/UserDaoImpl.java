package com.user.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.page.Pageable;
import com.page.PageableResultSet;
import com.user.dao.UserDao;
import com.user.po.CertType;
import com.user.po.City;
import com.user.po.IpAddress;
import com.user.po.Province;
import com.user.po.User;
import com.user.po.UserType;
import com.util.DBUtils;

/**
 * 用户表操作类
 */
public class UserDaoImpl implements UserDao {
	/**
	 * 数据库连接
	 */
	private Connection conn;

	/**
	 * 构造方法
	 * 
	 * @param conn
	 *            数据库连接
	 */
	public UserDaoImpl(Connection conn) {
		this.conn = conn;
	}
	/**
	 * 增加用户
	 * 
	 * @param user
	 *            用户对象
	 * @throws SQLException
	 */
	@Override
	public int save(User user) throws SQLException {
		//SQL语句
		String save_sql = "INSERT INTO t_user VALUES (T_USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement pstmt = null;
		int row = 0;
		int idx = 1;
		try {
			//设置语句对象，SQL语句条件
			pstmt = conn.prepareStatement(save_sql);
			pstmt.setString(idx, user.getUsername());
			pstmt.setString(++idx, user.getPassword());
			pstmt.setString(++idx, user.getRule());
			pstmt.setString(++idx, user.getRealname());
			pstmt.setString(++idx, user.getSex());
			pstmt.setString(++idx, user.getCity().getCityId());
			pstmt.setInt(++idx, user.getCertType().getId());
			pstmt.setString(++idx, user.getCert());
			pstmt.setDate(++idx,
					new java.sql.Date(user.getBirthday().getTime()));
			pstmt.setInt(++idx, user.getUserType().getId());
			pstmt.setString(++idx, user.getContent());
			pstmt.setString(++idx, user.getStatus());
			pstmt.setString(++idx, user.getLoginIp());
			pstmt.setString(++idx, user.getImagePath());

			row = pstmt.executeUpdate();
		} finally {
			DBUtils.closeStatement(null, pstmt);
		}
		return row;
	}
	/**
	 * 删除用户(采用IN方式完成)
	 * 
	 * @param userIdList
	 *            用户ID的集合
	 * @throws SQLException
	 */
	@Override
	public boolean deleteUsers(int[] userIdList) throws SQLException {
		// 构建所有删除用户字符串，形如(100, 101)
		String param = Arrays.toString(userIdList).replace("[", "(")
				.replace("]", ")");

		//SQL语句
		String sql = "DELETE FROM t_user WHERE id IN " + param;

		boolean row = false;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			row = pstmt.execute();
		} finally {
			DBUtils.closeStatement(null, pstmt);
		}
		return row;
	}
	/**
	 * 通过用户名、密码验证登录用户信息(Lazy Mode)
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return user 用户信息，若用户名、密码不相符返回对象为null
	 * @throws SQLException
	 */
	@Override
	public User login(String username, String password) throws SQLException {
		// SQL语句
		StringBuffer buff = new StringBuffer();
		buff.append("SELECT u.*, ");
		buff.append("c.city_id ccityid, c.city ccity, c.father cfather, ");
		buff.append("p.province_id pprovinceid, p.province pprovince, ");
		buff.append("t.id tid, t.content tcontent, ");
		buff.append("e.id eid, e.content econtent ");
		buff.append("FROM t_user  u, t_city c, t_province p, t_usertype t, t_certtype e ");
		buff.append("WHERE u.city = c.city_id AND c.father = p.province_id AND u.user_type = t.id AND u.cert_type = e.id ");
		buff.append("AND username=? AND password = ? ");
		String find_sql = buff.toString();
		
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//设置语句对象，SQL语句条件
			pstmt = conn.prepareStatement(find_sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				// 解析结果集对象，封装查询结果
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRule(rs.getString("rule"));
				user.setRealname(rs.getString("realname"));
				user.setSex(rs.getString("sex"));

				// city
				Province province = new Province();
				province.setProvince(rs.getString("pprovince"));
				province.setProvinceId(rs.getString("pprovinceid"));
				
				City city = new City();
				city.setCityId(rs.getString("ccityid"));
				city.setCity(rs.getString("ccity"));
				city.setProvince(province);
				
				user.setCity(city);

				// CertType
				CertType certType = new CertType();
				certType.setId(rs.getInt("eid"));
				certType.setContent(rs.getString("econtent"));
				user.setCertType(certType);

				user.setCert(rs.getString("cert"));
				user.setBirthday(rs.getDate("birthday"));

				// UserType
				UserType userType = new UserType();
				userType.setId(rs.getInt("tid"));
				userType.setContent(rs.getString("tcontent"));
				user.setUserType(userType);

				user.setContent(rs.getString("content"));
				user.setStatus(rs.getString("status"));
				user.setLoginIp(rs.getString("login_ip"));
				user.setImagePath(rs.getString("image_path"));
			}
		} finally {
			DBUtils.closeStatement(rs, pstmt);
		}
		return user;
	}
	/**
	 * 通过用户信息查询用户
	 * @param 用户信息，逐项信息查询，信息为空说明该查询条件为空
	 * @return 用户信息，若未查到符合条件用户则返回对象为null
	 * @throws SQLException
	 */
	@Override
	public User findUser(User one) throws SQLException {
		// SQL语句
		StringBuffer find_sql = new StringBuffer();
		find_sql.append("SELECT u.*, ");
		find_sql.append("c.city_id ccityid, c.city ccity, c.father cfather, ");
		find_sql.append("p.province_id pprovinceid, p.province pprovince, ");
		find_sql.append("t.id tid, t.content tcontent, ");
		find_sql.append("e.id eid, e.content econtent ");
		find_sql.append("FROM t_user  u, t_city c, t_province p, t_usertype t, t_certtype e ");
		find_sql.append("WHERE u.city = c.city_id AND c.father = p.province_id AND u.user_type = t.id AND u.cert_type = e.id");
		
		//查询条件标记
		boolean tag = false;
		//查询条件id字段
		Integer id = one.getId();
		if(id!=null && id!=0){
			find_sql.append(" AND u.id="+id);
			tag = true;
		}
		//查询条件username字段
		String username = one.getUsername();
		if(username!=null && !username.isEmpty()){
			find_sql.append(" AND u.username='"+username+"'");
			tag = true;
		}
		//查询条件password字段
		String password = one.getPassword();
		if(password!=null && !password.isEmpty()){
			find_sql.append(" AND u.password='"+password+"'");
			tag = true;
		}
		//查询条件rule字段
		String rule = one.getRule();
		if(rule!=null && !rule.isEmpty()){
			find_sql.append(" AND u.rule='"+rule+"'");
			tag = true;
		}
		//查询条件realname字段，模糊查询
		String realname = one.getRealname();
		if(realname!=null && !realname.isEmpty()){
			find_sql.append(" AND u.realname LIKE '%"+realname+"%'");
			tag = true;
		}
		//查询条件sex字段
		String sex = one.getSex();
		if(sex!=null && !sex.isEmpty()){
			find_sql.append(" AND u.sex='"+sex+"'");
			tag = true;
		}
		//查询条件city字段
		if(one.getCity()!=null){
			String city = one.getCity().getCityId();
			if(city!=null && city.length()!=0){
				find_sql.append(" AND u.city="+city);
				tag = true;
			}
		}
		//查询条件cert_type字段
		if(one.getCertType()!=null){
			Integer certtype = one.getCertType().getId();
			if(certtype!=null && certtype!=0){
				find_sql.append(" AND u.cert_type="+certtype);
				tag = true;
			}
		}
		//查询条件cert字段
		String cert = one.getCert();
		if(cert!=null && !cert.isEmpty()){
			find_sql.append(" AND u.cert LIKE '%"+cert+"%'");
			tag = true;
		}
		//查询条件user_type字段
		if(one.getUserType()!=null){
			Integer usertype = one.getUserType().getId();
			if(usertype!=null && usertype!=0){
				find_sql.append(" AND u.user_type="+usertype);
				tag = true;
			}
		}
		//查询条件content字段
		String content = one.getContent();
		if(content!=null && !content.isEmpty()){
			find_sql.append(" AND u.content LIKE '%"+content+"%'");
			tag = true;
		}
		//查询条件status字段
		String status = one.getStatus();
		if(status!=null && !status.isEmpty()){
			find_sql.append(" AND u.status='"+status+"'");
			tag = true;
		}
		//查询条件login_ip字段
		String ip = one.getLoginIp();
		if(ip!=null && !ip.isEmpty()){
			find_sql.append(" AND u.login_ip='"+ip+"'");
			tag = true;
		}
		//查询条件image_path字段
		String image = one.getImagePath();
		if(image!=null && !image.isEmpty()){
			find_sql.append(" AND u.image_path='"+image+"'");
			tag = true;
		}
		
		//若没有查询条件则返回对象为null
		if(!tag){
			return null;
		}
		
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(find_sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// 解析结果集对象，封装查询结果
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRule(rs.getString("rule"));
				user.setRealname(rs.getString("realname"));
				user.setSex(rs.getString("sex"));

				// city
				Province province = new Province();
				province.setProvince(rs.getString("pprovince"));
				province.setProvinceId(rs.getString("pprovinceid"));
				
				City city = new City();
				city.setCityId(rs.getString("ccityid"));
				city.setCity(rs.getString("ccity"));
				city.setProvince(province);
				
				user.setCity(city);

				// CertType
				CertType certType = new CertType();
				certType.setId(rs.getInt("eid"));
				certType.setContent(rs.getString("econtent"));
				user.setCertType(certType);

				user.setCert(rs.getString("cert"));
				user.setBirthday(rs.getDate("birthday"));

				// UserType
				UserType userType = new UserType();
				userType.setId(rs.getInt("tid"));
				userType.setContent(rs.getString("tcontent"));
				user.setUserType(userType);

				user.setContent(rs.getString("content"));
				user.setStatus(rs.getString("status"));
				user.setLoginIp(rs.getString("login_ip"));
				user.setImagePath(rs.getString("image_path"));
			}
		} finally {
			DBUtils.closeStatement(rs, pstmt);
		}
		return user;
	}
	/**
	 * 删除用户(采用调用存储过程完成)
	 * @param userIdList
	 * @return 操作是否成功
	 * @throws SQLException
	 */
	@Override
	public boolean deleteUsersProcedure(int[] userIdList) throws SQLException {
		// 构建所有删除用户字符串，形如(100, 101)
		String param = Arrays.toString(userIdList).replace("[", "(")
				.replace("]", ")");

		//SQL语句，调用存储过程delBigTab，第一个参数为表名，第二个参数为删除条件，第三个参数为每N条数据提交一次
		String sql = "{call delBigTab(?,?,?)}";

		boolean row = false;
		//调用存储过程需要声明CallableStatement对象
		CallableStatement cst = null;
		try {
			//获取执行对象
			cst = conn.prepareCall(sql);
			//设置第一个参数，表名
			cst.setString(1, "t_user");
			//设置第二个参数，删除条件
			cst.setString(2, "id IN "+param);
			//设置第三个参数，每100条数据提交一次
			cst.setInt(3,100);
			
			row = cst.execute();
		} finally {
			DBUtils.closeStatement(null, cst);
		}
		return row;
	}
	/**
	 * 更新用户信息
	 * @param one，需要更新的用户信息对象
	 * @return 执行是否成功
	 * @throws SQLException
	 */
	@Override
	public boolean updateUser(User one) throws SQLException {
		
		//SQL语句
		StringBuffer update_sql = new StringBuffer("UPDATE t_user set");
		//查询条件标记
		boolean tag = false;
		//查询条件id字段
		Integer id = one.getId();
		if(id==null || id==0){
			return false;
		}
		else{
			update_sql.append(" id="+id);
		}
		//查询条件username字段
		String username = one.getUsername();
		if(username!=null && !username.isEmpty()){
			update_sql.append(", username='"+username+"'");
			tag = true;
		}
		//查询条件password字段
		String password = one.getPassword();
		if(password!=null && !password.isEmpty()){
			update_sql.append(", password='"+password+"'");
			tag = true;
		}
		//查询条件rule字段
		String rule = one.getRule();
		if(rule!=null && !rule.isEmpty()){
			update_sql.append(", rule='"+rule+"'");
			tag = true;
		}
		//
		String realname = one.getRealname();
		if(realname!=null && !realname.isEmpty()){
			update_sql.append(", realname='"+realname+"'");
			tag = true;
		}
		//查询条件sex字段
		String sex = one.getSex();
		if(sex!=null && !sex.isEmpty()){
			update_sql.append(", sex='"+sex+"'");
			tag = true;
		}
		//查询条件city字段
		if(one.getCity()!=null){
			String city = one.getCity().getCityId();
			if(city!=null && city.length()!=0){
				update_sql.append(", city="+city);
				tag = true;
			}
		}
		//查询条件cert_type字段
		if(one.getCertType()!=null){
			Integer certtype = one.getCertType().getId();
			if(certtype!=null && certtype!=0){
				update_sql.append(", cert_type="+certtype);
				tag = true;
			}
		}
		//查询条件cert字段
		String cert = one.getCert();
		if(cert!=null && !cert.isEmpty()){
			update_sql.append(", cert='"+cert+"'");
			tag = true;
		}
		//查询条件user_type字段
		if(one.getUserType()!=null){
			Integer usertype = one.getUserType().getId();
			if(usertype!=null && usertype!=0){
				update_sql.append(", user_type="+usertype);
				tag = true;
			}
		}
		//查询条件content字段
		String content = one.getContent();
		if(content!=null && !content.isEmpty()){
			update_sql.append(", content='"+content+"'");
			tag = true;
		}
		//查询条件status字段
		String status = one.getStatus();
		if(status!=null && !status.isEmpty()){
			update_sql.append(", status='"+status+"'");
			tag = true;
		}
		//查询条件login_ip字段
		String ip = one.getLoginIp();
		if(ip!=null && !ip.isEmpty()){
			update_sql.append(", login_ip='"+ip+"'");
			tag = true;
		}
		//查询条件image_path字段
		String image = one.getImagePath();
		if(image!=null && !image.isEmpty()){
			update_sql.append(", image_path='"+image+"'");
			tag = true;
		}
		
		update_sql.append(" where id="+id);
		
		//若没有查询条件则返回对象为null
		if(!tag){
			return false;
		}
		
		PreparedStatement pstmt = null;
		try {
			//设置语句对象，SQL语句条件
			pstmt = conn.prepareStatement(update_sql.toString());
			int row = pstmt.executeUpdate();
			if(row!=0){
				tag = true;
			} else {
				tag = false;
			}
		} finally {
			DBUtils.closeStatement(null, pstmt);
		}
		return tag;
	}
	/**
	 * 获取用户列表最大行数
	 * @param one，查询条件
	 * @return 列表最大行数
	 * @throws SQLException
	 */
	@Override
	public int getUserListRowCount(User one) throws SQLException {
		int rowCount = 0;
		// SQL语句
		StringBuffer find_sql = new StringBuffer();
		find_sql.append("SELECT count(*) ");
		find_sql.append("FROM t_user  u, t_city c, t_province p, t_usertype t, t_certtype e ");
		find_sql.append("WHERE u.city = c.city_id AND c.father = p.province_id AND u.user_type = t.id AND u.cert_type = e.id");
		
		//查询条件id字段
		Integer id = one.getId();
		if(id!=null && id!=0){
			find_sql.append(" AND u.id="+id);
		}
		//查询条件username字段
		String username = one.getUsername();
		if(username!=null && !username.isEmpty()){
			find_sql.append(" AND u.username='"+username+"'");
		}
		//查询条件password字段
		String password = one.getPassword();
		if(password!=null && !password.isEmpty()){
			find_sql.append(" AND u.password='"+password+"'");
		}
		//查询条件rule字段
		String rule = one.getRule();
		if(rule!=null && !rule.isEmpty()){
			find_sql.append(" AND u.rule='"+rule+"'");
		}
		//查询条件realname字段，模糊查询
		String realname = one.getRealname();
		if(realname!=null && !realname.isEmpty()){
			find_sql.append(" AND u.realname LIKE '%"+realname+"%'");
		}
		//查询条件sex字段
		String sex = one.getSex();
		if(sex!=null && !sex.isEmpty()){
			find_sql.append(" AND u.sex='"+sex+"'");
		}
		//查询条件city字段
		if(one.getCity()!=null){
			String city = one.getCity().getCityId();
			if(city!=null && city.length()!=0){
				find_sql.append(" AND u.city='"+city+"'");
			}
		}
		//查询条件cert_type字段
		if(one.getCertType()!=null){
			Integer certtype = one.getCertType().getId();
			if(certtype!=null && certtype!=0){
				find_sql.append(" AND u.cert_type="+certtype);
			}
		}
		//查询条件cert字段
		String cert = one.getCert();
		if(cert!=null && !cert.isEmpty()){
			find_sql.append(" AND u.cert LIKE '%"+cert+"%'");
		}
		//查询条件user_type字段
		if(one.getUserType()!=null){
			Integer usertype = one.getUserType().getId();
			if(usertype!=null && usertype!=0){
				find_sql.append(" AND u.user_type="+usertype);
			}
		}
		//查询条件content字段
		String content = one.getContent();
		if(content!=null && !content.isEmpty()){
			find_sql.append(" AND u.content LIKE '%"+content+"%'");
		}
		//查询条件status字段
		String status = one.getStatus();
		if(status!=null && !status.isEmpty()){
			find_sql.append(" AND u.status='"+status+"'");
		}
		//查询条件login_ip字段
		String ip = one.getLoginIp();
		if(ip!=null && !ip.isEmpty()){
			find_sql.append(" AND u.login_ip='"+ip+"'");
		}
		//查询条件image_path字段
		String image = one.getImagePath();
		if(image!=null && !image.isEmpty()){
			find_sql.append(" AND u.image_path='"+image+"'");
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			pstmt = conn.prepareStatement(find_sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				//根据每页条数计算列表总页数
				rowCount = rs.getInt(1);
			}
		} finally {
			DBUtils.closeStatement(rs, pstmt);
		}
		
		return rowCount;
	}
	/**
	 * 获取用户列表最大页数
	 * @param pageSize，每页显示信息条数
	 * @param one，查询条件
	 * @return 列表最大页数
	 * @throws SQLException
	 */
	@Override
	public int getUserListPageCount(int pageSize, User one) throws SQLException {
		int res = 0;
		
		int rowCount = getUserListRowCount(one);
		if (rowCount%pageSize==0) {
			res = rowCount / pageSize;
		} else {
			res = rowCount / pageSize + 1;
		}
		
		return res;
	}
	/**
	 * 获取指定页用户信息列表，通过分页SQL语句实现
	 * @param pageSize，每页显示信息条数
	 * @param rowNum，需要获取的页数
	 * @param one，查询条件
	 * @return 用户信息列表，List[User]，若无满足条件则列表为空
	 * @throws SQLException
	 */
	@Override
	public List<User> getUserList(int pageSize, int rowNum, User one)
			throws SQLException {
		// SQL语句
		StringBuffer find_sql = new StringBuffer();
		find_sql.append("SELECT u.*, ");
		find_sql.append("c.city_id ccityid, c.city ccity, c.father cfather, ");
		find_sql.append("p.province_id pprovinceid, p.province pprovince, ");
		find_sql.append("t.id tid, t.content tcontent, ");
		find_sql.append("e.id eid, e.content econtent ");
		find_sql.append("FROM t_user  u, t_city c, t_province p, t_usertype t, t_certtype e ");
		find_sql.append("WHERE u.city = c.city_id AND c.father = p.province_id AND u.user_type = t.id AND u.cert_type = e.id");
		
		//查询条件id字段
		Integer id = one.getId();
		if(id!=null && id!=0){
			find_sql.append(" AND u.id="+id);
		}
		//查询条件username字段
		String username = one.getUsername();
		if(username!=null && !username.isEmpty()){
			find_sql.append(" AND u.username='"+username+"'");
		}
		//查询条件password字段
		String password = one.getPassword();
		if(password!=null && !password.isEmpty()){
			find_sql.append(" AND u.password='"+password+"'");
		}
		//查询条件rule字段
		String rule = one.getRule();
		if(rule!=null && !rule.isEmpty()){
			find_sql.append(" AND u.rule='"+rule+"'");
		}
		//查询条件realname字段，模糊查询
		String realname = one.getRealname();
		if(realname!=null && !realname.isEmpty()){
			find_sql.append(" AND u.realname LIKE '%"+realname+"%'");
		}
		//查询条件sex字段
		String sex = one.getSex();
		if(sex!=null && !sex.isEmpty()){
			find_sql.append(" AND u.sex='"+sex+"'");
		}
		//查询条件city字段
		if(one.getCity()!=null){
			String city = one.getCity().getCityId();
			if(city!=null && city.length()!=0){
				find_sql.append(" AND u.city='"+city+"'");
			}
		}
		//查询条件cert_type字段
		if(one.getCertType()!=null){
			Integer certtype = one.getCertType().getId();
			if(certtype!=null && certtype!=0){
				find_sql.append(" AND u.cert_type="+certtype);
			}
		}
		//查询条件cert字段
		String cert = one.getCert();
		if(cert!=null && !cert.isEmpty()){
			find_sql.append(" AND u.cert LIKE '%"+cert+"%'");
		}
		//查询条件user_type字段
		if(one.getUserType()!=null){
			Integer usertype = one.getUserType().getId();
			if(usertype!=null && usertype!=0){
				find_sql.append(" AND u.user_type="+usertype);
			}
		}
		//查询条件content字段
		String content = one.getContent();
		if(content!=null && !content.isEmpty()){
			find_sql.append(" AND u.content LIKE '%"+content+"%'");
		}
		//查询条件status字段
		String status = one.getStatus();
		if(status!=null && !status.isEmpty()){
			find_sql.append(" AND u.status='"+status+"'");
		}
		//查询条件login_ip字段
		String ip = one.getLoginIp();
		if(ip!=null && !ip.isEmpty()){
			find_sql.append(" AND u.login_ip='"+ip+"'");
		}
		//查询条件image_path字段
		String image = one.getImagePath();
		if(image!=null && !image.isEmpty()){
			find_sql.append(" AND u.image_path='"+image+"'");
		}
		//分页SQL语句
		String sql = "select*from(select a1.*,rownum rn from ("+find_sql.toString()+") a1 where rownum<="+rowNum*pageSize+")where rn>="+((rowNum-1)*pageSize+1);
		
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> list = new ArrayList<User>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 解析结果集对象，封装查询结果
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRule(rs.getString("rule"));
				user.setRealname(rs.getString("realname"));
				user.setSex(rs.getString("sex"));

				// city
				Province province = new Province();
				province.setProvince(rs.getString("pprovince"));
				province.setProvinceId(rs.getString("pprovinceid"));
				
				City city = new City();
				city.setCityId(rs.getString("ccityid"));
				city.setCity(rs.getString("ccity"));
				city.setProvince(province);
				
				user.setCity(city);

				// CertType
				CertType certType = new CertType();
				certType.setId(rs.getInt("eid"));
				certType.setContent(rs.getString("econtent"));
				user.setCertType(certType);

				user.setCert(rs.getString("cert"));
				user.setBirthday(rs.getDate("birthday"));

				// UserType
				UserType userType = new UserType();
				userType.setId(rs.getInt("tid"));
				userType.setContent(rs.getString("tcontent"));
				user.setUserType(userType);

				user.setContent(rs.getString("content"));
				user.setStatus(rs.getString("status"));
				user.setLoginIp(rs.getString("login_ip"));
				user.setImagePath(rs.getString("image_path"));
				
				list.add(user);
			}
		} finally {
			DBUtils.closeStatement(rs, pstmt);
		}
		return list;
	}
	/**
	 * 获取指定页用户信息列表，通过分析结果集ResultSet对象实现
	 * @param pageSize，每页显示信息条数
	 * @param pageNum，需要获取的页数
	 * @param one，需要获取的页数
	 * @return 用户信息列表，List[User]，若无满足条件则列表为空
	 * @throws SQLException
	 */
	@Override
	public List<User> getUserListRS(int pageSize, int pageNum, User one)
			throws SQLException {
		// SQL语句
		StringBuffer find_sql = new StringBuffer();
		find_sql.append("SELECT u.*, ");
		find_sql.append("c.city_id ccityid, c.city ccity, c.father cfather, ");
		find_sql.append("p.province_id pprovinceid, p.province pprovince, ");
		find_sql.append("t.id tid, t.content tcontent, ");
		find_sql.append("e.id eid, e.content econtent ");
		find_sql.append("FROM t_user  u, t_city c, t_province p, t_usertype t, t_certtype e ");
		find_sql.append("WHERE u.city = c.city_id AND c.father = p.province_id AND u.user_type = t.id AND u.cert_type = e.id");
		
		//查询条件id字段
		Integer id = one.getId();
		if(id!=null && id!=0){
			find_sql.append(" AND u.id="+id);
		}
		//查询条件username字段
		String username = one.getUsername();
		if(username!=null && !username.isEmpty()){
			find_sql.append(" AND u.username='"+username+"'");
		}
		//查询条件password字段
		String password = one.getPassword();
		if(password!=null && !password.isEmpty()){
			find_sql.append(" AND u.password='"+password+"'");
		}
		//查询条件rule字段
		String rule = one.getRule();
		if(rule!=null && !rule.isEmpty()){
			find_sql.append(" AND u.rule='"+rule+"'");
		}
		//查询条件realname字段，模糊查询
		String realname = one.getRealname();
		if(realname!=null && !realname.isEmpty()){
			find_sql.append(" AND u.realname LIKE '%"+realname+"%'");
		}
		//查询条件sex字段
		String sex = one.getSex();
		if(sex!=null && !sex.isEmpty()){
			find_sql.append(" AND u.sex='"+sex+"'");
		}
		//查询条件city字段
		if(one.getCity()!=null){
			String city = one.getCity().getCityId();
			if(city!=null && city.length()!=0){
				find_sql.append(" AND u.city='"+city+"'");
			}
		}
		//查询条件cert_type字段
		if(one.getCertType()!=null){
			Integer certtype = one.getCertType().getId();
			if(certtype!=null && certtype!=0){
				find_sql.append(" AND u.cert_type="+certtype);
			}
		}
		//查询条件cert字段
		String cert = one.getCert();
		if(cert!=null && !cert.isEmpty()){
			find_sql.append(" AND u.cert LIKE '%"+cert+"%'");
		}
		//查询条件user_type字段
		if(one.getUserType()!=null){
			Integer usertype = one.getUserType().getId();
			if(usertype!=null && usertype!=0){
				find_sql.append(" AND u.user_type="+usertype);
			}
		}
		//查询条件content字段
		String content = one.getContent();
		if(content!=null && !content.isEmpty()){
			find_sql.append(" AND u.content LIKE '%"+content+"%'");
		}
		//查询条件status字段
		String status = one.getStatus();
		if(status!=null && !status.isEmpty()){
			find_sql.append(" AND u.status='"+status+"'");
		}
		//查询条件login_ip字段
		String ip = one.getLoginIp();
		if(ip!=null && !ip.isEmpty()){
			find_sql.append(" AND u.login_ip='"+ip+"'");
		}
		//查询条件image_path字段
		String image = one.getImagePath();
		if(image!=null && !image.isEmpty()){
			find_sql.append(" AND u.image_path='"+image+"'");
		}
		
		User user = null;
		PreparedStatement pstmt = null;
		//声明分页工具接口对象
		Pageable rs = null;
		List<User> list = new ArrayList<User>();
		try {
			pstmt = conn.prepareStatement(find_sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=new PageableResultSet(pstmt.executeQuery());
			rs.setPageSize(pageSize);
			rs.gotoPage(pageNum);
			for (int i = 0; i < rs.getPageRowsCount(); i++) {
			    rs.next();
			    
			    // 解析结果集对象，封装查询结果
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRule(rs.getString("rule"));
				user.setRealname(rs.getString("realname"));
				user.setSex(rs.getString("sex"));

				// city
				Province province = new Province();
				province.setProvince(rs.getString("pprovince"));
				province.setProvinceId(rs.getString("pprovinceid"));
				
				City city = new City();
				city.setCityId(rs.getString("ccityid"));
				city.setCity(rs.getString("ccity"));
				city.setProvince(province);
				
				user.setCity(city);

				// CertType
				CertType certType = new CertType();
				certType.setId(rs.getInt("eid"));
				certType.setContent(rs.getString("econtent"));
				user.setCertType(certType);

				user.setCert(rs.getString("cert"));
				user.setBirthday(rs.getDate("birthday"));

				// UserType
				UserType userType = new UserType();
				userType.setId(rs.getInt("tid"));
				userType.setContent(rs.getString("tcontent"));
				user.setUserType(userType);

				user.setContent(rs.getString("content"));
				user.setStatus(rs.getString("status"));
				user.setLoginIp(rs.getString("login_ip"));
				user.setImagePath(rs.getString("image_path"));
				
				list.add(user);
			}
		} finally {
			DBUtils.closeStatement(rs, pstmt);
		}
		return list;
	}
	/**
	 * 统计Ip地址
	 * @throws SQLException
	 */
	public List<IpAddress> getIpList() throws SQLException {
		// SQL语句
		String sql = "select nvl(LOGIN_IP, '0.0.0.0') as ip, count(nvl(LOGIN_IP, '0.0.0.0')) as cnt from t_user group by LOGIN_IP";
		
		List<IpAddress> list = new ArrayList<IpAddress>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//设置语句对象，SQL语句条件
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 解析结果集对象，封装查询结果
				IpAddress ipAddress = new IpAddress();
				ipAddress.setIp(rs.getString(1));
				ipAddress.setCnt(rs.getInt(2));
				list.add(ipAddress);
				
			}
		} finally {
			DBUtils.closeStatement(rs, pstmt);
		}
		return list;
	}
}
