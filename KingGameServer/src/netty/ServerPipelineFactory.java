package netty;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public class ServerPipelineFactory implements ChannelPipelineFactory {
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		
		pipeline.addLast("decoder", new MsgDecoder());
		pipeline.addLast("encoder", new MsgEncoder());
//		HashedWheelTimer timer = new  HashedWheelTimer();
//		pipeline.addLast("timeout", new IdleStateHandler(timer, 20, 20, 0));//心跳包 监听器
//		pipeline.addLast("hearbeat", new HeartBeatHandler());//心跳包
		pipeline.addLast("handler", new MessageHandler());
		
		return pipeline;
	}
}