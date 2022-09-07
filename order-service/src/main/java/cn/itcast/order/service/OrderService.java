package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);

        // 2. 准备url
        String url = "http://localhost:8081/user/" + order.getUserId();
        // 2.1 发送http请求, 后端不存在跨域问题, 跨域仅仅是浏览器的同源策略
        // Restful发送请求时只需要知道对方的ip:port和请求的参数格式, 而与技术无关
        User user = restTemplate.getForObject(url, User.class);

        // 3. 设置值
        order.setUser(user);

        // 4.返回
        return order;
    }
}
