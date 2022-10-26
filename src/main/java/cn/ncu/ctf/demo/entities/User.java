package cn.ncu.ctf.demo.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: jinge
 * @Date: 2022/10/25 21:18
 */

@Data
@TableName("user")
public class User implements Serializable {

    @TableId
    private Long id_user;

    private String username;

    private String password;

    private String email;

    private String headImage;

    private Long scores;

    private String address;

    private String phone;

    private LocalDateTime updateTime;

}
