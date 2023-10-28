package com.juzi.design.pattern.observer.recommend;

import com.juzi.design.pattern.state.recommend.OrderState;
import com.juzi.design.pattern.state.recommend.OrderStateChangeAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.data.redis.RedisStateMachinePersister;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;

import java.util.EnumSet;

/**
 * 订单状态机配置（Spring）
 *
 * @author codejuzi
 */
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderState, OrderStateChangeAction> {

    /**
     * 状态机初始化操作
     */
    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderStateChangeAction> states) throws Exception {
        states.withStates()
                .initial(OrderState.ORDER_WAIT_PAY) // 订单创建成功后初始状态为 ORDER_WAIT_PAY
                .states(EnumSet.allOf(OrderState.class)); // 将OrderState中的所有状态加载到状态机中
    }

    /**
     * 状态转换操作
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderStateChangeAction> transitions) throws Exception {
        transitions
                // source -- event --> target
                // ORDER_WAIT_PAY -- PAY --> ORDER_WAIT_SEND
                .withExternal()
                .source(OrderState.ORDER_WAIT_PAY)
                .target(OrderState.ORDER_WAIT_SEND)
                .event(OrderStateChangeAction.PAY)
                .and()
                // ORDER_WAIT_SEND -- SEND --> ORDER_WAIT_RECEIVE
                .withExternal()
                .source(OrderState.ORDER_WAIT_SEND)
                .target(OrderState.ORDER_WAIT_RECEIVE)
                .event(OrderStateChangeAction.SEND)
                .and()
                // ORDER_WAIT_RECEIVE -- RECEIVE --> ORDER_FINISH
                .withExternal()
                .source(OrderState.ORDER_WAIT_RECEIVE)
                .target(OrderState.ORDER_FINISH)
                .event(OrderStateChangeAction.RECEIVE);
    }

    @Bean(name = "stateMachineRedisPersister")
    public RedisStateMachinePersister<OrderState, OrderStateChangeAction> getRedisPersister(
            RedisConnectionFactory redisConnectionFactory) {
        RedisStateMachineContextRepository<OrderState, OrderStateChangeAction> repository =
                new RedisStateMachineContextRepository<>(redisConnectionFactory);
        RepositoryStateMachinePersist<OrderState, OrderStateChangeAction> persister
                = new RepositoryStateMachinePersist<>(repository);
        return new RedisStateMachinePersister<>(persister);
    }
}
