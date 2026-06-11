package com.bankofamerica.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private Properties properties;

    private static final String DEFAULT_CONFIG = "config/config.properties";
    private static final String ENV_CONFIG_TEMPLATE = "config/config-%s.properties";

    private ConfigManager() {
        loadProperties();
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) instance = new ConfigManager();
        return instance;
    }

    private void loadProperties() {
        properties = new Properties();
        try (InputStream s = getClass().getClassLoader().getResourceAsStream(DEFAULT_CONFIG)) {
            if (s != null) { properties.load(s); logger.info("Loaded config from {}", DEFAULT_CONFIG); }
            else logger.warn("Config not found: {}", DEFAULT_CONFIG);
        } catch (IOException e) { logger.error("Error loading config: {}", e.getMessage()); }

        String env = System.getProperty("env");
        if (env != null && !env.isEmpty()) {
            String envPath = String.format(ENV_CONFIG_TEMPLATE, env);
            try (InputStream s = getClass().getClassLoader().getResourceAsStream(envPath)) {
                if (s != null) {
                    Properties ep = new Properties(); ep.load(s);
                    properties.putAll(ep);
                    logger.info("Loaded env config from {}", envPath);
                } else logger.warn("Env config not found: {}", envPath);
            } catch (IOException e) { logger.error("Error loading env config: {}", e.getMessage()); }
        }
    }

    public String getProperty(String key) {
        String sys = System.getProperty(key);
        return (sys != null && !sys.isEmpty()) ? sys : properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        String v = getProperty(key);
        return v != null ? v : defaultValue;
    }

    public String getBaseUrl() { return getProperty("base.url", "https://secure.bankofamerica.com"); }
    public String getAuthEndpoint() { return getProperty("auth.endpoint", "/auth/login"); }
    public String getTokenEndpoint() { return getProperty("token.endpoint", "/auth/token"); }

    public int getTimeoutSeconds() {
        try { return Integer.parseInt(getProperty("timeout.seconds", "30")); }
        catch (NumberFormatException e) { return 30; }
    }

    public int getMaxRetryCount() {
        try { return Integer.parseInt(getProperty("max.retry.count", "3")); }
        catch (NumberFormatException e) { return 3; }
    }

    public String getLogLevel() { return getProperty("log.level", "INFO"); }
}
