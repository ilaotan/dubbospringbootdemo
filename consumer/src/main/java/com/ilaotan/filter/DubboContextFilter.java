package com.ilaotan.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * 目的: 作为消费者远程调用服务时,提供自己的身份信息application
 * 调用方使用 RpcContext.getContext().getAttachment("application") 拿到信息
 * 注意本类要配合在META-INF/dubbo/com.alibaba.dubbo.rpc.Filter中添加内容
 *  dubboContextFilter=com.ilaotan.filter.DubboContextFilter
 *
 * @author tan
 */
@Activate(group = Constants.CONSUMER, order = -10000)
public class DubboContextFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String application = RpcContext.getContext().getUrl().getParameter("application");
        RpcContext.getContext().setAttachment("application", application);
        return invoker.invoke(invocation);
    }
}