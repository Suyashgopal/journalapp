//package com.springproj.journalApp.config;
//
//import com.mongodb.internal.connection.Server;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static java.awt.SystemColor.info;
//
//@Configuration
//public class swaggerconfig {
//    @Bean
//    public OpenAPI mycoustomconfig(){
//        return new OpenAPI().info(
//                new Info().title("Journal app apis")
//                        .description("by suyash")
//        ).servers(Arrays.asList((new Server().url("http://localhost:8081").description("local"),
//                new Server().url("http://localhost:8082").description("live")));
//
//                ;
//    }
//
//}
