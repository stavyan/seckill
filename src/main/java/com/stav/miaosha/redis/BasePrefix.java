package com.stav.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix {

    private String prefix;

    private int expireSeconds;

    public BasePrefix (int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public BasePrefix (String prefix) {
        this(0, prefix);
    }

    @Override
    public int expireSeconds() { // 默认0 代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
