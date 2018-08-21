package com.yzeng.charroom.entity;

/**
 * 用户关系关联
 * @author Yao.Zeng
 * @version  [版本号, 2018年8月21日]
 */
public class UserRelation {
	//用户
	private Integer userIdA;
	//用户
	private Integer userIdB;
	//用户之间的关系状态 1：好友  2：
	private int relationStatus;
}
