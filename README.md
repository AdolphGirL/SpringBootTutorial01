#### bean
- 綁定讀取Properties資料
    - 使用註解@ConfigurationProperties，綁定properties資料；其需要在容器中才會有效，因此需要讓spring boot啟動後掃描到所以加上@Component。prefix = "cpbean" 需要讀取yml檔內的前墜詞，不可大寫。
    - 支持JSR303數據校驗
    - 支持複雜類型(Map List Object等等)
- 讀取非全局配置(不在application.yml中)的資料
    - @PropertySource(value = { "" })，value是數組格式，可以加載多個
    - 用法與綁定讀取Properties資料方式相同
- 透過@Value注入
    - 支持#{演算}
    - 配置文件命名需要與變數一致，不支持鬆散語法
    - 引用的地方，需要啟動後須在容器內，以此範例為例，需要加個@Component
    - 不支持JSR303數據校驗
    - 不支持複雜類型

#### 自動配置原理
- 配置文件可以配置文件參考
- 開啟EnableAutoConfiguration，再經由AutoConfigurationPackage內的Import(AutoConfigurationPackages.Registrar.class)導入package，Import(AutoConfigurationImportSelector.class)的selectImports方法導入一些組件，其方法內有SpringFactoriesLoader.loadFactoryNames讀取類路徑下的資源(掃描META-INF/spring.factories)，且該資源有org.springframework.boot.autoconfigure.EnableAutoConfiguration的設定，則自動讀取。總結，將類路徑下的META-INF/spring.factories內配置的所有EnableAutoConfiguration的值，加入到容器中，即xxAutoConfiguration的class會被加載到容器中，並且根據xxProperties自動進行自動配置。
  
#### 注入Bean
- @Configuration，表示為設定文件
- @Bean -> 取代以前的xml，方法的返回值會添加到容器中，且id為方法的名稱

#### Error處理
- 瀏覽器client: 會返回一個默認的錯誤
- postman: 會返回一個默認的json資料 
- 原理:
    <br/>
    ErrorMvcAutoConfiguration.class配置
    - 4xx or 500 <br/>
        ErrorPageCustomizer.class
        ```
        @Value("${error.path:/error}") private String path = "/error";

        BasicErrorController接收/error
			@RequestMapping("${server.error.path:${error.path:/error}}")
			public class BasicErrorController extends AbstractErrorController {
			
			BasicErrorController請求的兩種處理
			@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
			public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
				HttpStatus status = getStatus(request);
				Map<String, Object> model = Collections
						.unmodifiableMap(getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
				response.setStatus(status.value());
				ModelAndView modelAndView = resolveErrorView(request, response, status, model);		resolveErrorView(request, response, status, model)->決定返回頁面
				return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
			}
			
			若有配置ErrorViewResolver，即會到該頁面
			
			@RequestMapping
			public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
				HttpStatus status = getStatus(request);
				if (status == HttpStatus.NO_CONTENT) {
					return new ResponseEntity<>(status);
				}
				Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
				return new ResponseEntity<>(body, status);
			}
        ```
- 自行配置
  - 如何訂製錯誤頁面(狀態馬，可寫5xx.html 4xx.html)
  - error/狀態馬.html
  - 有模板引擎，可讀取資料
	    - timestamp: 	時間搓
		- status: 	狀態馬
		- error: 		錯誤提示
		- exception:	錯誤對象
		- message:	異常訊息
		- errors: 	jsr303數據較驗錯誤都在這
  - 模板引擎找不到，則到靜態資源文件下，static/error/狀態馬.html，但上述的資訊，無法讀取則使用預設
  
#### Log處理 
```
spring boot log 處理
	 - 不管底層框架用甚麼。最後都會被抽象層的slf4j-api所套用；因此，造就spring boot可以自動配合底層框架
	 - 預設使用抽象層slf4j，底層logack
	 - 特殊情況，當使用logback.xml(會被log底層框架先讀取到)當設定檔時，無法使用<springProfile name="xxx"></springProfile>標籤
	 若名稱設為logback-spring.xml，則會先交由spring boot讀取，就可以使用spring的功能
	 (範例看logback-spring.xml)
	 - 客製化設定檔的讀取會以 -spring.xml為最高優先
	 tag語法  
	 <springProfile name="staging">
		    <!-- configuration to be enabled when the "staging" profile is active -->
		</springProfile>
		
		<springProfile name="dev | staging">
		    <!-- configuration to be enabled when the "dev" or "staging" profiles are active -->
		</springProfile>
		
		<springProfile name="!production">
		    <!-- configuration to be enabled when the "production" profile is not active -->
		</springProfile>
	   
```
#### 國際化處理
```
/**
	 * 
	 * 國際化配置
	 * - 編寫國際化配置文件，放在resources下
	 * - 配置文件.properties -> 中國(簡體中文)與台灣(正體中文)的是zh-CN與zh-TW
	 *   en_us；語系+國別的命名，若無法取得語系，則會使用預設名稱空項目的
	 * - org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration，
	 *   自動配置類別，spring boot以配置；
	 *   String basename = context.getEnvironment().getProperty("spring.messages.basename", "messages");
	 *   預設可以再classpath，放置messages.propperties，即不用在做任何配置。也可以經由spring.messages.basename指定
	 *   如下範例
	 *   spring.messages.basename=i18n.login (resources下的路徑，和文件前墜名)
	 *   
	 * - 在 WebMvcAutoConfiguration.class內，預設配置的解析locale如下，也就是沒有配置localeResolver的情況下，會使用下方配置
	 *   
	 *  	@Bean
			@ConditionalOnMissingBean
			@ConditionalOnProperty(prefix = "spring.mvc", name = "locale")
			public LocaleResolver localeResolver() {
				if (this.mvcProperties.getLocaleResolver() == WebMvcProperties.LocaleResolver.FIXED) {
					return new FixedLocaleResolver(this.mvcProperties.getLocale());
				}
				AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
				localeResolver.setDefaultLocale(this.mvcProperties.getLocale());
				return localeResolver;
			}
			
			AcceptHeaderLocaleResolver.class
			預設解析是根據
			Locale defaultLocale = getDefaultLocale();
			if (defaultLocale != null && request.getHeader("Accept-Language") == null) {
				return defaultLocale;
			}
			根據請求的Accept-Language，來便是語系和國別
			
		- 可以自行implements LocaleResolver，產生Bean，來取代原先的配置，如目前專案的範例
		  components/SelfLocaleResolver
		 	如果方法名不為localeResolver
		 	則須設定bean name為@Bean(name = "localeResolver")
	 * 
	 */
```

#### spring mvc配置
```
/**
	 * spring boot 自動配置 spring mvc (org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.class)
	 * - 自動配置ViewResolve，可以自訂視圖解析器，寫到容器中，spring boot啟動時，會載入
	 * - 自動註冊 Converter、GenericConverter、Formatter beans
	 *   處理前端請求的轉換
	 * - Support HttpMessageConverters，spring mvc用來轉換請求和回應
	 *   this.messageConvertersProvider.ifAvailable((customConverters) -> converters.addAll(customConverters.getConverters()));
	 * 
	 * - 拓展spring mvc，可以繼承WebMvcConfigurerAdapter.class
	 * - 若配置檔設定，@EnableWebMvc。。則原先spring boot web mvc 自動配置則會取消，包含css等都會取消
	 *   盡量不要使用，使用表示，要全面接管
	 * 
	 */
```

#### 靜態資源映射
```
/**
	 * web jar與映射資源規則 WebMvcAutoConfiguration，spring webmvc的自動配置 
	 * - addResourceHandlers添加資源映射
	 *  	if (!registry.hasMappingForPattern("/webjars/**")) {
	 * 				customizeResourceHandlerRegistration(registry.addResourceHandler("/webjars/**")
	 * 					.addResourceLocations("classpath:/META-INF/resources/webjars/")
	 * 					.setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
	 * 		}
	 * 		
	 * 		讀取的路徑/META-INF/resources/webjars/，使用jar的方式載入前端資源
	 * 		因此映射路徑/webjars/xxxx會對應/META-INF/resources/webjars/xxx
	 * 		
	 * - 自訂義的引入
	 *   ResourceProperties.class
	 *   ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)，在properties可以設定與資源相關的參數
	 *   
	 *   ResourceProperties不設定預設為以下路徑
	 *   private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
	 *   			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };
	 *   
	 *   訪問時
	 *   String staticPathPattern = this.mvcProperties.getStaticPathPattern(); -> 
	 *   staticPathPattern = "/**";
	 *   因此。當/** 來訪問時，若沒有被處理則會到ResourceProperties設定的路徑尋找
	 *   
	 *   
	 *   
	 * - 歡迎頁
	 *   WebMvcAutoConfiguration.class內
	 *   public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext,
	 *   	FormattingConversionService mvcConversionService, ResourceUrlProvider mvcResourceUrlProvider) {
	 *   }
	 *   
	 *   當/** 來訪問時，會找index.html，放置位置也同static resource
	 *   
	 * - 圖標
	 *   WebMvcAutoConfiguration.class內(讀取規則)
	 *   favicon.ico，放置位置也同static resource
	 *   
	 * - static resource，可以交由spring.resources設定
	 *   spring.resources-static-locations=classpath:/xxx/, classpath:/xxx/, ....
	 */
	
	/**
	 * <!-- (當純href不加/，則會根據上下文；加/，則會變成url從/(不管有無context-path)開始) -->
	 */
```

#### Thymeleaf
```
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
```

#### WEB映射(非全面接管)
```
/**
 * 簡單映射務需要再使用到controller
 * 
 * 
 * @EnableWebMvc -> 若開啟，則會全面接管Spring MVC
 * 請使用WebMvcConfigurerAdapter(@Deprecated在2.x之後)拓展Spring MVC
 * 改 implemetns WebMvcConfigurer(WebMvcConfigurerAdapter實作的對象) interface
 * 或者 extends WebMvcConfigurationSupport來實現
 * 但請留意，如果使用WebMvcConfigurationSupport，則其他靜態資源也要映射，有點等同於啟用了EnableWebMvc
 * 
 */
```

#### 攔截器
- 可以在 WebMvcMappingConfig implements WebMvcConfigurer中定義
```
/**
	 * 增加攔截器，防止未登入者可以查看其餘頁面
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginHandlerInterceptor())
//				所有請求路徑
				.addPathPatterns("/**")
//				spring boot已自動做好靜射資源，所以不需特別排除
//				.excludePathPatterns("/", "/login", "/webjars/**", "/user/auth");
				.excludePathPatterns("/", "/login", "/user/auth");
	}
```
```
/**
 * 攔截器，防止未登入者可以查看其餘頁面
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
	
	private final static Logger logger = LoggerFactory.getLogger(LoginHandlerInterceptor.class);
	
	public LoginHandlerInterceptor(){
		logger.info("[LoginHandlerInterceptor]-[加載]-[LoginHandlerInterceptor攔截器]");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Object user = request.getSession().getAttribute("loginUser");
		if(user != null){
			return true;
		}
		
//		為登入者，導到登入頁面
		request.setAttribute("loginMsg", "請先登入");
		request.getRequestDispatcher("/login").forward(request, response);
		
		return false;
	}
	
}
```

#### 多重yml定義
```
# 主配置文件，以application-{profile}.properties/yml
# -{profile}，來區隔；其中profile可以填上任意識別字母
# 在沒指定的情況下，默認會讀取application.properties/yml
# 指定方式spring.profiles.active=dev
# yml有個特別方式，在同一份文件內可以配置多種情況，使用---區隔開即可
# spring boot文件加載路徑，在resources下
# - file:/config/         寫法等同於classpath寫法，但是是相對於專案結構非classpath；由上往下，若上方有配置的內容，下方有出現，則以上方為主
# - file:/
# - classpath:/config/
# - classpath:
spring:
  profiles:
    active:
    - dev
  # thymeleaf配置
  # 不配置的話，預設也為如此
  thymeleaf:
    enabled: true
    encoding: UTF-8
    mode: LEGACYHTML5
    prefix: classpath:/templates/
    suffix: .html
#    留意cache
    cache: false
  messages:
    basename: i18n.login
  mvc:
    hiddenmethod:
      filter:
        enabled: true

server:
  servlet:
    context-path: /app

# 當其餘配置模塊內容沒有的部分，會自動延續下去；端看其所在位置，如果在模組dev內，則主模組會看不到
propertiesbean:
  name: Reyes
  age: 18
  weight: 123.05
  isP: true
  bir: 2019/01/02
  maps: {k1: v1, k2: v2}
  lists:
    - A
    - B
    - C
# 行內寫法 lists: [A, B, C]
  dtl:
#  yml編碼已為utf-8，因此中字有支援
    name: 陳
    age: 4756
    
vb:
# 可以使用${}取值，如果不存在也可以指定默認值，使用:
  name: ${propertiesbean.name:aaa}
    
---

spring:
  profiles:
  - dev

server:
  port: 9090
  
---

spring:
  profiles:
  - prod

server:
  port: 8080
```
