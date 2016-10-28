package com.user.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.user.po.User;
import com.user.po.UserType;
import com.user.service.UserService;
import com.util.Md5Utils;
import com.util.TextUtils;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		if ("user".equals(action)) {
			doUserEdit(request, response);
		} else if ("password".equals(action)) {
			doPasswordEdit(request, response);
		}
	}

	private void doPasswordEdit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession se = request.getSession();
		User user = (User) se.getAttribute("user");

		User one = new User();
		populatePassword(request, one);

		String msg = validate(one);
		if (TextUtils.isEmpty(msg)) {
			if (Md5Utils.md5(one.getPasswordOld()).equals(user.getPassword())) {
				UserService userService = UserService.getInstance();
				one.setId(user.getId());
				one.setPassword(Md5Utils.md5(one.getPassword()));
				userService.updateUser(one);

				// 更新Session中User
				user.setPassword(one.getPassword());

				request.getRequestDispatcher("/User/main.jsp").forward(request,
						response);
			} else {
				msg = "原密码不正确";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("/User/UserPassword_Edit.jsp")
						.forward(request, response);
			}
		} else {
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/User/UserPassword_Edit.jsp")
					.forward(request, response);
		}
	}

	private void doUserEdit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession se = request.getSession();
		User user = (User) se.getAttribute("user");

		User one = new User();

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");
				upload.setSizeMax(10 * 1024 * 1024);

				List<FileItem> fileItems = upload.parseRequest(request);
				Iterator<FileItem> iter = fileItems.iterator();

				// 依次处理每个表单域
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();

					if (item.isFormField()) {
						// 如果item是正常的表单域
						String name = new String(item.getFieldName().getBytes(
								"iso8859-1"), "utf-8");
						String value = new String(item.getString().getBytes(
								"iso8859-1"), "utf-8");

						if ("userType".equals(name)) {
							UserType userType = new UserType();
							userType.setId(Integer.parseInt(value));
							one.setUserType(userType);
						} else if ("content".equals(name)) {
							one.setContent(value);
						}

					} else {
						// 获得文件名及路径
						String fileName = item.getName();

						if (fileName != null && fileName.indexOf(".") != -1) {
							String name = new Date().getTime()
									+ "."
									+ fileName
											.substring(fileName.indexOf(".") + 1);
							File fullFile = new File(getServletContext()
									.getRealPath("/images/photo/"), name);
							one.setImagePath(name);
							item.write(fullFile);
						}
						
						item.delete();
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		UserService userService = UserService.getInstance();
		one.setId(user.getId());
		userService.updateUser(one);

		// 更新Session中User
		User dbUser = userService.findUser(one);
		se.setAttribute("user", dbUser);

		request.getRequestDispatcher("/User/UserManageInfo.jsp").forward(
				request, response);
	}

	private void populatePassword(HttpServletRequest request, User user) {
		String passwordOld = request.getParameter("passwordOld");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		user.setPasswordOld(passwordOld);
		user.setPassword(password);
		user.setPassword2(password2);
	}

	private String validate(User user) {
		String errorMsg = null;
		if (TextUtils.isEmpty(user.getPasswordOld())) {
			errorMsg = "请输入原密码";
		} else if (TextUtils.isEmpty(user.getPassword())) {
			errorMsg = "请输入新密码";
		} else if (TextUtils.isEmpty(user.getPassword2())) {
			errorMsg = "请输入确认密码";
		} else if (!user.getPassword().equals(user.getPassword2())) {
			errorMsg = "两次密码不相等";
		}
		return errorMsg;
	}
}
