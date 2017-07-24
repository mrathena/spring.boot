package com.mrathena.spring.boot.shiro;

import java.io.Serializable;

import org.apache.shiro.util.SimpleByteSource;

// 解决:java.io.NotSerializableException:org.apache.shiro.util.SimpleByteSource
public class MySimpleByteSource extends SimpleByteSource implements Serializable {
	
	private static final long serialVersionUID = 184659076398758824L;

	public MySimpleByteSource(String string) {
		super(string);
	}

}