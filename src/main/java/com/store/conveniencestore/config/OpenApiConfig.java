package com.store.conveniencestore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI 接口文档配置。
 */
@Configuration
public class OpenApiConfig {

    /**
     * 配置 Swagger 页面展示的项目基本信息。
     */
    @Bean
    public OpenAPI convenienceStoreOpenApi() {

        Info info = new Info()
                .title("便利店进销存管理系统 API")
                .description(
                        "商品、分类、供应商、采购、销售和库存接口文档"
                )
                .version("1.0.0");

        return new OpenAPI().info(info);
    }
}