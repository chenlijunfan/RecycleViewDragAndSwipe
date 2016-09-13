package com.recycleviewdragandswipe.app.recycleviewdragandswipe.info;

/**
 * Created by Administrator on 2016/9/10.
 */
public class UserInfo {

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 是否被选中
     */
    private boolean isCheck;

    public UserInfo(){
    }

    public UserInfo(boolean isCheck, String name, String sex) {
        this.isCheck = isCheck;
        this.name = name;
        this.sex = sex;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
