package org.kenny.drillmgt.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kenny.drillmgt.domain.Account;
import org.kenny.drillmgt.domain.Dept;
import org.kenny.drillmgt.service.impl.AccountServiceImpl;
import org.kenny.drillmgt.util.PageModel;
import org.nutz.dao.Cnd;
import org.nutz.dao.DaoException;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/account")
public class AccountModule {

	private AccountServiceImpl accountService;
	private static final Log log = Logs.getLog(AccountModule.class);

	@At("/login")
	@Ok("jsp:views.main")
	@Fail("jsp:views.login")
	public Account doLogin(@Param("loginName") String loginName,
			@Param("password") String password, HttpServletRequest req)
			throws Exception {

		log.debug("loginName = " + loginName);
		log.debug("password = " + password);
		Account a;
		try {
			a = accountService.doLogin(loginName, password);
		} catch (Exception e) {
			req.setAttribute("error", e.toString());
			throw new Exception(" ");
		}
		log.debug(a);
		req.getSession().setAttribute("me", a);
		return a;
	}

	@At({ "", "/list" })
	@Ok("jsp:views.accounts")
	public void list(@Param("currentPage") int currentPage,
			HttpServletRequest req) {

		PageModel<Account> pm = accountService.getAllAccounts(currentPage);
		req.setAttribute("pm", pm);
	}

	@At("/create")
	@Ok("jsp:views.accountForm")
	public void create(HttpServletRequest req) {

		List<Dept> depts = accountService.dao().query(Dept.class,
				Cnd.orderBy().asc("sname"));
		req.setAttribute("depts", depts);
	}

	@At("/update/?")
	@Ok("jsp:views.accountForm")
	public void update(int id, HttpServletRequest req) {

		Account a = accountService.dao().fetch(Account.class, id);
		List<Dept> depts = accountService.dao().query(Dept.class, null);

		req.setAttribute("account", a);
		req.setAttribute("depts", depts);
	}

	@At("/save")
	@Ok("redirect:/account")
	@Fail("jsp:views.accountForm")
	public void save(@Param("::acc.") Account acc, HttpServletRequest req)
			throws Exception {

		try {
			if (0 == acc.getId()) {
				accountService.addAccount(acc);
			} else {
				accountService.updateAccount(acc);
			}
		} catch (DaoException e) {
			log.debug("e.toString() = " + e.toString());
			req.setAttribute("error", "Duplicated names!");
			List<Dept> depts = accountService.dao().query(Dept.class, null);

			req.setAttribute("account", acc);
			req.setAttribute("depts", depts);
			throw new Exception(" ");
		}
	}

	@At("/delete/?")
	@Ok("redirect:/account")
	public void delete(int id) {
		accountService.deleteAccountById(id);
	}

	@At("/delByIds")
	@Ok("redirect:/account")
	public void delByIds(@Param("ids") String ids) {

		for (String id : ids.split(",")) {
			accountService.deleteAccountById(Integer.valueOf(id));
		}
	}

}
