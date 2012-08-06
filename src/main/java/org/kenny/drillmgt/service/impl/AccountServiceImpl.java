package org.kenny.drillmgt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kenny.drillmgt.domain.Account;
import org.kenny.drillmgt.domain.Role;
import org.kenny.drillmgt.util.PageModel;
import org.kenny.drillmgt.util.SystemContext;
import org.kenny.drillmgt.util.Util;
import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.service.NameEntityService;


public class AccountServiceImpl extends NameEntityService<Account> {

	private static final Log log = Logs.getLog(AccountServiceImpl.class);

	public Account doLogin(String loginName, String password) throws Exception {
		Account a = this.fetch(Cnd.where("loginName", "=", loginName));
		if (null == a) {
			throw new Exception("loginName error£¡");
		}

		if (!password.equals(a.getPassword())) {
			throw new Exception("password error£¡");
		}

		dao().fetchLinks(a, "dept");
		dao().fetchLinks(a, "roles");
		List<Role> temp = new ArrayList<Role>();
		if (null != a.getRoles()) {
			for (Role r : a.getRoles()) {
				r = dao().fetchLinks(r, "permissions");
				temp.add(r);
			}
			a.setRoles(temp);
		}
		return a;
	}

	public List<Account> getAllAccounts() {
		return this.query(null, null);
	}

	public PageModel<Account> getAllAccounts(int currentPage) {

		if (currentPage == 0) {
			currentPage = 1;
		}
		Pager pager = dao().createPager(currentPage, SystemContext.PAGE_SIZE);
		List<Account> users = dao().query(Account.class,
				Cnd.orderBy().asc("loginName"), pager);
		int cnt = dao().count(Account.class);
		int maxPage = Util.maxPageSize(cnt, SystemContext.PAGE_SIZE);
		PageModel<Account> pm = new PageModel<Account>(users, maxPage);
		return pm;
	}

	public void addAccount(Account account) {
		dao().insert(account);
	}

	public void deleteAccountById(int id) {
		dao().delete(Account.class, id);
	}

	public void updateAccount(Account account) {
		dao().update(account);
	}

}
