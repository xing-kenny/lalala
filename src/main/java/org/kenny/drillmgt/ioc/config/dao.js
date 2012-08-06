var ioc = {
		/*
		dataSource : {
			type : 'org.h2.jdbcx.JdbcConnectionPool',
			events : { depose : 'dispose' },
     		args : [ "jdbc:h2:file:~/.h2/lala;AUTO_SERVER=TRUE", "sa", "" ] }
    }
    */
		dataSource:{
			type:"org.apache.commons.dbcp.BasicDataSource",
			events:{
				depose:"close"
			},
			fields:{
				driverClassName:"com.mysql.jdbc.Driver",
				url:"jdbc:mysql://127.0.0.1:3306/lala?useUnicode=true&characterEncoding=utf-8",
				username:"root",
				password:"root"
			}
		},	
	dao : {
		type : 'org.nutz.dao.impl.NutDao',
		args : [{refer:'dataSource'}]		
	}
};