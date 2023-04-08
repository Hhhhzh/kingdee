package kd.cosmic;

import kd.cosmic.server.Launcher;

/**
 * 启动本地应用程序(微服务节点)
 */
public class Application {

    public static void main(String[] args) {
        Launcher cosmic = new Launcher();

        cosmic.setClusterNumber("cosmic");
        cosmic.setTenantNumber("ierp");
        cosmic.setServerIP("192.168.56.1");

        cosmic.setAppName("cosmic-DELL-ls8Sk7C0");
        cosmic.setWebPath("D:/kingdee-server/webapp/static-file-service");


        cosmic.setStartWithQing(false);

        cosmic.start();
    }
}