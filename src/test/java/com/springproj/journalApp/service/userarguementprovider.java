//package com.springproj.journalApp.service;
//
//import com.springproj.journalApp.entity.user;
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.ArgumentsProvider;
//import org.junit.jupiter.params.provider.ArgumentsSource;
//
//import java.lang.annotation.Annotation;
//import java.util.stream.Stream;
//
//public class userarguementprovider  implements ArgumentsProvider {
//
//    @Override
//    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
//        return Stream.of( user.builder().username());
//    }
//}
