package com.user.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.Md5Utils;
import com.util.TextUtils;
import com.user.po.User;
import com.user.service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String action = request.getParameter("action");

		if ( action == null || "login".equals(action)) {
			doLogin(request, response);
		} else if ("autoLogin".equals(action)) {
			doAutoLogin(request, response);
		}
	}

	private void doAutoLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = "";
		String autoLogin = "";

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if ("username".equals(c.getName())) {
					username = c.getValue();
				} else if ("autoLogin".equals(c.getName())) {
					autoLogin = c.getValue();
				}
			}
		}

		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(autoLogin)) {
			response.sendRedirect(request.getContextPath() + "/Login.jsp");
		} else {
			UserService userService = UserService.getInstance();
			User one = new User();
			one.setUsername(username);
			User dbUser = userService.findUser(one);
			if (autoLogin.equals(Md5Utils.md5(username + ","
					+ dbUser.getPassword()))) {
				// 更新LoginIP
				User user = new User();
				populate(request, user);

				one = new User();
				one.setId(dbUser.getId());
				one.setLoginIp(user.getLoginIp());
				userService.updateUser(one);

				dbUser.setLoginIp(user.getLoginIp());
				HttpSession se = request.getSession();
				se.setAttribute("user", dbUser);

				if ("1".equals(dbUser.getRule())) {
					response.sendRedirect(request.getContextPath()
							+ "/Admin/Index.jsp");
				} else {
					response.sendRedirect(request.getContextPath()
							+ "/User/Index.jsp");
				}
			} else {
				Cookie c = new Cookie("username", "");
				c.setMaxAge(0);
				c.setPath("/");
				response.addCookie(c);

				Cookie cLogin = new Cookie("autoLogin", "");
				cLogin.setMaxAge(0);
				cLogin.setPath("/");
				response.addCookie(cLogin);

				response.sendRedirect(request.getContextPath() + "/Login.jsp");
			}
		}
	}

	private void doLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession se = request.getSession();
		
		// 取得请求参数
		User user = new User();
		populate(request, user);

		// 服务器端验证
		String msg = validate(user);
		if (TextUtils.isEmpty(msg)) {
			String code = (String) se.getAttribute("code");
			
			//图片文件的验证码和页面中文本框输入的验证比较
			if (!user.getCode().equalsIgnoreCase(code)) {
				msg = "验证码错误";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("/Login.jsp").forward(request,
						response);
			} else {
				// 调用Service方法
				UserService userService = UserService.getInstance();

				// 检查用户是否存在
				User dbUser = userService.login(user.getUsername(),
						user.getPassword());
				if (dbUser == null) {
					msg = "用户名或密码错误";
					request.setAttribute("msg", msg);
					request.getRequestDispatcher("/Login.jsp").forward(request,
							response);
				} else {
					// 更新LoginIP
					User one = new User();
					one.setId(dbUser.getId());
					one.setLoginIp(user.getLoginIp());
					userService.updateUser(one);

					dbUser.setLoginIp(user.getLoginIp());
					se.setAttribute("user", dbUser);

					// 保存上一次登录用户名
					Cookie c = new Cookie("username", dbUser.getUsername());
					c.setMaxAge(365 * 24 * 60);
					c.setPath("/");
					response.addCookie(c);

					// 自动登陆
					if (user.isAutoLogin()) {
						Cookie cLogin = new Cookie("autoLogin",
								Md5Utils.md5(dbUser.getUsername() + ","
										+ dbUser.getPassword()));
						cLogin.setMaxAge(365 * 24 * 60);
						cLogin.setPath("/");
						response.addCookie(cLogin);
					} else {
						Cookie cLogin = new Cookie("autoLogin", "");
						cLogin.setMaxAge(0);
						cLogin.setPath("/");
						response.addCookie(cLogin);
					}

					if ("1".equals(dbUser.getRule())) {
						response.sendRedirect(request.getContextPath()
								+ "/Admin/Index.jsp");
					} else {
						response.sendRedirect(request.getContextPath()
								+ "/User/Index.jsp");
					}
				}
			}
		} else {
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/Login.jsp").forward(request,
					response);
		}
	}

	private String validate(User user) {
		String errorMsg = null;
		if (TextUtils.isEmpty(user.getUsername())) {
			errorMsg = "请输入用户名";
		} else if (user.getUsername().length() < 6
				|| user.getUsername().length() > 30) {
			errorMsg = "用户名长度在6到30位之间";
		} else if (!user.getUsername().matches("[a-zA-Z0-9_]{6,30}")) {
			errorMsg = "用户名只能包含由字母、数字或“_”";
		} else if (TextUtils.isEmpty(user.getPassword())) {
			errorMsg = "请输入密码";
		} else if (TextUtils.isEmpty(user.getCode())) {
			errorMsg = "请输入验证码";
		}
		return errorMsg;
	}

	private void populate(HttpServletRequest request, User user) {
		// 获取客户端IP
		String loginIp = request.getRemoteAddr();

		// 获取表单参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String autoLogin = request.getParameter("autoLogin");
		String code = request.getParameter("code");

		user.setLoginIp(loginIp);
		user.setUsername(username);
		if(password != null){
			user.setPassword(Md5Utils.md5(password));
		}
		if (TextUtils.isEmpty(autoLogin)) {
			user.setAutoLogin(false);
		} else {
			user.setAutoLogin(true);
		}
		user.setCode(code);
	}
}
