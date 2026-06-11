package com.bankofamerica.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TestDataManager {

    private static final Logger logger = LogManager.getLogger(TestDataManager.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static TestDataManager instance;
    private JsonNode data;

    private TestDataManager() { load(); }

    public static synchronized TestDataManager getInstance() {
        if (instance == null) instance = new TestDataManager();
        return instance;
    }

    private void load() {
        try (InputStream s = getClass().getClassLoader().getResourceAsStream("testdata/login-testdata.json")) {
            if (s != null) { data = mapper.readTree(s); logger.info("Test data loaded"); }
            else { logger.warn("Test data file not found"); data = mapper.createObjectNode(); }
        } catch (Exception e) { logger.error("Error loading test data: {}", e.getMessage()); data = mapper.createObjectNode(); }
    }

    public String[][] getValidCredentials() {
        List<String[]> list = new ArrayList<>();
        for (JsonNode u : data.path("validUsers"))
            list.add(new String[]{ u.path("username").asText(), u.path("password").asText() });
        return list.toArray(new String[0][]);
    }

    public String[] getFirstValidCredentials() {
        String[][] c = getValidCredentials();
        return c.length > 0 ? c[0] : new String[]{"testuser001", "Test@Password1"};
    }

    public Object[][] getInvalidCredentials() {
        List<Object[]> list = new ArrayList<>();
        for (JsonNode n : data.path("invalidCredentials"))
            list.add(new Object[]{ n.path("username").asText(), n.path("password").asText(), n.path("expectedStatus").asInt(401) });
        return list.toArray(new Object[0][]);
    }

    public String[] getLockedCredentials() {
        JsonNode locked = data.path("lockedCredentials");
        if (locked.size() > 0) return new String[]{ locked.get(0).path("username").asText(), locked.get(0).path("password").asText() };
        return new String[]{"lockeduser001", "Test@Password1"};
    }

    public List<String> getSQLInjectionPayloads() {
        List<String> list = new ArrayList<>();
        data.path("sqlInjectionPayloads").forEach(n -> list.add(n.asText()));
        return list;
    }

    public List<String> getXSSPayloads() {
        List<String> list = new ArrayList<>();
        data.path("xssPayloads").forEach(n -> list.add(n.asText()));
        return list;
    }
}
