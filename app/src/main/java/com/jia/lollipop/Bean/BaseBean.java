package com.jia.lollipop.Bean;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：Aldrich
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class BaseBean<T> implements Serializable{
    public int code;
    public String msg;
    public T data;
}