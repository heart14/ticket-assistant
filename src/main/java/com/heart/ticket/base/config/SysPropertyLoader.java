package com.heart.ticket.base.config;


import com.heart.ticket.base.exceptions.SysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

/**
 * About:
 * Other:
 * Created: wfli on 2022/9/27 16:48.
 * Editored:
 */
public class SysPropertyLoader {

    private static final Logger log = LoggerFactory.getLogger(SysPropertyLoader.class);
    private static final String PROPERTIES_FILE_NAME = "system.properties";
    private static final String PERSISTENCE_PATH = "cache/persistence/";
    private static SysPropertyLoader instance;
    private static File persistence;
    private Properties properties;

    /**
     * 构造方法私有化，防止私自构造对象
     */
    private SysPropertyLoader() {
        log.info("SysPropertyLoader constructor execute...");
        // 自定义构造对象的方法
        init();
    }

    /**
     * 获取SysPropertyLoader实例静态方法
     *
     * @return
     */
    public static SysPropertyLoader getInstance() {
        log.info("SysPropertyLoader getInstance execute...");
        // 懒汉模式构造单例实例
        if (instance == null) {
            instance = new SysPropertyLoader();
        }
        return instance;
    }

    /**
     * 自定义实例初始化方法，加载配置文件到Properties实例
     */
    private void init() {
        log.info("SysPropertyLoader init execute...");
        // 判断是否已加载配置文件
        if (this.properties == null) {
            URL resource = getClassLoader().getResource(PROPERTIES_FILE_NAME);
            if (resource == null) {
                log.error("load system properties fail");
                // 配置文件加载失败直接返回
                return;
            }
            try {
                InputStream inputStream = resource.openStream();
                this.properties = new Properties();
                // 将配置文件加载到Properties实例
                this.properties.load(inputStream);
            } catch (IOException e) {
                log.error("load system properties fail!");
            }
        }
    }

    /**
     * 获取类加载器
     *
     * @return
     */
    private ClassLoader getClassLoader() {
        log.info("SysPropertyLoader getClassLoader execute...");
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 根据配置文件中的key获取value
     *
     * @param pKey
     * @return
     */
    public String getSysProperty(String pKey) {
        if (this.properties == null) {
            return getInnerSysProperty(pKey);
        }
        // 获取value值
        String pValue = this.properties.getProperty(pKey);
        if (pValue == null) {
            return getInnerSysProperty(pKey);
        }
        return pValue;
    }


    /**
     * 获取jvm中的系统属性
     *
     * @param pKey
     * @return
     */
    private String getInnerSysProperty(String pKey) {
        return System.getProperty(pKey);
    }


    /**
     * 将加载完成的Properties实例临时数据持久化存储
     *
     * @throws SysException
     */
    public void storeProperties() throws SysException {
        FileOutputStream os = null;
        if (persistence == null) {
            persistence = new File(PERSISTENCE_PATH);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("system.properties Properties Persistence.");
        try {
            os = new FileOutputStream(persistence);
            this.properties.store(os, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 加载Properties
     *
     * @param forceReload 是否强制重新加载
     * @throws SysException
     */
    public void loadProperties(boolean forceReload) throws SysException {
        // Properties实例Null或者强制重新加载
        if (this.properties == null || forceReload) {
            InputStream is = null;
            try {
                // 持久化文件不存在则从配置文件加载
                if (persistence == null) {
                    URL resource = getClassLoader().getResource(PROPERTIES_FILE_NAME);
                    if (resource == null) {
                        log.error("load system properties fail");
                        // 配置文件加载失败直接返回
                        return;
                    }
                    is = resource.openStream();
                } else {
                    // 持久化文件存在则从持久化文件加载
                    is = new FileInputStream(persistence);
                }
                this.properties = new Properties();
                if (is != null) {
                    // 加载Properties实例
                    this.properties.load(is);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 重新加载Properties
     */
    public void reloadProperties() {
        // 强制重新加载Properties
        loadProperties(Boolean.TRUE);
    }

    /**
     * 获取配置文件keySet
     *
     * @return
     */
    public Set<Object> sysPropertyKeySet() {
        return this.properties.keySet();
    }

    /**
     * 获取jvm中的系统属性keySet
     *
     * @return
     */
    public Set<Object> innerSysPropertyKeySet() {
        return System.getProperties().keySet();
    }
}
