package ifmo.dma.apigateway.redisConfig


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter


//TODO delete all
@Configuration
class MessageListenersConfiguration {
    private fun wrapInAdapter(messageListener: MessageListener):MessageListenerAdapter{
        val messageListenerAdapter = MessageListenerAdapter(messageListener)
        messageListenerAdapter.afterPropertiesSet()
        return messageListenerAdapter
    }

}