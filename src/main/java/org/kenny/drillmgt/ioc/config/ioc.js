var ioc = {
	accountService : {
		type : "org.kenny.drillmgt.service.impl.AccountServiceImpl",
		fields : {
			dao : {
				refer : 'dao'
			}
		}
	},
	accountModule : {
		type : "org.kenny.drillmgt.web.AccountModule",
		fields : {
			accountService : {
				refer : 'accountService'
			}
		}
	}
};
