package com.user.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user.po.CertType;
import com.user.po.City;
import com.user.po.IpAddress;
import com.user.po.User;
import com.user.po.UserType;
import com.user.service.UserService;
import com.util.Md5Utils;
import com.util.TextUtils;

import jxl.Workbook;
import jxl.format.Border;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends HttpServlet {
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
		if ("add".equals(action)) {
			doAdminAdd(request, response);
		} else if ("delete".equals(action)) {
			doAdminDelete(request, response);
		} else if ("edit".equals(action)) {
			doAdminEdit(request, response);
		} else if ("update".equals(action)) {
			doAdminUpdate(request, response);
		} else if ("list".equals(action)) {
			doAdminList(request, response);
		} else if ("export".equals(action)) {
			doAdminExport(request, response);
		} else if ("chart".equals(action)) {
			doAdminChart(request, response);
		}
	}

	private void doAdminChart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserService userService = UserService.getInstance();
		List<IpAddress> list = userService.getIpList();
		request.setAttribute("iplist", list);
		request.getRequestDispatcher("/Admin/chart.jsp").forward(request,
				response);

	}

	private void doAdminUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	private void doAdminExport(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession se = request.getSession();

		User user = (User) se.getAttribute("listUser");
		UserService userService = UserService.getInstance();
		int rowCount = userService.getUserListRowCount(user);
		List<User> list = userService.getUserList(rowCount, 1, user);

		response.setHeader("Content-disposition", "attachment; filename="
				+ new String("用户".getBytes("GB2312"), "8859_1") + ".xls");
		response.setHeader("pragma", "no-cache");
		response.setContentType("application/msexcel");
		ServletOutputStream os = response.getOutputStream();

		if (list == null) {
			return;
		}
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			WritableSheet ws = workbook.createSheet("用户列表", 0);

			int rowNum = 0;

			WritableFont font1 = new WritableFont(WritableFont.TIMES, 16,
					WritableFont.BOLD);
			WritableCellFormat format1 = new WritableCellFormat(font1);
			Label cell = new Label(0, 0, "导出用户列表", format1);
			ws.addCell(cell);

			WritableCellFormat cellFormat2 = new WritableCellFormat();
			cellFormat2.setBorder(Border.ALL, jxl.format.BorderLineStyle.THIN);

			ws.setColumnView(7, 50);

			rowNum = 1;
			for (int i = 0; i < list.size(); i++, rowNum++) {// 写sheet
				User tmp = list.get(i);

				ws.addCell(new Label(0, rowNum, tmp.getId() + "", cellFormat2));
				ws.addCell(new Label(1, rowNum, tmp.getUsername(), cellFormat2));
				ws.addCell(new Label(2, rowNum,
						"1".equals(tmp.getRule()) ? "管理员" : "普通用户", cellFormat2));
				ws.addCell(new Label(3, rowNum, tmp.getRealname(), cellFormat2));
				ws.addCell(new Label(4, rowNum, "1".equals(tmp.getSex()) ? "男"
						: "女", cellFormat2));
				ws.addCell(new Label(5, rowNum, tmp.getCity().getCity(),
						cellFormat2));
				ws.addCell(new Label(6, rowNum, tmp.getCertType().getContent(),
						cellFormat2));
				ws.addCell(new Label(7, rowNum, tmp.getCert(), cellFormat2));
			}
			workbook.write();
			workbook.close();
		} catch (RowsExceededException e) {
			throw new RuntimeException(e);
		} catch (WriteException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		os.close();
	}

	private void doAdminList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession se = request.getSession();

		String pageSize = request.getParameter("pageSize");
		if (pageSize == null) {
			pageSize = "10";
		}

		String page = request.getParameter("page");
		if (page == null) {
			page = "1";
		}
		String source = request.getParameter("source");
		if (source != null) {
			User user = new User();
			populateList(request, user);
			se.setAttribute("listUser", user);
			se.setAttribute("pageSize", pageSize);
		}

		User user = (User) se.getAttribute("listUser");
		UserService userService = UserService.getInstance();
		List<User> list = userService.getUserList(Integer.parseInt(pageSize),
				Integer.parseInt(page), user);
		int pageCount = userService.getUserListPageCount(
				Integer.parseInt(pageSize), user);

		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<res>");
		xml.append("<pageCount>" + pageCount + "</pageCount>");
		xml.append("<list>");
		for (User u : list) {
			xml.append("<user>");
			xml.append("<id>" + u.getId() + "</id>");
			xml.append("<realname>" + u.getRealname() + "</realname>");
			xml.append("<sex>" + ("1".equals(u.getSex()) ? "男" : "女")
					+ "</sex>");
			xml.append("<certType>" + u.getCertType().getContent()
					+ "</certType>");
			xml.append("<cert>" + u.getCert() + "</cert>");
			xml.append("<userType>" + u.getUserType().getContent()
					+ "</userType>");
			xml.append("</user>");
		}

		xml.append("</list>");
		xml.append("</res>");

		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.println(xml.toString());
		out.close();
	}

	private void doAdminEdit(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	private void doAdminDelete(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("ids");
		int[] userIdList = new int[ids.length];
		for (int i = 0; i < ids.length; i++) {
			userIdList[i] = Integer.parseInt(ids[i]);
		}
		UserService userService = UserService.getInstance();
		userService.deleteUsers(userIdList);
		response.sendRedirect(request.getContextPath()
				+ "/Admin/admin?action=list");
	}

	private void doAdminAdd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = new User();
		populate(request, user);
		user.setStatus("1");
		user.setPassword(Md5Utils.md5("123456"));

		String msg = validate(user);
		if (TextUtils.isEmpty(msg)) {
			// 调用Service方法
			UserService userService = UserService.getInstance();

			// 检查用户名是否重复
			User tmp = new User();
			tmp.setUsername(user.getUsername());
			User dbUser = userService.findUser(tmp);
			if (dbUser == null) {
				userService.addUser(user);
				msg = "添加成功";
			} else {
				msg = "用户名重复";
			}
		}
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/Admin/UserInfo_Add.jsp").forward(
				request, response);
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
		} else if (TextUtils.isEmpty(user.getRealname())) {
			errorMsg = "请输入真实姓名";
		} else if (user.getCity().getCityId() == "") {
			errorMsg = "请选择所在城市";
		} else if (TextUtils.isEmpty(user.getCert())) {
			errorMsg = "请输入证件号码";
		} else if (user.getBirthday() == null) {
			errorMsg = "请输入出生日期";
		}
		return errorMsg;
	}

	private void populate(HttpServletRequest request, User user) {
		// 获取表单参数
		String username = request.getParameter("username");
		String rule = request.getParameter("rule");
		String realname = request.getParameter("realname");
		String sex = request.getParameter("sex");
		// 需要修改前台代码
		String cityId = request.getParameter("city");
		String certTypeId = request.getParameter("certType");
		String cert = request.getParameter("cert");
		String birthday = request.getParameter("birthday");
		String userTypeId = request.getParameter("userType");
		String content = request.getParameter("content");

		user.setUsername(username);
		user.setRule(rule);
		user.setRealname(realname);
		user.setSex(sex);

		// City
		City city = new City();
		if(cityId == null || cityId.equals("城市")){
			city.setCityId("");
		}else{
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

	private void populateList(HttpServletRequest request, User user) {
		// 获取表单参数
		String realname = request.getParameter("realname");
		String sex = request.getParameter("sex");
		String certTypeId = request.getParameter("certType");
		String cert = request.getParameter("cert");
		String userTypeId = request.getParameter("userType");

		user.setRealname(realname);
		user.setSex(sex);
		// CertType
		CertType certType = new CertType();
		certType.setId(Integer.parseInt(certTypeId));
		user.setCertType(certType);

		// cert
		user.setCert(cert);

		// UserType
		UserType userType = new UserType();
		userType.setId(Integer.parseInt(userTypeId));
		user.setUserType(userType);
	}

}
