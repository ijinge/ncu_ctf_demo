package cn.ncu.ctf.demo.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jinge
 * @Date: 2022/10/26 22:53
 */
@Data
@TableName("manager")
public class Manager implements Serializable {
    @TableId
    private Long id_manager;
    private String username;
    private String password;
    private String email;
    private String headImage;

    //权限等级 1~4 超级管理员，用户管理员，题库管理员，社区管理员
    private int level;
    private String phone;
}
