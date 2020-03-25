package com.reyes.tutorial.config;

public class ThymeleafBase {
	
	
	/**
	 * spring boot
	 * - 建議使用模板引擎Thymeleaf
	 * - 預設不支持jsp
	 * - 
	 * - <thymeleaf.version>3.0.11.RELEASE</thymeleaf.version> 3.0以上的thymeleaf的模板主程序，配佈局layout 2.x以上
	 * - <thymeleaf-layout-dialect.version>2.4.1</thymeleaf-layout-dialect.version>
	 * - <thymeleaf-extras-springsecurity.version>3.0.4.RELEASE</thymeleaf-extras-springsecurity.version> thymeleaf與spring security
	 * - thymeleaf-spring5；spring 與 thymeleaf的整合
	 * 
	 * - 可由spring boot autoconfiguration 查看 thymeleaf的自動配置(ThymeleafAutoConfiguration.class)
	 * - ThymeleafProperties.class基本設定
	 *   private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;
	 *   public static final String DEFAULT_PREFIX = "classpath:/templates/";
	 *   public static final String DEFAULT_SUFFIX = ".html";
	 *   
	 * - thymeleaf基本語法。可查詢官方文件Standard Expression Syntax
	 * 
	 * - 標準變量表達式
	 *    - ${xx} -> 支持OGNL，
	 *      - 可以取值、也可以調用方法、可以調用內置基本對象，如下
	 *      - #ctx: the context object(當前上下文對象)
	 *      - #vars: the context variable(當前上下文變數)
	 *      - #locale: the context locale(當前上下文國家區域) - #locale : direct access to the java.util.Locale associated with current request.
	 *      - #session: 如果在web環境，HttpSession Object
	 *      - #request: 如果在web環境，HttpServletRequest Object
	 *      - #response: 如果在web環境，HttpServletResponse Object
	 *      - #servletcontext: 如果在web環境，ServletContext Object
	 *      
	 *      Web context namespaces for request/session attributes, etc.When using Thymeleaf in a web environment,
	 *      we can use a series of shortcuts for accessing request parameters, session attributes and application attributes:
	 *        param : for retrieving request parameters
	 *           ${param.foo}
	 *           ${param.size()}
	 *           ${param.isEmpty()}
	 *           ${param.containsKey('foo')} ...
	 *           
	 *           ${#request.getAttribute('foo')}
	 *        session : for retrieving session attributes
	 *           ${session.foo} ...
	 *           
	 *           ${#session.getAttribute('foo')}，不用shortcut
	 *        application : for retrieving application/servlet context attributes
	 *           ${application.foo} ...
	 *           {#servletContext.getAttribute('foo')}
	 *      - 內置工具對象
	 *      
	 *        
	 *    - *{xx} -> 選擇表達式 Not only can variable expressions be written as ${...}, but also as *{...}
	 *               The result of an expression using the th:object attribute
	 *    - #{xx} -> Messages，As we already know, #{...} message expressions allow us to link this:
	 *    - @{xx} -> Link URLs <a href="details.html" th:href="@{/order/details(orderId=${o.id})}">view</a>
	 *               <a href="details.html" th:href="@{/order/{orderId}/details(orderId=${o.id})}">view</a>
	 *    - ~{xx} -> Fragments: <div th:insert="~{commons :: main}">...</div>
	 *    
	 *    Expressions between [[...]] or [(...)] are considered inlined expressions in Thymeleaf, 
	 *    and inside them we can use any kind of expression that would also be valid in a th:text or th:utext attribute.
	 *    
	 *    抽取
	 *    <div th:fragment="copy">
	 *       xxx
	 *    </div>
	 *    
	 *    引入(footer.html)
	 *    ~{模板名::片段名}
	 *    模板名，會根據spring boot配置的thymeleaf規則，前墜和後墜名組合
	 *    <div th:insert="~{footer::copy}"></div>	insert插入		--> 在div內插入引入的片段
	 *    <div th:replace="~{footer::copy}"></div>	replace取代		--> 會將div替換為引入的片段
	 *    <div th:include="~{footer::copy}"></div>	include取代		--> 在div標籤內，引入，片段包含的內容
	 *    
	 *    ~{模板名::選擇器}
	 *    ~{xxx::#yyy}		#->id的意思
	 *    
	 *    模板參數傳遞
	 *    - 引入時，傳遞參數
	 *    - 也可以在宣告模板的時候傳遞
	 *    	宣告
	 *    	<div class="alert alert-dismissable" th:fragment="alert (type, message)" th:assert="${!#strings.isEmpty(type) and !#strings.isEmpty(message)}" th:classappend="'alert-' + ${type}">      
      			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
      			<span th:text="${message}">Test</span>
    		</div>
    		
    		引用
    		<div th:replace="fragments/alert :: alert (type='danger', message=${errorMessage})">...</div>
	 */

}
