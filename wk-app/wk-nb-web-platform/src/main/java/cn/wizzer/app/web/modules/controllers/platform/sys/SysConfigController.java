package cn.wizzer.app.web.modules.controllers.platform.sys;

import cn.wizzer.framework.base.Result;
import cn.wizzer.app.web.commons.slog.annotation.SLog;
import cn.wizzer.app.web.commons.utils.StringUtil;
import cn.wizzer.framework.page.datatable.DataTableColumn;
import cn.wizzer.framework.page.datatable.DataTableOrder;
import cn.wizzer.app.sys.modules.models.Sys_config;
import cn.wizzer.app.sys.modules.services.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import org.nutz.dao.Cnd;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@IocBean
@At("/platform/sys/config")
public class SysConfigController{
    private static final Log log = Logs.get();
    @Inject
    @Reference
    private SysConfigService sysConfigService;

    @At("")
    @Ok("beetl:/platform/sys/config/index.html")
    @RequiresPermissions("platform.sys.config")
    public void index() {
    }

    @At("/data")
    @Ok("json:full")
    @RequiresPermissions("platform.sys.config")
    public Object data(@Param("length") int length, @Param("start") int start, @Param("draw") int draw, @Param("::order") List<DataTableOrder> order, @Param("::columns") List<DataTableColumn> columns) {
		Cnd cnd = Cnd.NEW();
    	return sysConfigService.data(length, start, draw, order, columns, cnd, null);
    }

    @At("/add")
    @Ok("beetl:/platform/sys/config/add.html")
    @RequiresPermissions("platform.sys.config")
    public void add() {

    }

    @At("/addDo")
    @Ok("json")
    @RequiresPermissions("platform.sys.config.add")
    @SLog(tag = "Sys_config", msg = "${args[0].id}")
    public Object addDo(@Param("..")Sys_config sysConfig, HttpServletRequest req) {
		try {
			sysConfigService.insert(sysConfig);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
    }

    @At("/edit/?")
    @Ok("beetl:/platform/sys/config/edit.html")
    @RequiresPermissions("platform.sys.config")
    public void edit(String id,HttpServletRequest req) {
		req.setAttribute("obj", sysConfigService.fetch(id));
    }

    @At("/editDo")
    @Ok("json")
    @RequiresPermissions("platform.sys.config.edit")
    @SLog(tag = "Sys_config", msg = "${args[0].id}")
    public Object editDo(@Param("..")Sys_config sysConfig, HttpServletRequest req) {
		try {
            sysConfig.setOpBy(StringUtil.getPlatformUid());
			sysConfig.setOpAt(Times.getTS());
			sysConfigService.updateIgnoreNull(sysConfig);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
    }

    @At({"/delete/?", "/delete"})
    @Ok("json")
    @RequiresPermissions("platform.sys.config.delete")
    @SLog(tag = "Sys_config", msg = "${req.getAttribute('id')}")
    public Object delete(String id, @Param("ids")  String[] ids, HttpServletRequest req) {
		try {
			if(ids!=null&&ids.length>0){
				sysConfigService.delete(ids);
    			req.setAttribute("id", org.apache.shiro.util.StringUtils.toString(ids));
			}else{
				sysConfigService.delete(id);
    			req.setAttribute("id", id);
			}
            return Result.success("system.success");
        } catch (Exception e) {
            return Result.error("system.error");
        }
    }

    @At("/detail/?")
    @Ok("beetl:/platform/sys/config/detail.html")
    @RequiresPermissions("platform.sys.config")
	public void detail(String id, HttpServletRequest req) {
		if (!Strings.isBlank(id)) {
            req.setAttribute("obj", sysConfigService.fetch(id));
		}else{
            req.setAttribute("obj", null);
        }
    }

}
