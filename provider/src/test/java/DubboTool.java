import com.alibaba.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

public class DubboTool {

    private static GenericService genericService;

    private static ReferenceConfig<GenericService> reference;

    /**
     * @param localUrl  本地dubbo地址+端口  端口来源于dubbo.protocol.port
     * @param interfaceName  dubbo接口路径 比如 com.ilaotan.interfaces.IDemoService
     */
    public DubboTool(String localUrl, String interfaceName) {

        RegistryConfig config = new RegistryConfig();
        config.setGroup("dubbo");
        // 直连本地 可以去掉这个address
//        config.setAddress("nacos://nacos-cs:8848?namespace=dev2");
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-tool");
        applicationConfig.setRegistry(config);

        reference = new ReferenceConfig<>();
        String group = "";
        String version = "1.0.0";
        String intf = interfaceName;
        reference.setGeneric(true);
        reference.setApplication(applicationConfig);
        reference.setInterface(intf);
        reference.setVersion(version);
        reference.setGroup(group);
        reference.setUrl(localUrl);
        //Keep it consistent with the ConfigManager cache
        reference.setSticky(false);

        try{
            genericService = reference.get();
        }catch (Exception e) {
            // 摊牌了 我不装了.
            throw e;
        }

        // 确保执行完毕后 销毁.  或者主动销毁
        Runtime.getRuntime().addShutdownHook(new Thread("DubboCommonShutdownHook") {
            @Override
            public void run() {
                reference.destroy();
            }
        });

    }

    public Object run(String method, String[] parameterTypes, Object[] args) {
        return genericService.$invoke(method, parameterTypes, args);
    }

    /**
     * 确保执行完毕后 销毁. 或者主动销毁
     */
    public void destory() {
        reference.destroy();
    }

}
