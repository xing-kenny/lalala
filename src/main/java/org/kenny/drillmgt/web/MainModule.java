package org.kenny.drillmgt.web;

import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

@Modules(value = { AccountModule.class }, scanPackage = true)
// @IocBy(type = JsonIocProvider.class, args = {
// "org/kenny/drillmgt/ioc/config/dao.js",
// "org/kenny/drillmgt/ioc/config/ioc.js" })
@IocBy(type = ComboIocProvider.class, args = {
		"*org.nutz.ioc.loader.json.JsonLoader",
		"org/kenny/drillmgt/ioc/config/dao.js",
		"*org.nutz.ioc.loader.json.JsonLoader",
		"org/kenny/drillmgt/ioc/config/ioc.js",
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
		"org.kenny.drillmgt" })
public class MainModule {

	@At({ "/", "/logout" })
	@Ok("jsp:views.login")
	public String doIdx() {
		return "Hello Every One!";
	}


}
