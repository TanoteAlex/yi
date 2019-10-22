package com.yi.base.helper;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.Properties;

/**
 * Properties属性文件加载工具类，将所有加载异常转换成RuntimeException
 */
public class PropertiesHelper {
    private PropertiesHelper() {
    }

    /**
     * 加载指定资源文件内容为Properties对象
     *
     * @param resource
     * @return
     */
    public static Properties load(Resource resource) {
        try {
            InputStream in = resource.getInputStream();
            return load(in);
        } catch (IOException ex) {
            throw new RuntimeException("Properties文件加载失败.", ex);
        }
    }

    /**
     * 加载指定文件内容为Properties对象
     *
     * @param file
     * @return
     */
    public static Properties load(File file) {
        try {
            InputStream in = new FileInputStream(file);
            return load(in);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("找不到需要加载的Properties文件", ex);
        }
    }

    /**
     * 加载指定输入流内容为Properties对象
     *
     * @param in
     * @return
     */
    public static Properties load(InputStream in) {
        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException ex) {
            throw new RuntimeException("Properties文件加载失败.", ex);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return props;
    }

    /**
     * 按XML格式加载指定输入流内容为Properties对象
     *
     * @param in
     * @return
     */
    public static Properties loadFromXML(InputStream in) {
        Properties props = new Properties();
        try {
            props.loadFromXML(in);
        } catch (IOException ex) {
            throw new RuntimeException("Properties XML文件加载失败.", ex);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return props;
    }
}
