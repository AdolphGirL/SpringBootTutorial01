package com.reyes.tutorial.config;

/**
 * 自動配置原理
 * - 配置文件可以配置文件參考
 * 
 * - 開啟EnableAutoConfiguration，再經由AutoConfigurationPackage內
 *   的Import(AutoConfigurationPackages.Registrar.class)導入package
 *   Import(AutoConfigurationImportSelector.class)的selectImports方法導入一些組件
 *   其方法內有SpringFactoriesLoader.loadFactoryNames讀取類路徑下的資源(掃描META-INF/spring.factories)
 *   且該資源有org.springframework.boot.autoconfigure.EnableAutoConfiguration的設定，則自動讀取
 *   
 *   總結
 *   將類路徑下的META-INF/spring.factories內配置的所有EnableAutoConfiguration的值，加入到容器中
 *   即xxAutoConfiguration的class會被加載到容器中，並且根據xxProperties自動進行自動配置
 *   
 * - 
 */
public class AutoConfigPrinciple {

}
