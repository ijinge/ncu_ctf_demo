package cn.ncu.ctf.demo.service.impl;

import cn.ncu.ctf.demo.entities.Manager;
import cn.ncu.ctf.demo.mapper.ManagerMapper;
import cn.ncu.ctf.demo.service.ManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: jinge
 * @Date: 2022/10/26 23:08
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {
}
