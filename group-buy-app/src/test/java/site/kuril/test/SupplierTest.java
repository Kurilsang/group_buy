package site.kuril.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Supplier;

/**
 * Supplier函数式编程测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SupplierTest {

    @Test
    public void test_Supplier(){
        // 创建一个 Supplier 实例，返回一个字符串
        Supplier<String> stringSupplier = () -> "Hello, XFG!";
        // 使用 get() 方法获取 Supplier 提供的值
        String result = stringSupplier.get();
        // 输出结果
        System.out.println(result);
        log.info("字符串结果: {}", result);
        
        // 另一个示例，使用 Supplier 提供当前时间
        Supplier<Long> currentTimeSupplier = System::currentTimeMillis;
        // 获取当前时间
        Long currentTime = currentTimeSupplier.get();
        // 输出当前时间
        System.out.println("Current time in milliseconds: " + currentTime);
        log.info("当前时间戳: {}", currentTime);
    }
} 