package cn.tcc.foundation.core;

import javax.servlet.ServletException;

public interface XApplicationInitializer {

    void onStartup() throws ServletException;
}
