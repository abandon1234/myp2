package com.nageoffer.onethread.core.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 配置解析器处理器
 */
public final class ConfigParserHandler {

    private static final List<ConfigParser> PARSERS = new ArrayList<>();

    private ConfigParserHandler() {
        PARSERS.add(new YamlConfigParser());//将 YAML 文档解析成 Java 中的 Map<Object, Object> 结构
        PARSERS.add(new PropertiesConfigParser());//将 Properties 文档解析成 Java 中的 Map<Object, Object> 结构
    }

    public Map<Object, Object> parseConfig(String content, ConfigFileTypeEnum type) throws IOException {
        for (ConfigParser parser : PARSERS) {
            if (parser.supports(type)) {
                return parser.doParse(content);
            }
        }
        return Collections.emptyMap();
    }

    public static ConfigParserHandler getInstance() {
        return ConfigParserHandlerHolder.INSTANCE;
    }

    private static class ConfigParserHandlerHolder {

        private static final ConfigParserHandler INSTANCE = new ConfigParserHandler();
    }
}
