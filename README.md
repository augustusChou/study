# study
框架学习旅程

study-core 拥有简单的依赖注入和注解扩展（通过实现接口来被框架引入）
study-vertx-mvc vertx的辅助模块 现在只要继承AbstractImplVerticle 就可以让框架介入
引入很简单，加入jar(study-vertx-mvc)包后 将继承AbstractVerticle的改为继承AbstractImplVerticle
其他不变 注册路由就使用注解：
@Register
@Api(path = "student")
public class StudentController {

    private Map<Integer, Student> studentMap = new HashMap<>();

    private static final String successResult=JSON.toJSONString(JsonResult.SUCESS);
    private static final String failResult=JSON.toJSONString(JsonResult.FAIL());

    @Get
    public void get(RoutingContext routingContext) {
        List<Student> resultList= new ArrayList<>(studentMap.values());
        String jsonResult=JSON.toJSONString(resultList);
        routingContext.response().end(jsonResult);
    }

    @Post
    public void post(RoutingContext routingContext) {
        Student student = Json.decodeValue(routingContext.getBodyAsString(), Student.class);
        if (student != null) {
            studentMap.put(studentMap.size() + 1, student);
            routingContext.response().end(successResult);
        } else {
            routingContext.response().end(failResult);
        }
    }

    @Put(path = ":id")
    public void put(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id != null) {
            Student student = Json.decodeValue(routingContext.getBodyAsString(), Student.class);
            if (student != null) {
                studentMap.put(Integer.valueOf(id), student);
                routingContext.response().end(successResult);
            }
        }
        routingContext.response().end(failResult);
    }

    @Delete(path = ":id")
    public void delete(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id != null) {
            studentMap.remove(Integer.parseInt(id));
            routingContext.response().end(successResult);
        } else {
            routingContext.response().end(failResult);
        }
    }
}
