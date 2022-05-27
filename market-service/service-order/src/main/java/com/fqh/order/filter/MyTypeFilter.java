package com.fqh.order.filter;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/18 22:51:02
 * 用到spring注解驱动开发的自定义扫描过滤器
 */
public class MyTypeFilter implements TypeFilter {

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        String className = classMetadata.getClassName();
        if (className.equals("com.fqh.lizgoods.client.UCenterServiceClient")) {
            System.out.println("成功排除lizgoods里面的ucenter客户端");
            return false;
        }
        System.out.println("不排除: " + className);
        return false;
    }
}
