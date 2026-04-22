package com.lilac.agent;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ReAct代理抽象类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ReActAgent extends BaseAgent {

    // 思考
    public abstract boolean think();

    // 行动
    public abstract String act();

    /**
     * 步骤
     */
    @Override
    public String step() {
        try {
            boolean shouldAct = think();
            if (!shouldAct) {
                return "思考完成 - 无需行动";
            }
            return act();
        } catch (Exception e) {
            e.printStackTrace();
            return "步骤执行失败: " + e.getMessage();
        }
    }
}