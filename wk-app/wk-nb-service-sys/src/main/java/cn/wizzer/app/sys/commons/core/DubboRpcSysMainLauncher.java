package cn.wizzer.app.sys.commons.core;

import cn.wizzer.app.sys.modules.models.*;
import org.nutz.boot.NbApp;
import org.nutz.dao.Chain;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Mirror;
import org.nutz.lang.Times;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.Modules;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by wizzer on 2018/3/16.
 */
@IocBean(create = "init", depose = "depose")
@Modules(packages = "cn.wizzer")
public class DubboRpcSysMainLauncher {
    private static final Log log = Logs.get();

    @Inject
    private Dao dao;

    public static void main(String[] args) throws Exception {
        NbApp nb = new NbApp().setArgs(args).setPrintProcDoc(true);
        nb.getAppContext().setMainPackage("cn.wizzer");
        nb.run();
    }

    public void init() {
        //通过POJO类创建表结构
        try {
            Daos.createTablesInPackage(dao, "cn.wizzer.app.sys", false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        try {
            if (log.isDebugEnabled()) {
                //通过POJO类修改表结构
                Daos.migration(dao, "cn.wizzer.app.sys", true, false);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        // 若必要的数据表不存在，则初始化数据库
        if (0 == dao.count(Sys_user.class)) {
            //初始化配置表
            Sys_config conf = new Sys_config();
            conf.setConfigKey("AppName");
            conf.setConfigValue("综合管理平台");
            conf.setNote("系统名称");
            dao.insert(conf);
            conf = new Sys_config();
            conf.setConfigKey("AppShrotName");
            conf.setConfigValue("综合管理平台");
            conf.setNote("系统短名称");
            dao.insert(conf);
            conf = new Sys_config();
            conf.setConfigKey("AppDomain");
            conf.setConfigValue("http://127.0.0.1");
            conf.setNote("系统域名");
            dao.insert(conf);
            conf = new Sys_config();
            conf.setConfigKey("AppUploadPath");
            conf.setConfigValue("D://files/upload");
            conf.setNote("文件上传文件夹");
            dao.insert(conf);
            conf = new Sys_config();
            conf.setConfigKey("AppUploadBase");
            conf.setConfigValue("/upload");
            conf.setNote("文件访问路径");
            dao.insert(conf);
            //初始化单位
            Sys_unit unit = new Sys_unit();
            unit.setPath("0001");
            unit.setName("系统管理");
            unit.setAliasName("System");
            unit.setLocation(0);
            unit.setAddress("沈阳奥体万达");
            unit.setEmail("261960571@qq.com");
            unit.setTelephone("");
            unit.setHasChildren(false);
            unit.setParentId("");
            unit.setWebsite("https://wizzer.cn");
            Sys_unit dbunit = dao.insert(unit);
            //初始化菜单
            List<Sys_menu> menuList = new ArrayList<Sys_menu>();
            Sys_menu menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001");
            menu.setName("系统");
            menu.setNote("系统");
            menu.setAliasName("System");
            menu.setIcon("");
            menu.setLocation(0);
            menu.setHref("");
            menu.setTarget("");
            menu.setIsShow(true);
            menu.setHasChildren(true);
            menu.setParentId("");
            menu.setType("menu");
            menu.setPermission("sys");
            Sys_menu m0 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("00010001");
            menu.setName("系统管理");
            menu.setNote("系统管理");
            menu.setAliasName("Manager");
            menu.setIcon("ti-settings");
            menu.setLocation(0);
            menu.setHref("");
            menu.setTarget("");
            menu.setIsShow(true);
            menu.setHasChildren(true);
            menu.setParentId(m0.getId());
            menu.setType("menu");
            menu.setPermission("sys.manager");
            Sys_menu m1 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("000100010001");
            menu.setName("单位管理");
            menu.setAliasName("Unit");
            menu.setLocation(0);
            menu.setHref("/platform/sys/unit");
            menu.setTarget("data-pjax");
            menu.setIsShow(true);
            menu.setPermission("sys.manager.unit");
            menu.setParentId(m1.getId());
            menu.setType("menu");
            Sys_menu m2 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100010001");
            menu.setName("添加单位");
            menu.setAliasName("Add");
            menu.setLocation(0);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.unit.add");
            menu.setParentId(m2.getId());
            menu.setType("data");
            Sys_menu m21 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100010002");
            menu.setName("修改单位");
            menu.setAliasName("Edit");
            menu.setLocation(0);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.unit.edit");
            menu.setParentId(m2.getId());
            menu.setType("data");
            Sys_menu m22 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100010003");
            menu.setName("删除单位");
            menu.setAliasName("Delete");
            menu.setLocation(0);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.unit.delete");
            menu.setParentId(m2.getId());
            menu.setType("data");
            Sys_menu m23 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("000100010002");
            menu.setName("用户管理");
            menu.setAliasName("User");
            menu.setLocation(0);
            menu.setHref("/platform/sys/user");
            menu.setTarget("data-pjax");
            menu.setIsShow(true);
            menu.setPermission("sys.manager.user");
            menu.setHasChildren(false);
            menu.setParentId(m1.getId());
            menu.setType("menu");
            Sys_menu m3 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100020001");
            menu.setName("添加用户");
            menu.setAliasName("Add");
            menu.setLocation(0);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.user.add");
            menu.setParentId(m3.getId());
            menu.setType("data");
            Sys_menu m31 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100020002");
            menu.setName("修改用户");
            menu.setAliasName("Edit");
            menu.setLocation(1);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.user.edit");
            menu.setParentId(m3.getId());
            menu.setType("data");
            Sys_menu m32 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100020003");
            menu.setName("删除用户");
            menu.setAliasName("Delete");
            menu.setLocation(2);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.user.delete");
            menu.setParentId(m3.getId());
            menu.setType("data");
            Sys_menu m33 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("000100010003");
            menu.setName("角色管理");
            menu.setAliasName("Role");
            menu.setLocation(0);
            menu.setHref("/platform/sys/role");
            menu.setIsShow(true);
            menu.setPermission("sys.manager.role");
            menu.setTarget("data-pjax");
            menu.setParentId(m1.getId());
            menu.setType("menu");
            Sys_menu m4 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100030001");
            menu.setName("添加角色");
            menu.setAliasName("Add");
            menu.setLocation(1);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.role.add");
            menu.setParentId(m4.getId());
            menu.setType("data");
            Sys_menu m41 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100030002");
            menu.setName("修改角色");
            menu.setAliasName("Edit");
            menu.setLocation(2);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.role.edit");
            menu.setParentId(m4.getId());
            menu.setType("data");
            Sys_menu m42 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100030003");
            menu.setName("删除角色");
            menu.setAliasName("Delete");
            menu.setLocation(3);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.role.delete");
            menu.setParentId(m4.getId());
            menu.setType("data");
            Sys_menu m43 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100030004");
            menu.setName("分配菜单");
            menu.setAliasName("SetMenu");
            menu.setLocation(4);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.role.menu");
            menu.setParentId(m4.getId());
            menu.setType("data");
            Sys_menu m44 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100030005");
            menu.setName("分配用户");
            menu.setAliasName("SetUser");
            menu.setLocation(5);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.role.user");
            menu.setParentId(m4.getId());
            menu.setType("data");
            Sys_menu m45 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("000100010004");
            menu.setName("菜单管理");
            menu.setAliasName("Menu");
            menu.setLocation(0);
            menu.setHref("/platform/sys/menu");
            menu.setTarget("data-pjax");
            menu.setIsShow(true);
            menu.setPermission("sys.manager.menu");
            menu.setParentId(m1.getId());
            menu.setType("menu");
            Sys_menu m5 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100040001");
            menu.setName("添加菜单");
            menu.setAliasName("Add");
            menu.setLocation(1);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.menu.add");
            menu.setParentId(m5.getId());
            menu.setType("data");
            Sys_menu m51 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100040002");
            menu.setName("修改菜单");
            menu.setAliasName("Edit");
            menu.setLocation(2);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.menu.edit");
            menu.setParentId(m5.getId());
            menu.setType("data");
            Sys_menu m52 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100040003");
            menu.setName("删除菜单");
            menu.setAliasName("Delete");
            menu.setLocation(3);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.menu.delete");
            menu.setParentId(m5.getId());
            menu.setType("data");
            Sys_menu m53 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("000100010005");
            menu.setName("系统参数");
            menu.setAliasName("Param");
            menu.setLocation(0);
            menu.setHref("/platform/sys/conf");
            menu.setTarget("data-pjax");
            menu.setIsShow(true);
            menu.setPermission("sys.manager.conf");
            menu.setParentId(m1.getId());
            menu.setType("menu");
            Sys_menu m6 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100050001");
            menu.setName("添加参数");
            menu.setAliasName("Add");
            menu.setLocation(1);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.conf.add");
            menu.setParentId(m6.getId());
            menu.setType("data");
            Sys_menu m61 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100050002");
            menu.setName("修改参数");
            menu.setAliasName("Edit");
            menu.setLocation(2);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.conf.edit");
            menu.setParentId(m6.getId());
            menu.setType("data");
            Sys_menu m62 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100050003");
            menu.setName("删除参数");
            menu.setAliasName("Delete");
            menu.setLocation(3);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.conf.delete");
            menu.setParentId(m6.getId());
            menu.setType("data");
            Sys_menu m63 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("000100010006");
            menu.setName("日志管理");
            menu.setAliasName("Log");
            menu.setLocation(0);
            menu.setHref("/platform/sys/log");
            menu.setTarget("data-pjax");
            menu.setIsShow(true);
            menu.setPermission("sys.manager.log");
            menu.setParentId(m1.getId());
            menu.setType("menu");
            Sys_menu m7 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100060001");
            menu.setName("清除日志");
            menu.setAliasName("Delete");
            menu.setLocation(1);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.log.delete");
            menu.setParentId(m7.getId());
            menu.setType("data");
            Sys_menu m71 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("000100010007");
            menu.setName("定时任务");
            menu.setAliasName("Task");
            menu.setLocation(0);
            menu.setHref("/platform/sys/task");
            menu.setTarget("data-pjax");
            menu.setIsShow(true);
            menu.setPermission("sys.manager.task");
            menu.setParentId(m1.getId());
            menu.setType("menu");
            Sys_menu m8 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100070001");
            menu.setName("添加任务");
            menu.setAliasName("Add");
            menu.setLocation(1);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.task.add");
            menu.setParentId(m8.getId());
            menu.setType("data");
            Sys_menu m81 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100070002");
            menu.setName("修改任务");
            menu.setAliasName("Edit");
            menu.setLocation(2);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.task.edit");
            menu.setParentId(m8.getId());
            menu.setType("data");
            Sys_menu m82 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100070003");
            menu.setName("删除任务");
            menu.setAliasName("Delete");
            menu.setLocation(3);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.task.delete");
            menu.setParentId(m8.getId());
            menu.setType("data");
            Sys_menu m83 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("000100010008");
            menu.setName("自定义路由");
            menu.setAliasName("Route");
            menu.setLocation(0);
            menu.setHref("/platform/sys/route");
            menu.setTarget("data-pjax");
            menu.setIsShow(true);
            menu.setPermission("sys.manager.route");
            menu.setParentId(m1.getId());
            menu.setType("menu");
            Sys_menu m9 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100080001");
            menu.setName("添加路由");
            menu.setAliasName("Add");
            menu.setLocation(1);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.route.add");
            menu.setParentId(m9.getId());
            menu.setType("data");
            Sys_menu m91 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100080002");
            menu.setName("修改路由");
            menu.setAliasName("Edit");
            menu.setLocation(2);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.route.edit");
            menu.setParentId(m9.getId());
            menu.setType("data");
            Sys_menu m92 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000100080003");
            menu.setName("删除路由");
            menu.setAliasName("Delete");
            menu.setLocation(3);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.route.delete");
            menu.setParentId(m9.getId());
            menu.setType("data");
            Sys_menu m93 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setParentId(m0.getId());
            menu.setDisabled(false);
            menu.setPath("00010002");
            menu.setName("系统配置");
            menu.setAliasName("Config");
            menu.setType("menu");
            menu.setLocation(0);
            menu.setIcon("ti-pencil-alt");
            menu.setIsShow(true);
            menu.setPermission("sys.config");
            menu.setHasChildren(true);
            Sys_menu pp1 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("000100020001");
            menu.setName("数据字典");
            menu.setAliasName("Dict");
            menu.setLocation(0);
            menu.setHref("/platform/sys/dict");
            menu.setTarget("data-pjax");
            menu.setIsShow(true);
            menu.setPermission("sys.manager.dict");
            menu.setParentId(pp1.getId());
            menu.setType("menu");
            Sys_menu d = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000200010001");
            menu.setName("添加字典");
            menu.setAliasName("Add");
            menu.setLocation(1);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.dict.add");
            menu.setParentId(d.getId());
            menu.setType("data");
            Sys_menu d1 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000200010002");
            menu.setName("修改字典");
            menu.setAliasName("Edit");
            menu.setLocation(2);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.dict.edit");
            menu.setParentId(d.getId());
            menu.setType("data");
            Sys_menu d2 = dao.insert(menu);
            menu = new Sys_menu();
            menu.setDisabled(false);
            menu.setPath("0001000200010003");
            menu.setName("删除字典");
            menu.setAliasName("Delete");
            menu.setLocation(3);
            menu.setIsShow(false);
            menu.setPermission("sys.manager.dict.delete");
            menu.setParentId(d.getId());
            menu.setType("data");
            Sys_menu d3 = dao.insert(menu);
            //初始化角色
            Sys_role role = new Sys_role();
            role.setName("公共角色");
            role.setCode("public");
            role.setAliasName("Public");
            role.setNote("All user has role");
            role.setUnitid("");
            role.setDisabled(false);
            dao.insert(role);
            role = new Sys_role();
            role.setName("系统管理员");
            role.setCode("sysadmin");
            role.setAliasName("Sysadmin");
            role.setNote("System Admin");
            role.setUnitid("");
            role.setMenus(menuList);
            role.setDisabled(false);
            Sys_role dbrole = dao.insert(role);
            //初始化用户
            Sys_user user = new Sys_user();
            user.setLoginname("superadmin");
            user.setUsername("超级管理员");
            user.setOpAt(Times.getTS());
            //String slat=R.UU32();
            //new Sha256Hash("1",ByteSource.Util.bytes(s), 1024).toHex();
            user.setSalt("r5tdr01s7uglfokpsdmtu15602");
            user.setPassword("1bba9287ebc50b766bff84273d11ccefaa7a8da95d078960f05f116e9d970fb0");
            user.setLoginIp("127.0.0.1");
            user.setLoginAt(0L);
            user.setLoginCount(0);
            user.setEmail("wizzer@qq.com");
            user.setLoginTheme("palette.css");
            user.setLoginBoxed(false);
            user.setLoginScroll(false);
            user.setLoginSidebar(false);
            user.setLoginPjax(true);
            user.setUnitid(dbunit.getId());
            Sys_user dbuser = dao.insert(user);
            //不同的插入数据方式(安全)
            dao.insert("sys_user_unit", Chain.make("userId", dbuser.getId()).add("unitId", dbunit.getId()));
            dao.insert("sys_user_role", Chain.make("userId", dbuser.getId()).add("roleId", dbrole.getId()));
            //执行SQL脚本
            FileSqlManager fm = new FileSqlManager("db/");
            List<Sql> sqlList = fm.createCombo(fm.keys());
            Sql[] sqls = sqlList.toArray(new Sql[sqlList.size()]);
            for (Sql sql : sqls) {
                dao.execute(sql);
            }
            //菜单关联到角色
            dao.execute(Sqls.create("INSERT INTO sys_role_menu(roleId,menuId) SELECT @roleId,id FROM sys_menu").setParam("roleId", dbrole.getId()));
            //初始化自定义路由
            Sys_route route = new Sys_route();
            route.setDisabled(false);
            route.setUrl("/sysadmin");
            route.setToUrl("/platform/login");
            route.setType("hide");
            dao.insert(route);
        }
    }

    public void depose() {
        // 非mysql数据库,或多webapp共享mysql驱动的话,以下语句删掉
        try {
            Mirror.me(Class.forName("com.mysql.jdbc.AbandonedConnectionCleanupThread")).invoke(null, "shutdown");
        } catch (Throwable e) {
        }
        // 解决com.alibaba.druid.proxy.DruidDriver和com.mysql.jdbc.Driver在reload时报warning的问题
        // 多webapp共享mysql驱动的话,以下语句删掉
        Enumeration<Driver> en = DriverManager.getDrivers();
        while (en.hasMoreElements()) {
            try {
                Driver driver = en.nextElement();
                String className = driver.getClass().getName();
                log.debug("deregisterDriver: " + className);
                DriverManager.deregisterDriver(driver);
            } catch (Exception e) {
            }
        }
    }
}
