package com.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.user.po.CertType;
import com.user.po.City;
import com.user.po.User;
import com.user.po.UserType;
import com.user.service.UserService;
import com.util.Md5Utils;
import com.util.TextUtils;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		// 取得请求参数
		User user = new User();
		populate(request, user);
		// rule(可以放在Service中)
		user.setRule("2");
		// Status(可以放在Service中)
		user.setStatus("1");

		// 服务器端验证
		String msg = validate(user);
		if (TextUtils.isEmpty(msg)) {
			// 调用Service方法
			UserService userService = UserService.getInstance();

			// 检查用户名是否重复
			User tmp = new User();
			tmp.setUsername(user.getUsername());
			User dbUser = userService.findUser(tmp);
			if (dbUser == null) {
				user.setPassword(Md5Utils.md5(user.getPassword()));
				userService.addUser(user);
				msg = "注册成功";
			} else {
				msg = "用户名重复";
			}
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>注册信息</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p>" + msg + "</p>");
		out.println("</body>");
		out.println("</html>");
		out.close();

	}

	private String validate(User user) {
		String errorMsg = null;
		if (TextUtils.isEmpty(user.getUsername())) {
			errorMsg = "请输入用户名";
		} else if (user.getUsername().length() < 6 || user.getUsername().length() > 30) {
			errorMsg = "用户名长度在6到30位之间";
		} else if (!user.getUsername().matches("[a-zA-Z0-9_]{6,30}")) {
			errorMsg = "用户名只能包含由字母、数字或“_”";
		} else if (TextUtils.isEmpty(user.getPassword())) {
			errorMsg = "请输入密码";
		} else if (!user.getPassword().equals(user.getPassword2())) {
			errorMsg = "两次密码不相等";
		} else if (TextUtils.isEmpty(user.getRealname())) {
			errorMsg = "请输入真实姓名";
		} else if (TextUtils.isEmpty(user.getCert())) {
			errorMsg = "请输入证件号码";
		} else if (user.getBirthday() == null) {
			errorMsg = "请输入出生日期";
		} else if (user.getCity().getCityId() == "") {
			errorMsg = "请选择城市";
		}
		return errorMsg;
	}

	private void populate(HttpServletRequest request, User user) {
		// 获取客户端IP
		String loginIp = request.getRemoteAddr();

		// 获取表单参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String realname = request.getParameter("realname");
		String sex = request.getParameter("sex");
		// 需要修改前台代码
		String cityId = request.getParameter("city");
		String certTypeId = request.getParameter("certType");
		String cert = request.getParameter("cert");
		String birthday = request.getParameter("birthday");
		String userTypeId = request.getParameter("userType");
		String content = request.getParameter("content");

		user.setLoginIp(loginIp);
		user.setUsername(username);
		user.setPassword(password);
		user.setPassword2(password2);
		user.setRealname(realname);
		user.setSex(sex);

		// City
		City city = new City();
		if (cityId == null || cityId.equals("城市")) {
			city.setCityId("");
		} else {
			city.setCityId(cityId);
		}
		user.setCity(city);

		// CertType
		CertType certType = new CertType();
		certType.setId(Integer.parseInt(certTypeId));
		user.setCertType(certType);

		// cert
		user.setCert(cert);

		// birthday
		if (!TextUtils.isEmpty(birthday)) {
			user.setBirthday(Date.valueOf(birthday));
		}

		// UserType
		UserType userType = new UserType();
		userType.setId(Integer.parseInt(userTypeId));
		user.setUserType(userType);

		// content
		user.setContent(content);
	}

}
