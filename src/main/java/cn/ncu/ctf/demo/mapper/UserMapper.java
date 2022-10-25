package cn.ncu.ctf.demo.mapper;

import cn.ncu.ctf.demo.entities.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: jinge
 * @Date: 2022/10/25 21:23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
