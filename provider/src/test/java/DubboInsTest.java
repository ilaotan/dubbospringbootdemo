
public class DubboInsTest {


    public static void main(String[] args) {

        DubboTool dubboTool = new DubboTool(22222, "com.ilaotan.interfaces.IDemoService");
//        String[] strArray={"java.lang.String"};
//        Object[] argsArray={"sdfsdfsd"};
        Object res1 = dubboTool.run("sayHello", new String[]{"java.lang.String"}, new Object[]{"1111"});
        Object res2 = dubboTool.run("sayHello", new String[]{"java.lang.String"}, new Object[]{"2222"});
        Object res3 = dubboTool.run("sayHello", new String[]{"java.lang.String"}, new Object[]{"3333"});

        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);

        dubboTool.destory();

    }


}
